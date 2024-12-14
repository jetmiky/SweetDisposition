package interfaces;

import exceptions.AuthException;
import exceptions.FormException;
import javafx.scene.Scene;

public interface IAuthController {

	public Scene login();
	
	public void attemptLogin(String email, String password) throws AuthException, FormException;

	public void logout();
}
