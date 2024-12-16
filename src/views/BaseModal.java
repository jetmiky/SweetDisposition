package views;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.AuthService;
import services.StateService;

abstract public class BaseModal {

	protected Stage stage;

	public AuthService auth() {
		return AuthService.getInstance();
	}

	public StateService state() {
		return StateService.getInstance();
	}

	public BaseModal(String title) {
		this.stage = new Stage();
		this.stage.initModality(Modality.APPLICATION_MODAL);
		this.stage.setTitle(title);
	}

	public void show() {
		Scene scene = this.render();

		this.stage.setScene(scene);
		this.stage.showAndWait();
	}

	public void close() {
		this.stage.close();
	}

	abstract public Scene render();

}
