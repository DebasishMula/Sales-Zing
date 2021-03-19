package com.example.testproject2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject2.R;
import com.example.testproject2.models.HomeList;

import java.util.ArrayList;

public class AdapterOfHomeItems extends RecyclerView.Adapter<AdapterOfHomeItems.ViewHolder>{
    Context context;
    ArrayList<HomeList> homes=new ArrayList<>();

    public AdapterOfHomeItems(Context context, ArrayList<HomeList> home)
    {
        this.context=context;
        this.homes=home;
    }
    @NonNull
    @Override
    public AdapterOfHomeItems.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOfHomeItems.ViewHolder holder, int position) {
        holder.name.setText(homes.get(position).getName());
        holder.qty.setText(homes.get(position).getQty());
        holder.value.setText(homes.get(position).getValue());
        int sl_no=position+1;
        holder.sl_no.setText(""+sl_no);
    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,qty,value,sl_no;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.home_card_name);
            qty=itemView.findViewById(R.id.home_card_qty);
            value=itemView.findViewById(R.id.home_card_value);
            sl_no=itemView.findViewById(R.id.home_card_sl);

        }
    }
}
