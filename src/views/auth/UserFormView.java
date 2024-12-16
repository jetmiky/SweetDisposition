package views.auth;

import exceptions.FormException;
import interfaces.IUserController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import views.BaseModal;
import views.components.AlertComponent;

public class UserFormView extends BaseModal {

    private final IUserController controller;

    public UserFormView(IUserController controller) {
    	super("Tambah User");
        
    	this.controller = controller;
    }

    @Override
    public Scene render() {
        ImageView imageView = new ImageView();
        
        try {
            Image image = new Image(getClass().getResource("/resources/adduser.png").toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
        } catch (Exception e) {
            System.out.println("Error loading image");
        }

        // Title
        Text formTitle = new Text("Tambah User");
        formTitle.setFont(Font.font("Open Sans", FontWeight.BOLD, 24));
        formTitle.setFill(Color.BLACK);

        // Form Fields Section
        TextField nameField = new TextField();
        nameField.setPromptText("Nama Lengkap");
        nameField.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> roleField = new ComboBox<>(FXCollections.observableArrayList("Staff", "Manager", "Admin"));
        roleField.getSelectionModel().selectFirst();
        roleField.setMaxWidth(Double.MAX_VALUE);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(Double.MAX_VALUE);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password Default");
        passwordField.setMaxWidth(Double.MAX_VALUE);

        // Grid Layout for Form Fields
        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setAlignment(Pos.CENTER);
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        formGrid.getColumnConstraints().addAll(col1, col2);

        formGrid.add(new Text("Nama"), 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(new Text("Role"), 0, 1);
        formGrid.add(roleField, 1, 1);
        formGrid.add(new Text("Email"), 0, 2);
        formGrid.add(emailField, 1, 2);
        formGrid.add(new Text("Password"), 0, 3);
        formGrid.add(passwordField, 1, 3);

        // Buttons Section
        Button createButton = new Button("Create Account");
        createButton.setStyle("-fx-background-color: #1E88E5; -fx-font-size: 14px; -fx-text-fill: white; -fx-font-weight: bold;");
        createButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String role = roleField.getSelectionModel().getSelectedItem();
            String password = passwordField.getText();

            try {
                controller.store(name, email, role, password);
                
                AlertComponent.success("Sukses", "Registrasi User Sukses!");
                this.close();
            } catch (FormException error) {
                AlertComponent.error("Failed", error.getMessage());
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #E53935; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        cancelButton.setOnAction(e -> this.close());

        HBox buttonBar = new HBox(10, createButton, cancelButton);
        buttonBar.setAlignment(Pos.CENTER);

        // Main Layout
        VBox mainLayout = new VBox(20, imageView, formTitle, formGrid, buttonBar);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #f1f5f9;");

        return new Scene(mainLayout, 400, 600);
    }
    
}
