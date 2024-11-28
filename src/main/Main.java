package main;

import controllers.LoginController;
import controllers.TaskController;
import javafx.application.Application;
import javafx.stage.Stage;
import services.ScreenService;
import views.ErrorView;
import views.LoginView;
import views.task.TaskFormView;

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
	public void start(Stage primaryStage) throws Exception {
		ScreenService.initialize(primaryStage);
		ScreenService.getInstance().register("error", new ErrorView());
		ScreenService.getInstance().register("login", new LoginView(new LoginController()));
		ScreenService.getInstance().register("tasks", new TaskFormView(new TaskController()));
		
		ScreenService.getInstance().redirect("tasks");
		
		primaryStage.setTitle(APP_NAME);
		primaryStage.show();
	}
	
}
