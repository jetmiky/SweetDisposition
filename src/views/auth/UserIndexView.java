package views.auth;

import interfaces.IAuthController;
import interfaces.IUserController;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controllers.UserController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.User;
import repositories.UserRepository;
import views.BaseView;
import views.components.MenuComponent;

public class UserIndexView extends BaseView {

    private IUserController controller;
    private TableView<User> table;
    private List<User> allUsers;

    public UserIndexView(IUserController controller) {
        this.controller = controller;
    }

    @Override
    public Scene render() {
        // Menu bar
        MenuBar menu = MenuComponent.getInstance().render();

        // Header section
        Text title = new Text("Hallo, Admin");
        title.setFont(new Font("Open Sans", 30));
        title.setFill(Color.BLACK);

        Button addUserButton = new Button("Tambah User");
        addUserButton.setStyle(
            "-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;"
        );
        addUserButton.setOnAction(e -> {
            // Create a new Stage for the modal
            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL); // Make it modal
            modal.setTitle("Tambah User");
            
            // Create a new UserFormView and pass the controller
            UserFormView formView = new UserFormView(controller);
            Scene formScene = formView.render();

            // Set the scene for the modal stage
            modal.setScene(formScene);
            modal.setResizable(false); // Optional: Disable resizing
            modal.showAndWait(); // Show the modal and wait for it to close
        	
        });
        addUserButton.setOnAction(e -> screen().redirect("users.create"));

        HBox header = new HBox(20, title, addUserButton);
        header.setAlignment(Pos.CENTER_LEFT);
//        header.setPadding(new Insets(10));

//        // Cards for totals
//        int totalAdmins = (int) allUsers.stream().filter(user -> "Admin".equals(user.getRole())).count();
//        int totalManagers = (int) allUsers.stream().filter(user -> "Manager".equals(user.getRole())).count();
//        int totalStaff = (int) allUsers.stream().filter(user -> "Staff".equals(user.getRole())).count();
//
//        Text adminCard = createCard("Total Admins", totalAdmins);
//        Text managerCard = createCard("Total Managers", totalManagers);
//        Text staffCard = createCard("Total Staff", totalStaff);
//
//        HBox cards = new HBox(20, adminCard, managerCard, staffCard);
//        cards.setAlignment(Pos.CENTER);
//        cards.setPadding(new Insets(10));
        
        
        // Search bar
        TextField searchField = new TextField();
        searchField.setPromptText("Cari User");
        searchField.setPrefWidth(300);

        Button searchButton = new Button("Cari");
        searchButton.setStyle(
            "-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5px;"
        );
        searchButton.setOnAction(e -> {
            String query = searchField.getText().toLowerCase();
            List<User> filteredUsers = allUsers.stream()
                .filter(user -> user.getName().toLowerCase().contains(query))
                .collect(Collectors.toList());
            table.setItems(FXCollections.observableArrayList(filteredUsers));
        });

        HBox searchBar = new HBox(10, searchField, searchButton);
        searchBar.setAlignment(Pos.CENTER_RIGHT);
//        searchBar.setPadding(new Insets(10));

        // Table view
        allUsers = (ArrayList<User>) this.data.getOrDefault("users", new ArrayList<User>());
        int totalAdmins = 0, totalManagers = 0, totalStaff = 0;
        if (allUsers != null && !allUsers.isEmpty()) {
            totalAdmins = (int) allUsers.stream().filter(user -> "admin".equals(user.getRole())).count();
            totalManagers = (int) allUsers.stream().filter(user -> "manager".equals(user.getRole())).count();
            totalStaff = (int) allUsers.stream().filter(user -> "staff".equals(user.getRole())).count();
        }
        
     // Create and display the cards with text inside the rectangles
        HBox adminCard = createCard(totalAdmins, "  Admins");
        HBox managerCard = createCard(totalManagers, "  Managers");
        HBox staffCard = createCard(totalStaff, "  Staff");

        HBox cards = new HBox(20, adminCard, managerCard, staffCard);
        cards.setAlignment(Pos.CENTER_LEFT);
        cards.setPadding(new Insets(10));

        
        
        
        table = new TableView<>(FXCollections.observableArrayList(allUsers));
        table.setStyle("-fx-border-color: purple; -fx-border-width: 2px;");

        TableColumn<User, String> noColumn = new TableColumn<>("No");
        TableColumn<User, String> nameColumn = new TableColumn<>("Nama");
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        TableColumn<User, Void> actionsColumn = new TableColumn<>("Actions");

        noColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Style and add action buttons
        actionsColumn.setCellFactory(col -> new TableCellWithActions());

