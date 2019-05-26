package ru.ralnik.wing.sqlitedb;

import ru.ralnik.wing.myLibrary.NavigationButton.DemonsrationButton;

public class CreateSQLQuery {
    private String query;

    public CreateSQLQuery(String query) {
        this.query = query;
    }

    public void whereIN(String colName, DemonsrationButton... button){
        String result = "";
        for(int i=0;i<button.length;i++){
            if(button[i].getStatus() == false){
                continue;
            }
            if(result.length() == 0){
                result = button[i].getTag().toString();
            }else{
                result = result + "," + button[i].getTag().toString();
            }
        }
        if(result.length() > 0) {
            query = query + " and " + colName + " in (" + result + ")";
        }
    }

    public void whereOR(String colName, DemonsrationButton... button){
        String result = "";
        for(int i=0;i<button.length;i++){
            if(button[i].getStatus() == false){
                continue;
            }
            if(result.length() == 0){
                result = colName + "=" + button[i].getTag().toString();
            }else{
                result = result + " or " + colName + "=" + button[i].getTag().toString();
            }
        }

        if(result.length() > 0) {
            query = query + " and (" + result + ")";
        }
    }

    public void whereAND(String colName, DemonsrationButton... button){
        String result = "";
        for(int i=0;i<button.length;i++){
            if(button[i].getStatus() == false){
                continue;
            }
            if(result.length() == 0){
                result = colName + "=" + button[i].getTag().toString();
            }else{
                result = result + " and " + colName + "=" + button[i].getTag().toString();
            }
        }

        if(result.length() > 0) {
            query = query + " and (" + result + ")";
        }
    }
    
    public void whereRange(String colName, String minValue, String maxValue){
        query = query + " and ( " + colName + " >= " + minValue + " and " + colName +" <= " + maxValue + ") ";
    }


    @Override
    public String toString() {
        return query;
    }
}
