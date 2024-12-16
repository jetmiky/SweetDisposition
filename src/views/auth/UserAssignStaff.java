package views.auth;

import exceptions.FormException;
import interfaces.IUserController;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.User;
import views.BaseModal;
import views.components.AlertComponent;

public class UserAssignStaff extends BaseModal {

    private final IUserController controller;
    private final User user;
    private final ObservableList<User> managers;

    public UserAssignStaff(IUserController controller, User user, List<User> managers) {
        super("Assign Staff to Manager");
    	
    	this.controller = controller;
        this.user = user;
        this.managers = FXCollections.observableArrayList(managers);
    }

    @Override
    public Scene render() {
        ImageView imageView = new ImageView();
        
        try {
            Image image = new Image(getClass().getResource("/resources/teamgoals.png").toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
        } catch (Exception e) {
            System.out.println("Error loading image");
        }

        // Title
        Text formTitle = new Text("Assign Staff to Manager");
        formTitle.setFont(Font.font("Open Sans", FontWeight.BOLD, 24));
        formTitle.setFill(Color.BLACK);

        // User Details Section (styled like a card)
        Text detailsTitle = new Text("Current Staff Details");
        detailsTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));
        detailsTitle.setFill(Color.DARKSLATEGRAY);

        TextField nameField = new TextField(user.getName());
        nameField.setEditable(false);
        nameField.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: #BDBDBD; -fx-font-size: 14px;");
        nameField.setMaxWidth(Double.MAX_VALUE);

        TextField roleField = new TextField(user.getRole());
        roleField.setEditable(false);
        roleField.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: #BDBDBD; -fx-font-size: 14px;");
        roleField.setMaxWidth(Double.MAX_VALUE);

        GridPane userDetailsGrid = new GridPane();
        userDetailsGrid.setHgap(10);
        userDetailsGrid.setVgap(10);
        userDetailsGrid.setPadding(new Insets(10, 0, 10, 0));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        userDetailsGrid.getColumnConstraints().addAll(col1, col2);

        userDetailsGrid.add(new Text("Name:"), 0, 0);
        userDetailsGrid.add(nameField, 1, 0);
        userDetailsGrid.add(new Text("Role:"), 0, 1);
        userDetailsGrid.add(roleField, 1, 1);

        VBox userDetailsCard = new VBox(10, detailsTitle, userDetailsGrid);
        userDetailsCard.setPadding(new Insets(15));
        userDetailsCard.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #E0E0E0; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        // Manager ComboBox Section
        Text managerSelectionTitle = new Text("Select Manager");
        managerSelectionTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        managerSelectionTitle.setFill(Color.DARKSLATEGRAY);

        ComboBox<User> managerComboBox = new ComboBox<>(this.managers);
        managerComboBox.setMaxWidth(Double.MAX_VALUE);
        managerComboBox.getSelectionModel().selectFirst();

        VBox managerSelectionBox = new VBox(10, managerSelectionTitle, managerComboBox);
        managerSelectionBox.setPadding(new Insets(10, 0, 10, 0));

        // Buttons
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        saveButton.setOnAction(e -> {
            User manager = managerComboBox.getSelectionModel().getSelectedItem();
            user.setSupervisor(manager);

            try {
                controller.update(user);
                
                AlertComponent.success("Success", "Staff successfully assigned to manager!");
                this.close();
            } catch (FormException error) {
                AlertComponent.error("Failed", error.getMessage());
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #E53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        cancelButton.setOnAction(e -> this.close());

        HBox buttonBar = new HBox(10, saveButton, cancelButton);
        buttonBar.setAlignment(Pos.CENTER);

        // Main Layout
        VBox mainLayout = new VBox(20, imageView, formTitle, userDetailsCard, managerSelectionBox, buttonBar);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #f1f5f9;");

        return new Scene(mainLayout, 450, 600);
    }
    
}
