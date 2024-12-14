package views.components;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import models.User;
import services.AuthService;
import services.ScreenService;

public class MenuComponent {
	
	private static MenuComponent instance;
	private AuthService auth = AuthService.getInstance();
	private ScreenService screen = ScreenService.getInstance();
	private MenuBar bar = new MenuBar();
	
	public static MenuComponent getInstance() {
		if (instance == null) instance = new MenuComponent();
		return instance;
	}
	
	public MenuBar render() {
		return this.bar;
	}
	
	private MenuComponent() {
		User user = auth.user();
		List<Menu> menus = this.getMenus(user.getRole());
		
		bar.getMenus().addAll(menus);
		
		Menu logout = new Menu("Logout");
		logout.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent e) {
				screen.redirect("auth.logout");
			}
			
		});
		
		bar.getMenus().add(logout);
	}

	private List<Menu> getMenus(String role) {
		switch (role) {
		case "admin":
			return this.getAdminMenu();
		default:
			return new ArrayList<Menu>();
		}
	}

	private List<Menu> getAdminMenu() {
		ArrayList<Menu> menus = new ArrayList<>();
		
		Menu users = new Menu("Users");
		
		MenuItem manageUsers = new MenuItem("Manage Users");
		manageUsers.setOnAction(e -> {
			screen.redirect("users.index");
		});
		
		MenuItem assignStaff = new MenuItem("Assign Staffs to Manager");
		assignStaff.setOnAction(e -> {
			screen.redirect("users.assign");
		});
		
		users.getItems().addAll(manageUsers, assignStaff);
		menus.add(users);
		
		return menus;		
	}
}
