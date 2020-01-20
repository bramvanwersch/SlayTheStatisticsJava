import Item

class Card(Item.Item):
    def __init__(self, info):
        Item.Item.__init__(self,info[0], info[1],info[4])
        self.type = info[2]
        self.mana = info[3]

    @property
    def exhaust(self):
        return "exhaust." in self.descriptionWords()

    @property
    def innate(self):
        return "ïnnate." in self.descriptionWords()

    @property
    def ethereal(self):
        return "ethereal." in self.descriptionWords()

class IroncladCard(Card):
    def __init__(self, info):
        Card.__init__(self, info)
        self.character = "Ironclad"

class SilentCard(Card):
    def __init__(self, info):
        Card.__init__(self, info)
        self.character = "Silent"

class DefectCard(Card):
    def __init__(self, info):
        Card.__init__(self, info)
        self.character = "Defect"

class WatcherCard(Card):
    def __init__(self, info):
        Card.__init__(self, info)
        self.character = "Watcher"

    @property
    def retain(self):
        return "retain" in self._descriptionWords()


"""
firs word for direction of effect
second word for number of effect
third word for the effect
if no numbers are available check for certain keywords that notify a effect
check for global modifiers like turn or something to that effect.
There are single key words that notify of a certain effect
Check for enemies on wiki and check for the effects those have.
Also check for potions.
"""