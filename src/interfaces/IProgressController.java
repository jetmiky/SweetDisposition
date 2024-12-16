package interfaces;

import exceptions.FormException;
import models.Progress;
import models.Task;

public interface IProgressController {

	public void store(Task task, String description, Boolean isCompleted) throws FormException;

	public void delete(Progress progress) throws FormException;
	
}
