package interfaces;

import exceptions.FormException;
import javafx.scene.Scene;

public interface ITaskController {

	public Scene create();

	public void store(String title, String description) throws FormException;

}
