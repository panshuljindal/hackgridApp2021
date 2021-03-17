package com.adgvit.hackgrid.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.adapter.SpeakerAdapter;
import com.adgvit.hackgrid.adapter.SponsorsAdapter;
import com.adgvit.hackgrid.model.SpeakerModel;
import com.adgvit.hackgrid.model.SponsorsModel;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class partners extends Fragment {
    View view;
    List<SponsorsModel> sponsorsList;
    List<SpeakerModel> speakerList;
    RecyclerView sponsorRecyclerView,speakerRecyclerView;
    Button aboutUs;
    DatabaseReference myref,myref1;
    LottieAnimationView animation;
    ConstraintLayout ui1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_partners, container, false);
        sponsorRecyclerView = view.findViewById(R.id.sponsorRecyclerView);
        speakerRecyclerView = view.findViewById(R.id.speakerRecyclerView);
        aboutUs = view.findViewById(R.id.aboutUsButton);
        ui1 = view.findViewById(R.id.partnersConstraintLayout);
        animation = view.findViewById(R.id.partnersAnimationView);

        animation.setVisibility(View.VISIBLE);
        ui1.setVisibility(View.INVISIBLE);

        sponsorsList =new ArrayList<>();
        speakerList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref = database.getReference("Partners").child("sponsors");
        myref1 = database.getReference("Partners").child("speakers");
        addData();
        adapter1();
        adapter2();
        onclickListeners();
        return view;
    }
    public void onclickListeners(){
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void adapter1(){
        SponsorsAdapter sponsorsAdapter = new SponsorsAdapter(sponsorsList,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        sponsorRecyclerView.setLayoutManager(manager);
        sponsorRecyclerView.setAdapter(sponsorsAdapter);
        if (sponsorsList.isEmpty()){
            ui1.setVisibility(View.INVISIBLE);
            animation.setVisibility(View.VISIBLE);

        }
        else {
            ui1.setVisibility(View.VISIBLE);
            animation.pauseAnimation();
            animation.setVisibility(View.INVISIBLE);
        }
    }
    public void adapter2(){
        SpeakerAdapter adapter = new SpeakerAdapter(speakerList,view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        speakerRecyclerView.setLayoutManager(manager);
        speakerRecyclerView.setAdapter(adapter);
        if (speakerList.isEmpty()){
            ui1.setVisibility(View.INVISIBLE);
            animation.setVisibility(View.VISIBLE);

        }
        else {
            ui1.setVisibility(View.VISIBLE);
            animation.pauseAnimation();
            animation.setVisibility(View.INVISIBLE);
        }
    }
    public void addData(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    SponsorsModel model = ds.getValue(SponsorsModel.class);
                    sponsorsList.add(model);
                }
                adapter1();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    SpeakerModel model = ds.getValue(SpeakerModel.class);
                    speakerList.add(model);
                }
                adapter2();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}