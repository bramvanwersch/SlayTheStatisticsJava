library(glmnet)
library(chemometrics)
library(class)
library(tree)
library(randomForest)

data <- read.table(".\\data\\IRONCLAD_fullSummary.csv", sep = ",", header = TRUE)




write.csv(x, ".\\data\\test.csv",quote=FALSE, row.names=FALSE)
