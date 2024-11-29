package main;

import controllers.AuthController;
import controllers.TaskController;
import javafx.application.Application;
import javafx.stage.Stage;
import services.ScreenService;

public class Main extends Application {

	public String APP_NAME = "Sweet Disposition";

	public static void main(String[] args) {
		try {
			Application.launch(args);
		} catch (Exception e) {
			System.err.println("Error: " + e.getCause().getMessage());
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		initializeRoutes(stage);

		ScreenService.getInstance().redirect("auth.login");

		stage.setTitle(APP_NAME);
		stage.show();
	}

	public void initializeRoutes(Stage stage) {
		ScreenService.initialize(stage);

		ScreenService screen = ScreenService.getInstance();

		screen.register("auth.login", AuthController.getInstance(), "login");
		screen.register("tasks.create", TaskController.getInstance(), "create");	
	}

}
