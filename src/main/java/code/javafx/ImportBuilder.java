package code.javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ImportBuilder extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Directory Import Builder");

        VBox root = new VBox();

        //--------------------------------------------------------------
        VBox header = new VBox();
        header.setSpacing(6.0);

        header.getChildren().add(new Label("Enter your API key, the Workspace id for the customer, and Worksheet to put the data pulled from Airtables"));

        Button exportButton = new Button("Build Excel Worksheet");
        exportButton.setDisable(true);
        header.getChildren().add(exportButton);

        VBox.setMargin(header, new Insets(6, 0, 0, 6));
        header.setPadding(new Insets(6, 0, 0, 6));
        root.getChildren().add(header);

        //--------------------------------------------------------------
        VBox apiKeyBox = new VBox();
        apiKeyBox.setPrefHeight(200.0);
        apiKeyBox.getChildren().add(new Label("Enter your Airtable API key  (Your key is located in the middle of your Account page)"));

        Hyperlink link = new Hyperlink("Account page at https://airtable.com/account");
        apiKeyBox.getChildren().add(link);
        link.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                getHostServices().showDocument("https://airtable.com/account");
            }
        });

        TextField apiText = new TextField();
        apiKeyBox.getChildren().add(apiText);

        TitledPane apiKeyPane = new TitledPane("Airtable API Key", apiKeyBox);
        apiKeyPane.setPrefHeight(106.0);
        root.getChildren().add(apiKeyPane);
        VBox.setMargin(apiKeyPane, new Insets(15, 0, 0, 6));


        //--------------------------------------------------------------
        VBox workspaceBox = new VBox();
        workspaceBox.setPrefHeight(200.0);
        workspaceBox.getChildren().add(new Label("Get the id by selecting the workspace from https://airtable.com/api, then pull the id out of the http:// text"));
        workspaceBox.getChildren().add(new Label("Example: Selecting Enid will display https://airtable.com/appUgdwFKLNeC8yW2/api/docs for the URL"));
        workspaceBox.getChildren().add(new Label("The id is appUgdwFKLNeC8yW2"));

        Hyperlink workspaceLink = new Hyperlink("Select workspace at https://airtable.com/api");
        workspaceBox.getChildren().add(workspaceLink);
        workspaceLink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                getHostServices().showDocument("https://airtable.com/api");
            }
        });

        TextField workspaceIdText = new TextField();
        workspaceBox.getChildren().add(workspaceIdText);

        TitledPane workspanePane = new TitledPane("Airtable Workspace Id", workspaceBox);
        workspanePane.setPrefHeight(106.0);
        root.getChildren().add(workspanePane);
        VBox.setMargin(workspanePane, new Insets(15, 0, 0, 6));


        //--------------------------------------------------------------
        VBox workbookBox = new VBox();
        workbookBox.setPrefHeight(200.0);
        workbookBox.getChildren().add(new Label("Enter the full path of the Excel worksheet that will be built." ));

        TextField workbookText = new TextField();
        workbookBox.getChildren().add(workbookText);

        TitledPane workbookPane = new TitledPane("Output Excel Worksheet", workbookBox);
        workbookPane.setPrefHeight(106.0);
        root.getChildren().add(workbookPane);
        VBox.setMargin(workbookPane, new Insets(15, 0, 0, 6));

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
