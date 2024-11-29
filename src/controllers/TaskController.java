package controllers;

import exceptions.ViewException;
import interfaces.ITaskController;
import javafx.scene.Scene;
import models.Task;
import repositories.TaskRepository;
import views.task.TaskFormView;

public class TaskController extends BaseController implements ITaskController {

	private static TaskController instance;

	public static TaskController getInstance() {
		if (instance == null) {
			instance = new TaskController();
		}

		return instance;
	}

	public TaskRepository DBTasks() {
		return TaskRepository.getInstance();
	}

	public Scene create() throws ViewException {
		return new TaskFormView(this).with("ADS", "AD").render();
	}

	@Override
	public void store(String title, String description) throws ViewException {
		Task task = new Task(1, 1, title, description);

		DBTasks().save(task);

		screen().redirect("login");
	}

}
