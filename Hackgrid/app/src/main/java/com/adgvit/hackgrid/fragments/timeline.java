package com.adgvit.hackgrid.fragments;

import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.adapter.timeLineAdapter;
import com.adgvit.hackgrid.model.faqModel;
import com.adgvit.hackgrid.model.timeLineModel;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class timeline extends Fragment {

    View view;
    List<timeLineModel> day1List,day2List,day3List;
    RecyclerView day1RecyclerView,day2RecyclerView,day3RecyclerView;
    ScrollView ui2;
    DatabaseReference myref1,myref2,myref3;
    LottieAnimationView lottieAnimationView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_timeline, container, false);

        ui2 = view.findViewById(R.id.scrollView2);
        day1RecyclerView = view.findViewById(R.id.day1RecyclerView);
        day2RecyclerView = view.findViewById(R.id.day2RecyclerView);
        day3RecyclerView = view.findViewById(R.id.day3RecyclerView);
        lottieAnimationView = view.findViewById(R.id.animationView);
        ui2.setVisibility(View.INVISIBLE);
        lottieAnimationView.setVisibility(View.VISIBLE);
        day1List = new ArrayList<>();
        day2List = new ArrayList<>();
        day3List = new ArrayList<>();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        myref1 = db.getReference("Timeline").child("day1");
        myref2=db.getReference("Timeline").child("day2");
        myref3 = db.getReference("Timeline").child("day3");
        recyclerView();


        return view;
    }
    public void recyclerView(){
        loadData1();
        addData1();
        adapter1();

        loadData2();
        addData2();
        adapter2();

        loadData3();
        addData3();
        adapter3();

    }
    public void addData3(){
        myref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                day3List.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    timeLineModel model = ds.getValue(timeLineModel.class);
                    day3List.add(model);
                }
                saveData3();
                adapter3();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadData3();
            }
        });
    }
    public void addData2(){
        myref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                day2List.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    timeLineModel model = ds.getValue(timeLineModel.class);
                    day2List.add(model);
                }
                saveData2();
                adapter2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadData2();
            }
        });
    }

    public void addData1(){
        myref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                day1List.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    timeLineModel model = ds.getValue(timeLineModel.class);
                    day1List.add(model);
                }
                saveData1();
                adapter1();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               loadData1();
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
    public void adapter2(){
        timeLineAdapter adapter = new timeLineAdapter(day2List,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        day2RecyclerView.setLayoutManager(manager);
        day2RecyclerView.setAdapter(adapter);
    }
    public void adapter3(){
        timeLineAdapter adapter = new timeLineAdapter(day3List,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        day3RecyclerView.setLayoutManager(manager);
        day3RecyclerView.setAdapter(adapter);

        if (day1List.isEmpty() || day2List.isEmpty() || day3List.isEmpty()){
            ui2.setVisibility(View.INVISIBLE);
            lottieAnimationView.setVisibility(View.VISIBLE);

        }
        else {
            ui2.setVisibility(View.VISIBLE);
            lottieAnimationView.pauseAnimation();
            lottieAnimationView.setVisibility(View.INVISIBLE);
        }
    }
    public void saveData1(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.timeline", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(day1List);
        editor.putString("timeline1",json);
        editor.apply();
    }
    public void loadData1(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.timeline",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("timeline1","");
        Type type = new TypeToken<ArrayList<timeLineModel>>() {}.getType();
        day1List =gson.fromJson(json,type);
        if(day1List==null){
            day1List =new ArrayList<>();
        }
    }

    public void saveData2(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.timeline", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(day2List);
        editor.putString("timeline2",json);
        editor.apply();
    }
    public void loadData2(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.timeline",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("timeline2","");
        Type type = new TypeToken<ArrayList<timeLineModel>>() {}.getType();
        day2List =gson.fromJson(json,type);
        if(day2List==null){
            day2List =new ArrayList<>();
        }
    }
    public void saveData3(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.timeline", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(day3List);
        editor.putString("timeline3",json);
        editor.apply();
    }
    public void loadData3(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.timeline",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("timeline3","");
        Type type = new TypeToken<ArrayList<timeLineModel>>() {}.getType();
        day3List =gson.fromJson(json,type);
        if(day3List==null){
            day3List =new ArrayList<>();
        }
    }
}