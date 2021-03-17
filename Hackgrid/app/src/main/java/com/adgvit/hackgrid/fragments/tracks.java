package com.adgvit.hackgrid.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.adapter.problemStatementAdapter;
import com.adgvit.hackgrid.adapter.trackDomainAdapter;
import com.adgvit.hackgrid.model.faqModel;
import com.adgvit.hackgrid.model.problemStatementModel;
import com.adgvit.hackgrid.model.trackDomain;
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

public class tracks extends Fragment {

    View view;
    DatabaseReference myref,myref1;
    RecyclerView domainRecyclerView,problemRecyclerView;
    List<trackDomain> domainList;
    List<problemStatementModel> problemList;
    LottieAnimationView lottieAnimationView;
    ConstraintLayout ui1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_tracks, container, false);

        domainRecyclerView = view.findViewById(R.id.domainRecycler);
        lottieAnimationView = view.findViewById(R.id.tracksAnimationView);
        ui1 = view.findViewById(R.id.tracksConstraintLayout);

        ui1.setVisibility(View.INVISIBLE);
        lottieAnimationView.setVisibility(View.VISIBLE);

        domainList=new ArrayList<>();
        SharedPreferences preferences1 = view.getContext().getSharedPreferences("com.adgvit.hackgrid.track", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences1.edit();
        editor.clear();
        editor.apply();
        loadData();
        loadData2();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref1 = database.getReference("Tracks");
        addData();
        adapter();
        return view;
    }
    public void problemStatement(String firstDomain){
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        Log.i("firstDomain",firstDomain);
        myref = database1.getReference("Tracks").child(firstDomain).child("problemStatements");
        problemRecyclerView = view.findViewById(R.id.problemRecycler1);
        problemList = new ArrayList<>();
        addProblemData();
        adapter2();
    }
    public void adapter2(){
        List<problemStatementModel> problemList1;
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.track", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("filterProblem","");
        if(json.isEmpty()){
            Log.i("Empty","isEmpty");
        }
        else {
            Type type = new TypeToken<ArrayList<problemStatementModel>>() {}.getType();
            problemList1 = gson.fromJson(json, type);
            if (problemList1 == null) {
                problemList1 = new ArrayList<>();
            }
            problemList = problemList1;
        }
        problemStatementAdapter adapter = new problemStatementAdapter(problemList,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        //GridLayoutManager manager = new GridLayoutManager(view.getContext(),2);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        problemRecyclerView.setLayoutManager(manager);
        problemRecyclerView.setAdapter(adapter);

        if (domainList.isEmpty() || problemList.isEmpty() ){
            ui1.setVisibility(View.INVISIBLE);
            lottieAnimationView.setVisibility(View.VISIBLE);

        }
        else {
            ui1.setVisibility(View.VISIBLE);
            lottieAnimationView.pauseAnimation();
            lottieAnimationView.setVisibility(View.INVISIBLE);
        }
    }
    public void addProblemData(){

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                problemList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){
                    //Log.i("ds",ds.getValue().toString());
                   problemStatementModel model = ds.getValue(problemStatementModel.class);
                   problemList.add(model);
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
    public void addData(){

        myref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                domainList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    trackDomain domain = ds.getValue(trackDomain.class);
                    domainList.add(new trackDomain(domain.getDomainName(),domain.getNoOfTracks()));
                }
                String firstDomain = domainList.get(0).getDomainName();
                problemStatement(firstDomain);
                saveData();
                adapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadData();
            }
        });
    }
    public void adapter(){
        trackDomainAdapter adapter = new trackDomainAdapter(domainList,view.getContext(),tracks.this);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        domainRecyclerView.setAdapter(adapter);
        domainRecyclerView.setLayoutManager(manager);
    }public void saveData2(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.trackproblem", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problemList);
        editor.putString("faq",json);
        editor.apply();
    }
    public void loadData2(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.trackproblem",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("faq","");
        Type type = new TypeToken<ArrayList<problemStatementModel>>() {}.getType();
        problemList =gson.fromJson(json,type);
        if(problemList==null){
            problemList =new ArrayList<>();
        }
    }
    public void saveData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.tracks.domain", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(domainList);
        editor.putString("domain",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.tracks.domain",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("domain","");
        Type type = new TypeToken<ArrayList<trackDomain>>() {}.getType();
        domainList =gson.fromJson(json,type);
        if(domainList==null){
            domainList =new ArrayList<>();
        }
    }
}