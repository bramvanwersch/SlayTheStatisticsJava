import Item

class Relic(Item):
    def __init__(self, info):
        Item.__init__(self,info[0],info[1],info[2])

    @property
    def character(self):
        """
        The character property for the relic class. It searches a list because of inconsistencies
        :return: a String that represents the character name.
        """
        if "(" in self.rarity:
            r = self.rarity
            m = re.search("\(.+\)",r)
            if m:
                #naming is inconsistent this is the best way.
                for name in CHARACTER_NAMES:
                    if name in m.group(0):
                        return name
        return "any"
