package com.adgvit.hackgrid.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.fragments.tracks;
import com.adgvit.hackgrid.model.problemStatementModel;
import com.adgvit.hackgrid.model.trackDomain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class trackDomainAdapter extends RecyclerView.Adapter<trackDomainAdapter.MyViewHolder> {
    List<trackDomain> domainList;
    Context context;
    tracks tracks;
    public trackDomainAdapter(List<trackDomain> domainList, Context context,tracks track1) {
        this.domainList = domainList;
        this.context = context;
        this.tracks = track1;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView domainName;
        TextView noOfTracks;
        LinearLayout domainLinear;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            domainName = itemView.findViewById(R.id.domainName);
            noOfTracks = itemView.findViewById(R.id.domainIntro);
            domainLinear = itemView.findViewById(R.id.domainLinear);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.domainrecycleritem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        trackDomain item = domainList.get(position);
        holder.domainName.setText(item.getDomainName());
        holder.noOfTracks.setText(item.getNoOfTracks());
        holder.domainLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, holder.domainName.getText(), Toast.LENGTH_SHORT).show();
                addData(holder.domainName.getText().toString());

            }
        });
    }

    @Override
    public int getItemCount() {
        return domainList.size();
    }
    public void addData(String domain){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myref1 = db.getReference("Tracks").child(domain).child("problemStatements");
        List<problemStatementModel> problemList1;
        problemList1 = new ArrayList<>();

        myref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    //Log.i("ds",ds.getValue().toString());
                    problemStatementModel model = ds.getValue(problemStatementModel.class);
                    problemList1.add(new problemStatementModel(model.getDomainName(),model.getStatementDownload(),model.getStatementInfo(),model.getStatementName()));
                    //Log.i("INFO",problemList1.toString());

                }
                adapter(problemList1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void adapter(List<problemStatementModel> list1){
        //Log.i("List",list1.get(0).getStatementDownload());
        SharedPreferences preferences = context.getSharedPreferences("com.adgvit.hackgrid.track", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list1);
        editor.putString("filterProblem",json);
        editor.apply();
        tracks.adapter2();
    }



}
