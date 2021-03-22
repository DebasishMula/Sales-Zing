package com.example.testproject2.models;

public class RegisterItem {
    String mobileno,ItemName,StockCategoryName,fsize,mrp,mrpvalue,currtime,created_on;

    public RegisterItem(String mobileno, String itemName, String stockCategoryName, String fsize, String mrp, String mrpvalue, String currtime, String created_on) {
        this.mobileno = mobileno;
        ItemName = itemName;
        this.StockCategoryName = stockCategoryName;
        this.fsize = fsize;
        this.mrp = mrp;
        this.mrpvalue = mrpvalue;
        this.currtime = currtime;
        this.created_on = created_on;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getStockCategoryName() {
        return StockCategoryName;
    }

    public void setStockCategoryName(String stockCategoryName) {
        this.StockCategoryName = stockCategoryName;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getMrpvalue() {
        return mrpvalue;
    }

    public void setMrpvalue(String mrpvalue) {
        this.mrpvalue = mrpvalue;
    }

    public String getCurrtime() {
        return currtime;
    }

    public void setCurrtime(String currtime) {
        this.currtime = currtime;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }
}