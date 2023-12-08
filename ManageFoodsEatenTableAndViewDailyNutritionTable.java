package nutritionproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ManageFoodsEatenTableAndViewDailyNutritionTable
{
    //Connection variables
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    //Overall controls/variables
    Label labelTitle = new Label("Manage Daily Consumption ");
    Button buttonMenuScreen = new Button("Menu Screen");
    HBox hboxTitleAndMenuScreenButton = new HBox();
    Label labelExplantion = new Label("**Disclaimer** Table 3 is generated from Tables 1 and 2 and cannot be edited directly.");    
    
    //Insert into Table 2 controls/variables
    Label labelInserting = new Label("Add Row to Table 2");
    Label labelEnterDateEaten = new Label("Date Eaten (YYYY-MM-DD)");
    TextField textfieldEnterDateEaten = new TextField();
    Label labelEnterFoodName = new Label("Food Name (string)");
    ArrayList <RadioButton> arraylistInsertFoodNameRadioButtons = new ArrayList <RadioButton>();
    ToggleGroup togglegroupInsertFoodNameRadioButtons = new ToggleGroup();
    VBox vboxInsertFoodNameRadioButtons = new VBox();
    ScrollPane scrollpaneInsertFoodNameRadioButtons = new ScrollPane(vboxInsertFoodNameRadioButtons);
    Label labelEnterServingSize = new Label("Serving Size = ");
    TextField textfieldEnterServingSize = new TextField();
    Label labelEnterServings = new Label("Servings Eaten (Float value xxxx.xx)");
    TextField textfieldEnterServings = new TextField();
    Button buttonAddItem = new Button("Add Item");
    
    //Delete from Table 2 controls/variables
    Label labelDeleting = new Label("Delete Row from Table 2");
    Label labelDeleteItem = new Label("Delete an entry by selecting its date and food name");
    Label labelDeleteDateEatenAndFoodName =  new Label("Date Eaten and Food Name");
    Button buttonDeleteFoodItem = new Button("Delete Item");
    ArrayList <RadioButton> arraylistDeleteDateEatenAndFoodNameRadioButtons = new ArrayList <RadioButton>();
    ToggleGroup togglegroupDeleteDateEatenAndFoodNameRadioButtons = new ToggleGroup();
    VBox vboxDeleteDateEatenAndFoodNameRadioButtons = new VBox();
    ScrollPane scrollpaneDeleteDateEatenAndFoodNameRadioButtons = new ScrollPane(vboxDeleteDateEatenAndFoodNameRadioButtons);
    
    //Update Table 2 controls/variables
    Label labelUpdating = new Label("Update Cell in Table 2");
    Label labelUpdateExplanation = new Label
    (
        "In Table 2, only the 'Servings Eaten' column can be updated. To make\n" +
        "updates to Table 2, select the cell in the table you'd like to change, make\n" + 
        "the changes you'd like, and hit enter to finalize your changes."
    );
    
    //Table 2 controls/variables
    VBox vboxTableFoodsEaten = new VBox();
    Label labelTableFoodsEatenTitle = new Label("Table 2 (foods_eaten)");
    private TableView<FoodsEaten> tableFoodsEaten = new TableView<FoodsEaten>();
    private final ObservableList<FoodsEaten> dataFoodsEaten = FXCollections.observableArrayList();
    TableColumn tableColumnDateEaten = new TableColumn("Date Eaten");
    TableColumn tableColumnFoodName = new TableColumn("Food Name");
    TableColumn tableColumnServingSize = new TableColumn("Serving Size");
    TableColumn tableColumnServings = new TableColumn("Servings Eaten");
    
    //Table 3 controls/variables
    VBox vboxTableDailyNutrition = new VBox();
    Label labelTableDailyNutritionTitle = new Label("Table 3 (daily_nutrition)");
    private TableView<DailyNutrition> tableDailyNutrition = new TableView<DailyNutrition>();
    private final ObservableList<DailyNutrition> dataDailyNutrition = FXCollections.observableArrayList();
        
    public Scene createManageFoodsEatenScene(Stage primaryStage, Scene sceneMenuScreen)
    {                
        //GridPane gridpaneAddItem      
        GridPane gridpaneAddItem = new GridPane();
        gridpaneAddItem.setPadding(new Insets(10, 10, 10, 10));
        gridpaneAddItem.setMinSize(300, 300);
        gridpaneAddItem.setVgap(10);
        gridpaneAddItem.setHgap(10);
        generateVboxInsertFoodNameRadioButtons();        
        gridpaneAddItem.add(labelInserting, 0, 0, 2, 1); 
        gridpaneAddItem.add(labelEnterDateEaten, 0, 1);
        gridpaneAddItem.add(textfieldEnterDateEaten, 1, 1);
        gridpaneAddItem.add(labelEnterFoodName, 0, 2);
        gridpaneAddItem.add(scrollpaneInsertFoodNameRadioButtons, 1, 2); 
        gridpaneAddItem.add(labelEnterServingSize, 1, 3);
        gridpaneAddItem.add(labelEnterServings, 0, 4);
        gridpaneAddItem.add(textfieldEnterServings, 1, 4);
        gridpaneAddItem.add(buttonAddItem, 0, 5);
        
        //GridPane gridpaneDeleteItem
        GridPane gridpaneDeleteItem = new GridPane();
        gridpaneDeleteItem.getChildren().clear();
        gridpaneDeleteItem.setPadding(new Insets(10, 10, 10, 10));
        gridpaneDeleteItem.setMinSize(300, 200);
        gridpaneDeleteItem.setMaxHeight(300);
        gridpaneDeleteItem.setVgap(10);
        gridpaneDeleteItem.setHgap(10);     
        generateVboxDeleteDateEatenAndFoodNameRadioButtons();
        scrollpaneDeleteDateEatenAndFoodNameRadioButtons.setMaxHeight(200);
        scrollpaneDeleteDateEatenAndFoodNameRadioButtons.setMinWidth(250);
        scrollpaneDeleteDateEatenAndFoodNameRadioButtons.setFitToHeight(true);
        scrollpaneDeleteDateEatenAndFoodNameRadioButtons.setFitToWidth(false);
        gridpaneDeleteItem.add(labelDeleting, 0, 0, 2, 1); 
        gridpaneDeleteItem.add(labelDeleteItem, 0, 1, 2, 1);
        gridpaneDeleteItem.add(labelDeleteDateEatenAndFoodName, 0, 2);
        gridpaneDeleteItem.add(scrollpaneDeleteDateEatenAndFoodNameRadioButtons, 1, 2);
        gridpaneDeleteItem.add(buttonDeleteFoodItem, 0, 3);
        
        //GridPane paneUpdateItem
        GridPane gridpaneUpdateItem = new GridPane();
        gridpaneUpdateItem.getChildren().clear();
        gridpaneUpdateItem.setPadding(new Insets(10, 10, 10, 10));
        gridpaneUpdateItem.setVgap(10);
        gridpaneUpdateItem.setHgap(10);  
        gridpaneUpdateItem.add(labelUpdating, 0, 0, 2, 1);
        gridpaneUpdateItem.add(labelUpdateExplanation, 0, 1, 1, 4);
        
        //GridPane gridpaneManageFoodsEaten
        GridPane gridpaneManageFoodsEaten = new GridPane(); 
        gridpaneManageFoodsEaten.getChildren().clear();
        gridpaneManageFoodsEaten.setPadding(new Insets(10, 10, 10, 10));
        gridpaneManageFoodsEaten.setMinSize(300, 300);
        gridpaneManageFoodsEaten.setVgap(10);
        gridpaneManageFoodsEaten.setHgap(10);      
        hboxTitleAndMenuScreenButton.getChildren().clear();
        hboxTitleAndMenuScreenButton.getChildren().addAll(labelTitle, buttonMenuScreen);
        gridpaneManageFoodsEaten.add(hboxTitleAndMenuScreenButton, 0, 0, 2, 1);
        gridpaneManageFoodsEaten.add(labelExplantion, 0, 1, 4, 1);

        gridpaneManageFoodsEaten.add(gridpaneAddItem, 0, 2, 1, 2);
        gridpaneManageFoodsEaten.add(gridpaneDeleteItem, 1, 2);
        gridpaneManageFoodsEaten.add(gridpaneUpdateItem, 1, 3);
        gridpaneManageFoodsEaten.add(vboxTableFoodsEaten, 2, 2, 4, 2);
        gridpaneManageFoodsEaten.add(vboxTableDailyNutrition, 0, 4, 4, 1);
  
        //Setting borders and backgrounds
        BorderStroke borderStroke1 = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2));       
        Border border1 = new Border(borderStroke1);  
        Background background1 = new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY));
        Background background2 = new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY));
        Background background3 = new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY));
        Background background4 = new Background(new BackgroundFill(new Color(.8, .5, 1, .9), CornerRadii.EMPTY, Insets.EMPTY)); 
        Background background5 = new Background(new BackgroundFill(new Color(1, .7, 0, .9), CornerRadii.EMPTY, Insets.EMPTY));
        Background background6 = new Background(new BackgroundFill(new Color(0, 1, 0, .2), CornerRadii.EMPTY, Insets.EMPTY));
        gridpaneAddItem.setBorder(border1);
        gridpaneAddItem.setBackground(background1);
        gridpaneDeleteItem.setBorder(border1);
        gridpaneDeleteItem.setBackground(background2);
        gridpaneUpdateItem.setBorder(border1);
        gridpaneUpdateItem.setBackground(background3);
        vboxTableFoodsEaten.setBorder(border1);
        vboxTableFoodsEaten.setBackground(background4);
        vboxTableDailyNutrition.setBorder(border1);
        vboxTableDailyNutrition.setBackground(background5);
        gridpaneManageFoodsEaten.setBackground(background6);
                
        //Setting fonts
        labelTitle.setFont(Font.font("Britannic Bold", 40));
        labelInserting.setFont(Font.font("Britannic Bold", 30));
        labelDeleting.setFont(Font.font("Britannic Bold", 30));
        labelUpdating.setFont(Font.font("Britannic Bold", 30));
        labelTableFoodsEatenTitle.setFont(Font.font("Britannic Bold", 30));
        labelTableDailyNutritionTitle.setFont(Font.font("Britannic Bold", 30));
        labelExplantion.setFont(Font.font("Britannic", 20));
        
        //Creating and loading/calculating the tables
        createTableFoodsEaten();
        loadDataFoodsEaten();
        createTableDailyNutrition();
        calculateDataDailyNutrition();
        
        //Setting actions
        buttonAddItem.setOnAction(this::buttonAddItemPushed);
        buttonDeleteFoodItem.setOnAction(this::buttonDeleteFoodItemPushed);
        buttonMenuScreen.setOnAction(e -> primaryStage.setScene(sceneMenuScreen));
        
        //Create and return this scene
        Scene sceneManageFoodsEatenTableAndViewDailyNutritionTable = new Scene(gridpaneManageFoodsEaten, 6000, 1000);
        return sceneManageFoodsEatenTableAndViewDailyNutritionTable;
    }
    
    public void generateVboxInsertFoodNameRadioButtons()
    {
        vboxInsertFoodNameRadioButtons.getChildren().clear();
        arraylistInsertFoodNameRadioButtons.clear();
        
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
            ps = conn.prepareStatement("select * from food_item");
            rs = ps.executeQuery();
            while (rs.next())
            {
                String stringFoodName = rs.getString("food_name");
                RadioButton radioButtonInsertFoodName = new RadioButton(stringFoodName);
                radioButtonInsertFoodName.setOnAction(this::getCorrespondingServingSize); //when the radio button of a food is clicked, that food's serving_size will be shown on the screen.
                radioButtonInsertFoodName.setToggleGroup(togglegroupInsertFoodNameRadioButtons);
                arraylistInsertFoodNameRadioButtons.add(radioButtonInsertFoodName);
                vboxInsertFoodNameRadioButtons.getChildren().add(radioButtonInsertFoodName);
            }
            //closing connections
            conn.close();
            conn = null;
            ps.close();
            ps = null;
            rs.close();
            rs = null;
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                conn = null;
            }
            if (ps != null)
            {
                try
                {
                    ps.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                ps = null;
            }
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                rs = null;
            }
        }
    }
    
    public void generateVboxDeleteDateEatenAndFoodNameRadioButtons()
    {
        vboxDeleteDateEatenAndFoodNameRadioButtons.getChildren().clear();
        arraylistDeleteDateEatenAndFoodNameRadioButtons.clear();
        
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
            ps = conn.prepareStatement("select * from foods_eaten");
            rs = ps.executeQuery();
            while (rs.next())
            {
                String stringDateEaten = rs.getString("date_eaten");
                String stringFoodName = rs.getString("food_name");
                RadioButton radioButtonDeleteDateEatenAndFoodName = new RadioButton(stringDateEaten + ", " + stringFoodName);
                radioButtonDeleteDateEatenAndFoodName.setToggleGroup(togglegroupDeleteDateEatenAndFoodNameRadioButtons);
                arraylistDeleteDateEatenAndFoodNameRadioButtons.add(radioButtonDeleteDateEatenAndFoodName);
                vboxDeleteDateEatenAndFoodNameRadioButtons.getChildren().add(radioButtonDeleteDateEatenAndFoodName);
            }
            //closing connections
            conn.close();
            conn = null;
            ps.close();
            ps = null;
            rs.close();
            rs = null;
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                conn = null;
            }
            if (ps != null)
            {
                try
                {
                    ps.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                ps = null;
            }
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                rs = null;
            }
        }
    }
    
    public void createTableFoodsEaten()
    {
        vboxTableFoodsEaten.getChildren().clear();
        tableFoodsEaten.getColumns().clear();
                
        //set the width of each column
        tableColumnDateEaten.setMinWidth(130);
        tableColumnFoodName.setMinWidth(200);
        tableColumnServingSize.setMinWidth(130);
        tableColumnServings.setMinWidth(130);

        //allow each column to be able to add new values
        tableColumnDateEaten.setCellValueFactory(new PropertyValueFactory<FoodsEaten, String>("dateEaten"));
        tableColumnFoodName.setCellValueFactory(new PropertyValueFactory<FoodsEaten, String>("foodName"));
        tableColumnServingSize.setCellValueFactory(new PropertyValueFactory<FoodsEaten, String>("servingSize"));
        tableColumnServings.setCellValueFactory(new PropertyValueFactory<FoodsEaten, String>("servings"));
        
        //I believe this allows each cell to be treated like a textfield where the value can be changed.
        tableColumnDateEaten.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnFoodName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnServingSize.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnServings.setCellFactory(TextFieldTableCell.forTableColumn());
        
        //set editable for each column except for the dateEaten and foodName column, which are primary keys in the MySQL table. Then allow the edits made be reflected in the rest of the program.
        tableFoodsEaten.setEditable(true);
        tableColumnDateEaten.setEditable(false);   
        tableColumnFoodName.setEditable(false);       
        tableColumnServingSize.setEditable(false);
        
        tableColumnServings.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodsEaten, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodsEaten, String> t) 
                {          
                    ((FoodsEaten) t.getTableView().getItems().get(t.getTablePosition().getRow())).setServings(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update foods_eaten set servings = ? where date_eaten = ? and food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().dateEaten);
                        ps.setString(3, t.getRowValue().foodName);
                        ps.executeUpdate();
                        
                        //closing connections
                        conn.close();
                        conn = null;
                        ps.close();
                        ps = null;
                    } 
                    catch (SQLException ex) 
                    {
                        System.out.println(ex.getMessage());
                    }
                    finally
                    {
                        if (conn != null)
                        {
                            try
                            {
                                conn.close();
                            }
                            catch (SQLException ex) 
                            {
                                System.out.println(ex.getMessage());
                            }
                            conn = null;
                        }
                        if (ps != null)
                        {
                            try
                            {
                                ps.close();
                            }
                            catch (SQLException ex) 
                            {
                                System.out.println(ex.getMessage());
                            }
                            ps = null;
                        }
                    }
                    generateVboxDeleteDateEatenAndFoodNameRadioButtons();
                    loadDataFoodsEaten();
                    calculateDataDailyNutrition();
                }
            }
        );

        tableFoodsEaten.setItems(dataFoodsEaten);
        tableFoodsEaten.getColumns().addAll(tableColumnDateEaten, tableColumnFoodName, tableColumnServingSize, tableColumnServings);
        vboxTableFoodsEaten.setSpacing(5);
        vboxTableFoodsEaten.setPadding(new Insets(10, 0, 0, 10));
        vboxTableFoodsEaten.getChildren().addAll(labelTableFoodsEatenTitle, tableFoodsEaten);
    }
     
    public void createTableDailyNutrition()
    {
        vboxTableDailyNutrition.getChildren().clear();
        tableDailyNutrition.getColumns().clear();
        tableDailyNutrition.setEditable(true);

        TableColumn tableColumnDateEaten = new TableColumn("Date Eaten");
        TableColumn tableColumnCalories = new TableColumn("Calories");
        TableColumn tableColumnProtein = new TableColumn("Protein (g)");
        TableColumn tableColumnCarbs = new TableColumn("Carbs (g)");
        TableColumn tableColumnTotalFat = new TableColumn("Total Fat (g)");
        TableColumn tableColumnSaturatedFat = new TableColumn("Saturated Fat (g)");
        TableColumn tableColumnTotalSugar = new TableColumn("Total Sugar (g)");
        TableColumn tableColumnAddedSugar = new TableColumn("Added Sugar (g)");
        TableColumn tableColumnSodium = new TableColumn("Sodium (mg)");
        TableColumn tableColumnCholesterol = new TableColumn("Cholesterol (mg)");
        TableColumn tableColumnFiber = new TableColumn("Fiber (g)");

        //set the width of each column
        tableColumnDateEaten.setMinWidth(130);
        tableColumnCalories.setMinWidth(130);
        tableColumnProtein.setMinWidth(130);
        tableColumnCarbs.setMinWidth(130);
        tableColumnTotalFat.setMinWidth(130);
        tableColumnSaturatedFat.setMinWidth(130);
        tableColumnTotalSugar.setMinWidth(130);
        tableColumnAddedSugar.setMinWidth(130);
        tableColumnSodium.setMinWidth(130);
        tableColumnCholesterol.setMinWidth(130);
        tableColumnFiber.setMinWidth(130);

        //allow each column to be able to add new values
        tableColumnDateEaten.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("dateEaten"));
        tableColumnCalories.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("calories"));
        tableColumnProtein.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("protein"));
        tableColumnCarbs.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("carbs"));
        tableColumnTotalFat.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("totalFat"));
        tableColumnSaturatedFat.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("saturatedFat"));
        tableColumnTotalSugar.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("totalSugar"));
        tableColumnAddedSugar.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("addedSugar"));
        tableColumnSodium.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("sodium"));
        tableColumnCholesterol.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("cholesterol"));
        tableColumnFiber.setCellValueFactory(new PropertyValueFactory<DailyNutrition, String>("fiber"));

        tableDailyNutrition.setItems(dataDailyNutrition);

        tableDailyNutrition.getColumns().addAll
        (
            tableColumnDateEaten, 
            tableColumnCalories, 
            tableColumnProtein, 
            tableColumnCarbs, 
            tableColumnTotalFat, 
            tableColumnSaturatedFat, 
            tableColumnTotalSugar, 
            tableColumnAddedSugar, 
            tableColumnSodium, 
            tableColumnCholesterol, 
            tableColumnFiber
        );
        
        vboxTableDailyNutrition.setSpacing(5);
        vboxTableDailyNutrition.setPadding(new Insets(10, 0, 0, 10));
        vboxTableDailyNutrition.getChildren().addAll(labelTableDailyNutritionTitle, tableDailyNutrition);
    }
       
    public void loadDataFoodsEaten()
    {
        dataFoodsEaten.clear();    
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");         
            ps = conn.prepareStatement("select * from foods_eaten");
            rs = ps.executeQuery();        
            while (rs.next())
            {
                dataFoodsEaten.add(new FoodsEaten(rs.getString("date_eaten"), rs.getString("food_name"), rs.getString("serving_size"), Double.toString(rs.getDouble("servings"))));
            }
            //closing connections
            conn.close();
            conn = null;
            ps.close();
            ps = null;
            rs.close();
            rs = null;
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                conn = null;
            }
            if (ps != null)
            {
                try
                {
                    ps.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                ps = null;
            }
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                rs = null;
            }
        }
    }
    
    public void calculateDataDailyNutrition()
    {
        ResultSet rsFoodsEaten = null;
        ResultSet rsFoodItem = null;
        ResultSet rsCheckDateInDailyNutrition = null;
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");            
            ps = conn.prepareStatement("delete from daily_nutrition");
            ps.execute();
            
            ps = conn.prepareStatement("select * from foods_eaten");
            rsFoodsEaten = ps.executeQuery();            
            while (rsFoodsEaten.next())
            {
                String stringFoodsEatenDateEaten = rsFoodsEaten.getString("date_eaten");
                String stringFoodsEatenFoodName = rsFoodsEaten.getString("food_name");
                String stringFoodsEatenServingSize = rsFoodsEaten.getString("serving_size");
                Double doubleFoodsEatenServings = rsFoodsEaten.getDouble("servings");
                
                ps = conn.prepareStatement("select * from food_item where food_name = ?");
                ps.setString(1, stringFoodsEatenFoodName);
                rsFoodItem = ps.executeQuery();
                
                rsFoodItem.next();
                String stringFoodItemFoodName = rsFoodItem.getString("food_name");
                Double doubleFoodItemCalories = rsFoodItem.getDouble("calories");
                Double doubleFoodItemProtein = rsFoodItem.getDouble("protein");
                Double doubleFoodItemCarbs = rsFoodItem.getDouble("carbs");
                Double doubleFoodItemTotalFat = rsFoodItem.getDouble("total_fat");
                Double doubleFoodItemSaturatedFat = rsFoodItem.getDouble("saturated_fat");
                Double doubleFoodItemTotalSugar = rsFoodItem.getDouble("total_sugar");
                Double doubleFoodItemAddedSugar = rsFoodItem.getDouble("added_sugar");
                Double doubleFoodItemSodium = rsFoodItem.getDouble("sodium");
                Double doubleFoodItemCholesterol = rsFoodItem.getDouble("cholesterol");
                Double doubleFoodItemFiber = rsFoodItem.getDouble("fiber");

                ps = conn.prepareStatement("select count(*) from daily_nutrition where date_eaten = ?");
                ps.setString(1, stringFoodsEatenDateEaten);
                rsCheckDateInDailyNutrition = ps.executeQuery();
                
                rsCheckDateInDailyNutrition.next();
                int counts = rsCheckDateInDailyNutrition.getInt("count(*)");
                if (counts == 0)
                {
                    ps = conn.prepareStatement("insert into daily_nutrition values(?, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)");
                    ps.setString(1, stringFoodsEatenDateEaten);
                    ps.execute();
                }
                
                ps = conn.prepareStatement
                (
                    "update daily_nutrition " +
                    "set calories = calories + ?, " +
                    "protein = protein + ?, " +
                    "carbs = carbs + ?, " +
                    "total_fat = total_fat + ?, " +
                    "saturated_fat = saturated_fat + ?, " +
                    "total_sugar = total_sugar + ?, " +
                    "added_sugar = added_sugar + ?, " +
                    "sodium = sodium + ?, " +
                    "cholesterol = cholesterol + ?, " +
                    "fiber = fiber + ? " +
                    "WHERE date_eaten = ?;"
                 );
                ps.setDouble(1, doubleFoodItemCalories * doubleFoodsEatenServings);
                ps.setDouble(2, doubleFoodItemProtein * doubleFoodsEatenServings);
                ps.setDouble(3, doubleFoodItemCarbs * doubleFoodsEatenServings);
                ps.setDouble(4, doubleFoodItemTotalFat * doubleFoodsEatenServings);
                ps.setDouble(5, doubleFoodItemSaturatedFat * doubleFoodsEatenServings);
                ps.setDouble(6, doubleFoodItemTotalSugar * doubleFoodsEatenServings);
                ps.setDouble(7, doubleFoodItemAddedSugar * doubleFoodsEatenServings);
                ps.setDouble(8, doubleFoodItemSodium * doubleFoodsEatenServings);
                ps.setDouble(9, doubleFoodItemCholesterol * doubleFoodsEatenServings);
                ps.setDouble(10, doubleFoodItemFiber * doubleFoodsEatenServings);
                ps.setString(11, stringFoodsEatenDateEaten);  
                ps.execute();
            }
            //closing connections
            conn.close();
            conn = null;
            ps.close();
            ps = null;
            rsFoodsEaten.close();
            rsFoodsEaten = null;
            if (rsFoodItem != null)
            {
                rsFoodItem.close(); 
            }
            rsFoodItem = null;
            if (rsCheckDateInDailyNutrition != null)
            {
                rsCheckDateInDailyNutrition.close();
            }
            rsCheckDateInDailyNutrition = null;  
        } 
        catch (SQLException ex) 
        {
           System.out.println(ex.getMessage());
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                conn = null;
            }
            if (ps != null)
            {
                try
                {
                    ps.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                ps = null;
            }
            if (rsFoodsEaten != null)
            {
                try
                {
                    rsFoodsEaten.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                rsFoodsEaten = null;
            }
            if (rsFoodItem != null)
            {
                try
                {
                    rsFoodItem.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                rsFoodItem = null;
            }
            if (rsCheckDateInDailyNutrition != null)
            {
                try
                {
                    rsCheckDateInDailyNutrition.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                rsCheckDateInDailyNutrition = null;
            }
        }
        //Once the data is added to the MySQL table, have it appear in the table in JavaFXX
        loadDataDailyNutrition();
    }
    
    public void loadDataDailyNutrition()
    {
        dataDailyNutrition.clear();
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");        
            ps = conn.prepareStatement("select * from daily_nutrition");
            rs = ps.executeQuery();
            while (rs.next())
            {
                dataDailyNutrition.add
                (
                    new DailyNutrition
                    (
                        rs.getString("date_eaten"),
                        Double.toString(rs.getDouble("calories")),
                        Double.toString(rs.getDouble("protein")),
                        Double.toString(rs.getDouble("carbs")),
                        Double.toString(rs.getDouble("total_fat")),
                        Double.toString(rs.getDouble("saturated_fat")),
                        Double.toString(rs.getDouble("total_sugar")),
                        Double.toString(rs.getDouble("added_sugar")),
                        Double.toString(rs.getDouble("sodium")),
                        Double.toString(rs.getDouble("cholesterol")),
                        Double.toString(rs.getDouble("fiber"))
                    )
                );
            }
            //closing connections
            conn.close();
            conn = null;
            ps.close();
            ps = null;
            rs.close();
            rs = null;
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                conn = null;
            }
            if (ps != null)
            {
                try
                {
                    ps.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                ps = null;
            }
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                rs = null;
            }
        }
    }
    
    private int getIndexOfFoodNameRadioButtonSelectedDuringInsertion()
    {
        int numberOfRadioButtons = arraylistInsertFoodNameRadioButtons.size();
        for (int j = 0; j < numberOfRadioButtons; j++)
        {
            if (arraylistInsertFoodNameRadioButtons.get(j).isSelected())
            {
                return j;
            }
        }
        return -1;
    }
    
    private void getCorrespondingServingSize(ActionEvent event)
    {
        int indexRadioButtonSelected = getIndexOfFoodNameRadioButtonSelectedDuringInsertion();  
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
            ps = conn.prepareStatement("select serving_size from food_item where food_name = ?");
            ps.setString(1, arraylistInsertFoodNameRadioButtons.get(indexRadioButtonSelected).getText());
            rs = ps.executeQuery();
            while (rs.next())
            {
                labelEnterServingSize.setText("Serving Size = " + rs.getString("serving_size"));
            }
            //closing connections
            conn.close();
            conn = null;
            ps.close();
            ps = null;
            rs.close();
            rs = null;
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                conn = null;
            }
            if (ps != null)
            {
                try
                {
                    ps.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                ps = null;
            }
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                rs = null;
            }
        }
    }
    
    private void buttonAddItemPushed(ActionEvent event)
    {
        int indexRadioButtonSelected = getIndexOfFoodNameRadioButtonSelectedDuringInsertion();    
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
            ps = conn.prepareStatement("insert into foods_eaten(date_eaten, food_name, serving_size,  servings) values(?, ?, ?, ?)");
            ps.setString(1, textfieldEnterDateEaten.getText());
            ps.setString(2, arraylistInsertFoodNameRadioButtons.get(indexRadioButtonSelected).getText());
            ps.setString(3, labelEnterServingSize.getText().substring(15)); //15 characters eliminates "Serving size = " and leaves only the actual servingSize value
            ps.setDouble(4, Double.parseDouble(textfieldEnterServings.getText()));
            ps.executeUpdate();
            
            //closing connections
            conn.close();
            conn = null;
            ps.close();
            ps = null;
        } 
        catch (SQLException ex) 
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                conn = null;
            }
            if (ps != null)
            {
                try
                {
                    ps.close();
                }
                catch (SQLException ex) 
                {
                    System.out.println(ex.getMessage());
                }
                ps = null;
            }
        }
        arraylistInsertFoodNameRadioButtons.get(indexRadioButtonSelected).setSelected(false);
        labelEnterServingSize.setText("Serving Size = ");
        textfieldEnterServings.clear();
        
        loadDataFoodsEaten();
        calculateDataDailyNutrition();
        generateVboxDeleteDateEatenAndFoodNameRadioButtons();
    }
      
    private int getIntRadioButtonIndexToDelete()
    {
        int indexToDelete = -1;
        int numberOfRadioButtons = arraylistDeleteDateEatenAndFoodNameRadioButtons.size();
        for (int j = 0; j < numberOfRadioButtons; j++)
        {
            if (arraylistDeleteDateEatenAndFoodNameRadioButtons.get(j).isSelected())
            {
                return j;
            }
        }
        return -1;
    }
    
    public void buttonDeleteFoodItemPushed(ActionEvent event)
    {
        int intRadioButtonIndexToDelete = getIntRadioButtonIndexToDelete();
        
        if (intRadioButtonIndexToDelete != -1)
        {
            String stringRadioButtonText = arraylistDeleteDateEatenAndFoodNameRadioButtons.get(intRadioButtonIndexToDelete).getText();
            String[] arrayStringsDateEatenAndFoodName = stringRadioButtonText.split(", ");
            
            dataFoodsEaten.removeIf(i -> i.getDateEaten().equals(arrayStringsDateEatenAndFoodName[0]) && i.getFoodName().equals(arrayStringsDateEatenAndFoodName[1]));
            try 
            {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                ps = conn.prepareStatement("delete from foods_eaten where date_eaten = ? and food_name = ?");
                ps.setString(1, arrayStringsDateEatenAndFoodName[0]);
                ps.setString(2, arrayStringsDateEatenAndFoodName[1]);
                ps.executeUpdate();
                
                //closing connections
                conn.close();
                conn = null;
                ps.close();
                ps = null;
            } 
            catch (SQLException ex) 
            {
                System.out.println(ex.getMessage());
            }
            finally
            {
                if (conn != null)
                {
                    try
                    {
                        conn.close();
                    }
                    catch (SQLException ex) 
                    {
                        System.out.println(ex.getMessage());
                    }
                    conn = null;
                }
                if (ps != null)
                {
                    try
                    {
                        ps.close();
                    }
                    catch (SQLException ex) 
                    {
                        System.out.println(ex.getMessage());
                    }
                    ps = null;
                }
            }
            arraylistDeleteDateEatenAndFoodNameRadioButtons.get(intRadioButtonIndexToDelete).setSelected(false);
            arraylistDeleteDateEatenAndFoodNameRadioButtons.remove(intRadioButtonIndexToDelete);
            vboxDeleteDateEatenAndFoodNameRadioButtons.getChildren().remove(intRadioButtonIndexToDelete, intRadioButtonIndexToDelete + 1);
        }       
        calculateDataDailyNutrition();
        generateVboxDeleteDateEatenAndFoodNameRadioButtons();
    }
}
