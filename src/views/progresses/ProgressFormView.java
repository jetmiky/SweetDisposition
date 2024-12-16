package views.progresses;

import controllers.ProgressController;
import exceptions.FormException;
import interfaces.IProgressController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.Task;
import views.BaseModal;
import views.components.AlertComponent;

public class ProgressFormView extends BaseModal {

	private IProgressController controller;
	private Task task;

	public ProgressFormView(IProgressController controller, Task task) {
		super("Add Task Progress");

		this.controller = controller;
		this.task = task;
	}

	public ProgressFormView(Task task) {
		super("Add Task Progress");

		this.controller = ProgressController.getInstance();
		this.task = task;
	}

	@Override
	public Scene render() {
		// Title for the modal
        Text formTitle = new Text("Progress Task: " + this.task.getTitle());
        formTitle.setFont(Font.font("Arial", 20));
        formTitle.setFill(Color.BLACK);

        // Form Fields Section
        Label descriptionLabel = new Label("Description");
        TextArea descriptionField = new TextArea();
        descriptionField.setPromptText("Describe the progress...");

        CheckBox completedCheckbox = new CheckBox("Is the task completed?");
        completedCheckbox.setSelected(false);

        // Save Button
        Button saveButton = new Button("Save Progress");
        saveButton.setStyle("-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        saveButton.setOnAction(e -> {
            try {
                String description = descriptionField.getText();
                Boolean isCompleted = completedCheckbox.isSelected();

                this.controller.store(this.task, description, isCompleted);
                AlertComponent.success("Berhasil", "Progress telah ditambahkan");
                this.close();
            } catch (FormException error) {
                AlertComponent.error("Gagal", error.getMessage());
            }
        });

        // Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #E53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        cancelButton.setOnAction(e -> this.close());

        // Buttons Container (Save & Cancel)
        HBox buttonBar = new HBox(10, saveButton, cancelButton);
        buttonBar.setAlignment(Pos.CENTER);

        // Form Layout
        VBox mainLayout = new VBox(20, formTitle, descriptionLabel, descriptionField, completedCheckbox, buttonBar);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #f1f5f9;");

        return new Scene(mainLayout, 400, 400);
	}

}
