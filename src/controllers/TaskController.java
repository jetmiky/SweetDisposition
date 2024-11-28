package controllers;

import interfaces.ITaskController;
import models.Task;
import repositories.TaskRepository;

public class TaskController extends BaseController implements ITaskController {

	public TaskRepository DBTasks() {
		return TaskRepository.getInstance();
	}

	@Override
	public void create(String title, String description) throws Exception {

		Task task = DBTasks().get(3);
		
		throw new Exception("DATE: " + task.getCreatedAt().toLocaleString());

	}

}
