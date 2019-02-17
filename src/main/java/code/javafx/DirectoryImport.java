package code.javafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class DirectoryImport extends Application {

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("CallWorks Directory Importer");

        final VBox mainVLayout = new VBox();
        mainVLayout.setPadding(new Insets(10, 10, 0, 10));

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome to CallWorks Directory Importer");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // url
        Label siteUrlLabel = new Label("Site URL:");
        grid.add(siteUrlLabel, 0, 1);
        TextField siteUrlTextField = new TextField();
        grid.add(siteUrlTextField, 1, 1);

        // user
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 2);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 2);

        // password
        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);

        Button loginBtn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginBtn);
        grid.add(hbBtn, 1, 4);

        // result
        Label result = new Label("");
        grid.add(result, 1, 5);
        result.setText("Unable to sign-in");

        //-----------------------------------------------------------------------
        // Import and display directories from excel file
        VBox excelButtonLayout = new VBox(10);

        Button excelBtn = new Button("Select Excel File");
        excelBtn.setDisable(true);

        Button importBtn = new Button("Start import processing");
        importBtn.setDisable(true);

        Button cancelBtn = new Button("Cancel processing");
        cancelBtn.setDisable(true);

        HBox excelHbBtn = new HBox(10);
        excelHbBtn.setAlignment(Pos.BOTTOM_LEFT);
        excelHbBtn.setPadding(new Insets(0, 0, 10, 0));
        excelHbBtn.getChildren().addAll(excelBtn, importBtn, cancelBtn);
        excelButtonLayout.getChildren().add(excelHbBtn);


        final TableView<DirectoryEntry> table = new TableView<DirectoryEntry>();
//        HBox hbTable = new HBox(10);
//        hbTable.setAlignment(Pos.BOTTOM_LEFT);
//        hbTable.getChildren().add(table);
//        excelButtonLayout.getChildren().add(hbTable);

        // Name--
        // Phone Number--
        // Star Code Pattern--
        // Category/Tab--
        // Bool--
        // Common Place?--
        // Bool--
        TableColumn<DirectoryEntry,String> nameCol = new TableColumn<DirectoryEntry,String>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        nameCol.prefWidthProperty().bind(table.widthProperty().divide(3)); // w * 1/3

        TableColumn<DirectoryEntry,String> phoneNumberCol = new TableColumn<DirectoryEntry,String>("Phone Number");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        phoneNumberCol.prefWidthProperty().bind(table.widthProperty().divide(6)); // w * 1/6

        TableColumn<DirectoryEntry,String> startCodePatternsCol = new TableColumn<DirectoryEntry,String>("Star Code Pattern");
        startCodePatternsCol.setCellValueFactory(new PropertyValueFactory("starCodePattern"));
        startCodePatternsCol.prefWidthProperty().bind(table.widthProperty().divide(6)); // w * 1/6

        TableColumn<DirectoryEntry,String> categoryCol = new TableColumn<DirectoryEntry,String>("Category/Tab");
        categoryCol.setCellValueFactory(new PropertyValueFactory("categoryTab"));
        categoryCol.prefWidthProperty().bind(table.widthProperty().divide(3)); // w * 1/3

        table.getColumns().addAll(nameCol, phoneNumberCol, startCodePatternsCol, categoryCol);
        table.setItems(data);


//        final Text actiontarget = new Text();
//        grid.add(actiontarget, 0, 6);
//        grid.setColumnSpan(actiontarget, 2);
//        grid.setHalignment(actiontarget, RIGHT);
//        actiontarget.setId("actiontarget");

