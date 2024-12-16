package views.progresses;

import controllers.ProgressController;
import exceptions.FormException;
import interfaces.IProgressController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		Text title = new Text(this.task.getTitle());
		title.setFont(new Font("Arial Black", 24));
		
		Label descriptionLabel = new Label("Description");
		TextArea descriptionField = new TextArea();
		CheckBox completedCheckbox = new CheckBox("Is the task completed?");
		completedCheckbox.setSelected(false);
		
		Button saveButton = new Button("Save Progress");
		saveButton.setOnAction(e -> {
			try {
				String description = descriptionField.getText();
				Boolean isCompleted = completedCheckbox.isSelected();
				
				this.controller.store(this.task, description, isCompleted);
				AlertComponent.success("Berhasil", "Berhasil menambahkan task progress");
				this.close();
			} catch (FormException error) {
				AlertComponent.error("Gagal", error.getMessage());
			}
		});
		
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(e -> this.close());
		
		HBox buttons = new HBox();
		buttons.setSpacing(8);
		buttons.getChildren().addAll(saveButton, cancelButton);
		
		VBox container = new VBox();
		container.setPadding(new Insets(16));
		container.setSpacing(16);
		container.getChildren().addAll(title, descriptionLabel, descriptionField, completedCheckbox, buttons);
		
		return new Scene(container, 800, 600);
	}

}
