package interfaces;

import exceptions.AuthException;

public interface ILoginController {

	public void login(String email, String password) throws AuthException;

}
