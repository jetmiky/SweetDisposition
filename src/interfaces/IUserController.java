package interfaces;

import exceptions.FormException;
import javafx.scene.layout.Pane;
import models.User;

public interface IUserController {

	public Pane index();
	
	public User store(String name, String email, String role, String password) throws FormException;

	public User update(User user) throws FormException;

	public void delete(User user) throws FormException;

}
