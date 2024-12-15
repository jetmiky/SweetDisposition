package views.auth;

import exceptions.FormException;
import interfaces.IUserController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import views.BaseView;
import views.components.AlertComponent;

public class UserFormView extends BaseView {

	private IUserController controller;

	public UserFormView(IUserController controller) {
		this.controller = controller;
	}

	@Override
	public Pane render() {
		HBox menuBar = new HBox(10);
		menuBar.setPadding(new Insets(10));
		menuBar.setStyle("-fx-background-color: #1565C0;");

		Button userMenuButton = new Button("User Management");
		userMenuButton.setStyle("-fx-background-color: #1E88E5;" + "-fx-text-fill: white;" + "-fx-font-size: 14px;"
				+ "-fx-font-weight: bold;" + "-fx-background-radius: 5px;");
		
		menuBar.getChildren().add(userMenuButton);

		// Form title
		Text formTitle = new Text("Tambah User");
		formTitle.setFont(Font.font("Open Sans", FontWeight.BOLD, 24));
		formTitle.setFill(Color.BLACK);

		// User form
		GridPane userForm = new GridPane();
		userForm.setHgap(10);
		userForm.setVgap(15);
		userForm.setAlignment(Pos.CENTER);

		TextField nameField = new TextField();
		nameField.setPromptText("Nama Lengkap");

		ComboBox<String> roleField = new ComboBox<>(FXCollections.observableArrayList("Staff", "Manager", "Admin"));
		roleField.getSelectionModel().selectFirst();

		TextField emailField = new TextField();
		emailField.setPromptText("Email");

		TextField passwordField = new TextField();
		passwordField.setPromptText("Password Default");

		userForm.add(new Text("Nama"), 0, 0);
		userForm.add(nameField, 1, 0);
		userForm.add(new Text("Role"), 0, 1);
		userForm.add(roleField, 1, 1);
		userForm.add(new Text("Email"), 0, 2);
		userForm.add(emailField, 1, 2);
		userForm.add(new Text("Password"), 0, 3);
		userForm.add(passwordField, 1, 3);

		// Buttons
		Button createButton = new Button("Create");
		createButton.setStyle(
				"-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;");
		createButton.setOnAction(e -> {
			String name = nameField.getText();
			String email = emailField.getText();
			String role = roleField.getSelectionModel().getSelectedItem();
			String password = passwordField.getText();

			try {
				controller.store(name, email, role, password);
			} catch (FormException error) {
				AlertComponent.error("Failed", error.getMessage());
			}
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.setStyle(
				"-fx-background-color: #E53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;");
		cancelButton.setOnAction(e -> screen().redirect("users.index"));

		HBox buttonBar = new HBox(10, createButton, cancelButton);
		buttonBar.setAlignment(Pos.CENTER);

		VBox formContainer = new VBox(20, formTitle, userForm, buttonBar);
		formContainer.setAlignment(Pos.CENTER);
		formContainer.setPadding(new Insets(40));

		// Image placeholder
		VBox imagePlaceholder = new VBox();
		imagePlaceholder.setAlignment(Pos.CENTER);
		imagePlaceholder.setStyle("-fx-background-color: #dae4fe;");

		try {
			Image image = new Image(getClass().getResource("/resources/loginIlust.png").toURI().toString());
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(400);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			imagePlaceholder.getChildren().add(imageView);
		} catch (Exception e) {
			System.out.println("Error loading image");
		}

		// Main layout
		BorderPane layout = new BorderPane();
		layout.setTop(menuBar);
		layout.setRight(formContainer);
		layout.setCenter(imagePlaceholder);

		return layout;
	}
	
}
