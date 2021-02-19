package com.adgvit.hackgrid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.adapter.faqAdapter;
import com.adgvit.hackgrid.model.faqModel;

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
    SpeechRecognizer speechRecognizer;
    Intent speechRecognizerIntent;
    ImageView micButton;
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
        list1.add(new faqModel("Q. Hello","ODs will be provided to the participants for attending the workshops and events."));
        list1.add(new faqModel("Q. Will ODs be provided?","ODs will be provided to the participants for attending the workshops and events."));
        list1.add(new faqModel("Q. Will ODs be provided?","ODs will be provided to the participants for attending the workshops and events."));
        list1.add(new faqModel("Q. Will ODs be provided?","ODs will be provided to the participants for attending the workshops and events."));
        list1.add(new faqModel("Q. Will ODs be provided?","ODs will be provided to the participants for attending the workshops and events."));
        list1.add(new faqModel("Q. Will ODs be provided?","ODs will be provided to the participants for attending the workshops and events."));

    }
    public void adapter(){
        faqAdapter= new faqAdapter(list1,view.getContext());
        LinearLayoutManager manager =new LinearLayoutManager(view.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        faq.setLayoutManager(manager);
        faq.setAdapter(faqAdapter);
    }
}