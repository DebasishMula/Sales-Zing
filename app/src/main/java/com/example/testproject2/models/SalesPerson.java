package com.example.testproject2.models;

public class SalesPerson {
    String SalespersonID,salespersonname;

    public SalesPerson(String salespersonID, String salespersonname) {
        SalespersonID = salespersonID;
        this.salespersonname = salespersonname;
    }

    public String getSalespersonID() {

        return SalespersonID;
    }

    public void setSalespersonID(String salespersonID) {
        SalespersonID = salespersonID;
    }

    public String getSalespersonname() {
        return salespersonname;
    }

    public void setSalespersonname(String salespersonname) {
        this.salespersonname = salespersonname;
    }

    @Override
    public String toString() {
        return
                "SalespersonID='" + SalespersonID + '\'' +
                        ", salespersonname='" + salespersonname + '\''
                ;
    }
}