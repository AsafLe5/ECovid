package com.example.ecovid;
import com.example.ecovid.Connect;

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
    private Connect connector;
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
        this.connector = new Connect();
        this.connector.openConnection();
        System.out.println(this.connector);
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

    public void numCasesSortedByWealth(ActionEvent event) {
        //sort by richest countries and show the total amount of sick people there are per country

        String query =
        "Select from_country_to_id.country, corona_cases.country_id, sum(New_cases) as totalCases, gdp_usd_per_cap "
        +System.lineSeparator()+
        "From corona_data.corona_cases, corona_data.from_country_to_id, corona_data.gdp_per_country "
                +System.lineSeparator()+
        "where from_country_to_id.COUNTRYID = corona_cases.country_id AND corona_cases.country_id = gdp_per_country.country_id "
                +System.lineSeparator()+
        "group by gdp_usd_per_cap "
                +System.lineSeparator()+
        "order by gdp_usd_per_cap Desc";

        System.out.println(query);
        this.connector = new Connect();
        this.connector.openConnection();
        this.connector.callSQL(query);



    }



    public static void main(String[] args) {
        launch();
    }
}