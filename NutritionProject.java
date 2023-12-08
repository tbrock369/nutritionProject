package nutritionproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NutritionProject extends Application 
{
    @Override
    public void start(Stage primaryStage) 
    {
        Label labelMenuScreenTitle = new Label("Menu Screen");
        Button buttonManageFoodItemTable = new Button("Manage Food Catalog");
        Button buttonManageFoodsEaten = new Button("Manage Daily Consumption");
        Button buttonManageDailyNutrition = new Button("Manage Daily Nutrition");
        
        labelMenuScreenTitle.setFont(Font.font("Britannic Bold", 40));
        
        GridPane gridpaneMenuScreen = new GridPane();
        gridpaneMenuScreen.setPadding(new Insets(10, 10, 10, 10));
        gridpaneMenuScreen.setMinSize(300, 300);
        gridpaneMenuScreen.setVgap(10);
        gridpaneMenuScreen.setHgap(10);
        
        gridpaneMenuScreen.add(labelMenuScreenTitle, 0, 0);
        gridpaneMenuScreen.add(buttonManageFoodItemTable, 0, 2);
        gridpaneMenuScreen.add(buttonManageFoodsEaten, 0, 3);
        
        Background background1 = new Background(new BackgroundFill(Color.KHAKI, CornerRadii.EMPTY, Insets.EMPTY));
        gridpaneMenuScreen.setBackground(background1);
        
        Scene sceneMenuScreen = new Scene(gridpaneMenuScreen, 6000, 1000);
        
        primaryStage.setScene(sceneMenuScreen);
        primaryStage.show();
        
        ManageFoodItemTable objManageFoodItemTable = new ManageFoodItemTable();   
        buttonManageFoodItemTable.setOnAction(e -> primaryStage.setScene(objManageFoodItemTable.createManageFoodItemTableScene(primaryStage, sceneMenuScreen)));
        
        ManageFoodsEatenTableAndViewDailyNutritionTable objManageFoodsEaten = new ManageFoodsEatenTableAndViewDailyNutritionTable();   
        buttonManageFoodsEaten.setOnAction(e -> primaryStage.setScene(objManageFoodsEaten.createManageFoodsEatenScene(primaryStage, sceneMenuScreen)));
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
