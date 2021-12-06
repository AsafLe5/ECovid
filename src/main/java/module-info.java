module com.example.ecovid {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ecovid to javafx.fxml;
    exports com.example.ecovid;
}