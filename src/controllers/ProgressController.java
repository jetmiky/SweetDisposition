package controllers;

import exceptions.FormException;
import interfaces.IProgressController;
import models.Progress;
import models.Task;

public class ProgressController extends BaseController implements IProgressController {

	private static ProgressController instance;

	public static ProgressController getInstance() {
		if (instance == null) {
			instance = new ProgressController();
		}

		return instance;
	}

	@Override
	public Progress store(Task task, String description, Boolean isCompleted) throws FormException {
		if (description.isBlank())
			throw new FormException("Description cannot be empty");
		
		if (isCompleted) {
			task.setCompleted(isCompleted);
			db().tasks().save(task);
		}
		
		Progress progress = new Progress(task.getId(), description, isCompleted);
		return db().progresses().save(progress);
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
