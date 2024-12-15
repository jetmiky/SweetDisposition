package views;

import javafx.scene.layout.Pane;
import services.ScreenService;

abstract public class BaseView {
		
	public ScreenService screen() {
		return ScreenService.getInstance();
	}

	abstract public Pane render();
}
