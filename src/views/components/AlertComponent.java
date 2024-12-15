package views.components;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertComponent {

	private AlertComponent() {
	}
	
	private static Alert build(AlertType type, String title, String message) {
		Alert alert = new Alert(type);
		
		alert.setTitle(title);
		alert.setHeaderText(title);
		alert.setContentText(message);
		
		return alert;
	}
	
	public static void success(String title, String message) {
		Alert alert = build(AlertType.INFORMATION, title, message);
		alert.show();
	}
	
	public static void warn(String title, String message) {
		Alert alert = build(AlertType.WARNING, title, message);
		alert.show();
	}
	
	public static void error(String title, String message) {
		Alert alert = build(AlertType.ERROR, title, message);
		alert.show();
	}
	
	public static void info(String title, String message) {
		Alert alert = build(AlertType.NONE, title, message);
		alert.show();
	}
	
	public static Optional<ButtonType> confirm(String title, String message) {
		Alert alert = build(AlertType.CONFIRMATION, title, message);
		return alert.showAndWait();
	}
}
