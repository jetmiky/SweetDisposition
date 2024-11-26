package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		try {
			Application.launch(args);
		} catch (Exception e) {
			System.out.println("Error: " + e.getCause().getMessage());
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Hello world!");
		stage.show();
	}

}
