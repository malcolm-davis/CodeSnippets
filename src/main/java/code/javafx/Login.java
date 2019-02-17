package code.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CallWorks Directory Importer");
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

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        Button excelBtn = new Button("Select Excel File");
        HBox excelHbBtn = new HBox(10);
        excelHbBtn.setAlignment(Pos.BOTTOM_LEFT);
        excelHbBtn.getChildren().add(excelBtn);
        grid.add(excelHbBtn, 1, 6);



        final TableView table = new TableView();
        HBox hbTable = new HBox(10);
        hbTable.setAlignment(Pos.BOTTOM_LEFT);
        hbTable.getChildren().add(table);
        grid.add(hbTable, 1, 7);

        // Name--
        // Phone Number--
        // Star Code Pattern--
        // Category/Tab--
        // Bool--
        // Common Place?--
        // Bool--
        TableColumn nameCol = new TableColumn("Name");
        TableColumn phoneNumberCol = new TableColumn("Phone Number");
        TableColumn startCodePatternsCol = new TableColumn("Star Code Pattern");
        TableColumn categoryCol = new TableColumn("Category/Tab");
        table.getColumns().addAll(nameCol, phoneNumberCol, startCodePatternsCol, categoryCol);

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

        Scene scene = new Scene(grid, 600, 750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
