package com.adgvit.hackgrid.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class problemStatementAdapter extends RecyclerView.Adapter<problemStatementAdapter.MyViewHolder> {

    List<problemStatementModel> problemList,problemList1;
    Context context;
    String fileName;


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
        boolean paperDownloaded = false;
        problemStatementModel item = problemList.get(position);
        holder.domainName.setText(item.getDomainName());
        holder.problemName.setText(item.getStatementName());
        holder.problemInfo.setText(item.getStatementInfo());
        fileName = item.getStatementFileName();
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable(context)) {
                    savePdf(item.getStatementDownload(), item.getStatementFileName());
                }
                else {
                    Toast.makeText(context, "Please connect to internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return problemList.size();
    }
    private void savePdf(String url,String title){
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);
        DownloadManager manager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        request.setMimeType("application/pdf");
        request.allowScanningByMediaScanner();
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        manager.enqueue(request);
        Toast.makeText(context, "Paper saved at Downloads/" + title, Toast.LENGTH_LONG).show();
    }
    boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }



}
