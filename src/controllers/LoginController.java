package controllers;

import exceptions.AuthException;
import interfaces.ILoginController;
import models.User;
import repositories.UserRepository;

public class LoginController extends BaseController implements ILoginController {

	public UserRepository DBUsers() {
		return UserRepository.getInstance();
	}

	@Override
	public void login(String email, String password) throws AuthException {
//		- Get user by email
//		User user = DBUsers().select().where("email", email).first();		
//		User user = DBUsers().getByEmail(email);

//		- Get user with Admin role
//		User user = DBUsers().select().get();

//		- Insert user data
//		User user = new User("ABC", "abc@example.com", "password");
//		DBUsers().create(user);

//		- Update user data
//		user.setName("Another name.");
//		DBUsers().update(user);

		User user = DBUsers().getByEmail(email);

		if (user.exists() && user.getPassword().equals(password)) {
			throw new AuthException("VALIDDDDD");
		} else {
			throw new AuthException("No valid account with these credentials.");
		}
	}

}
