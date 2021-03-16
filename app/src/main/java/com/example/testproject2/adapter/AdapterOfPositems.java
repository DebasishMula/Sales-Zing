package com.example.testproject2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject2.R;
import com.example.testproject2.models.PosItem;

import java.util.ArrayList;

public class AdapterOfPositems extends RecyclerView.Adapter<AdapterOfPositems.ViewHolder> {
    Context context;
    ArrayList<PosItem> posItems=new ArrayList<>();

    public AdapterOfPositems(Context context, ArrayList<PosItem> posItems) {
        this.context = context;
        this.posItems = posItems;
    }

    @NonNull
    @Override
    public AdapterOfPositems.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.pos_article_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOfPositems.ViewHolder holder, int position) {
     holder.pos_card_art_batch.setText(posItems.get(position).getItemName()+" / "+posItems.get(position).getBatch_name());
     holder.pos_card_size_color.setText(posItems.get(position).getFSize()+" / "+posItems.get(position).getColor_name());
     holder.pos_card_mrp.setText(posItems.get(position).getMRP());
     holder.pos_card_qty.setText("1");
     holder.pos_card_gross_amount.setText(String.valueOf(Float.valueOf(posItems.get(position).getMRP()) * Float.valueOf(holder.pos_card_qty.getText().toString())));

    }

    @Override
    public int getItemCount() {
        return posItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pos_card_art_batch,pos_card_size_color,pos_card_gross_amount,pos_card_mrp;
        EditText pos_card_qty;
        ImageButton pos_card_del_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pos_card_art_batch=itemView.findViewById(R.id.pos_card_art_batch);
            pos_card_gross_amount=itemView.findViewById(R.id.pos_card_gross_amount);
            pos_card_mrp=itemView.findViewById(R.id.pos_card_mrp);
            pos_card_qty=itemView.findViewById(R.id.pos_card_qty);
            pos_card_size_color=itemView.findViewById(R.id.pos_card_size_color);
            pos_card_del_btn=itemView.findViewById(R.id.pos_card_del_btn);
        }
    }
}
