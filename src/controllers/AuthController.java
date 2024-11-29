package controllers;

import exceptions.AuthException;
import exceptions.ViewException;
import interfaces.IAuthController;
import javafx.scene.Scene;
import models.User;
import views.auth.LoginView;

public class AuthController extends BaseController implements IAuthController {

	private static AuthController instance;

	public static AuthController getInstance() {
		if (instance == null) {
			instance = new AuthController();
		}

		return instance;
	}

	@Override
	public Scene login() throws ViewException {
		return new LoginView(this).render();
	}

	@Override
	public void attemptLogin(String email, String password) throws AuthException{
		User user = db().users().select().where("email", email).first();

		if (user.exists() && user.getPassword().equals(password)) {
			try {
				screen().redirect("tasks.create");
			} catch (ViewException e) {
				e.printStackTrace();
				throw new AuthException("Failed to redirect view");
			}
		} else {
			throw new AuthException("No valid account with these credentials.");
		}
	}

}
