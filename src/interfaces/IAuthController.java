package interfaces;

import exceptions.AuthException;
import exceptions.FormException;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public interface IAuthController {

	public Scene login();
	public static final Stage stage = new Stage();
	public static final BorderPane bp = new BorderPane();
	public static final Scene scene = new Scene(bp, 1280,720);
	public static final GridPane gp = new GridPane();
	public static final TilePane tp = new TilePane();
	public static final FlowPane fp = new FlowPane();
	public static final ScrollPane sp = new ScrollPane();
	
	public void attemptLogin(String email, String password) throws AuthException, FormException;

	public void logout();
}
