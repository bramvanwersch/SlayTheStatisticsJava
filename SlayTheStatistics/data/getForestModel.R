library(glmnet)
library(chemometrics)
library(class)
library(tree)
library(randomForest)

#assume this data is always present needs supplying of file name or direct data.
data <- read.table(".\\data\\IRONCLAD_fullSummary.csv", sep = ",", header = TRUE)
rownames(data) <- data$run
data <- data[,2:ncol(data)]

model_pred <- function(data, pred_col, train){
  forest <- randomForest(Win ~ . ,data = data[train,], importance = TRUE, ntree= 2000, mtry = 10, keep.inbag =TRUE )
  return (head(sort(forest$importance[,4], decreasing = TRUE), 10))
}

#scale card data
scale_card_data <- data.frame(scale(card_data[1:ncol(card_data) -1]))
scale_card_data$Win <- data$Win

ntrain <-round(nrow(scale_card_data)*3/4)
train <- sample(1:nrow(scale_card_data), ntrain)
print("Scale all data report:")
model_pred(scale_card_data, ncol(scale_card_data), train)


#scale card data
logical_relic_data <- data.frame(relic_data[1:ncol(relic_data) -1] > 0)
logical_relic_data$Win <- data$Win

ntrain <-round(nrow(logical_relic_data)*3/4)
train <- sample(1:nrow(logical_relic_data), ntrain)
print("Scale all data report:")
model_pred(logical_relic_data, ncol(logical_relic_data), train)

write.csv(x, ".\\data\\test.csv",quote=FALSE, row.names=FALSE)
