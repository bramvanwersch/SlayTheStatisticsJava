import re

CHARACTER_NAMES = ["Ironclad","Silent","Defect","Watcher"]

class Item:
    def __init__(self, name, rarity, description):
        self.name = name
        self.rarity = rarity
        self.description = description

    def number_effects(self):
        """
        Gets the number and associated effect for a card
        :return: an array of arrays that contains the numbers and effects for each sentence.
        """
        number_effects = []
        for line in self.item_description_as_array():
            number_indexes = [x for x in range(len(line)) if line[x].isdigit()]
            sentence_effect = []
            for index in number_indexes:
                try:
                    sentence_effect.append("{} {}".format(line[index], line[index + 1]))
                except IndexError:
                    sentence_effect.append(line[index])
            number_effects.append(sentence_effect)
        return number_effects

    def item_description_as_array(self):
        """
        Splits the description into words and sentences
        :return: An array of arrays containing words in there sentences as strings.
        """
        splt_text = self.description.split(".")
        final_split = []
        for sentence in splt_text:
            words = sentence.split(" ")
            words = [x for x in words if x != ""]
            #remove some odd characters
            words = [x.replace(",", "").replace(")", "").replace("(", "").replace("\\", "").replace("'", "").replace("-", "")for x in words]
            if words: final_split.append(words)
        return final_split

class Relic(Item):
    def __init__(self, info):
        Item.__init__(self,info[0],info[1],info[2])

    @property
    def character(self):
        if "(" in self.rarity:
            r = self.rarity
            m = re.search("\(.+\)",r)
            if m:
                #naming is inconsistent this is the best way.
                for name in CHARACTER_NAMES:
                    if name in m.group(0):
                        return name
        return "any"

class Card(Item):
    def __init__(self, info):
        Item.__init__(self,info[0], info[1],info[4])
        self.type = info[2]
        self.mana = info[3]
        self.character = info[5]

class IroncladCard(Card):
    def __init__(self, item):
        Card.__init__(self, info)

class SilentCard(Card):
    def __init__(self, item):
        Card.__init__(self, info)

class DefectCard(Card):
    def __init__(self, info):
        Card.__init__(self, info)

class WatcherCard(Card):
    def __init__(self, info):
        Card.__init__(self, info)

