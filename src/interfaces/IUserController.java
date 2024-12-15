package interfaces;

import exceptions.FormException;
import javafx.scene.layout.Pane;
import models.User;

public interface IUserController {

	public Pane index();
	
	public Pane create();

	public void store(String name, String email, String role, String password) throws FormException;

	public void update(User user) throws FormException;

	public void delete(User user) throws FormException;

}
