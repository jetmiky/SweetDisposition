package interfaces;

import exceptions.ViewException;
import javafx.scene.Scene;

public interface ITaskController {

	public Scene create();

	public void store(String title, String description) throws ViewException;

}
