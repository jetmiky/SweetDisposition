package views.task;

import exceptions.FormException;
import interfaces.ITaskController;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Task;
import models.User;
import views.BaseView;
import views.components.AlertComponent;

public class TaskIndexStaffView extends BaseView {

    private ITaskController controller;
    private ObservableList<Task> tasks;

    public TaskIndexStaffView(ITaskController controller) {
    	User user = auth().user();
    	
        this.controller = controller;
        this.tasks = FXCollections.observableArrayList(user.getTasks());
    }

    @Override
    public Pane render() {
    	 // Header
        Text hello = new Text("Hello, Staff");
        hello.setFont(new Font("Open Sans Bold", 30));
        hello.setFill(Color.BLACK);

        ImageView profileImageView = new ImageView();
        try {
            Image profileImage = new Image(getClass().getResource("/resources/header/fotoadmin.jpg").toURI().toString());
            profileImageView.setImage(profileImage);
            profileImageView.setFitWidth(80);
            profileImageView.setFitHeight(80);
            javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(80, 80);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            profileImageView.setClip(clip);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HBox header = new HBox(20, profileImageView, hello);
        header.setAlignment(Pos.CENTER_LEFT);

        // Search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search Tasks");
        searchField.setPrefWidth(300);

        Button searchButton = new Button("Search");
        searchButton.setStyle(
            "-fx-background-color: #1E88E5; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-background-radius: 5px;");

        // Add hover effect for search button
        searchButton.setOnMouseEntered(event -> {
            searchButton.setStyle(searchButton.getStyle() + "-fx-background-color: #1565C0;");
        });
        searchButton.setOnMouseExited(event -> {
            searchButton.setStyle(searchButton.getStyle().replace("-fx-background-color: #1565C0;", "-fx-background-color: #1E88E5;"));
        });

        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            // Filter tasks based on title
            List<Task> filteredTasks = tasks.stream()
                    .filter(task -> task.getTitle().toLowerCase().contains(query))
                    .collect(Collectors.toList());
            // Update the TableView with filtered tasks
            ((TableView<Task>) tasks).setItems(FXCollections.observableArrayList(filteredTasks));
        });

        HBox searchBar = new HBox(10, searchField, searchButton);
        searchBar.setAlignment(Pos.CENTER_RIGHT);

        HBox addSearchBar = new HBox(460, searchBar);
        addSearchBar.setAlignment(Pos.CENTER_LEFT);

        // Task Table
        TableView<Task> table = new TableView<>(tasks);
        TableColumn<Task, String> taskIdColumn = new TableColumn<>("ID");
        TableColumn<Task, String> taskTitleColumn = new TableColumn<>("Title");
        TableColumn<Task, String> taskDescriptionColumn = new TableColumn<>("Description");
        TableColumn<Task, Boolean> taskCompletedColumn = new TableColumn<>("Completed");
        TableColumn<Task, String> taskCreatedAtColumn = new TableColumn<>("Created At");

        taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("isCompleted"));
        taskCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        table.getColumns().addAll(taskIdColumn, taskTitleColumn, taskDescriptionColumn, taskCompletedColumn, taskCreatedAtColumn);

        // View Task Button
        Button viewTaskButton = new Button("View Task");
        viewTaskButton.setStyle(
            "-fx-background-color: #FF9800; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-background-radius: 5px; ");
        viewTaskButton.setOnMouseEntered(event -> {
            viewTaskButton.setStyle(viewTaskButton.getStyle() + "-fx-background-color: #FF5722;");
        });
        viewTaskButton.setOnMouseExited(event -> {
            viewTaskButton.setStyle(viewTaskButton.getStyle().replace("-fx-background-color: #FF5722;", "-fx-background-color: #FF9800;"));
        });

        viewTaskButton.setOnAction(e -> {
            try {
                Task task = table.getSelectionModel().getSelectedItem();
                if (task == null)
                    throw new FormException("Please select a task to view");
                
                new TaskShowStaffView(controller, task).show();
            } catch (FormException error) {
                AlertComponent.error("Failed", error.getMessage());
            }
        });

        // Buttons
        HBox buttons = new HBox(viewTaskButton);
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));

        // Background Image
        Image backgroundImage = new Image(getClass().getResource("/resources/header/bg2.jpg").toString());
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(
                BackgroundSize.DEFAULT.getWidth(),
                BackgroundSize.DEFAULT.getHeight(),
                true,
                true,
                false,
                false));

        Region backgroundRegion = new Region();
        backgroundRegion.setBackground(new Background(background));
        backgroundRegion.setMaxHeight(150);

        // Root Layout
        StackPane root = new StackPane();
        VBox container = new VBox(header, addSearchBar, table, buttons);
        container.setSpacing(20);
        container.setPadding(new Insets(160, 30, 30, 30));

        root.getChildren().addAll(backgroundRegion, container);
        StackPane.setAlignment(container, Pos.TOP_CENTER);
        StackPane.setAlignment(backgroundRegion, Pos.TOP_LEFT);
        backgroundRegion.prefWidthProperty().bind(root.widthProperty());

        return root;
    }
}
