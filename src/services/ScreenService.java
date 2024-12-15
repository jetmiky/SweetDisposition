package services;

import controllers.BaseController;
import exceptions.ViewException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import views.BaseLayout;

public class ScreenService {

	private static ScreenService instance;
	private static Stage stage;
	private Map<String, ScreenHandler> routes = new HashMap<>();

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

	private ScreenHandler get(String route) throws ViewException {
		ScreenHandler handler = routes.getOrDefault(route, null);

		if (handler != null)
			return handler;

		throw new ViewException("Route not found");
	}

	public void redirect(String route) {
		try {
			ScreenHandler handler = get(route);

			BaseController controller = handler.controller;
			String methodName = handler.method;

			Method method = controller.getClass().getMethod(methodName);
			Pane content = (Pane) method.invoke(controller);

			Scene scene = BaseLayout.getInstance().render(content);		
			stage.setScene(scene);
		} catch (Exception error) {
			throw new RuntimeException(error.getMessage());
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
