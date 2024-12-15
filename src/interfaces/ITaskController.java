package interfaces;

import exceptions.FormException;
import javafx.scene.layout.Pane;
import models.Task;
import models.User;

public interface ITaskController {

	public Pane manager();

	public Pane staff();

	public Pane create();

	public void store(String title, String description, User staff) throws FormException;

	public void delete(Task task) throws FormException;
}
