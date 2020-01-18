import re

class Item:
    def __init__(self, name, rarity, description):
        self.name = name
        self.rarity = rarity
        self.description = description

class Card(Item):
    def __init__(self, info):
        Item.__init__(self,info[0], info[1],info[4])
        self.type = info[2]
        self.mana = info[3]
        self.character = info[5]

class Relic(Item):
    def __init__(self, info):
        Item.__init__(self,info[0],info[1],info[2])

    @property
    def character():
        if "(" in self.rarity:
            r = self.rarity
            m = re.search("\(.+\)",r)
            if m:
                return m.group(1)
        return "any"