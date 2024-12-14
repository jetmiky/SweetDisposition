package interfaces;

import exceptions.FormException;
import javafx.scene.Scene;

public interface IUserController {

	public Scene index();
	
	public Scene create();

	public void store(String name, String email, String role, String password) throws FormException;

}
