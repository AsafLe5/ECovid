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

    public void vaccRateMonth(ActionEvent event)  throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("country_wealth_options_view.fxml"));
        root = loader.load();
        CountryWealthOptionsController countryWealthOptionsController = loader.getController();
        String queryPart1 = "select w.curYear, w.curMonth, sum(accumVacc), sum(w.population), sum(NewVacPerPop)/40\n" +
                " from (\n" +
                "\tselect EXTRACT(YEAR FROM x.Dr1) as curYear,EXTRACT(MONTH FROM x.Dr1) as curMonth, x.accumVacc,\n" +
                " \t\tpopulation_by_country_2020.Population_2020 as population, (x.accumVacc/population_by_country_2020.Population_2020)*100 as NewVacPerPop\n" +
                "\tfrom corona_data.from_country_to_id, corona_data.gdp_per_country, corona_data.population_by_country_2020, (\n" +
                " \t\t\tSelect country_vaccinations.country_id as id1, daily_vaccinations as dV1,Date as Dr1, sum(daily_vaccinations) OVER (PARTITION BY country_id ORDER BY Date) as accumVacc\n" +
                " \t\t\tFrom corona_data.country_vaccinations) as x\n" +
                " \twhere  from_country_to_id.COUNTRYID=x.id1 AND x.id1= gdp_per_country.country_id AND corona_data.population_by_country_2020.country_id = x.id1 AND gdp_per_country.rank ";
        String queryPart2 = "AND EXTRACT(DAY FROM x.Dr1) = (\n"+
        "Select Max(EXTRACT(DAY FROM country_vaccinations.date)) from corona_data.country_vaccinations where x.id1= country_vaccinations.country_id AND\n"+
        "EXTRACT(MONTH FROM country_vaccinations.date) = EXTRACT(MONTH FROM x.Dr1) AND EXTRACT(YEAR FROM country_vaccinations.date) = EXTRACT(YEAR FROM x.Dr1))\n"+
        "group by EXTRACT(MONTH FROM x.Dr1),EXTRACT(YEAR FROM x.Dr1), x.id1\n"+
        "order by gdp_per_country.rank) as w\n"+
        "where (w.curYear!= 2021 or w.curMonth<=9)\n"+
        "group by w.curMonth, w.curYear\n"+
        "order by w.curYear, w.curMonth";

        //create the headers
        countryWealthOptionsController.headers = new ArrayList<String>();
        countryWealthOptionsController.headers.add("curYear");
        countryWealthOptionsController.headers.add("curMonth");
        countryWealthOptionsController.headers.add("sum(accumVacc)");
        countryWealthOptionsController.headers.add("sum(w.population)");
        countryWealthOptionsController.headers.add("sum(NewVacPerPop)/40");
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

    public void totVacCountry(ActionEvent event) throws IOException {
        //sort by richest countries and show the total amount of sick people there are per country
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data-display.fxml"));
        root =loader.load();
        DataDisplayController dataDisplayController = loader.getController();
        String query =
                "select x.id1, x.countryName, max(accumVacc), x.population, ((max(accumVacc)/2)/x.population)*100 as vaccinePerPopulation, x.countryRank as countryRank, x.times_visited\n"+
        "from (\n"+
                "Select cdcv.country_id as id1, daily_vaccinations as dV1,Date as Dr1, sum(daily_vaccinations) OVER (PARTITION BY cdcv.country_id ORDER BY Date) as accumVacc,\n"+
                "population_by_country_2020.Population_2020 as population, from_country_to_id.Country as countryName, gdp_per_country.rank as countryRank, from_country_to_id.times_visited as times_visited\n"+
                "From corona_data.country_vaccinations as cdcv, corona_data.from_country_to_id, corona_data.population_by_country_2020, corona_data.gdp_per_country\n"+
                "where corona_data.from_country_to_id.COUNTRYID=cdcv.country_id AND corona_data.population_by_country_2020.country_id = cdcv.country_id AND cdcv.country_id= gdp_per_country.country_id) as x\n"+
        "group by x.id1\n"+
        "order by vaccinePerPopulation desc";

        dataDisplayController.headers = new ArrayList<String>();
        dataDisplayController.headers.add("id1");
        dataDisplayController.headers.add("countryName");
        dataDisplayController.headers.add("max(accumVacc)");
        dataDisplayController.headers.add("population");
        dataDisplayController.headers.add("vaccinePerPopulation");
        dataDisplayController.headers.add("countryRank");
        dataDisplayController.headers.add("times_visited");

        dataDisplayController.c1.setCellValueFactory(new PropertyValueFactory<>("att1"));
        dataDisplayController.c2.setCellValueFactory(new PropertyValueFactory<>("att2"));
        dataDisplayController.c3.setCellValueFactory(new PropertyValueFactory<>("att3"));
        dataDisplayController.c4.setCellValueFactory(new PropertyValueFactory<>("att4"));
        dataDisplayController.c5.setCellValueFactory(new PropertyValueFactory<>("att5"));
        dataDisplayController.c6.setCellValueFactory(new PropertyValueFactory<>("att6"));
        dataDisplayController.c7.setCellValueFactory(new PropertyValueFactory<>("att7"));

        dataDisplayController.setColText(dataDisplayController.headers);




        //System.out.println(query);
        this.connector = new Connect();
        this.connector.openConnection();
        //dataDisplayController.tableMap = this.connector.callSQL(query,dataDisplayController.headers);
        //tableViewResults contains observable list of the values that should be displayed in the table

        dataDisplayController.tableViewResult.setItems(this.connector.callSQL(query,dataDisplayController.headers));
        //System.out.println(dataDisplayController.tableViewResult);
        dataDisplayController.displayQuery("Total percent of vaccinated people per country");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    //DID VAC CORRECT AS GETSTRING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void countryPhizer(ActionEvent event) throws IOException {
        //sort by richest countries and show the total amount of sick people there are per country
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data-display.fxml"));
        root =loader.load();
        DataDisplayController dataDisplayController = loader.getController();
        String query =
                "SELECT country_id, from_country_to_id.Country, vacc_correct\n"+
        "FROM corona_data.type_vacc, corona_data.from_country_to_id\n"+
        "where type_vacc.country_id = from_country_to_id.COUNTRYID AND vacc_correct = \'Pfizer/BioNTech\'";

        dataDisplayController.headers = new ArrayList<String>();
        dataDisplayController.headers.add("country_id");
        dataDisplayController.headers.add("Country");
        //DID VAC CORRECT AS GETSTRING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        dataDisplayController.headers.add("vacc_correct");

        dataDisplayController.c1.setCellValueFactory(new PropertyValueFactory<>("att1"));
        dataDisplayController.c2.setCellValueFactory(new PropertyValueFactory<>("att2"));
        dataDisplayController.c3.setCellValueFactory(new PropertyValueFactory<>("att3"));

        dataDisplayController.setColText(dataDisplayController.headers);




        //System.out.println(query);
        this.connector = new Connect();
        this.connector.openConnection();
        //dataDisplayController.tableMap = this.connector.callSQL(query,dataDisplayController.headers);
        //tableViewResults contains observable list of the values that should be displayed in the table

        dataDisplayController.tableViewResult.setItems(this.connector.callSQL(query,dataDisplayController.headers));
        //System.out.println(dataDisplayController.tableViewResult);
        dataDisplayController.displayQuery("Countries that used Phizer");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void richestMostVacc(ActionEvent event) throws IOException {
        //sort by richest countries and show the total amount of sick people there are per country
        FXMLLoader loader = new FXMLLoader(getClass().getResource("data-display.fxml"));
        root =loader.load();
        DataDisplayController dataDisplayController = loader.getController();
        String query =
                "select w.id1, w.countryName,max(w.vaccinePerPopulation), w.population, w.countryRank, w.times_visited\n" +
                        "from (select x.id1 as id1, x.countryName as countryName, max(accumVacc),\n" +
                        "\t\tx.population as population, ((max(accumVacc)/2)/x.population)*100 as vaccinePerPopulation, x.countryRank as countryRank, x.times_visitedS as times_visited\n" +
                        "\tfrom (\n" +
                        "\t\tSelect cdcv.country_id as id1, daily_vaccinations as dV1,Date as Dr1, sum(daily_vaccinations) OVER (PARTITION BY cdcv.country_id ORDER BY Date) as accumVacc,\n" +
                        "\t\t population_by_country_2020.Population_2020 as population, from_country_to_id.Country as countryName, gdp_per_country.rank as countryRank, from_country_to_id.times_visited as times_visitedS\n" +
                        "\t\tFrom corona_data.country_vaccinations as cdcv, corona_data.from_country_to_id, corona_data.population_by_country_2020, corona_data.gdp_per_country\n" +
                        "\t\twhere corona_data.from_country_to_id.COUNTRYID=cdcv.country_id AND corona_data.population_by_country_2020.country_id = cdcv.country_id AND cdcv.country_id= gdp_per_country.country_id) as x \n" +
                        "\tgroup by x.id1\n" +
                        "\torder by vaccinePerPopulation desc) as w\n" +
                        "where countryRank<30";

        dataDisplayController.headers = new ArrayList<String>();
        dataDisplayController.headers.add("id1");
        dataDisplayController.headers.add("countryName");
        dataDisplayController.headers.add("max(w.vaccinePerPopulation)");
        dataDisplayController.headers.add("population");
        dataDisplayController.headers.add("countryRank");
        dataDisplayController.headers.add("times_visited");

        dataDisplayController.c1.setCellValueFactory(new PropertyValueFactory<>("att1"));
        dataDisplayController.c2.setCellValueFactory(new PropertyValueFactory<>("att2"));
        dataDisplayController.c3.setCellValueFactory(new PropertyValueFactory<>("att3"));
        dataDisplayController.c4.setCellValueFactory(new PropertyValueFactory<>("att4"));
        dataDisplayController.c5.setCellValueFactory(new PropertyValueFactory<>("att5"));
        dataDisplayController.c6.setCellValueFactory(new PropertyValueFactory<>("att6"));

        dataDisplayController.setColText(dataDisplayController.headers);




        //System.out.println(query);
        this.connector = new Connect();
        this.connector.openConnection();
        //dataDisplayController.tableMap = this.connector.callSQL(query,dataDisplayController.headers);
        //tableViewResults contains observable list of the values that should be displayed in the table

        dataDisplayController.tableViewResult.setItems(this.connector.callSQL(query,dataDisplayController.headers));
        //System.out.println(dataDisplayController.tableViewResult);
        dataDisplayController.displayQuery("Richest most vaccinated country");
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}