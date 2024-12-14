package views;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import services.ScreenService;

abstract public class BaseView {

	protected Map<String, Object> data = new HashMap<>();

	public void data(String key, Object value) {
		this.data.put(key, value);
	}
	
	public ScreenService screen() {
		return ScreenService.getInstance();
	}

	abstract public Scene render();
}
