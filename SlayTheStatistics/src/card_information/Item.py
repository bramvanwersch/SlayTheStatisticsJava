import re

CHARACTER_NAMES = ["ironclad","silent","defect","watcher"]

class Item:
    def __init__(self, name, rarity, description):
        self.name = name
        self.rarity = rarity
        self.description = description.lower()

    def number_effects(self):
        """
        Gets the number and words around it for a card to determine what the quantifiable effect is.
        :return: an array of arrays that contains the numbers and effects for each sentence.
        """
        number_effects = []
        for line in self.description_sentences():
            number_indexes = [x for x in range(len(line)) if line[x].isdigit() or line[x] == "x"]
            for index in number_indexes:
                # precaution dont expect this scenario
                if index - 1 < 0 and index + 1 >= len(line):
                    number_effects.append(line[index])
                elif index - 1 < 0:
                    number_effects.append("{} {}".format(line[index], line[index + 1]))
                elif index + 1 >= len(line):
                    number_effects.append("{} {}".format(line[index -1], line[index]))
                else:
                    number_effects.append("{} {} {}".format(line[index -1], line[index], line[index + 1]))

        return number_effects

    def description_sentences(self):
        """
        Splits the description into words and sentences
        :return: An array of arrays containing words in there sentences as strings.
        """
        splt_text = self.description.split(".")
        final_split = []
        for sentence in splt_text:
            words = self.description_words(sentence)
            if words: final_split.append(words)
        return final_split

    def description_words(self, sentence = False):
        """
        splits a sentence into words that are devided by spaces
        :param sentence: default the description and optionaly a self provided sentence
        :return: an array of strings being the individual words.
        """
        if sentence == False:
            sentence = self.description
        words = sentence.split(" ")
        words = [x for x in words if x != ""]
        # remove some odd characters
        words = [x.replace(",", "").replace(")", "").replace("(", "").replace("\\", "").replace("'", "")\
                 .replace("-", "").replace(".","") for x in words]
        return words