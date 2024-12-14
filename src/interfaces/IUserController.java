package interfaces;

import exceptions.FormException;
import javafx.scene.Scene;

public interface IUserController {

	public Scene index();
	
	public Scene create();

	public void store(String name, String email, String role, String password) throws FormException;

	public void deleteUser(Integer id);

	public boolean updateUserRole(Integer id, String newRole);
}