//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent e) {
//                actiontarget.setFill(Color.FIREBRICK);
//                actiontarget.setText("Sign in button pressed");
//            }
//        });


        TextArea statusArea = new TextArea();
        statusArea.setVisible(false);
        statusArea.setPrefRowCount(25);

        mainVLayout.getChildren().addAll(grid, excelButtonLayout, table);

        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if( StringUtils.isBlank(siteUrlTextField.getText()) ) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Missing URL");
                    alert.setHeaderText("URL required");
                    String s ="The URL to CallWorks product is required to process";
                    alert.setContentText(s);
                    alert.show();
                    return ;
                }
                if( StringUtils.isBlank(userTextField.getText()) ) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Missing User name");
                    alert.setHeaderText("User name required");
                    String s ="User name is required to login and process";
                    alert.setContentText(s);
                    alert.show();
                    return ;
                }
                if( StringUtils.isBlank(pwBox.getText()) ) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Missing Password");
                    alert.setHeaderText("Password required");
                    String s ="Password is required to login and process";
                    alert.setContentText(s);
                    alert.show();
                    return ;
                }



                result.setText("Logged in.  Select Excel file containing customer data");
                excelBtn.setDisable(false);
            }
        });

        excelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Directory File");

                File file = fileChooser.showOpenDialog(primaryStage);
                if( file==null || !file.exists() ) {
                    return ;
                }

                if( mainVLayout.getChildren().contains(statusArea) ) {
                    mainVLayout.getChildren().remove(statusArea);
                    statusArea.setVisible(false);

                    mainVLayout.getChildren().add(table);
                    table.setVisible(true);
                }

                // File file = new File("C:\\dev\\projects\\ecx\\VintonOhDirec.xlsx");
                data.clear();

                readData(file);

                table.requestLayout();

                excelBtn.setDisable(true);
                importBtn.setDisable(false);
//                if( mainVLayout.getChildren().contains(table) ) {
//                    mainVLayout.getChildren().remove(table);
//                    table.setVisible(false);
//                }
//
//                mainVLayout.getChildren().add(statusArea);
//                statusArea.setVisible(true);
            }
        });

        importBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                importBtn.setDisable(true);
                cancelBtn.setDisable(false);

                if( mainVLayout.getChildren().contains(table) ) {
                    mainVLayout.getChildren().remove(table);
                    table.setVisible(false);
                    mainVLayout.getChildren().add(statusArea);
                    statusArea.setVisible(true);
                }
            }
        });

        cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                excelBtn.setDisable(false);
                importBtn.setDisable(false);
                cancelBtn.setDisable(true);
//
//                if( mainVLayout.getChildren().contains(table) ) {
//                    mainVLayout.getChildren().remove(table);
//                    table.setVisible(false);
//                }
//
//                mainVLayout.getChildren().add(statusArea);
//                statusArea.setVisible(true);
            }
        });




        Scene scene = new Scene(mainVLayout, 900, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void readData(File file) {
        // Name--
        // Phone Number--
        // Star Code Pattern--
        // Category/Tab--
        // Bool--
        // Common Place?--
        // Bool--
        List<DirectoryEntry>directoryEntries = new ArrayList<DirectoryEntry>();
        List<String>directoryTypes = new ArrayList<String>();
        try {
            FileInputStream excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            int rowCnt=0;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();

                // skip the first 3 rows
                rowCnt++;
                if(rowCnt<=3) {
                    continue ;
                }


                // iterate over cells on the row
                int columnCnt=0;
                String name = null;
                String phoneNumber = null;
                String starCodePattern = null;
                String categoryTab = null;

                Iterator<Cell> columnIterator = currentRow.iterator();
                while (columnIterator.hasNext()) {
                    Cell currentCell = columnIterator.next();

                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    String value = "";
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        value = currentCell.getStringCellValue();
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        value = "" + currentCell.getNumericCellValue();
                    }

                    columnCnt++;
                    switch(columnCnt) {
                        case 1:
                            name = value;
                            break;
                        case 2:
                            phoneNumber = value;
                            break;
                        case 3:
                            starCodePattern = value;
                            break;
                        case 4:
                            categoryTab = value;
                            if( !directoryTypes.contains(categoryTab) ) {
                                directoryTypes.add(categoryTab);
                            }
                            break;
                    }
                }

                if(StringUtils.isNotBlank(name)) {
                    directoryEntries.add(new DirectoryEntry(name, phoneNumber, starCodePattern, categoryTab));
                    data.add(new DirectoryEntry(name, phoneNumber, starCodePattern, categoryTab));
                }
            }

            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    final ObservableList<DirectoryEntry> data = FXCollections.observableArrayList();

}
