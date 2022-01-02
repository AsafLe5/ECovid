package com.example.ecovid;
//package DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.Scanner;

import static java.lang.String.valueOf;


public class Connect {
    String schema ="";
    Connection conn; // DB connection
    public String getSchema() {
        return schema;
    }
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
        String host="";
        String port="";
//        String schema="";
        String user="";
        String password="";
        try {
            Scanner sc = new Scanner(new File("conf.csv"));
            sc.useDelimiter(",");   //sets the delimiter pattern
            //read info from conf file
            // creating the connection
            host = sc.next().toString();
            port = sc.next().toString();
            this.schema = sc.next().toString();
            user = sc.next().toString();
            password = sc.next().toString();
            sc.close();
        } catch (Exception e) {
            return false;
        }


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

    //returns the value of rs from the query result
    private String getRsVal(String header, ResultSet rs, int index) throws SQLException {
        String val = "";
        switch (header) {

            case "vacc_correct":
            case "Country":
            case "country":
            case "countryName":
                val = rs.getString(header);
                break;
            case "Last_updated":
                val =rs.getDate(header).toString();
                break;
            case "rank":
            case "countryRank":
            case "times_visited":

            case "population":
            case "sum(accumVacc)":
            case "sum(accumCases)":
            case "curMonth":
            case "sum(w.population)":
            case "curYear":
            case "country_id":
            case "totalCases":
            case "gdp_usd_per_cap":
            case "id1":

                val = valueOf(rs.getInt(header));
                break;
            case "max(w.vaccinePerPopulation)":
            case "sum(NewVacPerPop)/40":
            case "sum(NewCassesPerPop)/20":
            case "vaccinePerPopulation":
                val = valueOf(rs.getDouble(header));
                break;
            case "max(accumVacc)":
            case "Total_vaccines":
            case "Total_Deaths":
            case "Population":
                val = valueOf((rs.getLong(header)));
        }
        return val;
    }

    public ObservableList<ModelTable> callSQL(String query, List<String> headers) {

        //HashMap<String,List<String>> resMap = new HashMap<>();
        //the observable list will hold headers and rows
        ObservableList<ModelTable> obList = FXCollections.observableArrayList();
        //execute the query
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            List<String> line; //keeps each line to create a ModelTable
            //open up the query line by line
            while (rs.next()) {
                line = new ArrayList<>();
                for (int i = 0; i < headers.size(); i++) {
                    //add each line to lines array
                    line.add(getRsVal(headers.get(i), rs, i));

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

    //update query returns if update succeded or not
    public boolean queryUpdate(String query) {
        try (Statement stmt = conn.createStatement();) {
            stmt.executeUpdate(query);
            return true;

        } catch (SQLException e) {
            return false;
        }
    }
}











