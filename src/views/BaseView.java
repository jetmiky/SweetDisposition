package views;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import services.ScreenService;

abstract public class BaseView {
	public static Stage stage = new Stage();
	public static BorderPane bp = new BorderPane();
	public static Scene scene = new Scene(bp, 1280,720);
	public static GridPane gp = new GridPane();
	public static TilePane tp = new TilePane();
	public static FlowPane fp = new FlowPane();
	public static ScrollPane sp = new ScrollPane();
	
	protected Map<String, Object> data = new HashMap<>();

	public void data(String key, Object value) {
		this.data.put(key, value);
	}
	
	public ScreenService screen() {
		return ScreenService.getInstance();
	}

	abstract public Scene render();
}
