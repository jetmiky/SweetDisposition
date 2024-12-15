package interfaces;

import exceptions.AuthException;
import exceptions.FormException;
import javafx.scene.layout.Pane;

public interface IAuthController {

	public Pane login();
	
	public void attemptLogin(String email, String password) throws AuthException, FormException;

	public void logout();
}
