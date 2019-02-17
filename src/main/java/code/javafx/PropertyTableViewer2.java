package code.javafx;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import code.classType.ConfigOptions;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PropertyTableViewer2 extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        Properties config = new Properties();
        config.setProperty(ConfigOptions.BooleanSample.toString(), "True");
        config.setProperty(ConfigOptions.DoubleSample.toString(), "10.10");
        config.setProperty(ConfigOptions.IntSample.toString(), "44");
        config.setProperty(ConfigOptions.ModeSample.toString(), "truethy");
        config.setProperty("NAME", "a name");
        config.setProperty("isEdits", "an edit");
        config.setProperty("leve", "a level");
        config.setProperty("level", "a level");
        config.setProperty("mockKey", "mockValue");

//        List<String>issues = ConfigOptions.validateConfig(config);
//        System.out.println("********************************************************");
//        for (String issue : issues) {
//            System.out.println(issue);
//        }


        //---------------------------------------------------------------------------------------
        ObservableList<PropertyWrapper> issueData = FXCollections.observableArrayList();
        Map<String,String>issues = ConfigOptions.validateConfig(config);
        int heightCnt=0;
        for (String issueKey : issues.keySet()) {
            String value = (config.get(issueKey)==null) ? "" : config.get(issueKey).toString();
            issueData.add(new PropertyWrapper(issueKey, value, issues.get(issueKey)));
            heightCnt++;
        }

        getProblemTable().setItems(issueData);
        final VBox issuePane = new VBox();
        issuePane.setSpacing(5);
        issuePane.setPadding(new Insets(10, 10, 5, 10));
        Label issueLabel = new Label("Potential cwbrowser.cfg issues");
        issueLabel.setFont(new Font("Arial", 16));
        issuePane.getChildren().addAll(issueLabel, getProblemTable());
        if(heightCnt==0) {
            issuePane.setPrefHeight(0);
        } else {
            double height = 90 + (heightCnt*60);
            issuePane.setPrefHeight(height);
        }

        //---------------------------------------------------------------------------------------
        ObservableList<PropertyWrapper> data = FXCollections.observableArrayList();
        Map<ConfigOptions, Object>options = ConfigOptions.translate(config);
        for (ConfigOptions key : options.keySet()) {
            data.add(new PropertyWrapper(key.toString(), options.get(key).toString(), null));
        }
        getPropetyTable().setItems(data);

        final VBox configPane = new VBox();
        configPane.setSpacing(5);
        configPane.setPadding(new Insets(10, 10, 5, 10));
        Label propertyLabel = new Label("Browser deciphered view of the cwbrowser.cfg");
        propertyLabel.setFont(new Font("Arial", 16));
        configPane.getChildren().addAll(propertyLabel, getPropetyTable());


        //---------------------------------------------------------------------------------------
        Button resetButton = new Button("Reset configuration to default");
        resetButton.setOnAction((event) -> {
            // Button was clicked, do something...
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Reset confirmation");
            alert.setHeaderText("A reset is irreversible, and will restart the browser");
            alert.setContentText("Click Yes to reset and restart the browser.  Click No to cancel.");

            // override the button behavior so that YES/OK/Enter is not accidently clicked.
            alert.getButtonTypes().clear();
            alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

            //Deactivate Defaultbehavior for yes-Button:
            Button yesButton = (Button) alert.getDialogPane().lookupButton( ButtonType.YES);
            yesButton.setDefaultButton( false );

            //Activate Defaultbehavior for no-Button:
            Button noButton = (Button) alert.getDialogPane().lookupButton( ButtonType.NO);
            noButton.setDefaultButton( true );

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES){
                // ... user chose OK
                System.out.println("OK");
            } else {
                // ... user chose CANCEL or closed the dialog
                System.out.println("CANCEL");
            }
        });
        final VBox resetPane = new VBox();
        resetPane.setSpacing(5);
        resetPane.setPadding(new Insets(10, 10, 5, 10));
        resetPane.getChildren().addAll(resetButton);



        //---------------------------------------------------------------------------------------
        final VBox root = new VBox();
        root.setSpacing(20);
        if(heightCnt==0) {
            root.getChildren().addAll(configPane, resetPane);
        } else {
            root.getChildren().addAll(issuePane, configPane,resetPane);
        }
        final Scene scene = new Scene(root, 700, 500);

        primaryStage.setTitle("Property View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    TableView<PropertyWrapper>getProblemTable() {
        if(problemTableView==null) {
            problemTableView = new TableView<PropertyWrapper>();
            problemTableView.setPrefWidth(800);

            TableColumn<PropertyWrapper, String> keyCol = new TableColumn<>("Key");
            keyCol.setPrefWidth(75.0);
            // keyCol.setStyle("-fx-alignment: LEFT;");
            keyCol.setCellValueFactory(new PropertyValueFactory("key"));

            TableColumn<PropertyWrapper, String> valueCol = new TableColumn<>("Value");
            valueCol.setPrefWidth(150.0);
            //valueCol.setStyle("-fx-alignment: LEFT;");
            valueCol.setCellValueFactory(new PropertyValueFactory("value"));

            TableColumn<PropertyWrapper, String> issueCol = new TableColumn<>("Issue");
            issueCol.setPrefWidth(400.0);
            //valueCol.setStyle("-fx-alignment: LEFT;");
            issueCol.setCellValueFactory(new PropertyValueFactory("issue"));

            problemTableView.getColumns().add(keyCol);
            problemTableView.getColumns().add(valueCol);
            problemTableView.getColumns().add(issueCol);
        }
        return problemTableView;
    }

    private TableView<PropertyWrapper> problemTableView;

    TableView<PropertyWrapper>getPropetyTable() {
        if(propertyTableView==null) {
            propertyTableView = new TableView<PropertyWrapper>();
            propertyTableView.setPrefWidth(800.0);

            TableColumn<PropertyWrapper, String> keyCol = new TableColumn<>("Key");
            keyCol.setPrefWidth(75.0);
            // keyCol.setStyle("-fx-alignment: LEFT;");
            keyCol.setCellValueFactory(new PropertyValueFactory("key"));

            TableColumn<PropertyWrapper, String> valueCol = new TableColumn<>("Value");
            valueCol.setPrefWidth(150.0);
            //valueCol.setStyle("-fx-alignment: LEFT;");
            valueCol.setCellValueFactory(new PropertyValueFactory("value"));

            propertyTableView.getColumns().add(keyCol);
            propertyTableView.getColumns().add(valueCol);
        }
        return propertyTableView;
    }

    private TableView<PropertyWrapper> propertyTableView;
}