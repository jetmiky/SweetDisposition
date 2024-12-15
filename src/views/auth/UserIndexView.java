package views.auth;

import exceptions.FormException;
import interfaces.IUserController;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
		Text title = new Text("Hello, Admin");
		title.setFont(new Font("Open Sans", 30));
		title.setFill(Color.BLACK);

		Button addUserButton = new Button("Tambah User");
		addUserButton.setStyle("-fx-background-color: #1E88E5; " + " -fx-text-fill: white; " + "-fx-font-size: 14px; "
				+ "-fx-font-weight: bold; " + "-fx-background-radius: 5px;");

		addUserButton.setOnAction(e -> screen().redirect("users.create"));

		HBox header = new HBox(20);
		header.getChildren().addAll(title, addUserButton);
		header.setAlignment(Pos.CENTER_LEFT);

		// Search bar
		TextField searchField = new TextField();
		searchField.setPromptText("Cari User");
		searchField.setPrefWidth(300);

		Button searchButton = new Button("Cari");
		searchButton.setStyle(
				"-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;");
		searchButton.setOnAction(e -> {
			String query = searchField.getText().toLowerCase();
			List<User> filteredUsers = users.stream().filter(user -> user.getName().toLowerCase().contains(query))
					.collect(Collectors.toList());
			table.setItems(FXCollections.observableArrayList(filteredUsers));
		});

		HBox searchBar = new HBox(10, searchField, searchButton);
		searchBar.setAlignment(Pos.CENTER_RIGHT);

		// Table view
		int totalAdmins = 0, totalManagers = 0, totalStaff = 0;
		if (users != null && !users.isEmpty()) {
			totalAdmins = (int) users.stream().filter(user -> "admin".equals(user.getRole())).count();
			totalManagers = (int) users.stream().filter(user -> "manager".equals(user.getRole())).count();
			totalStaff = (int) users.stream().filter(user -> "staff".equals(user.getRole())).count();
		}

		// Create and display the cards with text inside the rectangles
		HBox adminCard = createCard(totalAdmins, "  Admins");
		HBox managerCard = createCard(totalManagers, "  Managers");
		HBox staffCard = createCard(totalStaff, "  Staff");

		HBox cards = new HBox(20, adminCard, managerCard, staffCard);
		cards.setAlignment(Pos.CENTER_LEFT);
		cards.setPadding(new Insets(10));

		table = new TableView<>(users);
		table.setStyle("-fx-border-color: purple; -fx-border-width: 2px;");

		TableColumn<User, String> noColumn = new TableColumn<>("No");
		TableColumn<User, String> nameColumn = new TableColumn<>("Nama");
		TableColumn<User, String> emailColumn = new TableColumn<>("Email");
		TableColumn<User, String> roleColumn = new TableColumn<>("Role");

		noColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

		table.getColumns().addAll(noColumn, nameColumn, emailColumn, roleColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		Button assignStaffButton = new Button("Assign Staff to Manager");
		assignStaffButton.setOnAction(e -> {
			try {
				User user = table.getSelectionModel().getSelectedItem();
				List<User> managers = users.stream().filter(u -> u.getRole().equalsIgnoreCase("manager"))
						.collect(Collectors.toList());
				
				if (user == null || !user.getRole().equalsIgnoreCase("staff"))
					throw new FormException("Please select a staff to be assigned");

				new UserAssignStaff(controller, user, managers).show();
			} catch (FormException error) {
				AlertComponent.error("Gagal", error.getMessage());
			}
		});

		Button assignRoleButton = new Button("Assign Role");
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

		Button deleteButton = new Button("Delete User");
		deleteButton.setOnAction(e -> {
			User user = table.getSelectionModel().getSelectedItem();

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

		HBox buttons = new HBox();
		buttons.setSpacing(10);
		buttons.getChildren().addAll(assignRoleButton, assignStaffButton, deleteButton);

		// Container layout
		VBox container = new VBox(header, cards, searchBar, table, buttons);
		container.setSpacing(10);
		container.setPadding(new Insets(10));
		container.setStyle("-fx-background-color: #f1f5f9;");

		return container;
	}

	private HBox createCard(int count, String title) {
		// Create a rectangle to act as the card background
		javafx.scene.shape.Rectangle cardBackground = new javafx.scene.shape.Rectangle(200, 80);
		cardBackground.setStyle("-fx-background-color: #dcefff");
		cardBackground.setArcWidth(10); // Rounded corners
		cardBackground.setArcHeight(10);

		// Create the text to display inside the card
		Text cardText = new Text(count + title);
		cardText.setFont(new Font("Open Sans", 18));
		cardText.setFill(Color.DARKBLUE);

		// Create a VBox to center the text inside the rectangle
		VBox cardContent = new VBox(cardText);
		cardContent.setAlignment(Pos.CENTER); // Center the text
		cardContent.setPrefWidth(200);
		cardContent.setPrefHeight(80);
		cardContent.setStyle("-fx-padding: 10px;"); // Add some padding to prevent the text from touching the edges

		// Set the background rectangle size and place it behind the text
		cardContent.setStyle("-fx-background-color: #dcefff; -fx-border-radius: 10px; -fx-background-radius: 10px;");

		// Combine the rectangle and text into a single card container
		HBox card = new HBox(cardContent);
		card.setAlignment(Pos.CENTER);
		card.setPrefSize(200, 80); // Define the card size

		return card;
	}

}
