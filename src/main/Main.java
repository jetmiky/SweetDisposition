package main;

import controllers.AuthController;
import controllers.TaskController;
import exceptions.ViewException;
import javafx.application.Application;
import javafx.stage.Stage;
import services.ScreenService;

public class Main extends Application {

	public String APP_NAME = "Sweet Disposition";

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
			System.err.println("Global exception handler: " + throwable.getMessage());
		});

		try {
			initializeRoutes(stage);

			ScreenService.getInstance().redirect("auth.login");

			stage.setTitle(APP_NAME);
			stage.show();
		} catch (ViewException error) {
			System.err.println("View exception handler: " + error.getMessage());
		} catch (Exception error) {
			System.err.println("Other exception handler: " + error.getMessage());
		}
	}

	public void initializeRoutes(Stage stage) {
		ScreenService.initialize(stage);

		ScreenService screen = ScreenService.getInstance();

		screen.register("auth.login", AuthController.getInstance(), "login");
		screen.register("tasks.create", TaskController.getInstance(), "create");
	}

}
