import re
import Item

class Relic(Item.Item):
    def __init__(self, info):
        Item.Item.__init__(self,info[0],info[1],info[2])
        self.__CHARACTER_NAMES = ["ironclad","silent","defect","watcher"]
        self.character = "any"

    @property
    def character(self):
        return self.__character

    @character.setter
    def character(self, value):
        """
        Sets the character to the default value 'any' or to the value in the rarity
        :param value: the default value
        """
        if "(" in self.rarity:
            r = self.rarity.lower()
            m = re.search("\(.+\)", r)
            if m:
                # naming is inconsistent this is the best way.
                for name in self.__CHARACTER_NAMES:
                    if name in m.group(0):
                        self.__character = name
                        return
        self.__character = value

