package com.example.ecovid;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController extends Application {
    @FXML
    private TextField nameTextField;
    private Parent root;
    private Stage stage;
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource("menu-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
        //runMenu();
    }

    private void runMenu() throws IOException {

    }

    public void button(ActionEvent event) throws IOException {
        String userName = nameTextField.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data-display.fxml"));
        root =loader.load();
        DataDisplayController dataDisplayController = loader.getController();
        dataDisplayController.displayQuery("Query Results: ");

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}