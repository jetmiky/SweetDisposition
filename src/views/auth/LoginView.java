package views.auth;

import exceptions.AuthException;
import exceptions.FormException;
import interfaces.IAuthController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

public class LoginView extends BaseView {

	private IAuthController controller;

	public LoginView(IAuthController controller) {
		this.controller = controller;
	}

	public Pane render() {
		// Menu bar
		HBox menuBar = new HBox(10);
		menuBar.setPadding(new Insets(10));
		menuBar.setStyle("-fx-background-color: #1565C0;");

		Button loginMenuButton = new Button("Sweet Disposition");
		styleMenuButton(loginMenuButton);
		menuBar.getChildren().addAll(loginMenuButton);

		// Login form
		VBox loginFormContainer = new VBox(20);
		loginFormContainer.setPadding(new Insets(40));
		loginFormContainer.setAlignment(Pos.CENTER);

		Text formTitle = new Text("Login");
		formTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
		formTitle.setFill(Color.BLACK);

		GridPane loginForm = new GridPane();
		loginForm.setHgap(10);
		loginForm.setVgap(15);
		loginForm.setAlignment(Pos.CENTER);

		Label emailLabel = new Label("Email");
		TextField usernameField = new TextField();
		usernameField.setPromptText("Email...");

		Label passwordLabel = new Label("Password");
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Password...");

		loginForm.add(emailLabel, 0, 0);
		loginForm.add(usernameField, 1, 0);
		loginForm.add(passwordLabel, 0, 1);
		loginForm.add(passwordField, 1, 1);

		Button signInButton = new Button("Sign in");
		signInButton.setStyle(
				"-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;");
		signInButton.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();

			try {
				this.controller.attemptLogin(username, password);
			} catch (AuthException | FormException error) {
				AlertComponent.error("Gagal", error.getMessage());
			}
		});

		Label forgotPassword = new Label("Forgot your password?");
		forgotPassword.setTextFill(Color.GRAY);

		VBox formActions = new VBox(10, signInButton, forgotPassword);
		formActions.setAlignment(Pos.CENTER);

		loginFormContainer.getChildren().addAll(formTitle, loginForm, formActions);

		// Image placeholder
		VBox imagePlaceholder = new VBox();
		imagePlaceholder.setAlignment(Pos.CENTER);
		imagePlaceholder.setStyle("-fx-background-color: #dae4fe;");

		// Load and add the image
		try {
			Image imageView = new Image(getClass().getResource("/resources/loginIlust.png").toURI().toString());
			ImageView iv = new ImageView(imageView);

			iv.setFitWidth(500); // Adjusted to stretch full
			iv.setPreserveRatio(true); // Keep the aspect ratio
			iv.setSmooth(true);
			imagePlaceholder.getChildren().add(iv);
		} catch (Exception e) {
			Text errorText = new Text("Image not found");
			errorText.setFill(Color.GRAY);
			errorText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			imagePlaceholder.getChildren().add(errorText);
		}

		// Main layout
		BorderPane layout = new BorderPane();
		
		layout.setTop(menuBar);
		layout.setLeft(loginFormContainer);
		layout.setCenter(imagePlaceholder);

		BorderPane.setAlignment(loginFormContainer, Pos.CENTER_LEFT);
		BorderPane.setAlignment(imagePlaceholder, Pos.CENTER_RIGHT);

		layout.setLeft(loginFormContainer);
		layout.setCenter(imagePlaceholder);

		return layout;
	}

	private void styleMenuButton(Button button) {
		button.setStyle("-fx-background-color: #1E88E5;" + "-fx-text-fill: white;" + "-fx-font-size: 14px;"
				+ "-fx-font-weight: bold;" + "-fx-background-radius: 5px;");
	}
}
