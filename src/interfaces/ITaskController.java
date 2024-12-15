package interfaces;

import exceptions.FormException;
import javafx.scene.layout.Pane;
import models.Task;
import models.User;

public interface ITaskController {

	public Pane managerIndex();

	public Pane staffIndex();

	public Pane create();

	public void store(String title, String description, User staff) throws FormException;
	
	public Pane managerShow();

	public Pane staffShow();

	public void delete(Task task) throws FormException;
}
