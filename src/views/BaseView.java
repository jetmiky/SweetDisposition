package views;

import javafx.scene.layout.Pane;
import services.AuthService;
import services.ScreenService;
import services.StateService;

abstract public class BaseView {

	public AuthService auth() {
		return AuthService.getInstance();
	}

	public ScreenService screen() {
		return ScreenService.getInstance();
	}

	public StateService state() {
		return StateService.getInstance();
	}

	abstract public Pane render();
}
