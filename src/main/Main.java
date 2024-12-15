package main;

import java.net.URISyntaxException;

import controllers.AuthController;
import controllers.TaskController;
import controllers.UserController;
import javafx.application.Application;
import javafx.scene.image.Image;
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
			System.err.println("Uncaught error: " + throwable.getMessage());
		});

		initializeRoutes(stage);
		setStageIcon(stage, "/resources/icons.png");
		
		ScreenService.getInstance().redirect("auth.login");
		
		stage.setTitle(APP_NAME);
		stage.show();
	}

	public void initializeRoutes(Stage stage) {
		ScreenService.initialize(stage);

		ScreenService screen = ScreenService.getInstance();

		screen.register("auth.login", AuthController.getInstance(), "login");
		screen.register("users.index", UserController.getInstance(), "index");
		screen.register("users.create", UserController.getInstance(), "create");
		screen.register("tasks.index.manager", TaskController.getInstance(), "manager");
		screen.register("tasks.index.staff", TaskController.getInstance(), "staff");
		screen.register("tasks.create", TaskController.getInstance(), "create");
		screen.register("tasks.view", TaskController.getInstance(), "view");
	}
	
	public void setStageIcon(Stage stage, String path) {
		try {			
			stage.getIcons().add(new Image(getClass().getResource(path).toURI().toString()));
		} catch (URISyntaxException error) {
			System.err.println("Error setting icon: " + error.getMessage());
		}
	}

}
