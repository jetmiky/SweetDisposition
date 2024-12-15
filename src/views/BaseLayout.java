package views;

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import views.components.FooterComponent;
import views.components.MenuComponent;

public class BaseLayout {

	private static BaseLayout instance;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 800;
	
	private BaseLayout() {
	}
	
	public static BaseLayout getInstance() {
		if (instance == null) instance = new BaseLayout();
		return instance;
	}
	
	public Scene render(Pane content) {
		MenuBar menu = MenuComponent.getInstance().render();
		Pane footer = FooterComponent.getInstance().render();
		
		BorderPane container = new BorderPane();

		container.setTop(menu);
		container.setCenter(content);
		container.setBottom(footer);
		
		return new Scene(container, WIDTH, HEIGHT);
	}
	
}
