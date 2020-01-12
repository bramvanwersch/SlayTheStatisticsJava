library(glmnet)
library(chemometrics)
library(class)
library(tree)
library(randomForest)

#assume this data is always present needs supplying of file name or direct data.
#data <- read.table("D:\\Eclipse STS workplace\\SlayTheStatisticsGit\\SlayTheStatistics\\data\\IRONCLAD_fullSummary.csv", sep = ",", header = TRUE)

data <- read.table(".\\data\\IRONCLAD_fullSummary.csv", sep = ",", header = TRUE)
rownames(data) <- data$run
data <- data[,2:ncol(data)]
relic_data <- data[,207:ncol(data)]
card_data <- data[,1:206]
card_data$Win <- data$Win

accuracy <- function(predictions, answers){
  sum((predictions == answers)/length(answers))
}

model_pred <- function(data, pred_col, train){
  forest <- randomForest(Win ~ . ,data = data[train,], importance = TRUE, ntree= 2000, mtry = sqrt(pred_col -1), keep.inbag =TRUE )
  predictions <- predict(forest, newdata = data[-train,])
  print(sprintf("the acccuracy of the forest model is: %s",accuracy(predictions, data[-train, pred_col])))
  return (forest$importance[,4])
}
#scale all data
scale_data <- data.frame(scale(data[1:ncol(data) -1]))
scale_data$Win <- data$Win

ntrain <-round(nrow(scale_data)*9/10)
train <- sample(1:nrow(scale_data), ntrain)
all_results <- model_pred(scale_data, ncol(scale_data), train)

#scale card data
scale_card_data <- data.frame(scale(card_data[1:ncol(card_data) -1]))
scale_card_data$Win <- data$Win

ntrain <-round(nrow(scale_card_data)*3/4)
train <- sample(1:nrow(scale_card_data), ntrain)
card_results <- model_pred(scale_card_data, ncol(scale_card_data), train)

#relic data 
logical_relic_data <- data.frame(relic_data[1:ncol(relic_data) -1] > 0)
logical_relic_data$Win <- data$Win

ntrain <-round(nrow(logical_relic_data)*3/4)
train <- sample(1:nrow(logical_relic_data), ntrain)
relic_results <- model_pred(logical_relic_data, ncol(logical_relic_data), train)

predictors <- cbind(all_results, c(card_results, rep(0,127)), c(rep(0, 206), relic_results))

print("done")
write.table(predictors, file = ".\\data\\forest_gini_results.csv", sep = ",", append = FALSE, col.names = FALSE)

