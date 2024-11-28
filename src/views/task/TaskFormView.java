package views.task;

import interfaces.ITaskController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import views.BaseView;

public class TaskFormView extends BaseView {

	private ITaskController controller;

	public TaskFormView(ITaskController controller) {
		this.controller = controller;
	}

	public Scene getScene() {
		TextField titleField = new TextField();
		titleField.setPromptText("Task Title");

		TextField descriptionField = new TextField();
		descriptionField.setPromptText("Task Description");

		Label titleLabel = new Label("Title");
		Label descriptionLabel = new Label("Description");

		Text errorText = new Text("");

		Button button = new Button("Add Task");
		button.setOnAction(e -> {
			String title = titleField.getText();
			String description = descriptionField.getText();

			try {

				this.controller.create(title, description);
				
			} catch (Exception error) {
				String message = error.getMessage();
				errorText.setText(message);
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
		container.add(button, 0, 2);
		container.add(errorText, 0, 3);
		GridPane.setColumnSpan(errorText, 2);

		return new Scene(container);
	}

}
