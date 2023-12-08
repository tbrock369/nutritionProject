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
import javax.swing.JOptionPane;

public class ManageFoodItemTable
{
    //Connection variables
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    //Heading controls/variables
    Label labelTitle = new Label("Manage Food Item Table ");
    Button buttonMenuScreen = new Button("Menu Screen");
    HBox hboxTitleAndMenuScreenButton = new HBox();

    //Table 1 controls/variables
    private TableView<FoodItem> table = new TableView<FoodItem>();
    private final ObservableList<FoodItem> data = FXCollections.observableArrayList();
    Label labelTableFoodsEatenTitle = new Label("Table 1 (food_item)");
    VBox vboxTable = new VBox();
   
    //Insert controls/variables     
    Label labelInserting = new Label("Add Row to Table 1");
    Label labelEnterFoodName = new Label("Food Name");
    TextField textfieldEnterFoodName = new TextField();
    Label labelEnterServingSize = new Label("Serving Size");
    TextField textfieldEnterServingSize = new TextField();
    Label labelEnterCalories = new Label("Calories");
    TextField textfieldEnterCalories = new TextField();
    Label labelEnterProtein = new Label("Protein");
    TextField textfieldEnterProtein = new TextField();
    Label labelEnterCarbs = new Label("Carbs");
    TextField textfieldEnterCarbs = new TextField();
    Label labelEnterTotalFat = new Label("Total Fat");
    TextField textfieldEnterTotalFat = new TextField();
    Label labelEnterSaturatedFat = new Label("Saturated Fat");
    TextField textfieldEnterSaturatedFat = new TextField();
    Label labelEnterTotalSugar = new Label("Total Sugar");
    TextField textfieldEnterTotalSugar = new TextField();
    Label labelEnterAddedSugar = new Label("Added Sugar");
    TextField textfieldEnterAddedSugar = new TextField();
    Label labelEnterSodium = new Label("Sodium");
    TextField textfieldEnterSodium = new TextField();
    Label labelEnterCholesterol = new Label("Cholesterol");
    TextField textfieldEnterCholesterol = new TextField();
    Label labelEnterFiber = new Label("Fiber");
    TextField textfieldEnterFiber = new TextField();
    Button buttonAddItem = new Button("Add Item");
    
    //Deleting controls/variables
    Label labelDeleting = new Label("Delete Row from Table 1");
    Label labelDeleteFoodItem = new Label("Select the name of the food you would like to delete");
    ArrayList <RadioButton> arraylistFoodNameRadioButtons;
    ToggleGroup togglegroupFoodNameRadioButtons;
    VBox vboxFoodNameRadioButtons;
    ScrollPane scrollpaneFoodNameRadioButtons;
    Button buttonDeleteFoodItem = new Button("Delete Item");
    
    //Updating controls/variables
    Label labelUpdating = new Label("Update Cell in Table 1");
    Label labelUpdateExplanation = new Label
    (
        "To make updates to Table 2, select the cell\n" +
        "in the table you'd like to change, make the\n" +
        "changes you'd like, and hit enter to finalize\n" +
        "your changes. These changes may then also\n" +
        "affect Tables 2 and 3."
    );
  
