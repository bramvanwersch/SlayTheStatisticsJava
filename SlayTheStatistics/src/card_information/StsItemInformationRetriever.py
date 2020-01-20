import urllib.request
import re
import Card
import Relic
from bs4 import BeautifulSoup

#TODO: change because jsut for tests
CHARACTER_CARD_NAMES = ["Ironclad"]#,"Silent","Defect", "Watcher"]#,"Neutral_Cards"]

def getItemInformation(run_relics = False, run_cards = False):
    if run_relics:
        relics = getRelics()
    if run_cards:
        all_cards = {}
        for character in CHARACTER_CARD_NAMES:
            all_cards[character] = getCharacterCards(character)

def getCharacterCards(name):
    """
    Get all the normal and upgraded cards for all of the characters in CHARACTER_CARD_NAMES
    :return: a array of dictionaries for each character in the order of the characters ub CHARACTER_CARD_NAMES
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
        relic = Item.Relic(info)
        # relic_dict[]
        relic_dict[relic.name] = relic
        print(relic.character)
    return relic_dict

def upgradedInfo(mixed_text):
    """
    Function for getting the upgraded and normal version of a card. The information gives
    both in one text. based on the brackets diferentiating the two is possible at least for
    the numbers

    :returns an array of arrays containing a list of normal information and a list of upgraded information

    NOTE: does not properly seperate text for upgraded and normal cards. So descriptions of cards can be
    innacurate. That is at this moment not that important
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
        if x == 1:
            normal_text[1] = re.sub("(\([A-z0-9\.\+ ]+?\))","",normal_text[1])
            upgraded_text[1] = upgraded_text[1].replace("(", " ").replace(")"," ")
    return [normal_text, upgraded_text]

if __name__ == "__main__":
    getItemInformation(run_relics = False, run_cards = True)
