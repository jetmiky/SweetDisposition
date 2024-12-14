package controllers;

import exceptions.AuthException;
import exceptions.FormException;
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
	public Scene login() {
		return new LoginView(this).render();
	}

	@Override
	public void attemptLogin(String email, String password) throws AuthException, FormException {
		User user = db().users().select().where("email", email).first();

		if (user.exists() && user.isPasswordMatched(password)) {

			auth().setUser(user);

			String role = user.getRole();
			String path = "";

			switch (role) {
			case "admin":
				path = "users.index";
				break;
			case "manager":
				path = "tasks.index.manager";
				break;
			case "staff":
				path = "tasks.index.staff";
				break;
			default:
				break;
			}

			screen().redirect(path);

		} else {
			throw new AuthException("No valid account with these credentials.");
		}
	}

	@Override
	public void logout() {
		auth().setUser(null);
		screen().redirect("auth.login");
	}

}
