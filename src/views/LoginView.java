package views;

import exceptions.AuthException;
import interfaces.ILoginController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class LoginView extends BaseView {

	private ILoginController controller;

	public LoginView(ILoginController controller) {
		this.controller = controller;
	}

	public Scene getScene() {
		TextField emailField = new TextField();
		emailField.setPromptText("user@example.com");

		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("********");

		Label emailLabel = new Label("Email");
		Label passwordLabel = new Label("Password");

		Text errorText = new Text("");

		Button button = new Button("LOGINNNNN");
		button.setOnAction(e -> {
			String email = emailField.getText();
			String password = passwordField.getText();

			try {
				this.controller.login(email, password);
			} catch (AuthException error) {
				String message = error.getMessage();
				errorText.setText(message);
			}
		});

		GridPane container = new GridPane();
		container.setPadding(new Insets(16));
		container.setHgap(12);
		container.setVgap(12);

		container.add(emailLabel, 0, 0);
		container.add(emailField, 1, 0);
		container.add(passwordLabel, 0, 1);
		container.add(passwordField, 1, 1);
		container.add(button, 0, 2);
		container.add(errorText, 0, 3);
		GridPane.setColumnSpan(errorText, 2);

		return new Scene(container);
	}

}
