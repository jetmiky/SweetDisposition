package views.auth;

import java.util.List;

import exceptions.FormException;
import interfaces.IUserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.User;
import views.components.AlertComponent;

public class UserAssignStaff {

	private final IUserController controller;
	private final User user;
	private final ObservableList<User> managers;

	public UserAssignStaff(IUserController controller, User user, List<User> managers) {
		this.controller = controller;
		this.user = user;
		this.managers = FXCollections.observableArrayList(managers);
	}

	public void show() {
		Stage modal = new Stage();
		modal.initModality(Modality.APPLICATION_MODAL);
		modal.setTitle("Assign Staff to Manager");

		// Title
		Text title = new Text("Assign Staff");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		// User Details
		Text userDetails = new Text("User: " + user.getName() + " (" + user.getRole() + ")");
		userDetails.setFont(Font.font("Arial", 16));

		// Manager selection
		ComboBox<User> managerComboBox = new ComboBox<>(this.managers);
		managerComboBox.getSelectionModel().selectFirst();

		// Buttons
		Button saveButton = new Button("Save");
		saveButton.setStyle(
				"-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;");
		saveButton.setOnAction(e -> {
			User manager = managerComboBox.getSelectionModel().getSelectedItem();
			user.setSupervisor(manager);

			try {
				controller.update(user);
				AlertComponent.success("Berhasil", "Berhasil assign staff ke manager");
			} catch (FormException error) {
				AlertComponent.error("Gagal", error.getMessage());
			}

			modal.close();
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.setStyle(
				"-fx-background-color: #E53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;");
		cancelButton.setOnAction(e -> modal.close());

		HBox buttonBar = new HBox(10, saveButton, cancelButton);
		buttonBar.setAlignment(Pos.CENTER);

		VBox layout = new VBox(20, title, userDetails, managerComboBox, buttonBar);
		layout.setPadding(new Insets(20));
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout, 400, 250);
		modal.setScene(scene);
		modal.showAndWait();
	}
}
