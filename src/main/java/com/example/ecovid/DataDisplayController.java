package com.example.ecovid;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class DataDisplayController implements Initializable {
    @FXML
    TableView<ModelTable> tableViewResult;
    @FXML
    Label labelName = new Label();
    @FXML
    public TableColumn<ModelTable, String> c1;
    @FXML
    public TableColumn<ModelTable, String> c2;
    @FXML
    public TableColumn<ModelTable, String> c3;
    @FXML
    public TableColumn<ModelTable, String> c4;
    @FXML
    public TableColumn<ModelTable, String> c5;
    @FXML
    public TableColumn<ModelTable, String> c6;
    @FXML
    public TableColumn<ModelTable, String> c7;
    @FXML
    public TableColumn<ModelTable, String> c8;
    @FXML
    public TableColumn<ModelTable, String> c9;
    List<String> headers;
    Map<String, List<String>> tableMap;


    public void displayQuery(String queryName /*,Map<Integer, List> resMap*/) {

        labelName.setText(queryName);


        //TableView<ObservableList<String>> tableView = new TableView<>();
/*        for(Map.Entry<Integer,List> map : resMap.entrySet()){
            map.getValue();
        }*/
/*        for (int i = 0; i < headers.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    headers.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            this.labelName.setText("shmuel");
            //tableViewResult.getColumns().add(column);
            System.out.println("shmuel");

        }*/
    }

    public void setColText(List<String> headers) {
        int size = headers.size();
        switch (size) {
            case 9:
                c9.setText(headers.get(8));

            case 8:
                c8.setText(headers.get(7));

            case 7:
                c7.setText(headers.get(6));

            case 6:
                c6.setText(headers.get(5));

            case 5:
                c5.setText(headers.get(4));
            case 4:
                c4.setText(headers.get(3));
            case 3:
                c3.setText(headers.get(2));

            case 2:
                c2.setText(headers.get(1));
            case 1:
                c1.setText(headers.get(0));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
