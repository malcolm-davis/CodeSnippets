package code.javafx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PropertyTableViewer extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        final StackPane root = new StackPane();
        root.getChildren().add(getTableView());

        final Scene scene = new Scene(root, 700, 250);

        primaryStage.setTitle("Property View");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    TableView<PropertyWrapper>getTableView() {
        if(tableView==null) {
            tableView = new TableView<PropertyWrapper>();
            tableView.setPrefWidth(900.0);

            TableColumn<PropertyWrapper, String> keyCol = new TableColumn<>("Key");
            keyCol.setPrefWidth(75.0);
            // keyCol.setStyle("-fx-alignment: LEFT;");
            keyCol.setCellValueFactory(new PropertyValueFactory("key"));

            TableColumn<PropertyWrapper, String> valueCol = new TableColumn<>("Value");
            valueCol.setPrefWidth(225.0);
            //valueCol.setStyle("-fx-alignment: LEFT;");
            valueCol.setCellValueFactory(new PropertyValueFactory("value"));

            TableColumn<PropertyWrapper, String> issueCol = new TableColumn<>("Issue");
            issueCol.setPrefWidth(400.0);
            //valueCol.setStyle("-fx-alignment: LEFT;");
            issueCol.setCellValueFactory(new PropertyValueFactory("issue"));

            tableView.getColumns().add(keyCol);
            tableView.getColumns().add(valueCol);
            tableView.getColumns().add(issueCol);

            tableView.setItems(data);
        }
        return tableView;
    }

    private final ObservableList<PropertyWrapper> data =
            FXCollections.observableArrayList(
                new PropertyWrapper("name", "Jacob Smith", null),
                new PropertyWrapper("email", "isabella.johnson@example.com", null),
                new PropertyWrapper("bday", "10.10.2000", null) );

    private TableView<PropertyWrapper> tableView;
}