import urllib.request
import re
import Card
import Relic
from bs4 import BeautifulSoup

#TODO: change because jsut for tests
CHARACTER_CARD_NAMES = ["Silent","Defect", "Watcher","Ironclad"]#,"Neutral_Cards"]

def getItemInformation(run_relics = False, run_cards = False, characters = CHARACTER_CARD_NAMES):
    relics, all_cards = {},{}
    if run_relics:
        relics = getRelics()
    if run_cards:
        all_cards = {}
        for character in characters:
            all_cards[character] = getCharacterCards(character)
    return [relics, all_cards]

def getCharacterCards(name):
    """
    Get all the normal and upgraded cards for all of the characters in CHARACTER_CARD_NAMES
    :param name a String giving a character name
    :return: a dictionaries for the character provided by name
    """
    final = []
    char_dict = {}
    url = "http://slay-the-spire.wikia.com/wiki/" + name + "_Cards"
    source_code = urllib.request.urlopen(url)
    text = str(source_code.read())
    text = text.replace("\\n", "").replace("\\t","").replace("\\xc2\\xa0", " ")
    html_text = BeautifulSoup(text, "html.parser")
    #first entry is an empty match.
    html_text = html_text.find('table').find_all("tr")[1:]
    for item in html_text:
        info = item.find_all("td")
        #removing image information
        del info[1]
        #removing hyperlinks and html borders
        info = [x.get_text() for x in info]
        #getting normal and upgraded inforation. Taking only this part because sometimes empty matches at the end.
        norm_up = upgradedInfo(info[3:5])
        #replace no mana cost cards whit unplayable
        if norm_up[0][0] == "": norm_up[0] = ["Unplayable"] + [norm_up[0][1]]
        if norm_up[1][0] == "": norm_up[1] = ["Unplayable"] + [norm_up[1][1]]
        character_card_class = getattr(Card, name+"Card")
        norm_card = character_card_class(info[:3] + norm_up[0])
        char_dict[norm_card.name] = norm_card
        #add a +1 to the name to match the name of the upgraded card.
        up_card = character_card_class([info[0]+"+1"]+info[1:3] + norm_up[1])
        char_dict[up_card.name] = up_card
    return char_dict

def getRelics():
    """
    Mines the relics from the wiki page.
    :return: a dictionary of relics with names as keys and Relic objects as items.
    """
    url = "http://slay-the-spire.wikia.com/wiki/Relics"
    source_code = urllib.request.urlopen(url)
    text = str(source_code.read())
    text = text.replace("\\n", "").replace("\\t","").replace("\\xc2\\xa0", " ")
    soup = BeautifulSoup(text, "html.parser")
    soup = soup.find('table').find_all('tr')[1:]
    relic_dict = {}
    for item in soup:
        info = item.find_all("td")
        #removing image information
        del info[0]
        info = [x.get_text() for x in info]
        relic = Relic.Relic(info)
        # relic_dict[]
        relic_dict[relic.name] = relic
    return relic_dict

def upgradedInfo(mixed_text):
    """
    Function for getting the upgraded and normal version of a card. The information gives
    both in one text. This is done based on the brackets in the text. Both for the mana cost and the
    description
    :param mixed_text array of 2 strings, one being the mined mana cost and the other being the mined description.
    :returns an array of arrays containing a list of normal information and a list of upgraded information

    NOTE: this does not solve all problems eg. card(s) solved double(triple) not solved. These bracket problems
    are hard to estimate when to include what.
    """
    normal_text, upgraded_text = [],[]
    for x in range(len(mixed_text)):
        norm_val = re.findall("([0-9]+)\([0-9]+\)", mixed_text[x])
        up_val = re.findall("[0-9]+\(([0-9]+)\)", mixed_text[x])
        #if no match was found the normal value is added. There is no difference between
        #normal and upgraded.
        if len(norm_val) == 0:
            normal_text += [mixed_text[x]]
            upgraded_text += [mixed_text[x]]
        else:
            normal_text += [re.sub("([0-9]+\([0-9]+\))", "{}", mixed_text[x]).format(*norm_val)]
            upgraded_text += [re.sub("[0-9]+\([0-9]+\)", "{}", mixed_text[x]).format(*up_val)]
        #when looking at the description try to remove between brackets text to make description nicer.
        #this does not solve all problems eg. card(s) solved double(triple) not solved.
        if x == 1:
            normal_text[1] = re.sub("(\([A-z0-9\.\+ ]+?\))","",normal_text[1])
            upgraded_text[1] = upgraded_text[1].replace("(", "").replace(")"," ")
    return [normal_text, upgraded_text]

def testFucntion():
    all_cards = getItemInformation(run_relics = False, run_cards = True)[1]
    word_dict = {}
    ban_list = ["a","gain","card","deal","block","damage","apply","deal","gain","draw","add", "channel", "heal",\
                "exhaust", "lose","remove", "discard"]
    for character_cards in all_cards.keys():
        cards_dict = all_cards[character_cards]
        for key in cards_dict.keys():
            words = cards_dict[key].descriptionWords()
            for word in words:
                if word.isdigit() or word in ban_list:
                    continue
                elif word in word_dict.keys():
                    word_dict[word] += 1
                else:
                    word_dict[word] = 1
    for key in sorted(word_dict, key=lambda x: int(word_dict[x])):
        print("{} : {}".format(key, word_dict[key]))


if __name__ == "__main__":
    testFucntion()
   # getItemInformation(run_relics = False, run_cards = True)
