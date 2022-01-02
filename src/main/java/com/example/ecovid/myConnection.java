package com.example.ecovid;

import java.io.FileNotFoundException;

public class myConnection {
    static Connect conn;
    static String schema;
//static function to create singleton connection, if it's already connected, return the connection
    public static Connect getConnObj() {
        if (conn == null) {
            conn = new Connect();
            //if couldn't connect, return null
            if (!conn.openConnection()) {
                conn = null;
                return null;
            }
            schema = conn.getSchema();
        }
        return conn;
    }
    
    public static String getSchema() {
        return schema;
    }
    //close the connection if there is one
    public static void closeConnObj() {
        if (conn != null) {
            conn.closeConnection();
        }
    }


}
