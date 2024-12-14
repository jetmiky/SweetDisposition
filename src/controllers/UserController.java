package controllers;

import java.util.List;

import exceptions.FormException;
import interfaces.IUserController;
import javafx.scene.Scene;
import models.User;
import views.auth.UserFormView;
import views.auth.UserIndexView;

public class UserController extends BaseController implements IUserController {
	private static UserController instance;

	public static UserController getInstance() {
		if (instance == null) {
			instance = new UserController();
		}

		return instance;
	}

	public Scene index() {
		List<User> users = db().users().getAll();
		
		UserIndexView view = new UserIndexView(this);
		view.data("users", users);
		
		return view.render();
	}

	public Scene create() {
		UserFormView view = new UserFormView(this);
		return view.render();
	}

	@Override
	public void store(String name, String email, String role, String password) throws FormException {
		User user = new User(name, email, role, password);

		db().users().save(user);

		screen().redirect("users.index");
	}
	
	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean updateUserRole(Integer id, String newRole) {
	    if (id == null || newRole == null || newRole.isEmpty()) {
	        return false;
	    }
	    User user = db().users().get(id);
	    if (user != null) {
	        user.setRole(newRole);
	        db().users().save(user);
	        return true;
	    }
	    return false;
	}

	public boolean deleteUserConfirmation(Integer id) {
		return false;
	}
}
