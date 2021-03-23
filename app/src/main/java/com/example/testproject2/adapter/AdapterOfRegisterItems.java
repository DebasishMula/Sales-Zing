package com.example.testproject2.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testproject2.R;
import com.example.testproject2.models.RegisterItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterOfRegisterItems extends RecyclerView.Adapter<AdapterOfRegisterItems.ViewHolder> {
    Context context;
    ArrayList<RegisterItem> registerItems=new ArrayList<>();
    ArrayList<Integer> list;

    public AdapterOfRegisterItems(Context context,ArrayList<RegisterItem> registerItems, ArrayList<Integer> list)
    {
        this.context=context;
        this.registerItems=registerItems;
        this.list=list;

    }

    @NonNull
    @Override
    public AdapterOfRegisterItems.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==R.layout.register_card) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.register_card, parent, false));
        }
        else
        {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.register_footer, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOfRegisterItems.ViewHolder holder, int position) {
        // holder.time.setText(registerItems.get(position).getCurrtime());
        //String date=registerItems.get(position).getCreated_on();
        if(registerItems.size()==position)
        {
            if(list.size()==0)
            {
                holder.count.setText("Count: 0");
                holder.tValue.setText("Total Amount: 0");
            }
            else
            {
                holder.count.setText("Count : "+String.valueOf(list.get(1)));
                holder.tValue.setText("Total Amount : "+String.valueOf(list.get(0)));
            }

        }
        else {


            holder.date.setText(registerItems.get(position).getCreated_on());

            holder.mrpValue.setText(registerItems.get(position).getMrpvalue());
            //holder.mrp.setText(registerItems.get(position).getMrp());
            // holder.size.setText("[ "+registerItems.get(position).getFsize()+" ]");
            //holder.stock_cat.setText(registerItems.get(position).getStockCategoryName());
            holder.mobile.setText(registerItems.get(position).getMobileno());
            //holder.article_no.setText(registerItems.get(position).getItemName());

        }



    }

    @Override
    public int getItemCount() {
        return registerItems.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position==registerItems.size())? R.layout.register_footer:R.layout.register_card;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView time,date,article_no,mobile,stock_cat,size,mrp,mrpValue,count,tValue;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //time=itemView.findViewById(R.id.reg_time);
            date=itemView.findViewById(R.id.reg_date);
            // article_no=itemView.findViewById(R.id.reg_article);
            mobile=itemView.findViewById(R.id.reg_mob);
            //stock_cat=itemView.findViewById(R.id.reg_stck);
            //size=itemView.findViewById(R.id.reg_size);
            //mrp=itemView.findViewById(R.id.reg_mrp);
            mrpValue=itemView.findViewById(R.id.reg_mrp_val);
            count=itemView.findViewById(R.id.reg_count);
            tValue=itemView.findViewById(R.id.reg_total_val);


        }
    }
}