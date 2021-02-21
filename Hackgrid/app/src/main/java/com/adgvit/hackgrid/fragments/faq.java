package com.adgvit.hackgrid.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.adapter.faqAdapter;
import com.adgvit.hackgrid.model.faqModel;
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
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class faq extends Fragment {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;
    View view;
    List<faqModel> list1,searchList;
    EditText searchEditText;
    RecyclerView faq;
    faqAdapter faqAdapter;
    ImageView micButton;
    DatabaseReference myref;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_faq, container, false);
        list1 = new ArrayList<>();
        faq = view.findViewById(R.id.faqRecyclerview);
        searchEditText = view.findViewById(R.id.searchEditText);
        micButton = view.findViewById(R.id.micButton1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myref=database.getReference("FAQ");
        loadData();
        addData();
        adapter();
        onclicklisteners();

        return view;
    }
    public void speak(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speak something");
        try{
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:
                if (resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchEditText.setText(result.get(0));
                }
        }
    }

    public void onclicklisteners(){

        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchList = new ArrayList<>();
                searchList.clear();
                for(faqModel faq1: list1){
                    if(faq1.getQuestion().toLowerCase().contains(s.toString().toLowerCase())){
                        searchList.add(faq1);

                    }
                }
                faqAdapter.filterList(searchList);
            }
        });
    }
    public void addData(){
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    list1.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        faqModel faq = ds.getValue(faqModel.class);
                        list1.add(new faqModel(faq.getQuestion(), faq.getAnswer()));
                    }
                    saveData();
                    adapter();
                }
                catch (Exception e){
                    Toast.makeText(view.getContext(), "Error Occurred Please Try Again", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void saveData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.faq", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list1);
        editor.putString("faq",json);
        editor.apply();
    }
    public void loadData(){
        SharedPreferences preferences = view.getContext().getSharedPreferences("com.adgvit.hackgrid.faq",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("faq","");
        Type type = new TypeToken<ArrayList<faqModel>>() {}.getType();
        list1 =gson.fromJson(json,type);
        if(list1==null){
            list1 =new ArrayList<>();
        }
    }
    public void adapter(){
        faqAdapter= new faqAdapter(list1,view.getContext());
        LinearLayoutManager manager =new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        faq.setLayoutManager(manager);
        faq.setAdapter(faqAdapter);
    }
}