package services;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import controllers.BaseController;
import exceptions.ViewException;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenService {

	private static ScreenService instance;
	private static Stage stage;
	private static Map<String, ScreenHandler> routes = new HashMap<>();

	public static ScreenService getInstance() {
		if (instance == null) {
			instance = new ScreenService();
		}

		return instance;
	}

	public static void initialize(Stage primaryStage) {
		stage = primaryStage;
	}

	public void register(String route, BaseController controller, String method) {
		routes.put(route, new ScreenHandler(controller, method));
	}
	
	private ScreenHandler get(String route) {
		ScreenHandler handler = routes.get(route);
		return handler;
	}

	public void redirect(String route) throws ViewException {
		ScreenHandler handler = get(route);

		BaseController controller = handler.controller;
		String methodName = handler.method;
		
		try {
			Method method = controller.getClass().getMethod(methodName);
			Scene scene = (Scene) method.invoke(controller); 
			stage.setScene(scene);
		} catch (Exception e) {
			throw new ViewException("Failed to redirect view.");
		}

	}
	
	public class ScreenHandler {
		public BaseController controller;
		public String method;

		public ScreenHandler(BaseController controller, String method) {
			this.controller = controller;
			this.method = method;
		}
	}

}
