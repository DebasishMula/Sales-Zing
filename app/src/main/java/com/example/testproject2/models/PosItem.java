package com.example.testproject2.models;

public class PosItem {
    String   item_id,batch_name,FSize,ItemName,UomName,PartNo,MRP,color_name,closingqty,gAmount;


    public String getgAmount() {
        return gAmount;
    }

    public void setgAmount(String gAmount) {
        this.gAmount = gAmount;
    }

    public PosItem(String item_id, String batch_name, String FSize, String itemName, String uomName, String partNo, String MRP, String color_name, String closingqty, String gAmount) {
        this.item_id = item_id;
        this.batch_name = batch_name;
        this.FSize = FSize;
        ItemName = itemName;
        UomName = uomName;
        PartNo = partNo;
        this.MRP = MRP;
        this.color_name = color_name;
        this.closingqty = closingqty;
        this.gAmount=gAmount;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getFSize() {
        return FSize;
    }

    public void setFSize(String FSize) {
        this.FSize = FSize;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getUomName() {
        return UomName;
    }

    public void setUomName(String uomName) {
        UomName = uomName;
    }

    public String getPartNo() {
        return PartNo;
    }

    public void setPartNo(String partNo) {
        PartNo = partNo;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    public String getClosingqty() {
        return closingqty;
    }

    public void setClosingqty(String closingqty) {
        this.closingqty = closingqty;
    }
}
