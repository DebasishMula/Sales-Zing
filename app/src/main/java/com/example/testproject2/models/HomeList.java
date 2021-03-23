package com.example.testproject2.models;

public class HomeList {
    String SalespersonName,qty,amount,Cnt;

    public HomeList(String SalespersonName, String qty, String amount,String Cnt) {
        this.SalespersonName = SalespersonName;
        this.qty = qty;
        this.amount = amount;
        this.Cnt=Cnt;
    }

    public String getName() {
        return SalespersonName;
    }

    public void setName(String name) {
        this.SalespersonName = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getValue() {
        return amount;
    }

    public void setValue(String value) {
        this.amount = amount;
    }

    public String getCnt() {
        return Cnt;
    }

    public void setCnt(String Cnt) {
        this.Cnt = Cnt;
    }

    @Override
    public String toString() {
        return "HomeList{" +
                "name='" + SalespersonName + '\'' +
                ", qty='" + qty + '\'' +
                ", value='" + amount + '\'' +
                '}';
    }
}
