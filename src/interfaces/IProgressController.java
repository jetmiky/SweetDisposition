package interfaces;

import exceptions.FormException;
import models.Progress;
import models.Task;

public interface IProgressController {

	public Progress store(Task task, String description, Boolean isCompleted) throws FormException;

	public void delete(Progress progress) throws FormException;
	
}
