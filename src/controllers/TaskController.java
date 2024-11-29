package controllers;

import exceptions.ViewException;
import interfaces.ITaskController;
import javafx.scene.Scene;
import models.Task;
import views.task.TaskFormView;

public class TaskController extends BaseController implements ITaskController {

	private static TaskController instance;

	public static TaskController getInstance() {
		if (instance == null) {
			instance = new TaskController();
		}

		return instance;
	}

	public Scene create() {
		return new TaskFormView(this).with("ADS", "AD").render();
	}

	@Override
	public void store(String title, String description) throws ViewException {
		Task task = new Task(1, 1, title, description);

		db().tasks().save(task);

		screen().redirect("auth.login");
	}

}
