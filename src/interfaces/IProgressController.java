package interfaces;

import exceptions.FormException;
import javafx.scene.layout.Pane;
import models.Task;

public interface IProgressController {

	public Pane create();

	public void store(Task task, String description, Boolean isCompleted) throws FormException;

}
