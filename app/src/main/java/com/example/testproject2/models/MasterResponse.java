package com.example.testproject2.models;

import org.json.JSONArray;

public class MasterResponse {
    JSONArray masterItemList;

    public MasterResponse(JSONArray masterItemList) {
        this.masterItemList = masterItemList;
    }

    public JSONArray getMasterItemList() {
        return masterItemList;
    }

    public void setMasterItemList(JSONArray masterItemList) {
        this.masterItemList = masterItemList;
    }
}
