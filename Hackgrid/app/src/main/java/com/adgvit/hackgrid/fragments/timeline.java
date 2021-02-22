package com.adgvit.hackgrid.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.adapter.timeLineAdapter;
import com.adgvit.hackgrid.model.timeLineModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class timeline extends Fragment {

    View view;
    List<timeLineModel> day1List,day2List;
    RecyclerView day1RecyclerView,day2RecyclerView;
    DatabaseReference myref1,myref2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_timeline, container, false);
        day1List = new ArrayList<>();
        day1RecyclerView = view.findViewById(R.id.day1RecyclerView);
        day2List = new ArrayList<>();
        day2RecyclerView = view.findViewById(R.id.day2RecyclerView);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref1 = db.getReference("Timeline").child("day1");
        myref2=db.getReference("Timeline").child("day2");
        addData1();
        adapter1();
        addData2();
        adapter2();
        return view;
    }
    public void addData2(){
        day1List.clear();
        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                day2List.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    timeLineModel model = ds.getValue(timeLineModel.class);
                    day2List.add(model);
                }
                adapter2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void adapter2(){
        timeLineAdapter adapter = new timeLineAdapter(day2List,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        day2RecyclerView.setLayoutManager(manager);
        day2RecyclerView.setAdapter(adapter);
    }
    public void addData1(){
        day1List.clear();
        myref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                day1List.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    timeLineModel model = ds.getValue(timeLineModel.class);
                    day1List.add(model);
                }
                adapter1();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void adapter1(){
        timeLineAdapter adapter = new timeLineAdapter(day1List,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        day1RecyclerView.setLayoutManager(manager);
        day1RecyclerView.setAdapter(adapter);
    }
}