package views.auth;

import exceptions.FormException;
import interfaces.IUserController;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import views.BaseView;
import views.components.MenuComponent;

public class UserFormView extends BaseView {

	private IUserController controller;

	public UserFormView(IUserController controller) {
		this.controller = controller;
	}

	@Override
	public Scene render() {
		MenuBar menu = MenuComponent.getInstance().render();

		TextField nameField = new TextField();
		nameField.setPromptText("Name");

		String[] roles = { "Staff", "Manager", "Admin" };
		ComboBox<String> roleField = new ComboBox<>(FXCollections.observableArrayList(roles));
		roleField.getSelectionModel().selectFirst();
		
		TextField emailField = new TextField();
		emailField.setPromptText("Email");

		TextField passwordField = new TextField();
		passwordField.setPromptText("Default Password");

		Text errorText = new Text();

		Button button = new Button("Create");
		button.setOnAction(e -> {
			String name = nameField.getText();
			String email = emailField.getText();
			String role = roleField.getSelectionModel().getSelectedItem();
			String password = passwordField.getText();

			try {
				controller.store(name, email, role, password);
			} catch (FormException error) {
				String message = error.getMessage();
				errorText.setText(message);
			}
		});

		VBox container = new VBox();
		container.getChildren().addAll(menu, nameField, roleField, emailField, passwordField, errorText, button);

		return new Scene(container);
	}

}
