package services;

import java.util.HashMap;

import javafx.stage.Stage;
import views.BaseView;
import views.ErrorView;

public class ScreenService {

	private static ScreenService instance;
	private static Stage stage;
	private HashMap<String, BaseView> routes = new HashMap<>();

	public static ScreenService getInstance() {
		if (instance == null) {
			instance = new ScreenService();
		}

		return instance;
	}

	public static void initialize(Stage primaryStage) {
		stage = primaryStage;
	}

	public void register(String route, BaseView view) {
		routes.put(route, view);
	}

	public BaseView get(String route) {
		return routes.getOrDefault(route, new ErrorView("Page not found!"));
	}

	public void redirect(String route) {
		BaseView view = get(route);
		stage.setScene(view.getScene());
	}

}
