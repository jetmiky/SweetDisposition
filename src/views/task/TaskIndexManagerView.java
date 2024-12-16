package views.task;

import exceptions.FormException;
import interfaces.ITaskController;
import java.net.URISyntaxException;
import java.util.Date;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Task;
import models.User;
import views.BaseView;
import views.components.AlertComponent;

public class TaskIndexManagerView extends BaseView {

	private ITaskController controller;
	private ObservableList<Task> tasks;
	
	public TaskIndexManagerView(ITaskController controller) {
		User user = auth().user();
		
		this.controller = controller;
		this.tasks = FXCollections.observableArrayList(user.getTasks());
	}

	public Pane render() {
		Text hello = new Text("Hello, Manager" );
		hello.setFont(new Font("Open Sans Bold", 30));
		hello.setFill(Color.BLACK);		
		
	    ImageView profileImageView = new ImageView();
	    try {
	        // Load the profile picture
			Image profileImage = new Image(getClass().getResource("/resources/header/fotoadmin.jpg").toURI().toString());
	        profileImageView.setImage(profileImage);
	        profileImageView.setFitWidth(80); // Set the width of the rectangle
	        profileImageView.setFitHeight(80); // Set the height of the rectangle

	        // Optional: Add rounded corners to the rectangle
	        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(80, 80); // Width and height of the rectangle
	        clip.setArcWidth(20); // Rounded corner width
	        clip.setArcHeight(20); // Rounded corner height// CenterX, CenterY, Radius
	        profileImageView.setClip(clip);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		Text title = new Text("Manage Tasks");
		title.setFont(new Font(24));

		Button createTaskButton = new Button("+ Create Task");
		createTaskButton.setStyle(
			    "-fx-background-color: #1E88E5; " +
					    "-fx-text-fill: white; " +
					    "-fx-font-size: 14px; " +
					    "-fx-font-weight: bold; " +
					    "-fx-background-radius: 5px; ");
		createTaskButton.setOnMouseEntered(event -> {
			createTaskButton.setStyle(createTaskButton.getStyle() + "-fx-background-color: #1565C0;"); // Darker blue on hover
		});
		createTaskButton.setOnMouseExited(event -> {
			createTaskButton.setStyle(createTaskButton.getStyle().replace("-fx-background-color: #1565C0;", "-fx-background-color: #1E88E5;")); // Original color when mouse exits
		});
		
		createTaskButton.setOnAction(e -> new TaskFormView(controller, tasks).show());
	  
		// HBox for title and profile picture
	    HBox header = new HBox(20); // 20px spacing between the profile picture and the text
	    header.getChildren().addAll(profileImageView, hello);
	    header.setAlignment(Pos.CENTER_LEFT);
	    
	    VBox headers = new VBox(8);
	    headers.getChildren().addAll(header, title);
	    headers.setAlignment(Pos.CENTER_LEFT);


		// Search bar
		TextField searchField = new TextField();
		searchField.setPromptText("Cari Tasks");
		searchField.setPrefWidth(300);
		
		Button searchButton = new Button("Cari");
		// "Cari" button with hover effect
		searchButton.setStyle(
		    "-fx-background-color: #1E88E5; " +
		    "-fx-text-fill: white; " +
		    "-fx-font-size: 14px; " +
		    "-fx-font-weight: bold; " +
		    "-fx-background-radius: 5px;");

		// Add hover effect for "Cari" button
		searchButton.setOnMouseEntered(event -> {
		    searchButton.setStyle(searchButton.getStyle() + "-fx-background-color: #1565C0;"); // Darker blue on hover
		});
		searchButton.setOnMouseExited(event -> {
		    searchButton.setStyle(searchButton.getStyle().replace("-fx-background-color: #1565C0;", "-fx-background-color: #1E88E5;")); // Original color when mouse exits
		});
		
		searchButton.setOnAction(e -> {
		    String query = searchField.getText().toLowerCase();
		    
		    // Filter tasks based on the title
		    List<Task> filteredTasks = tasks.stream()
		            .filter(task -> task.getTitle().toLowerCase().contains(query))
		            .collect(Collectors.toList());
		    
		    // Update the TableView with the filtered tasks
		    ((TableView<Task>) tasks).setItems(FXCollections.observableArrayList(filteredTasks));
		});
		
		HBox searchBar = new HBox(10, searchField, searchButton);
		searchBar.setAlignment(Pos.CENTER_RIGHT);
		
		HBox addSearchBar = new HBox(460, createTaskButton, searchBar);
		addSearchBar.setAlignment(Pos.CENTER_LEFT);
		
//		bar.setLeft(title);
//		bar.setRight(createTaskButton);
		
		//create tableview with tasks data
		TableView<Task> table = new TableView<>(tasks);
		TableColumn<Task, Integer> taskIdColumn = new TableColumn<>("ID");
		TableColumn<Task, String> taskTitleColumn = new TableColumn<>("Title");
		TableColumn<Task, String> taskDescriptionColumn = new TableColumn<>("Description");
		TableColumn<Task, Boolean> taskCompletedColumn = new TableColumn<>("Task Completed");
		TableColumn<Task, Date> taskCreatedAtColumn = new TableColumn<>("Created At");		

		taskIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		taskTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		taskCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("isCompleted"));
		taskCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
		
		table.getColumns().addAll(taskIdColumn, taskTitleColumn, taskDescriptionColumn, taskCompletedColumn, taskCreatedAtColumn);

		//Styling viewTaskButton
		ImageView ivTaskButton = new ImageView();
		try {
			Image taskButton = new Image(getClass().getResource("/resources/icons/assignrole.png").toURI().toString());
			ivTaskButton.setImage(taskButton);
			ivTaskButton.setFitWidth(20);  // Adjust the size of the icon
			ivTaskButton.setFitHeight(20);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		Button viewTaskButton = new Button("View Task Detail",ivTaskButton );
		viewTaskButton.setStyle(
		    "-fx-background-color: #FF9800; " + // Orange color for visual impact
		    "-fx-text-fill: white; " +
		    "-fx-font-size: 16px; " + // Larger font size for better readability
		    "-fx-font-weight: bold; " +
		    "-fx-background-radius: 5px; " + // Rounded corners for a modern look
		    "-fx-padding: 10px 20px; " + // Larger padding for a button that's easy to click
		    "-fx-transition: all 0.3s ease-in-out;"); // Smooth transition for hover effects
		// Hover effect
		viewTaskButton.setOnMouseEntered(event -> {
		    viewTaskButton.setStyle(
		        viewTaskButton.getStyle() + "-fx-background-color: #FF5722;"); // Darker orange when hovered
		});
		viewTaskButton.setOnMouseExited(event -> {
		    viewTaskButton.setStyle(
		        viewTaskButton.getStyle().replace("-fx-background-color: #FF5722;", "-fx-background-color: #FF9800;")); // Original color when mouse exits
		});

		viewTaskButton.setOnAction(e -> {
			try {
				Task task = table.getSelectionModel().getSelectedItem();
				if (task == null)
					throw new FormException("Please select a task to view");

				new TaskShowManagerView(controller, task).show();
			} catch (FormException error) {
				AlertComponent.error("Failed", error.getMessage());
			}
		});

		//Button Delete Task
		ImageView ivDelete = new ImageView();
		try {
			Image deleteTask = new Image(getClass().getResource("/resources/icons/filldelete.png").toURI().toString());
			ivDelete.setImage(deleteTask);
			ivDelete.setFitWidth(20);  // Adjust the size of the icon
			ivDelete.setFitHeight(20);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}


		Button deleteTaskButton = new Button("Delete Selected Task",ivDelete);
		deleteTaskButton.setStyle(
			    "-fx-background-color: #F44336; " + // Red for warning or delete
			    "-fx-text-fill: white; " +
			    "-fx-font-size: 16px; " +
			    "-fx-font-weight: bold; " +
			    "-fx-background-radius: 5px; " +
			    "-fx-padding: 10px 20px; " +
			    "-fx-transition: all 0.3s ease-in-out;");
		
		deleteTaskButton.setOnAction(e -> {
			try {
				Task task = table.getSelectionModel().getSelectedItem();

				controller.delete(task);
				table.getItems().remove(task);

				AlertComponent.success("Success", "Task successfully deleted");
			} catch (FormException error) {
				AlertComponent.error("Failed", error.getMessage());
			}
		});
		
		// Add hover effect for button
		deleteTaskButton.setOnMouseEntered(event -> {
			deleteTaskButton.setStyle(deleteTaskButton.getStyle() + "-fx-background-color: #D32F2F;");
		});
		deleteTaskButton.setOnMouseExited(event -> {
			deleteTaskButton.setStyle(deleteTaskButton.getStyle().replace("-fx-background-color: #D32F2F;", "-fx-background-color: #F44336;"));
		});
		
		HBox buttons = new HBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(viewTaskButton, deleteTaskButton);		

		// Ensure good alignment and spacing between the buttons
		buttons.setAlignment(Pos.CENTER);
		buttons.setStyle("-fx-padding: 20px; -fx-background-color: #F1F5F9;"); // Make sure the button group has appropriate padding

		// Load the background image
		Image backgroundImage = new Image(getClass().getResource("/resources/header/bg2.jpg").toString());
		BackgroundImage background = new BackgroundImage(
		    backgroundImage,
		    BackgroundRepeat.NO_REPEAT, // No horizontal repetition
		    BackgroundRepeat.NO_REPEAT, // No vertical repetition
		    BackgroundPosition.DEFAULT, // Align the image to the top-left corner
		    new BackgroundSize(
		        BackgroundSize.DEFAULT.getWidth(), // Use the image's original width
		        BackgroundSize.DEFAULT.getHeight(), // Use the image's original height
		        true,  // Scale to fit width
		        true,  // Scale to fit height
		        false, // Do not preserve aspect ratio
		        false  // Do not cover the container completely
		    )
		);
		// Create a region for the background
		Region backgroundRegion = new Region();
		backgroundRegion.setBackground(new Background(background));

		// Set a fixed height for the backgroundRegion to limit it to the top portion of the scene
		backgroundRegion.setMaxHeight(150); // Adjust the height as needed

		// StackPane layout
		StackPane root = new StackPane();
		
		// Main content container	
		VBox container = new VBox(10);
		container.setSpacing(10);
		container.getChildren().addAll(headers, addSearchBar, table, buttons);
	    container.setAlignment(Pos.TOP_CENTER); // Ensure all content is centered at the top
		// Add the background region to the StackPane
		root.getChildren().addAll(backgroundRegion, container);

		// Ensure the container is aligned to the top-center
		StackPane.setAlignment(container, Pos.TOP_CENTER);

		// Ensure the backgroundRegion is aligned to the top
		StackPane.setAlignment(backgroundRegion, Pos.TOP_LEFT);
		backgroundRegion.prefWidthProperty().bind(root.widthProperty()); // Bind width to scene width		
	    
		container.setPadding(new Insets(160, 30, 30, 30)); // Add top padding equal to backgroundRegion height
		
		return root;
	}

}
