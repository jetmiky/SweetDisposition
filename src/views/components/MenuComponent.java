package views.components;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import models.User;
import services.AuthService;
import services.ScreenService;

public class MenuComponent {

	private static AuthService auth = AuthService.getInstance();
	private static ScreenService screen = ScreenService.getInstance();
	
	private MenuBar bar = new MenuBar();

	public MenuBar render() {
		return this.bar;
	}

	public MenuComponent() {
		if (auth.check()) {
			User user = auth.user();
			List<Menu> menus = this.getMenus(user.getRole());

			bar.getMenus().addAll(menus);

			Menu logout = new Menu("Logout");
			MenuItem logoutItem = new MenuItem("Logout");
			logoutItem.setOnAction(e -> {
				auth.setUser(null);
				screen.redirect("auth.login");
			});
			
			logout.getItems().add(logoutItem);

			bar.getMenus().add(logout);
		}

	}

	private List<Menu> getMenus(String role) {
		switch (role.toLowerCase()) {
		case "admin":
			return new ArrayList<Menu>();
		case "manager":
			return new ArrayList<Menu>();
		case "staff":
			return new ArrayList<Menu>();
		default:
			return new ArrayList<Menu>();
		}
	}

}
