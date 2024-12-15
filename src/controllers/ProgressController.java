package controllers;

import exceptions.FormException;
import interfaces.IProgressController;
import javafx.scene.layout.Pane;
import models.Progress;
import models.Task;
import views.progresses.ProgressFormView;

public class ProgressController extends BaseController implements IProgressController {

	private static ProgressController instance;

	public static ProgressController getInstance() {
		if (instance == null) {
			instance = new ProgressController();
		}

		return instance;
	}

	@Override
	public Pane create() {
		Task task = (Task) state().get("task");

		return new ProgressFormView(this, task).render();
	}

	@Override
	public void store(Task task, String description, Boolean isCompleted) throws FormException {
		if (description.isBlank())
			throw new FormException("Description cannot be empty");

		Progress progress = new Progress(task.getId(), description, isCompleted);
		db().progresses().save(progress);
		
		if (isCompleted) {
			task.setCompleted(isCompleted);
			db().tasks().save(task);
		}

		state().set("task", task);
		screen().redirect("tasks.show.staff");
	}
	
	@Override
	public void delete(Progress progress) throws FormException {
		if (progress == null)
			throw new FormException("Please select a progress to delete");

		Task task = db().tasks().get(progress.getTaskId());
		
		if (task.isCompleted() && progress.isCompleted()) {
			task.setCompleted(false);
			db().tasks().save(task);
		}
		
		db().progresses().delete(progress);
	}

}
