package com.example.ecovid;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DataDisplayController {

    @FXML
    Label labelName;
    
    public void displayQuery(String queryName){
        labelName.setText(queryName);

    }

}
