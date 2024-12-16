package views.components;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import models.User;
import services.AuthService;
import services.ScreenService;

public class MenuComponent {

    private static AuthService auth = AuthService.getInstance();
    private static ScreenService screen = ScreenService.getInstance();

    private MenuBar bar;

    public MenuBar render() {
        return this.bar;
    }

    public MenuComponent() {    
        bar = new MenuBar();
        bar.setStyle("-fx-background-color: #1565C0; -fx-padding: 5px 10px;");

        if (auth.check()) {
            User user = auth.user();
            buildMenu(user);
        }
    }

    private void buildMenu(User user) {
        // Add Home menu
        Menu homeMenu = new Menu("Home");
        styleMenu(homeMenu);
        bar.getMenus().add(homeMenu);

        // Add role-based menus
        List<Menu> roleMenus = getMenus(user.getRole());
        for (Menu menu : roleMenus) {
            bar.getMenus().add(menu);
        }

        // Spacer for alignment (push items to the right)
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Menu spacerMenu = new Menu();
        spacerMenu.setGraphic(spacer);
        bar.getMenus().add(spacerMenu);

        // Add User menu (e.g., Logout) to the right
        Menu userMenu = new Menu(user.getName());
        styleMenu(userMenu);
        MenuItem logoutItem = createStyledMenuItem("Logout", e -> {
            auth.setUser(null);
            screen.redirect("auth.login");
        });
        userMenu.getItems().add(logoutItem);
        bar.getMenus().add(userMenu);
    }

    private List<Menu> getMenus(String role) {
        switch (role.toLowerCase()) {
            case "admin":
                return renderAdminMenu();
            case "manager":
                return renderManagerMenu();
            case "staff":
                return renderStaffMenu();
            default:
                return new ArrayList<>();
        }
    }

    private ArrayList<Menu> renderAdminMenu() {
        ArrayList<Menu> menus = new ArrayList<>();

        Menu userManagementMenu = new Menu("User Management");
        styleMenu(userManagementMenu);

        MenuItem manageUsers = createStyledMenuItem("Manage Users", e -> screen.redirect("admin.users"));
        userManagementMenu.getItems().add(manageUsers);

        menus.add(userManagementMenu);
        return menus;
    }

    private ArrayList<Menu> renderManagerMenu() {
        ArrayList<Menu> menus = new ArrayList<>();

        Menu reportsMenu = new Menu("Reports");
        styleMenu(reportsMenu);

        MenuItem viewReports = createStyledMenuItem("View Reports", e -> screen.redirect("manager.reports"));
        reportsMenu.getItems().add(viewReports);

        menus.add(reportsMenu);
        return menus;
    }

    private ArrayList<Menu> renderStaffMenu() {
        ArrayList<Menu> menus = new ArrayList<>();

        Menu tasksMenu = new Menu("Tasks");
        styleMenu(tasksMenu);

        MenuItem viewTasks = createStyledMenuItem("View Tasks", e -> screen.redirect("staff.tasks"));
        tasksMenu.getItems().add(viewTasks);

        menus.add(tasksMenu);
        return menus;
    }

    private void styleMenu(Menu menu) {

        menu.setStyle(
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-color: transparent;" // Keep menu background transparent for cleaner look
        );

        // Apply style to each menu item in the menu
        for (MenuItem item : menu.getItems()) {
            item.setStyle("-fx-text-fill: white;");
        }
    }

    private MenuItem createStyledMenuItem(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: black; -fx-padding: 5px 10px;");
        label.setOnMouseEntered(e -> label.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-color: #1E88E5; -fx-padding: 5px 10px;"));
        label.setOnMouseExited(e -> label.setStyle("-fx-font-size: 12px; -fx-text-fill: black; -fx-padding: 5px 10px;"));

        CustomMenuItem customMenuItem = new CustomMenuItem(label);
        customMenuItem.setOnAction(action);
        return customMenuItem;
    }
}
