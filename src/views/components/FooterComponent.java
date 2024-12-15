package views.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class FooterComponent {
	
	private static FooterComponent instance;

	private FooterComponent() {
	}
	
	public static FooterComponent getInstance() {
		if (instance == null) instance = new FooterComponent();
		return instance;
	}
	
	public Pane render() {
		HBox footer = new HBox(6);
		footer.setPadding(new Insets(10));
		footer.setStyle("-fx-background-color: #1565C0;");

		Text text = new Text("Created with love and passion");
		text.setFill(Color.WHITE);
		
		footer.getChildren().addAll(text);
		footer.setAlignment(Pos.CENTER);

		return footer;
	}
}
