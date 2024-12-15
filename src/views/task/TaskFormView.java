package views.task;

import exceptions.FormException;
import interfaces.ITaskController;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import models.User;
import views.BaseView;
import views.components.AlertComponent;

public class TaskFormView extends BaseView {

	private ITaskController controller;
	private ObservableList<User> staffs = FXCollections.observableArrayList();

	public TaskFormView(ITaskController controller, List<User> staffs) {
		this.controller = controller;
		this.staffs = FXCollections.observableArrayList(staffs);
	}

	public Pane render() {
		TextField titleField = new TextField();
		titleField.setPromptText("Task Title");

		TextArea descriptionField = new TextArea();
		descriptionField.setPromptText("Task Description");

		ComboBox<User> staffDropdown = new ComboBox<>();
		staffDropdown.setPromptText("Choose a staff");
		staffDropdown.setItems(this.staffs);

		Label titleLabel = new Label("Title");
		Label descriptionLabel = new Label("Description");
		Label staffLabel = new Label("Assign Task to");

		Button button = new Button("Add Task");
		button.setOnAction(e -> {
			String title = titleField.getText();
			String description = descriptionField.getText();
			User staff = staffDropdown.getValue();

			try {
				this.controller.store(title, description, staff);
				AlertComponent.success("Success", "Task successfully created");
			} catch (FormException error) {
				AlertComponent.error("Error", error.getMessage());
			}
		});

		GridPane container = new GridPane();
		container.setPadding(new Insets(16));
		container.setHgap(12);
		container.setVgap(12);

		container.add(titleLabel, 0, 0);
		container.add(titleField, 1, 0);
		container.add(descriptionLabel, 0, 1);
		container.add(descriptionField, 1, 1);
		container.add(staffLabel, 0, 2);
		container.add(staffDropdown, 1, 2);
		container.add(button, 0, 3);

		return container;
	}
	
}
