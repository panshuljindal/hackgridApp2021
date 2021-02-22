package com.adgvit.hackgrid.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.hackgrid.R;
import com.adgvit.hackgrid.model.timeLineModel;

import java.util.List;

public class timeLineAdapter extends RecyclerView.Adapter<timeLineAdapter.MyViewHolder> {

    List<timeLineModel> timelineList;
    Context context;

    public timeLineAdapter(List<timeLineModel> timelineList, Context context) {
        this.timelineList = timelineList;
        this.context = context;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventTime,eventName,eventDescription,eventLink;
        ImageView imageTimeline;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTime = itemView.findViewById(R.id.eventTimeID);
            eventName=itemView.findViewById(R.id.eventNameID);
            eventDescription = itemView.findViewById(R.id.eventIntro);
            eventLink = itemView.findViewById(R.id.eventLink);
            imageTimeline = itemView.findViewById(R.id.timelineStatus);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.timelinerecycleritem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        timeLineModel model = timelineList.get(position);
        holder.eventTime.setText(model.getTime());
        holder.eventName.setText(model.getHeading());
        holder.eventDescription.setText(model.getDescription());
       // holder.eventLink.setText(model.getLink());
        String isFirst=model.getIsFirst();
        String isCompleted = model.getIsCompleted();
        Log.i("isFirst",isFirst);
        Log.i("isCompleted",isCompleted);
        if(isCompleted.equals("true") & isFirst.equals("true")){
            Log.i("Type","Top Done");
            holder.eventName.setTextColor(ContextCompat.getColor(context,R.color.timeline_blue));
            holder.eventTime.setTextColor(ContextCompat.getColor(context,R.color.timeline_blue));
            holder.eventLink.setVisibility(View.VISIBLE);
            holder.eventLink.setEnabled(true);
            //holder.imageTimeline.setBackground(ContextCompat.getDrawable(context,R.drawable.topdone));
        }
        else if(isCompleted.equals("false") & isFirst.equals("true")){
            Log.i("Type","Top");
            holder.eventName.setTextColor(ContextCompat.getColor(context,R.color.timeline_black));
            holder.eventTime.setTextColor(ContextCompat.getColor(context,R.color.timeline_black));
            holder.eventLink.setVisibility(View.INVISIBLE);
            holder.eventLink.setEnabled(false);
            //holder.imageTimeline.setBackground(ContextCompat.getDrawable(context,R.drawable.top));
        }
        else if(isCompleted.equals("true") & isFirst.equals("false")){
            Log.i("Type","Middle Done");
            holder.eventName.setTextColor(ContextCompat.getColor(context,R.color.timeline_blue));
            holder.eventTime.setTextColor(ContextCompat.getColor(context,R.color.timeline_blue));
            holder.eventLink.setVisibility(View.VISIBLE);
            holder.eventLink.setEnabled(true);
            // holder.imageTimeline.setBackground(ContextCompat.getDrawable(context,R.drawable.middledone));
        }
        else if(isCompleted.equals("false") & isFirst.equals("false")){
            Log.i("Type","Middle");
            holder.eventName.setTextColor(ContextCompat.getColor(context,R.color.timeline_black));
            holder.eventTime.setTextColor(ContextCompat.getColor(context,R.color.timeline_black));
            holder.eventLink.setVisibility(View.INVISIBLE);
            holder.eventLink.setEnabled(false);
            //holder.imageTimeline.setBackground(ContextCompat.getDrawable(context,R.drawable.middle));
        }
        else {
            Log.i("Type","Else");
        }
    }

    @Override
    public int getItemCount() {
        return timelineList.size();
    }


}
