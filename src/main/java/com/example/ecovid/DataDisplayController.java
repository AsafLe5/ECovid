package com.example.ecovid;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class DataDisplayController {

    @FXML
    Label labelName;

    public void displayQuery(String queryName){
        labelName.setText(queryName);

        TableView<ObservableList<String>> tableView = new TableView<>();
        List<String> columnNames = dataGenerator.getNext(N_COLS);
        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    columnNames.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            tableView.getColumns().add(column);
        }

    }

}
