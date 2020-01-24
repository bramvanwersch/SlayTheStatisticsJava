import re

class Item:
    def __init__(self, name, rarity, description):
        # these do not capture all effects but these are certain without false hits except for conditional effects.
        self.__POSITIVE_EFFECTS = ["apply","deal","gain","draw","add", "channel", "heal"]
        self.__NEGTIVE_EFFECTS = ["exhaust", "lose","remove", "discard"]
        self.__EFFECT_MODIFIERS = ["all","twice","for each","random", "shuffle","additional","increase","time","times",
                                   "double"]
        self.__CONDITIONAL_MODIFIERS = ["whenever","next","if","next","equal","choose","discarded","only","kills",
                                        "until","loses"]
        self.name = name
        self.rarity = rarity
        self.description = ""
        self.__format_description(description)
        self.__numericalEffects = {}
        self.__calculateEffects()

    def getNumericalEffect(self, key):
        """
        Retrieve a numerical effect of a certain type by a key of the effect (damage, block).
        :param key: a string representing the ket for which a value is saved
        :return: an integer that quantifies the effect of the item
        """
        return self.__numericalEffects[key]

    def getAllEffects(self):
        """
        Give all the keys saved for an item.
        :return:
        """
        return self.__numericalEffects.keys()

    def __format_description(self, description):
        description = description.lower()
        description = description.replace(",", " ").replace(")", " ").replace("(", " ").replace("\\", " ").replace("'", " ")\
                 .replace("-", " ").replace(".",". ")
        self.description = " ".join(description.split()).strip()

    def __calculateEffects(self):
        effects = self.__numberEffects()
        for effect in effects:
            if effect[0] in self.__POSITIVE_EFFECTS:
                try:
                    self.__numericalEffects[effect[2]] = int(effect[1])
                    #incass of X effects
                except ValueError:
                    self.__numericalEffects[effect[2]] = effect[1]
            elif effect[0] in self.__NEGTIVE_EFFECTS:
                self.__numericalEffects[effect[2]] = -1 * int(effect[1])
            else:
                continue

    def __numberEffects(self):
        """
        Gets the number and words around it for a card to determine what the quantifiable effect is. Invluding the x
        and x+1 effects.
        :return: an array of arrays that contains the numbers and effects for each sentence.
        """
        number_effects = []
        for line in self._descriptionSentences():
            number_indexes = [x for x in range(len(line)) if line[x].isdigit() or line[x] == "x" or line[x] == "x+1"]
            for index in number_indexes:
                # precaution dont expect this scenario
                if index - 1 < 0 and index + 1 >= len(line):
                    number_effects.append(line[index])
                elif index - 1 < 0:
                    number_effects.append([line[index], line[index + 1]])
                elif index + 1 >= len(line):
                    number_effects.append([line[index -1], line[index]])
                else:
                    number_effects.append([line[index -1], line[index], line[index + 1]])
        return number_effects

    def _descriptionSentences(self):
        """
        Splits the description into words and sentences
        :return: An array of arrays containing words in there sentences as strings.
        """
        splt_text = self.description.split(".")
        final_split = []
        for sentence in splt_text:
            words = self.descriptionWords(sentence)
            if words: final_split.append(words)
        return final_split

    def descriptionWords(self, sentence = False):
        """
        splits a sentence into words that are devided by spaces
        :param sentence: default the description and optionaly a self provided sentence
        :return: an array of strings being the individual words.
        """
        if sentence == False:
            sentence = self.description
        words = sentence.split(" ")
        words = [x for x in words if x != ""]
        return words