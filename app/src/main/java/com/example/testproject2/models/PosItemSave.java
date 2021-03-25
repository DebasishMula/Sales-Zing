package com.example.testproject2.models;

public class PosItemSave {
    String itemid;
    String batchname;
    String qty;
    String mrp;
    String color;
    String uom;
    String amount;
    String gstrate;
    String fsize;




    public PosItemSave(String itemid, String batchname, String qty, String mrp, String color, String uom, String gstrate,String amount,String fsize) {
        this.itemid = itemid;
        this.batchname = batchname;
        this.qty = qty;
        this.mrp = mrp;
        this.color = color;
        this.uom = uom;
        this.gstrate = gstrate;
        this.amount=amount;
        this.fsize=fsize;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getGstrate() {
        return gstrate;
    }

    public void setGstrate(String gstrate) {
        this.gstrate = gstrate;
    }
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFsize() {
        return fsize;
    }

    public void setFsize(String fsize) {
        this.fsize = fsize;
    }

    @Override
    public String toString() {
        return "PosItemSave{" +
                "itemid='" + itemid + '\'' +
                ", batchname='" + batchname + '\'' +
                ", qty='" + qty + '\'' +
                ", mrp='" + mrp + '\'' +
                ", color='" + color + '\'' +
                ", uom='" + uom + '\'' +
                ", amount='" + amount + '\'' +
                ", gstrate='" + gstrate + '\'' +
                ", fsize='" + fsize + '\'' +
                '}';
    }
}
