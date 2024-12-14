package views.auth;

import interfaces.IUserController;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.User;
import views.BaseView;
import views.components.MenuComponent;

public class UserIndexView extends BaseView {

	private IUserController controller;

	public UserIndexView(IUserController controller) {
		this.controller = controller;
	}

	@Override
	public Scene render() {
		MenuBar menu = MenuComponent.getInstance().render();
		
		HBox bar = new HBox();

		Text title = new Text("User Management");
		title.setFont(new Font("Arial", 24));

		Button button = new Button("Create User");
		button.setOnAction(e -> {
			screen().redirect("users.create");
		});

		bar.getChildren().addAll(title, button);

		List<User> users = (ArrayList<User>) this.data.getOrDefault("users", new ArrayList<User>());

		TableView<User> table = new TableView<>(FXCollections.observableArrayList(users));
		TableColumn<User, String> idColumn = new TableColumn<>("ID");
		TableColumn<User, String> nameColumn = new TableColumn<>("Name");
		TableColumn<User, String> emailColumn = new TableColumn<>("Email");
		TableColumn<User, String> roleColumn = new TableColumn<>("Role");

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

		table.getColumns().addAll(idColumn, nameColumn, emailColumn, roleColumn);

		VBox container = new VBox();
		container.getChildren().addAll(menu, bar, table);

		return new Scene(container);
	}

}
