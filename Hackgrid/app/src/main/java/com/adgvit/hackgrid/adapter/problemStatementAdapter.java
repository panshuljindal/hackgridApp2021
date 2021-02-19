package com.adgvit.hackgrid.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.model.faqModel;
import com.adgvit.hackgrid.model.problemStatementModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class problemStatementAdapter extends RecyclerView.Adapter<problemStatementAdapter.MyViewHolder> {

    List<problemStatementModel> problemList,problemList1;
    Context context;

    public problemStatementAdapter(List<problemStatementModel> problemList, Context context) {
        this.problemList = problemList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView domainName,problemName,problemInfo;
        Button download;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            domainName = itemView.findViewById(R.id.problemItemTheme);
            problemName = itemView.findViewById(R.id.problemItemName);
            problemInfo = itemView.findViewById(R.id.problemItemIntro);
            download = itemView.findViewById(R.id.problemButton);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.problemrecycleritem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //getfilterList();
        problemStatementModel item = problemList.get(position);
        holder.domainName.setText(item.getDomainName());
        holder.problemName.setText(item.getStatementName());
        holder.problemInfo.setText(item.getStatementInfo());
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, item.getStatementDownload(), Toast.LENGTH_SHORT).show();
                //Log.i("List2",problemList1.get(0).getStatementDownload());
            }
        });
    }



    @Override
    public int getItemCount() {
        return problemList.size();
    }




}
