library(RSQLite)

# Connect to SQLite database
con <- dbConnect(SQLite(), dbname = "D:\\DatabaseSoccer\\database.sqlite")

# Query data from SQLite table
data <- dbGetQuery(con, "SELECT * FROM your_table")

# Close database connection
dbDisconnect(con)

# Create boxplot to find outliers
boxplot(data$your_column, main = "Boxplot of Your Data", ylab = "Your Column Name")

# Create histogram to find the average
hist(data$your_column, main = "Histogram of Your Data", xlab = "Your Column Name", ylab = "Frequency")




