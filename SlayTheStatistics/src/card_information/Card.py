import Item

class Card(Item):
    def __init__(self, info):
        Item.__init__(self,info[0], info[1],info[4])
        self.type = info[2]
        self.mana = info[3]

    @property
    def exhaust(self):
        """
        :return: a boolean telling if the card has an exhaust property
        """
        return "exhaust" in self.description_words()

    @property
    def vulnerable(self):
        """
        :return: a boolean telling if the card has an exhaust property
        """
        return "vulnerable" in self.description_words()

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


