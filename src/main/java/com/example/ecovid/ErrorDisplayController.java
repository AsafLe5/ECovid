package com.example.ecovid;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ErrorDisplayController {
    @FXML
    Label errorLine=new Label();

    public void setErrorDescription(String myErrorDescription) {
        this.errorLine.setText(myErrorDescription);
    }
}

