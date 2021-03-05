package com.adgvit.hackgrid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.model.SpeakerModel;

import java.util.List;

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.MyViewHolder> {
    List<SpeakerModel> speakerList;
    Context mContext;

    public SpeakerAdapter(List<SpeakerModel> speakerList, Context mContext) {
        this.speakerList = speakerList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.speakersrecycleritem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SpeakerModel model = speakerList.get(position);
        holder.speakerName.setText(model.getSpeakerName());
        holder.speakerDesignation.setText(model.getSpeakerDesignation());
        holder.speakerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, model.getSpeakerImage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return speakerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView speakerName,speakerDesignation;
        ConstraintLayout speakerImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            speakerName = itemView.findViewById(R.id.speakerName);
            speakerDesignation = itemView.findViewById(R.id.speakerDesig);
            speakerImage = itemView.findViewById(R.id.speakerImage);
        }
    }
}
