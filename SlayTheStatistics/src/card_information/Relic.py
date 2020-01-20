import Item

class Relic(Item.Item):
    def __init__(self, info):
        Item.Item.__init__(self,info[0],info[1],info[2])
        self.__character = "any"

    @property
    def character(self):
        return self.__character

    @character.setter
    def character(self, value):
        if "(" in self.rarity:
            r = self.rarity
            m = re.search("\(.+\)", r)
            if m:
                # naming is inconsistent this is the best way.
                for name in self.__CHARACTER_NAMES:
                    if name in m.group(0):
                        self.__character = name
        self.__character = value

