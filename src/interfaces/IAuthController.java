package interfaces;

import exceptions.AuthException;
import javafx.scene.Scene;

public interface IAuthController {

	public Scene login();
	
	public void attemptLogin(String email, String password) throws AuthException;

}