        table.getColumns().addAll(noColumn, nameColumn, emailColumn, roleColumn, actionsColumn);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Container layout
        VBox container = new VBox(menu, header, cards, searchBar, table);
        container.setSpacing(10);
        container.setPadding(new Insets(10));
        container.setStyle("-fx-background-color: #f1f5f9;");

        return new Scene(container, 1000, 600);
    }

    private class TableCellWithActions extends javafx.scene.control.TableCell<User, Void> {
        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
                return;
            }

            User user = getTableView().getItems().get(getIndex());
            Button editButton = new Button("Edit");
            editButton.setStyle(
                "-fx-background-color: #1E88E5; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 3px;"
            );
            editButton.setOnAction(e -> {
                // Tampilkan modal untuk edit user
                new UserAssignStaff(controller, user).show();

                // Dapatkan data terbaru setelah modal ditutup
                String newRole = user.getRole();
                Integer id = user.getId();

                // Update role user
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                boolean isUpdated = UserController.getInstance().updateUserRole(id, newRole);
                if (isUpdated) {
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Update Berhasil");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Pengguna berhasil diupdate!");
                    successAlert.showAndWait();

                    // Perbarui data tabel
                    allUsers = UserRepository.getInstance().getAll();
                    table.setItems(FXCollections.observableArrayList(allUsers));
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Update Gagal");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Terjadi kesalahan saat mengupdate pengguna.");
                    errorAlert.showAndWait();
                }
                table.refresh();
            }
        });
            });




            Button deleteButton = new Button("Delete");
            deleteButton.setStyle(
                "-fx-background-color: #E53935; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-background-radius: 3px;"
            );
            deleteButton.setOnAction(e -> {
                controller.deleteUser(user.getId());
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Konfirmasi Penghapusan");
                confirmationAlert.setHeaderText("Anda yakin ingin menghapus pengguna ini?");
                confirmationAlert.setContentText("Pengguna dengan nama: " + user.getName() + " akan dihapus.");

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        boolean isDeleted = getTableView().getItems().remove(user); // Memanggil metode hapus di controller
                        if (isDeleted) {
                            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                            successAlert.setTitle("Penghapusan Berhasil");
                            successAlert.setHeaderText(null);
                            successAlert.setContentText("Pengguna berhasil dihapus!");
                            successAlert.showAndWait();
                            UserRepository.getInstance().delete(user);
                        } else {
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setTitle("Penghapusan Gagal");
                            errorAlert.setHeaderText(null);
                            errorAlert.setContentText("Terjadi kesalahan saat menghapus pengguna.");
                            errorAlert.showAndWait();
                        }
                    }
                });
            });
            
            HBox actionButtons = new HBox(5, editButton, deleteButton);
            actionButtons.setAlignment(Pos.CENTER);

            setGraphic(actionButtons);
        }
    }

    private HBox createCard(int count, String title) {
        // Create a rectangle to act as the card background
        javafx.scene.shape.Rectangle cardBackground = new javafx.scene.shape.Rectangle(200, 80);
        cardBackground.setStyle("-fx-background-color: #dcefff");
        cardBackground.setArcWidth(10);  // Rounded corners
        cardBackground.setArcHeight(10);

        // Create the text to display inside the card
        Text cardText = new Text(count + title);
        cardText.setFont(new Font("Open Sans", 18));
        cardText.setFill(Color.DARKBLUE);

        // Create a VBox to center the text inside the rectangle
        VBox cardContent = new VBox(cardText);
        cardContent.setAlignment(Pos.CENTER);  // Center the text
        cardContent.setPrefWidth(200);
        cardContent.setPrefHeight(80);
        cardContent.setStyle("-fx-padding: 10px;");  // Add some padding to prevent the text from touching the edges

        // Set the background rectangle size and place it behind the text
        cardContent.setStyle("-fx-background-color: #dcefff; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        // Combine the rectangle and text into a single card container
        HBox card = new HBox(cardContent);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(200, 80);  // Define the card size
        
        return card;
    }


    private VBox createCardWithTextAbove(String title, int count) {
        // Create the text to display above the card
        Text cardText = new Text(count + title);
        
        cardText.setFont(new Font("Open Sans", 18));
        cardText.setFill(Color.DARKBLUE);

        // Create a rectangle to act as the card background
        javafx.scene.shape.Rectangle cardBackground = new javafx.scene.shape.Rectangle(200, 80);
        cardBackground.setFill(Color.LIGHTBLUE);
        cardBackground.setArcWidth(10);  // Rounded corners
        cardBackground.setArcHeight(10);

        // Arrange the text and rectangle in a VBox
        VBox card = new VBox(10, cardText, cardBackground);
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);
        card.setPrefHeight(120);  // Adjust the height to accommodate text and rectangle
        return card;
    }

    
}
