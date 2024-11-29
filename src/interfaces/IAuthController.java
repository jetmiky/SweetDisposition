package interfaces;

import exceptions.AuthException;
import exceptions.ViewException;
import javafx.scene.Scene;

public interface IAuthController {

	public Scene login() throws ViewException;
	
	public void attemptLogin(String email, String password) throws AuthException;

}
