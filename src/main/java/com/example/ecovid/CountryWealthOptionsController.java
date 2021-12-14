package com.example.ecovid;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class CountryWealthOptionsController {
    @FXML
    private Button rich;
    @FXML
    private Button middle;
    @FXML
    private Button poor;

    public String queryPart1;
    public String queryPart2;
    public Connect connection;
    private Parent root;
    public List<String> headers;
    private Stage stage;
    private Scene scene;


    //does for the 20 richest country
    public void richOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data-display.fxml"));
        root = loader.load();
        DataDisplayController dataDisplayController = loader.getController();
        //put the <=20 part in the query
        String fullQuery = queryPart1 + "<=20" + queryPart2;
        dataDisplayController.tableViewResult.setItems(connection.callSQL(fullQuery,headers));

        dataDisplayController.c1.setCellValueFactory(new PropertyValueFactory<>("att1"));
        dataDisplayController.c2.setCellValueFactory(new PropertyValueFactory<>("att2"));
        dataDisplayController.c3.setCellValueFactory(new PropertyValueFactory<>("att3"));
        dataDisplayController.c4.setCellValueFactory(new PropertyValueFactory<>("att4"));
        dataDisplayController.c5.setCellValueFactory(new PropertyValueFactory<>("att5"));
        dataDisplayController.setColText(headers);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //does for the 20 middle countries
    public void middleOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data-display.fxml"));
        root = loader.load();
        DataDisplayController dataDisplayController = loader.getController();
        String fullQuery = queryPart1 + "BETWEEN 91 AND 110" + queryPart2;

        dataDisplayController.tableViewResult.setItems(connection.callSQL(fullQuery,headers));

        dataDisplayController.c1.setCellValueFactory(new PropertyValueFactory<>("att1"));
        dataDisplayController.c2.setCellValueFactory(new PropertyValueFactory<>("att2"));
        dataDisplayController.c3.setCellValueFactory(new PropertyValueFactory<>("att3"));
        dataDisplayController.c4.setCellValueFactory(new PropertyValueFactory<>("att4"));
        dataDisplayController.c5.setCellValueFactory(new PropertyValueFactory<>("att5"));
        dataDisplayController.setColText(headers);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //does for the 20 poorest countries
    public void poorOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data-display.fxml"));
        root = loader.load();
        DataDisplayController dataDisplayController = loader.getController();
        String fullQuery = queryPart1 + ">175" + queryPart2;

        dataDisplayController.tableViewResult.setItems(connection.callSQL(fullQuery,headers));

        dataDisplayController.c1.setCellValueFactory(new PropertyValueFactory<>("att1"));
        dataDisplayController.c2.setCellValueFactory(new PropertyValueFactory<>("att2"));
        dataDisplayController.c3.setCellValueFactory(new PropertyValueFactory<>("att3"));
        dataDisplayController.c4.setCellValueFactory(new PropertyValueFactory<>("att4"));
        dataDisplayController.c5.setCellValueFactory(new PropertyValueFactory<>("att5"));
        dataDisplayController.setColText(headers);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public  void backToMenu(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-view.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
