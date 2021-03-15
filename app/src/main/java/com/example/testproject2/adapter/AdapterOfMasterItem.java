package com.example.testproject2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.testproject2.R;
import com.example.testproject2.models.MasterItem;


import java.util.List;

public class AdapterOfMasterItem extends BaseAdapter {
    Context context;
    List <MasterItem> masterItemList;

    public AdapterOfMasterItem(Context context, List<MasterItem> masterItemList) {
        this.context = context;
        this.masterItemList = masterItemList;
    }

    @Override
    public int getCount() {
        return masterItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return masterItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        convertView=layoutInflater.inflate(R.layout.master_card,parent,false);
        MasterItemHolder masterItemHolder=new MasterItemHolder();
        masterItemHolder.itemNoPartId=convertView.findViewById(R.id.masterCard_itemNoPartNo);
        masterItemHolder.color=convertView.findViewById(R.id.masterCard_color);
        masterItemHolder.size=convertView.findViewById(R.id.masterCard_size);
        masterItemHolder.cat=convertView.findViewById(R.id.masterCard_cat);
        masterItemHolder.closingQty=convertView.findViewById(R.id.masterCard_closingQty);
        masterItemHolder.mrp=convertView.findViewById(R.id.masterCard_mrp);
        masterItemHolder.tax=convertView.findViewById(R.id.masterCard_tax);
        masterItemHolder.itemNoPartId.setText(masterItemList.get(position).getItemname()+" "+masterItemList.get(position).getPartno());
        masterItemHolder.closingQty.setText((masterItemList.get(position).getFsize()));
        masterItemHolder.color.setText(masterItemList.get(position).getColour());
        masterItemHolder.mrp.setText(masterItemList.get(position).getMrp());
        masterItemHolder.cat.setText(masterItemList.get(position).getStockcategoryname());
        masterItemHolder.size.setText(masterItemList.get(position).getFsize());
        masterItemHolder.tax.setText(masterItemList.get(position).getTaxper());
        return convertView;
    }

    public class MasterItemHolder
    {
        MasterItem masterItem;
        TextView itemNoPartId,cat,tax,size,color,closingQty,mrp;

    }


}
