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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.lang.reflect.Field;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        //dataDisplayController.displayQuery("Query Results: ");
        dataDisplayController.headers = new ArrayList<String>();
        dataDisplayController.headers.add("country");
        dataDisplayController.headers.add("country_id");
        dataDisplayController.headers.add("totalCases");
        dataDisplayController.headers.add("gdp_usd_per_cap");
        //dataDisplayController.resultList =



        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void numCasesSortedByWealth(ActionEvent event) throws IOException {
        //sort by richest countries and show the total amount of sick people there are per country
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data-display.fxml"));
        root =loader.load();
        DataDisplayController dataDisplayController = loader.getController();
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

        dataDisplayController.headers = new ArrayList<String>();
        dataDisplayController.headers.add("country");
        dataDisplayController.headers.add("country_id");
        dataDisplayController.headers.add("totalCases");
        dataDisplayController.headers.add("gdp_usd_per_cap");

        dataDisplayController.c1.setCellValueFactory(new PropertyValueFactory<>("att1"));
        dataDisplayController.c2.setCellValueFactory(new PropertyValueFactory<>("att2"));
        dataDisplayController.c3.setCellValueFactory(new PropertyValueFactory<>("att3"));
        dataDisplayController.c4.setCellValueFactory(new PropertyValueFactory<>("att4"));

        dataDisplayController.setColText(dataDisplayController.headers);




        //System.out.println(query);
        this.connector = new Connect();
        this.connector.openConnection();
        //dataDisplayController.tableMap = this.connector.callSQL(query,dataDisplayController.headers);
        //tableViewResults contains observable list of the values that should be displayed in the table

        dataDisplayController.tableViewResult.setItems(this.connector.callSQL(query,dataDisplayController.headers));
        //System.out.println(dataDisplayController.tableViewResult);
        dataDisplayController.displayQuery("numCasesSortedByWealth");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void covidSpreadMonth(ActionEvent event)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("country_wealth_options_view.fxml"));
        root = loader.load();
        CountryWealthOptionsController countryWealthOptionsController = loader.getController();
        String queryPart1 = "select sum(accumCases), w.curMonth, sum(NewCassesPerPop)/20, sum(w.population), w.curYear\n" +
                "from (select x.id1, from_country_to_id.country, EXTRACT(YEAR FROM x.Dr1) as curYear, x.accumCases , EXTRACT(MONTH FROM x.Dr1) as curMonth, gdp_per_country.rank,\n" +
                " population_by_country_2020.Population_2020 as population, (x.accumCases/population_by_country_2020.Population_2020)*100 as NewCassesPerPop\n" +
                "\tfrom corona_data.from_country_to_id, corona_data.gdp_per_country, corona_data.population_by_country_2020, (\n" +
                "\t\tSelect corona_cases.country_id as id1, New_cases as Nc1,Date_reported as Dr1, sum(New_cases) OVER (PARTITION BY country_id ORDER BY Date_reported) as accumCases\n" +
                "\t\tFrom corona_data.corona_cases) as x\n" +
                "\twhere  from_country_to_id.COUNTRYID=x.id1 AND x.id1= gdp_per_country.country_id AND corona_data.population_by_country_2020.country_id = x.id1 AND gdp_per_country.rank ";
        String queryPart2 = "\tgroup by EXTRACT(MONTH FROM x.Dr1),EXTRACT(YEAR FROM x.Dr1), x.id1\n" +
                "\torder by gdp_per_country.rank) as w\n" +
                "group by w.curMonth, w.curYear";

        countryWealthOptionsController.headers = new ArrayList<String>();
        countryWealthOptionsController.headers.add("sum(accumCases)");
        countryWealthOptionsController.headers.add("curMonth");
        countryWealthOptionsController.headers.add("sum(NewCassesPerPop)/20");
        countryWealthOptionsController.headers.add("sum(w.population)");
        countryWealthOptionsController.headers.add("curYear");
        this.connector = new Connect();
        this.connector.openConnection();
        countryWealthOptionsController.connection = this.connector;
        countryWealthOptionsController.queryPart1 = queryPart1;
        countryWealthOptionsController.queryPart2 = queryPart2;
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}