package views;

import javafx.scene.layout.Pane;
import services.ScreenService;
import services.StateService;

abstract public class BaseView {
		
	public ScreenService screen() {
		return ScreenService.getInstance();
	}
	
	public StateService state() {
		return StateService.getInstance();
	}

	abstract public Pane render();
}
