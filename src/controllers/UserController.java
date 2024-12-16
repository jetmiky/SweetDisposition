package controllers;

import java.util.List;

import exceptions.FormException;
import interfaces.IUserController;
import javafx.scene.layout.Pane;
import models.User;
import utils.StringHelper;
import views.auth.UserIndexView;

public class UserController extends BaseController implements IUserController {
	private static UserController instance;

	public static UserController getInstance() {
		if (instance == null) {
			instance = new UserController();
		}

		return instance;
	}

	public Pane index() {
		List<User> users = db().users().getAll();

		return new UserIndexView(this, users).render();
	}

	@Override
	public void store(String name, String email, String role, String password) throws FormException {
		if (name.isBlank())
			throw new FormException("Name cannot be empty");
		if (email.isBlank())
			throw new FormException("Email cannot be empty");
		if (!StringHelper.isValidEmail(email))
			throw new FormException("Provide a valid email");
		if (password.isBlank())
			throw new FormException("Password cannot be empty");
		if (password.length() < 8)
			throw new FormException("Password should be minimum 8 characters long");

		User user = db().users().select().where("email", email).first();
		if (user.exists())
			throw new FormException("Email already taken.");

		user = new User(name, email, role, password);
		db().users().save(user);
	}

	@Override
	public void update(User user) throws FormException {
		if (user == null)
			throw new FormException("Please select a user to update");

		db().users().save(user);
	}

	@Override
	public void delete(User user) throws FormException {
		if (user == null)
			throw new FormException("Please select a user to delete");

		db().users().delete(user);
	}

}
