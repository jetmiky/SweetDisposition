package services;

import models.User;

public class AuthService {

	private static AuthService instance;
	private User user = null;
	
	public static AuthService getInstance() {
		if (instance == null) {
			instance = new AuthService();
		}
		
		return instance;
	}
	
	public User user() {
		return this.check() ? this.user : null;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean guest() {
		return !this.check();
	}
	
	public boolean check() {
		return this.user != null;
	}
	
}
