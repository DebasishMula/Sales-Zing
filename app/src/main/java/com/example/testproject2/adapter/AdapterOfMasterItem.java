package com.example.testproject2.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject2.R;
import com.example.testproject2.models.MasterItem;


import java.util.List;

public class AdapterOfMasterItem extends RecyclerView.Adapter<AdapterOfMasterItem.ViewHolder> {
    Context context;
    List <MasterItem> masterItemList;

    public AdapterOfMasterItem(Context context, List<MasterItem> masterItemList) {
        this.context = context;
        this.masterItemList = masterItemList;
    }

    @NonNull
    @Override
    public AdapterOfMasterItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.master_card,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterOfMasterItem.ViewHolder holder, int position) {
        holder.itemNoPartId.setText(masterItemList.get(position).getItemname()+"  ("+masterItemList.get(position).getPartno()+")");
        holder.color.setText(masterItemList.get(position).getColour());
        holder.size.setText(masterItemList.get(position).getFsize());
        holder.cat.setText(masterItemList.get(position).getStockcategoryname());
        holder.mrp.setText(masterItemList.get(position).getMrp());
        holder.tax.setText(masterItemList.get(position).getTaxper());
        holder.closingQty.setText(masterItemList.get(position).getFsize());

    }


    @Override
    public int getItemCount() {
        return masterItemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemNoPartId,cat,tax,size,color,closingQty,mrp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNoPartId=itemView.findViewById(R.id.masterCard_itemNoPartNo);
            color=itemView.findViewById(R.id.masterCard_color);
            size=itemView.findViewById(R.id.masterCard_size);
            cat=itemView.findViewById(R.id.masterCard_cat);
            closingQty=itemView.findViewById(R.id.masterCard_closingQty);
            mrp=itemView.findViewById(R.id.masterCard_mrp);
            tax=itemView.findViewById(R.id.masterCard_tax);

        }
    }
}


