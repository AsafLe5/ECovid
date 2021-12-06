package com.example.ecovid;
//package DB;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public Map<Integer, List> callSQL(String query) {

        List<String> country = new ArrayList<>();
        List<Integer> id = new ArrayList<>();
        List<Integer> total = new ArrayList<>();
        List<Integer> gdp = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {


            while (rs.next() == true) {

                /*System.out.print(rs.getInt("country_id"));
                System.out.print("\t");
                System.out.print(rs.getInt("daily_vaccinations"));
                System.out.println();*/
                country.add(rs.getString("country"));
                id.add(rs.getInt("country_id"));
                total.add(rs.getInt("totalCases"));
                gdp.add(rs.getInt("gdp_usd_per_cap"));
                System.out.println();

            }
        } catch (SQLException e) {
            System.out.println("ERROR executeQuery - " + e.getMessage());
        }
        Map<Integer, List> ret = new HashMap<Integer, List>();
        ret.put(0, country);
        ret.put(1, id);
        ret.put(2, total);
        ret.put(3, gdp);
        return ret;

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
