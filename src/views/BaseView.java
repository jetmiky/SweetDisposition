package views;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;

abstract public class BaseView<T> {

	protected Map<String, Object> data = new HashMap<>();

	public BaseView<T> with(String key, Object value) {
		this.data.put(key, value);
		return this;
	}

	abstract public Scene render();
}
