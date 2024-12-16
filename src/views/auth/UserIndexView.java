package views.auth;

import exceptions.FormException;
import interfaces.IUserController;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import models.User;
import views.BaseView;
import views.components.AlertComponent;

public class UserIndexView extends BaseView {

	private IUserController controller;
	private TableView<User> table;
	private ObservableList<User> users;

	public UserIndexView(IUserController controller, List<User> users) {
		this.controller = controller;
		this.users = FXCollections.observableArrayList(users);
	}

	@Override
	public Pane render() {
       
		Text title = new Text("Hello, Admin" );
		title.setFont(new Font("Open Sans Bold", 30));
		title.setFill(Color.BLACK);
		
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

		Button addUserButton = new Button("+ Tambah User");
		// "Tambah User" button with hover effect
		addUserButton.setStyle(
		    "-fx-background-color: #1E88E5; " +
		    "-fx-text-fill: white; " +
		    "-fx-font-size: 14px; " +
		    "-fx-font-weight: bold; " +
		    "-fx-background-radius: 5px; ");

		// Add hover effect for "Tambah User" button
		addUserButton.setOnMouseEntered(event -> {
		    addUserButton.setStyle(addUserButton.getStyle() + "-fx-background-color: #1565C0;"); // Darker blue on hover
		});
		addUserButton.setOnMouseExited(event -> {
		    addUserButton.setStyle(addUserButton.getStyle().replace("-fx-background-color: #1565C0;", "-fx-background-color: #1E88E5;")); // Original color when mouse exits
		});

		addUserButton.setOnAction(e -> new UserFormView(controller).show());


	    // HBox for title and profile picture
	    HBox header = new HBox(20); // 20px spacing between the profile picture and the text
	    header.getChildren().addAll(profileImageView, title);
	    header.setAlignment(Pos.CENTER_LEFT);

		// Search bar
		TextField searchField = new TextField();
		searchField.setPromptText("Cari User");
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
			List<User> filteredUsers = users.stream().filter(user -> user.getName().toLowerCase().contains(query))
					.collect(Collectors.toList());
			table.setItems(FXCollections.observableArrayList(filteredUsers));
		});

		HBox searchBar = new HBox(10, searchField, searchButton);
		searchBar.setAlignment(Pos.CENTER_RIGHT);
		
		HBox addSearchBar = new HBox(460, addUserButton, searchBar);
		addSearchBar.setAlignment(Pos.CENTER_LEFT);
		
		// Table view
		int totalAdmins = 0, totalManagers = 0, totalStaff = 0;
		if (users != null && !users.isEmpty()) {
			totalAdmins = (int) users.stream().filter(user -> "Admin".equals(user.getRole())).count();
			totalManagers = (int) users.stream().filter(user -> "Manager".equals(user.getRole())).count();
			totalStaff = (int) users.stream().filter(user -> "Staff".equals(user.getRole())).count();
		}

		// Create and display the cards with text inside the rectangles
		HBox adminCard = createCard(totalAdmins, "  Admins");
		HBox managerCard = createCard(totalManagers, "  Managers");
		HBox staffCard = createCard(totalStaff, "  Staff");

		HBox cards = new HBox(20, adminCard, managerCard, staffCard);
		cards.setAlignment(Pos.CENTER_LEFT);
	    cards.setPadding(new Insets(0, 0, 10, 0)); // Add bottom margin to separate from the next section


		// Create the TableView with user data
		table = new TableView<>(users);

		// Remove any custom styles to revert to default appearance
		table.setStyle(""); // Reset any previous custom styles

		// Defining the columns
		TableColumn<User, String> noColumn = new TableColumn<>("No");
		TableColumn<User, String> nameColumn = new TableColumn<>("Nama");
		TableColumn<User, String> emailColumn = new TableColumn<>("Email");
		TableColumn<User, String> roleColumn = new TableColumn<>("Role");
		TableColumn<User, String> spvColumn = new TableColumn<>("Supervisor");

		noColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
		spvColumn.setCellValueFactory(cellData -> {
			User supervisor = cellData.getValue().getSupervisor();
			
			return new javafx.beans.property.SimpleStringProperty(
		    		supervisor.exists() ? supervisor.getName() : "-"
		    );
		});

		// Add the columns to the table
		table.getColumns().addAll(noColumn, nameColumn, emailColumn, roleColumn, spvColumn);

		// Set the table's column resize policy to the default (based on column content size)
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// Reset row factory to default, removing custom hover effect
		table.setRowFactory(null); // This restores the default behavior of rows

		
		//============== Bottom Button =======================//
		ImageView ivAssignStaff = new ImageView();
		try {
			Image assignStaff = new Image(getClass().getResource("/resources/icons/assignrole.png").toURI().toString());
			ivAssignStaff.setImage(assignStaff);
			ivAssignStaff.setFitWidth(20);  // Adjust the size of the icon
			ivAssignStaff.setFitHeight(20);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		Button assignStaffButton = new Button("Assign Staff to Manager", ivAssignStaff);
		assignStaffButton.setStyle(
		    "-fx-background-color: #FF9800; " + // Orange color for visual impact
		    "-fx-text-fill: white; " +
		    "-fx-font-size: 16px; " + // Larger font size for better readability
		    "-fx-font-weight: bold; " +
		    "-fx-background-radius: 5px; " + // Rounded corners for a modern look
		    "-fx-padding: 10px 20px; " + // Larger padding for a button that's easy to click
		    "-fx-transition: all 0.3s ease-in-out;"); // Smooth transition for hover effects

		assignStaffButton.setOnAction(e -> {
		    try {
		        User user = table.getSelectionModel().getSelectedItem();
		        List<User> managers = users.stream().filter(u -> u.getRole().equalsIgnoreCase("manager"))
		                .collect(Collectors.toList());

		        if (user == null || !user.getRole().equalsIgnoreCase("staff"))
		            throw new FormException("Please select a staff to be assigned");

		        new UserAssignStaff(controller, user, managers).show();
		        table.refresh();
		    } catch (FormException error) {
		        AlertComponent.error("Gagal", error.getMessage());
		    }
		});

		// Add hover effect for button
		assignStaffButton.setOnMouseEntered(event -> {
		    assignStaffButton.setStyle(assignStaffButton.getStyle() + "-fx-background-color: #FF5722;"); // Change to a darker orange on hover
		});
		assignStaffButton.setOnMouseExited(event -> {
		    assignStaffButton.setStyle(assignStaffButton.getStyle().replace("-fx-background-color: #FF5722;", "-fx-background-color: #FF9800;")); // Return to original color when mouse exits
		});

		ImageView ivAssignRoleIcon = new ImageView();
		try {
			Image assignRoleIcon = new Image(getClass().getResource("/resources/icons/fillassigncheck.png").toURI().toString());
			ivAssignRoleIcon.setImage(assignRoleIcon);
			ivAssignRoleIcon.setFitWidth(20);  // Adjust the size of the icon
			ivAssignRoleIcon.setFitHeight(20);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		
		Button assignRoleButton = new Button("Assign Role", ivAssignRoleIcon);
		assignRoleButton.setStyle(
		    "-fx-background-color: #4CAF50; " + // Green color for positive action
		    "-fx-text-fill: white; " +
		    "-fx-font-size: 16px; " +
		    "-fx-font-weight: bold; " +
		    "-fx-background-radius: 5px; " +
		    "-fx-padding: 10px 20px; " +
		    "-fx-transition: all 0.3s ease-in-out;");

		assignRoleButton.setOnAction(e -> {
		    try {
		        User user = table.getSelectionModel().getSelectedItem();

		        if (user == null) {
		            throw new FormException("Please select a user to change role");
		        }

		        new UserAssignRole(controller, user).show();
		        table.refresh();
		    } catch (FormException error) {
		        AlertComponent.error("Gagal", error.getMessage());
		    }
		});

		// Add hover effect for button
		assignRoleButton.setOnMouseEntered(event -> {
		    assignRoleButton.setStyle(assignRoleButton.getStyle() + "-fx-background-color: #388E3C;");
		});
		assignRoleButton.setOnMouseExited(event -> {
		    assignRoleButton.setStyle(assignRoleButton.getStyle().replace("-fx-background-color: #388E3C;", "-fx-background-color: #4CAF50;"));
		});

		
		ImageView ivDelete = new ImageView();
		try {
			Image deleteStaff = new Image(getClass().getResource("/resources/icons/filldelete.png").toURI().toString());
			ivDelete.setImage(deleteStaff);
			ivDelete.setFitWidth(20);  // Adjust the size of the icon
			ivDelete.setFitHeight(20);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		Button deleteButton = new Button("Delete User", ivDelete);
		deleteButton.setStyle(
		    "-fx-background-color: #F44336; " + // Red for warning or delete
		    "-fx-text-fill: white; " +
		    "-fx-font-size: 16px; " +
		    "-fx-font-weight: bold; " +
		    "-fx-background-radius: 5px; " +
		    "-fx-padding: 10px 20px; " +
		    "-fx-transition: all 0.3s ease-in-out;");

		deleteButton.setOnAction(e -> {
		    User user = table.getSelectionModel().getSelectedItem();

		    if (user == null) {
		        AlertComponent.error("Error", "Please select a user to delete");
		        return;
		    }

		    AlertComponent.confirm("Konfirmasi Hapus", "Anda yakin ingin menghapus " + user.getName() + "?")
		            .ifPresent(response -> {
		                if (response == ButtonType.OK) {
		                    try {
		                        controller.delete(user);
		                        users.remove(user);

		                        AlertComponent.success("Berhasil", "Pengguna berhasil dihapus!");
		                    } catch (Exception error) {
		                        AlertComponent.error("Gagal", error.getMessage());
		                    }
		                }
		            });
		});

		// Add hover effect for button
		deleteButton.setOnMouseEntered(event -> {
		    deleteButton.setStyle(deleteButton.getStyle() + "-fx-background-color: #D32F2F;");
		});
		deleteButton.setOnMouseExited(event -> {
		    deleteButton.setStyle(deleteButton.getStyle().replace("-fx-background-color: #D32F2F;", "-fx-background-color: #F44336;"));
		});

		HBox buttons = new HBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(assignRoleButton, assignStaffButton, deleteButton);

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
		VBox container = new VBox(header, cards, addSearchBar, table, buttons);
		container.setSpacing(10);
	    container.setAlignment(Pos.TOP_CENTER); // Ensure all content is centered at the top

		// Add the background region to the StackPane
		root.getChildren().addAll(backgroundRegion, container);

		// Ensure the container is aligned to the top-center
		StackPane.setAlignment(container, Pos.TOP_CENTER);

		// Ensure the backgroundRegion is aligned to the top
		StackPane.setAlignment(backgroundRegion, Pos.TOP_LEFT);
		backgroundRegion.prefWidthProperty().bind(root.widthProperty()); // Bind width to scene width

		// Adjust container layout to avoid overlapping the background image
		container.setPadding(new Insets(160, 30, 30, 30)); // Add top padding equal to backgroundRegion height

		return root;
	}

	private HBox createCard(int count, String title) {
	    // Set card background properties
	    javafx.scene.shape.Rectangle cardBackground = new javafx.scene.shape.Rectangle(250, 100);
	    cardBackground.setArcWidth(20); // Rounded corners
	    cardBackground.setArcHeight(10);
	    cardBackground.setFill(Color.LIGHTBLUE); // Background color for visual clarity

	    // Create text for the card
	    Text cardText = new Text(count + title);
	    cardText.setFont(new Font("Open Sans", 20));
	    cardText.setFill(Color.DARKBLUE);

	    // Add text to a VBox for alignment
	    VBox cardContent = new VBox(cardText);
	    cardContent.setAlignment(Pos.CENTER); // Center the text within the card
	    cardContent.setSpacing(5); // Add spacing if there are multiple elements

	    // Create a StackPane to layer the background and content
	    StackPane card = new StackPane();
	    card.getChildren().addAll(cardBackground, cardContent);

	    // Set card size
//	    card.setPrefSize(150, 60);

	    // Wrap the card in an HBox for consistent alignment
	    HBox cardContainer = new HBox(card);
	    cardContainer.setAlignment(Pos.CENTER); // Align the card centrally in its container

	    return cardContainer;
	}


}
