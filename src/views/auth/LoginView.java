package views.auth;

import exceptions.AuthException;
import exceptions.FormException;
import interfaces.IAuthController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import views.BaseView;

public class LoginView extends BaseView {

	private IAuthController controller;

	public LoginView(IAuthController controller) {
		this.controller = controller;
	}

	public Scene render() {
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
				this.controller.attemptLogin(email, password);
			} catch (AuthException | FormException error) {
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
