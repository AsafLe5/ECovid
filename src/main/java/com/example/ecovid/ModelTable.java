package com.example.ecovid;

import java.util.ArrayList;
import java.util.List;

public class ModelTable {
    private String att1;
    private String att2;
    private String att3;
    private String att4;
    private String att5;
    private String att6;
    private String att7;
    private String att8;
    private String att9;

    public ModelTable(List<String> line) {
        int size = line.size();
        switch (size){
            case 9:
                att9 = line.get(8);
            case 8:
                att8 = line.get(7);
            case 7:
                att7 = line.get(6);
            case 6:
                att6 = line.get(5);
            case 5:
                att5 = line.get(4);
            case 4:
                att4 = line.get(3);
            case 3:
                att3 = line.get(2);
            case 2:
                att2 = line.get(1);
            case 1:
                att1 = line.get(0);
        }

    }

    public String getAtt1() {
        return att1;
    }

    public void setAtt1(String att1) {
        this.att1 = att1;
    }

    public String getAtt2() {
        return att2;
    }

    public void setAtt2(String att2) {
        this.att2 = att2;
    }

    public String getAtt3() {
        return att3;
    }

    public void setAtt3(String att3) {
        this.att3 = att3;
    }

    public String getAtt4() {
        return att4;
    }

    public void setAtt4(String att4) {
        this.att4 = att4;
    }

    public String getAtt5() {
        return att5;
    }

    public void setAtt5(String att5) {
        this.att5 = att5;
    }

    public String getAtt6() {
        return att6;
    }

    public void setAtt6(String att6) {
        this.att6 = att6;
    }

    public String getAtt7() {
        return att7;
    }

    public void setAtt7(String att7) {
        this.att7 = att7;
    }

    public String getAtt8() {
        return att8;
    }

    public void setAtt8(String att8) {
        this.att8 = att8;
    }

    public String getAtt9() {
        return att9;
    }

    public void setAtt9(String att9) {
        this.att9 = att9;
    }

    public ModelTable(String att1) {
        this.att1 = att1;
    }

    public ModelTable(String att1, String att2) {
        this.att1 = att1;
        this.att2 = att2;
    }

    public ModelTable(String att1, String att2, String att3) {
        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
    }

    public ModelTable(String att1, String att2, String att3, String att4) {
        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
        this.att4 = att4;
    }

    public ModelTable(String att1, String att2, String att3, String att4, String att5) {
        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
        this.att4 = att4;
        this.att5 = att5;
    }

    public ModelTable(String att1, String att2, String att3, String att4, String att5, String att6) {
        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
        this.att4 = att4;
        this.att5 = att5;
        this.att6 = att6;
    }

    public ModelTable(String att1, String att2, String att3, String att4, String att5, String att6, String att7) {
        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
        this.att4 = att4;
        this.att5 = att5;
        this.att6 = att6;
        this.att7 = att7;
    }

    public ModelTable(String att1, String att2, String att3, String att4, String att5, String att6, String att7, String att8) {
        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
        this.att4 = att4;
        this.att5 = att5;
        this.att6 = att6;
        this.att7 = att7;
        this.att8 = att8;
    }

    public ModelTable(String att1, String att2, String att3, String att4, String att5, String att6, String att7, String att8, String att9) {
        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
        this.att4 = att4;
        this.att5 = att5;
        this.att6 = att6;
        this.att7 = att7;
        this.att8 = att8;
        this.att9 = att9;
    }
}
