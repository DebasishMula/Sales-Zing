package com.example.testproject2.models;

public class MasterItem {

    private String itemname,partno,mrp,stockcategoryname,taxper,colour,fsize;


    public MasterItem(String itemname, String partno, String mrp, String stockcategoryname, String taxper, String colour, String fsize) {
        this.itemname = itemname;
        this.partno = partno;
        this.mrp = mrp;
        this.stockcategoryname = stockcategoryname;
        this.taxper = taxper;
        this.colour = colour;
        this.fsize = fsize;
    }
    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getPartno() {
        return partno;
    }

    public void setPartno(String partno) {
        this.partno = partno;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getStockcategoryname() {
        return stockcategoryname;
    }

    public void setStockcategoryname(String stockcategoryname) {
        this.stockcategoryname = stockcategoryname;
    }

    public String getTaxper() {
        return taxper;
    }

    public void setTaxper(String taxper) {
        this.taxper = taxper;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }
}
