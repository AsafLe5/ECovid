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
    List<String> headers;
    Map<String, List<String>> tableMap;


    
    public void displayQuery(String queryName /*,Map<Integer, List> resMap*/){

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
