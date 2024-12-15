package views;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ErrorView extends BaseView {

	private String message;
	
	public ErrorView() {
		this.message = "Unexpected error occured";
	}
	
	public ErrorView(String message) {
		this.message = message;
	}
	
	@Override
	public Pane render() {
		VBox container = new VBox();
		Text text = new Text(this.message);
		
		container.getChildren().add(text);
		
		return container;
	}
	
}
