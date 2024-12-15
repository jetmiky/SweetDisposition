package views.components;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertComponent {

	private AlertComponent() {
	}
	
	private static Alert build(AlertType type, String title, String header, String message) {
		Alert alert = new Alert(type);
		
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		
		return alert;
	}
	
	public static void success(String title, String message) {
		success(title, title, message);
	}
	
	public static void success(String title, String header, String message) {
		Alert alert = build(AlertType.INFORMATION, title, header, message);
		alert.show();
	}
	
	public static void warn(String title, String message) {
		warn(title, title, message);
	}
	
	public static void warn(String title, String header, String message) {
		Alert alert = build(AlertType.WARNING, title, header, message);
		alert.show();
	}
	
	public static void error(String title, String message) {
		error(title, title, message);
	}
	
	public static void error(String title, String header, String message) {
		Alert alert = build(AlertType.ERROR, title, header, message);
		alert.show();
	}
	
	public static void info(String title, String message) {
		info(title, title, message);
	}
	
	public static void info(String title, String header, String message) {
		Alert alert = build(AlertType.NONE, title, header, message);
		alert.show();
	}
	
	public static Optional<ButtonType> confirm(String title, String message) {
		return confirm(title, title, message);
	}
	
	public static Optional<ButtonType> confirm(String title, String header, String message) {
		Alert alert = build(AlertType.CONFIRMATION, title, header, message);
		return alert.showAndWait();
	}
	
}
