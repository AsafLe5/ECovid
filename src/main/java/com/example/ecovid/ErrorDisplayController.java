package com.example.ecovid;

import javafx.scene.control.Label;

public class ErrorDisplayController {
    Label errorDescription;

    public void setErrorDescription(String errorDescription) {
        this.errorDescription.setText(errorDescription);
    }
}
