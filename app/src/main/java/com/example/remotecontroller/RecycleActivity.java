package com.example.remotecontroller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class RecycleActivity extends AppCompatActivity {

    private ArrayList<Cards> cardList;
    private RecyclerView recyclerView;
    private recyclerAdapter.RecyclerViewListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        recyclerView = findViewById(R.id.recyclerView);
        cardList = new ArrayList<>();

        readFromAndroidFiles();

        setOnCLickListener();
        recyclerAdapter adapter = new recyclerAdapter(cardList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();

                File[] filesSwipe = getFilesDir().listFiles();
                ArrayList<String> lstSwipe = new ArrayList<>();
                for (int i = 0; i < filesSwipe.length; i++) {
                    if (filesSwipe[i].toString().endsWith(".txt"))
                        lstSwipe.add(String.valueOf(filesSwipe[i]));
                }
                File fileDelete = new File(lstSwipe.get(pos));
                fileDelete.delete();
                cardList.remove(pos);
                recyclerView.getAdapter().notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Törölve!", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void setOnCLickListener() {
        listener = new recyclerAdapter.RecyclerViewListener() {
            @Override
            public void onClick(View v, int position) {
                File[] filesClick = getFilesDir().listFiles();

                ArrayList<String> lstClick = new ArrayList<>();
                for (int i = 0; i < filesClick.length; i++) {
                    if (filesClick[i].toString().endsWith(".txt"))
                        lstClick.add(String.valueOf(filesClick[i]));
                }

                Intent intent_remoteController = new Intent();
                intent_remoteController.putExtra("filename", lstClick.get(position));
                setResult(66, intent_remoteController);

                finish();
            }
        };
    }

    public void downloadButtonHandler(View view) {
        EditText url = (EditText) findViewById(R.id.urlText);
        final String urlString = url.getText().toString();

        if(urlString.isEmpty()){
            Toast.makeText(getApplicationContext(), "Írjon be egy URL-t.", Toast.LENGTH_SHORT).show();
        }
        else{

            String path = this.getFilesDir().getPath() + "/data1.txt";;
            boolean isFileIn = false;
            File[] filesDownload = this.getFilesDir().listFiles();
            int fileCounter = filesDownload.length;

            ArrayList<String> lstDownload = new ArrayList<>();
            for (int i = 0; i < fileCounter; i++) {
                if (filesDownload[i].toString().endsWith(".txt"))
                    lstDownload.add(String.valueOf(filesDownload[i]));
            }
            Collections.sort(lstDownload);


            for (int i = 1; i <= fileCounter; i++) {
                path = this.getFilesDir().getPath() + "/data" + i + ".txt";

                if (lstDownload.contains(path)){
                    isFileIn = true;
                }
                else{
                    isFileIn = false;
                    break;
                }
            }
            fileCounter++;
            if (isFileIn){
                path = this.getFilesDir().getPath() + "/data" + fileCounter + ".txt";
            }
            final String finalPath = path;

            new Thread(()->{
                try (BufferedInputStream is = new BufferedInputStream(new URL(urlString).openStream());
                     FileOutputStream os = new FileOutputStream(finalPath)) {
                    byte[] data = new byte[1024];
                    int byteContent;
                    while ((byteContent = is.read(data, 0, 1024)) != -1) {
                        os.write(data, 0, byteContent);
                    }
                    is.close();
                    os.close();

                    runOnUiThread(() -> {
                        cardList.clear();
                        File[] filesThread = this.getFilesDir().listFiles();
                        for (int i = 0; i < filesThread.length; i++) {
                            try {
                                InputStream inputS = new FileInputStream(filesThread[i]);
                                if (filesThread[i].toString().endsWith(".txt")){
                                    Scanner sc = new Scanner(inputS, "UTF-8");
                                    String[] sp = sc.nextLine().split(":");
                                    cardList.add(new Cards(sp[0], sp[1]));
                                    sc.close();
                                    inputS.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        recyclerView.getAdapter().notifyDataSetChanged();

                    });
                } catch (IOException e) {
                    Log.e("error", "Download failed!");
                }
            }
            ).start();
        }
    }

    public void readFromAndroidFiles(){
        File[] filesRead = getFilesDir().listFiles();
        for (int i = 0; i < filesRead.length; i++) {
            try {
                InputStream is = new FileInputStream(filesRead[i]);
                if (filesRead[i].toString().endsWith(".txt")){
                    Scanner sc = new Scanner(is, "UTF-8");
                    String[] sp = sc.nextLine().split(":");
                    cardList.add(new Cards(sp[0], sp[1]));
                    sc.close();
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