    public Scene createManageFoodItemTableScene(Stage primaryStage, Scene sceneMenuScreen)
    {
        //Create the list of radio buttons that are used to delete entries from the table
        arraylistFoodNameRadioButtons = new ArrayList <RadioButton>();
        togglegroupFoodNameRadioButtons = new ToggleGroup();
        vboxFoodNameRadioButtons = new VBox();
        scrollpaneFoodNameRadioButtons = new ScrollPane(vboxFoodNameRadioButtons);
        generateVboxFoodNameRadioButtons();

        //Setting Fonts
        labelTitle.setFont(Font.font("Britannic Bold", 40));
        labelInserting.setFont(Font.font("Britannic Bold", 30));
        labelDeleting.setFont(Font.font("Britannic Bold", 30));
        labelUpdating.setFont(Font.font("Britannic Bold", 30));
        labelTableFoodsEatenTitle.setFont(Font.font("Britannic Bold", 30));
        
        //If sceneManageFoodItemTable has already been called and the user returned to the main menu, if they come back to this scene, the children of hboxTitleAndMenuScreenButton don't have to be added again.
        if (hboxTitleAndMenuScreenButton.getChildren().size() == 0)
        {
            hboxTitleAndMenuScreenButton.getChildren().addAll(labelTitle, buttonMenuScreen);
        }
        
        //Gridpane paneAddItem
        GridPane paneAddItem = new GridPane();
        paneAddItem.setPadding(new Insets(10, 10, 10, 10));
        paneAddItem.setMinSize(300, 300);
        paneAddItem.setVgap(10);
        paneAddItem.setHgap(10);
        
        paneAddItem.add(labelInserting, 0, 0, 2, 1);
        paneAddItem.add(labelEnterFoodName, 0, 1);
        paneAddItem.add(textfieldEnterFoodName, 1, 1);
        paneAddItem.add(labelEnterServingSize, 0, 2);
        paneAddItem.add(textfieldEnterServingSize, 1, 2);   
        paneAddItem.add(labelEnterCalories, 0, 3);
        paneAddItem.add(textfieldEnterCalories, 1, 3);       
        paneAddItem.add(labelEnterProtein, 0, 4);
        paneAddItem.add(textfieldEnterProtein, 1, 4);        
        paneAddItem.add(labelEnterCarbs, 0, 5);
        paneAddItem.add(textfieldEnterCarbs, 1, 5);        
        paneAddItem.add(labelEnterTotalFat, 0, 6);
        paneAddItem.add(textfieldEnterTotalFat, 1, 6);        
        paneAddItem.add(labelEnterSaturatedFat, 2, 1);
        paneAddItem.add(textfieldEnterSaturatedFat, 3, 1);        
        paneAddItem.add(labelEnterTotalSugar, 2, 2);
        paneAddItem.add(textfieldEnterTotalSugar, 3, 2);        
        paneAddItem.add(labelEnterAddedSugar, 2, 3);
        paneAddItem.add(textfieldEnterAddedSugar, 3, 3);        
        paneAddItem.add(labelEnterSodium, 2, 4);
        paneAddItem.add(textfieldEnterSodium, 3, 4);        
        paneAddItem.add(labelEnterCholesterol, 2, 5);
        paneAddItem.add(textfieldEnterCholesterol, 3, 5);        
        paneAddItem.add(labelEnterFiber, 2, 6);
        paneAddItem.add(textfieldEnterFiber, 3, 6);        
        paneAddItem.add(buttonAddItem, 0, 7);
        
        //GridPane paneDeleteItem
        GridPane paneDeleteItem = new GridPane();
        paneDeleteItem.setPadding(new Insets(10, 10, 10, 10));
        paneDeleteItem.setMinSize(300, 300);
        paneDeleteItem.setMaxHeight(350);
        paneDeleteItem.setVgap(10);
        paneDeleteItem.setHgap(10);
        
        paneDeleteItem.add(labelDeleting, 0, 0, 2, 1);
        paneDeleteItem.add(labelDeleteFoodItem, 0, 1);
        paneDeleteItem.add(scrollpaneFoodNameRadioButtons, 0, 2);
        paneDeleteItem.add(buttonDeleteFoodItem, 0, 3);
        
        //GridPane paneUpdateItem
        GridPane paneUpdateItem = new GridPane();
        paneUpdateItem.setPadding(new Insets(10, 10, 10, 10));
        paneUpdateItem.setMinSize(300, 300);
        paneUpdateItem.setVgap(10);
        paneUpdateItem.setHgap(10);
        
        paneUpdateItem.add(labelUpdating, 0, 0, 2, 1);
        paneUpdateItem.add(labelUpdateExplanation, 0, 1, 1, 4);
      
        //GridPane gridpaneManageFoodItemTable
        GridPane gridpaneManageFoodItemTable = new GridPane();
        gridpaneManageFoodItemTable.setPadding(new Insets(10, 10, 10, 10));
        gridpaneManageFoodItemTable.setMinSize(300, 300);
        gridpaneManageFoodItemTable.setVgap(10);
        gridpaneManageFoodItemTable.setHgap(10);
        
        gridpaneManageFoodItemTable.add(hboxTitleAndMenuScreenButton, 0, 0, 2, 1);
        gridpaneManageFoodItemTable.add(paneAddItem, 0, 2);
        gridpaneManageFoodItemTable.add(paneDeleteItem, 1, 2);
        gridpaneManageFoodItemTable.add(paneUpdateItem, 2, 2);
        gridpaneManageFoodItemTable.add(vboxTable, 0, 4, 8, 5);
        
        //Creating and Setting Boarders and Backgrounds
        BorderStroke borderStroke1 = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2));
        Border border1 = new Border(borderStroke1);
        Background background1 = new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY));
        Background background2 = new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY));
        Background background3 = new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY));
        Background background4 = new Background(new BackgroundFill(Color.AQUAMARINE, CornerRadii.EMPTY, Insets.EMPTY));
        Background background5 = new Background(new BackgroundFill(Color.LAVENDER, CornerRadii.EMPTY, Insets.EMPTY));
        
        paneAddItem.setBorder(border1);
        paneAddItem.setBackground(background1);
        paneDeleteItem.setBorder(border1);
        paneDeleteItem.setBackground(background2);
        paneUpdateItem.setBorder(border1);
        paneUpdateItem.setBackground(background3);
        vboxTable.setBorder(border1);
        vboxTable.setBackground(background4);
        gridpaneManageFoodItemTable.setBackground(background5);
        
        //Create the table displayed and fill it with the data stored in the corresponding MySQL table
        createTable();
        tableUpdate();    
        
        //Setting the actions of buttons
        buttonAddItem.setOnAction(this::buttonAddItemPushed);
        buttonDeleteFoodItem.setOnAction(this::buttonDeleteFoodItemPushed);
        buttonMenuScreen.setOnAction(e -> primaryStage.setScene(sceneMenuScreen));
        
        //Create and return this scene
        Scene sceneManageFoodItemTable = new Scene(gridpaneManageFoodItemTable, 6000, 1000);
        return sceneManageFoodItemTable;
    }
    
    public void generateVboxFoodNameRadioButtons()
    {
        vboxFoodNameRadioButtons.getChildren().clear();
        arraylistFoodNameRadioButtons.clear();
        
        try
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "your username", "your password");        
            ps = conn.prepareStatement("select * from food_item");  
            rs = ps.executeQuery();
            while (rs.next())
            {
                String stringFoodName = rs.getString("food_name");
                RadioButton radioButtonFoodName = new RadioButton(stringFoodName);
                radioButtonFoodName.setToggleGroup(togglegroupFoodNameRadioButtons);
                arraylistFoodNameRadioButtons.add(radioButtonFoodName);
                vboxFoodNameRadioButtons.getChildren().add(radioButtonFoodName);
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
    
    public void createTable()
    {
        vboxTable.getChildren().clear();
        table.getColumns().clear();      

        TableColumn tableColumnFoodName = new TableColumn("Food Name");
        TableColumn tableColumnServingSize = new TableColumn("Serving Size");
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
        tableColumnFoodName.setMinWidth(200);
        tableColumnServingSize.setMinWidth(200);
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
        tableColumnFoodName.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("foodName"));
        tableColumnServingSize.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("servingSize"));
        tableColumnCalories.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("calories"));
        tableColumnProtein.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("protein"));
        tableColumnCarbs.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("carbs"));
        tableColumnTotalFat.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("totalFat"));
        tableColumnSaturatedFat.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("saturatedFat"));
        tableColumnTotalSugar.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("totalSugar"));
        tableColumnAddedSugar.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("addedSugar"));
        tableColumnSodium.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("sodium"));
        tableColumnCholesterol.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("cholesterol"));
        tableColumnFiber.setCellValueFactory(new PropertyValueFactory<FoodItem, String>("fiber"));

        //I believe this allows each cell to be treated like a textfield where the value can be changed.
        tableColumnFoodName.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnServingSize.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnCalories.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnProtein.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnCarbs.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnTotalFat.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnSaturatedFat.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnTotalSugar.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnAddedSugar.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnSodium.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnCholesterol.setCellFactory(TextFieldTableCell.forTableColumn());
        tableColumnFiber.setCellFactory(TextFieldTableCell.forTableColumn());
        
        //set editable for each column except for the foodName column, which is a primary key in the MySQL table. Then allow the edits made be reflected in the rest of the program.
        table.setEditable(true);
        tableColumnFoodName.setEditable(false);       
        
        tableColumnServingSize.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setServingSize(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set serving_size = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();
                }
            }
        );
        tableColumnCalories.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCalories(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set calories = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();
                }
            }
        );
        tableColumnProtein.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setProtein(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set protein = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();
                }
            }
        );
        tableColumnCarbs.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCarbs(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set carbs = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();
                }
            }
        );
        tableColumnTotalFat.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTotalFat(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set total_fat = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();
                }
            }
        );
        tableColumnSaturatedFat.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSaturatedFat(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set saturated_fat = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();
                }
            }
        );
        tableColumnTotalSugar.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTotalSugar(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set total_sugar = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();
                }
            }
        );
        tableColumnAddedSugar.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAddedSugar(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set added_sugar = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();
                }
            }
        );
        tableColumnSodium.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSodium(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set sodium = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();                
                }
            }
        );
        tableColumnCholesterol.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCholesterol(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set cholesterol = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();
                }
            }
        );
        tableColumnFiber.setOnEditCommit
        (
            new EventHandler<TableColumn.CellEditEvent<FoodItem, String>>() 
            {
                @Override
                public void handle(TableColumn.CellEditEvent<FoodItem, String> t) 
                {
                    ((FoodItem) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFiber(t.getNewValue());
                    try 
                    {
                        conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                        ps = conn.prepareStatement("update food_item set fiber = ? where food_name = ?");
                        ps.setString(1, t.getNewValue());
                        ps.setString(2, t.getRowValue().foodName);
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
                    generateVboxFoodNameRadioButtons();
                    tableUpdate();
                }
            }
        );

        table.setItems(data);

        table.getColumns().addAll
        (
            tableColumnFoodName, 
            tableColumnServingSize,
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

        vboxTable.setSpacing(5);
        vboxTable.setPadding(new Insets(10, 0, 0, 10));
        vboxTable.getChildren().addAll(labelTableFoodsEatenTitle, table);     
    }
    
    public void tableUpdate()
    {
        try 
        {
            data.clear();
            
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
            ps = conn.prepareStatement("select * from food_item");
            rs = ps.executeQuery();
            while (rs.next())
            {
                data.add
                (
                    new FoodItem
                    (
                        rs.getString("food_name"),
                        rs.getString("serving_size"),
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
    
    private void buttonAddItemPushed(ActionEvent event)
    {
        try 
        {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
            ps = conn.prepareStatement("insert into "
                    + "food_item(food_name, serving_size, calories, protein, "
                    + "carbs, total_fat, saturated_fat, total_sugar,"
                    + " added_sugar, sodium, cholesterol, fiber) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, textfieldEnterFoodName.getText());
            ps.setString(2, textfieldEnterServingSize.getText());
            ps.setDouble(3, Double.parseDouble(textfieldEnterCalories.getText()));
            ps.setDouble(4, Double.parseDouble(textfieldEnterProtein.getText()));
            ps.setDouble(5, Double.parseDouble(textfieldEnterCarbs.getText()));
            ps.setDouble(6, Double.parseDouble(textfieldEnterTotalFat.getText()));
            ps.setDouble(7, Double.parseDouble(textfieldEnterSaturatedFat.getText()));
            ps.setDouble(8, Double.parseDouble(textfieldEnterTotalSugar.getText()));
            ps.setDouble(9, Double.parseDouble(textfieldEnterAddedSugar.getText()));
            ps.setDouble(10, Double.parseDouble(textfieldEnterSodium.getText()));
            ps.setDouble(11, Double.parseDouble(textfieldEnterCholesterol.getText()));
            ps.setDouble(12, Double.parseDouble(textfieldEnterFiber.getText()));
            ps.executeUpdate();
            
            //Update the list of radio buttons for deleting food items
            RadioButton radiobuttonFoodNameAdded = new RadioButton(textfieldEnterFoodName.getText());
            radiobuttonFoodNameAdded.setToggleGroup(togglegroupFoodNameRadioButtons);
            arraylistFoodNameRadioButtons.add(radiobuttonFoodNameAdded);
            vboxFoodNameRadioButtons.getChildren().add(radiobuttonFoodNameAdded);
            
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
        
        textfieldEnterFoodName.clear();
        textfieldEnterServingSize.clear();
        textfieldEnterCalories.clear();
        textfieldEnterProtein.clear();
        textfieldEnterCarbs.clear();
        textfieldEnterTotalFat.clear();
        textfieldEnterSaturatedFat.clear();
        textfieldEnterTotalSugar.clear();
        textfieldEnterAddedSugar.clear();
        textfieldEnterSodium.clear();
        textfieldEnterCholesterol.clear();
        textfieldEnterFiber.clear();

        tableUpdate();
    }
    
    private int getIntRadioButtonIndexToDelete()
    {
        int indexToDelete = -1;
        int numberOfRadioButtons = arraylistFoodNameRadioButtons.size();
        for (int j = 0; j < numberOfRadioButtons; j++)
        {
            if (arraylistFoodNameRadioButtons.get(j).isSelected())
            {
                return j;
            }
        }
        return -1;
    }
    
    
    private void buttonDeleteFoodItemPushed(ActionEvent event)
    {
        int intRadioButtonIndexToDelete = getIntRadioButtonIndexToDelete();
        
        if (intRadioButtonIndexToDelete != -1)
        {
            data.removeIf(i -> i.getFoodName().equals(arraylistFoodNameRadioButtons.get(intRadioButtonIndexToDelete).getText()));
            try 
            {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/nutritionproject", "root", "OrangeNotes25");
                ps = conn.prepareStatement("delete from food_item where food_name = ?");
                ps.setString(1, arraylistFoodNameRadioButtons.get(intRadioButtonIndexToDelete).getText());
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

            arraylistFoodNameRadioButtons.get(intRadioButtonIndexToDelete).setSelected(false);
            arraylistFoodNameRadioButtons.remove(intRadioButtonIndexToDelete);
            if (intRadioButtonIndexToDelete + 1 == vboxFoodNameRadioButtons.getChildren().size())
            {
                vboxFoodNameRadioButtons.getChildren().remove(intRadioButtonIndexToDelete, vboxFoodNameRadioButtons.getChildren().size());
            }
            else
            {
                vboxFoodNameRadioButtons.getChildren().remove(intRadioButtonIndexToDelete, intRadioButtonIndexToDelete + 1);
            }
        }
    }
}
