package com.example.ecovid;
//package DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;


public class Connect {
    Connection conn; // DB connection
    public boolean openConnection() {
        // loading the driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load the MySQL JDBC driver..");
            return false;
        }
        System.out.println("Driver loaded successfully");

        System.out.print("Trying to connect... ");

        // creating the connection. Parameters should be taken from config file.
        String host = "localhost";
        String port = "3306";
        String schema = "corona_data";
        String user = "root";
        String password = "#gL!P3NC2?msA9=";
        try {
            System.out.println("trying to connect");
            conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + schema, user, password);
        } catch (SQLException e) {
            System.out.println("Unable to connect - " + e.getMessage());
            conn = null;
            return false;
        }
        System.out.println("Connected!");
        return true;
    }
    public void numCasesSortedByWealth() {
        //sort by richest countries and show the total amount of sick people there are per country
        /*
        Select from_country_to_id.country, corona_cases.country_id, sum(New_cases) as totalCases, gdp_usd_per_cap
        From corona_data.corona_cases, corona_data.from_country_to_id, corona_data.gdp_per_country
        where from_country_to_id.COUNTRYID = corona_cases.country_id AND corona_cases.
        country_id = gdp_per_country.country_id
        group by gdp_usd_per_cap
        order by gdp_usd_per_cap Desc
         */


    }

    private String getRsVal(String header, ResultSet rs, int index) throws SQLException {
        String val="";
        switch (header){


            case "country":
                val = rs.getString(header);
                break;
            case "sum(accumCases)":
            case "curMonth":
            case "sum(w.population)":
            case "curYear":
            case "country_id":
            case "totalCases":
            case "gdp_usd_per_cap":
                val = valueOf(rs.getInt(header));
                break;
            case "sum(NewCassesPerPop)/20":
                val = valueOf(rs.getDouble(header));
                break;
        }
        return val;
    }

    public ObservableList<ModelTable> callSQL(String query,List<String> headers) {

        //HashMap<String,List<String>> resMap = new HashMap<>();
        //the observable list will hold headers and rows
        ObservableList<ModelTable> obList = FXCollections.observableArrayList();
        //execute the query
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
/*            for (int i = 0; i<headers.size();i++){
                resMap.put(headers.get(i),new ArrayList<String>());
            }*/
            List<String> line; //keeps each line to create a ModelTable
            //open up the query line by line
            while (rs.next()) {
                line = new ArrayList<>();
                for (int i = 0; i<headers.size();i++){
                    //resMap.get(headers.get(i)).add(getRsVal(headers.get(i),rs,i));
                    //add each line to lines array
                    line.add(getRsVal(headers.get(i),rs,i));

                }
                //once done, set the lines array to the observible list.
                obList.add(new ModelTable(line));
            }

        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        //return observable list;
        return obList;
    }
/*
SELECT * FROM corona_data.country_vaccinations
*/

    /**
     * close the connection
     */
    public void closeConnection() {
        // closing the connection
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Unable to close the connection - " + e.getMessage());
        }

    }
    public void pri(){
        System.out.println("hedf");
    }
}











