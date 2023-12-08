# Project Name:
nutritionProject

# Project Description: 
This is a personal project I created to record nutritional information using Java, JavaFX, and MySQL with NetBeans IDE 8.2 and MySQL 8.0.

# Description of Tables:
This project has three tables: food_item, foods_eaten, and daily_nutrition
1.	**food_item**: each entry in this table is a specific food. The attributes of this table are the food’s name, serving size, calories, protein, carbs, total fat, saturated fat, total sugar, added sugar, sodium, cholesterol, and fiber. Additionally, the serving size for each food item that the rest of the nutritional info was based on was included in the name. For example, a food item’s name may be “two slices of bread”. Here, the food_name is a primary key.
2.	**foods_eaten**: each entry in this table is how much a particular food was eaten on a particular day. There are four attributes of this table, which are date_eaten, food_name, serving_size, and servings. Here, there is a composite key of the date_eaten and food_name attributes. Additionally, the food_name attribute of this table is a foreign key to the food_name attribute of the food_item table.
3.	**daily_nutrition**: each entry in this table gives the total amount of each nutrient eaten on a given day. The attributes of this table are date_eaten, calories, protein, carbs, total fat, saturated fat, total sugar, added sugar, sodium, cholesterol, and fiber. The primary key for this table is date_eaten, and date_eaten is also a foreign key that references the date_eaten attribute of the table foods_eaten. 

# How this program is meant to work:
The table daily_nutrition is somewhat the ultimate purpose of this program. This program is meant to automatically calculate the user’s total nutritional intake each day based on the information they entered that day regarding what foods they ate and how much of each of those foods they ate. Every time you have eaten a food that doesn’t exist yet in the food_item table, you must first enter the nutritional info of this food before you can enter how much you ate of it that day in the foods_eaten table. Eventually, as more foods are entered into the food_item table, entering how much you ate each day becomes faster, and the program becomes more worthwhile to use. The contents of each table are displayed through a JavaFX application, which also provides the interface to insert, update, and delete information in the food_item and foods_eaten tables. However, because the information in the table daily_nutrition is meant to be calculated instead of entered, users can’t directly insert, update, or delete entries in this table. 

# Description of each Java Class:
1.	**ProjectNutrition**: This class is what is entered first when the project runs. It creates the scene sceneMenuScreen which includes two buttons which each change the scene when clicked. 
2.	**FoodItem**: This class is used by the ManageFoodItemTable class to create objects for entries to add to the food_item table. 
3.	**FoodsEaten**: This class is used by the ManageFoodsEatenTableAndViewDailyNutritionTable class to create objects for entries to add to the foods_eaten table. 
4.	**DailyNutrition**: This class is used by the ManageFoodsEatenTableAndViewDailyNutritionTable class to create objects for entries to add to the daily_nutrition table.
5.	**ManageFoodItemTable**: this creates the scene sceneManageFoodItemTable which involves inserting, deleting, updating entries from the food_item table as well as viewing the entire food_item table. 
6.	**ManageFoodsEatenTableAndViewDailyNutritionTable**: this creates the scene sceneManageFoodsEatenTableAndViewDailyNutritionTable which involves viewing both the entire foods_eaten and daily_nutrition tables, as well as inserting, updating, and deleing entries from the foods_eaten table.

# Features this project currently has:
*	A menu screen, a screen for adding and managing the nutritional info of different foods, and a screen for adding and managing what was eaten on a given day and viewing the resulting total nutritional intakes by date
*	An interface through JavaFX to perform C.R.U.D. operations on a MySQL database
*	Built in functionality of using TableViews to create my tables in JavaFX, including the ability to sort and change the width of columns.

# Features that I may like to add:
*	A screen for showing plots of different nutritional intakes over time such as the number of calories consumed.
*	An interface for filtering by dates and types of food to more easily navigate the tables. For the filters related to food, this may involve creating a new MySQL table with food_name as a primary key and a type attribute, with entries such as dairy, sandwich, fruit, etc.

# References: 
* YouTube video, “SQL Tutorial - Full Database Course for Beginners” by freeCodeCamp.org, https://youtu.be/HXV3zeQKqGY
  * This source was my first introduction to MySQL, and I consistently used notes I took from this video when working with MySQL for this project
* YouTube video, “Connect to MySQL Database from NetBeans 12.5 (2021) and Run SQL Queries” by BoostMyTool, https://youtu.be/VNoU590W750
  * This source helped me create a connection from Netbeans to my MySQL database
* YouTube video, “Java Simple Project step by step using mysql database” by Tutus Funny, https://youtu.be/F4vpJJH2PZU
  * Taught me to add a JAR file/folder to the Libraries section of my Netbeans Project
  * Taught me the code for how to connect to my MySQL database and execute queries. Specifically, code using these imports:
    * import java.sql.Connection;
    * import java.sql.DriverManager;
    * import java.sql.PreparedStatement;
    * import java.sql.ResultSet;
    * import java.sql.SQLException;
* Documentation, JavaFX Documentation Home > Using JavaFX UI Controls > 12 Table View, by Oracle, https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
  * Taught me the code for how to create a table in JavaFX, add columns to it, and create data to add to the table. This code involved the following imports:
    * import javafx.collections.ObservableList;
    * import javafx.scene.control.TableView;
    * import javafx.scene.control.TableColumn;
  * Taught me to create classes to represent a particular entry in a JavaFX table. This made me create the classes FoodItem, FoodsEaten, and DailyNutrition.
  * Taught me the code for how to update/edit my table in JavaFx by clicking the cell and typing in the change. I also made it so these changes would also update the corresponding MySQL table.
* Documentation, MySQL Connector/J Developer Guide  /  Connection Pooling with Connector/J , by MySQL, https://dev.mysql.com/doc/refman/8.0/en/create-table-foreign-keys.html
  * When I would use my application for a while, I would eventually get the error message “Data source rejected establishment of connection,  message from server: "Too many connections”. Therefore, I found this source, which taught me how to close my Connections, PreparedStatements, and ResultSets and set them to null within the try statement as well as in a finally statement in case they somehow didn’t get closed the first time.
