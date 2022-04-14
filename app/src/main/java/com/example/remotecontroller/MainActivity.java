package com.example.remotecontroller;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private final ArrayList<String> codes = new ArrayList<>();
    private final ArrayList<int[]> btns = new ArrayList<>();
    private final ArrayList<String> buttonSigns = new ArrayList<>();
    public Button b1;
    public Button b2;
    public Button b3;
    public Button b4;
    public Button b5;
    public Button b6;
    public Button b7;
    public Button b8;
    public Button b9;
    public Button b10;
    public Button b11;
    public Button b12;
    public Button b13;
    public Button b14;
    public Button b15;
    public Button b16;
    public TextView tv;


    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 66){
                        Intent intent_remoteController = result.getData();

                        String fileName = intent_remoteController.getStringExtra("filename");

                        codes.clear();
                        buttonSigns.clear();

                        InputStream is = null;
                        try {
                            is = new FileInputStream(fileName);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Scanner sc = new Scanner(is, "UTF-8");
                        String[] sp = sc.nextLine().split(":");

                        tv.setText(sp[0]);
                        for (int i = 0; i < 16; i++) {
                            sp = sc.nextLine().split(":");
                            codes.add(sp[1]);
                            buttonSigns.add(sp[0]);
                        }
                        sc.close();

                        btns.clear();
                        String[] invertTmp;
                        for (int i = 0; i < 16; i++) {
                            invertTmp = codes.get(i).split(", ");

                            int[] btn = new int[invertTmp.length];
                            for (int j = 0; j < invertTmp.length; j++) {
                                System.out.println(btn[j]);
                                btn[j] = Integer.parseInt(invertTmp[j]);
                            }
                            btns.add(btn);
                        }
                        setButtonClickable();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView_deviceName);
        tv.setText("Válasszon ki egy távirányítót!");
        setButtonNotClickable();
    }

    public void recycleViewOpener(View view) {
        Intent intent_remoteController = new Intent(this, RecycleActivity.class);
        activityLauncher.launch(intent_remoteController);
    }

    public void buttonHandler(View view) {
        ConsumerIrManager cIR = (ConsumerIrManager)getSystemService(CONSUMER_IR_SERVICE);
        int[] pattern;
        int frac = 38028;

        try {
            if(view.getId() == R.id.oneBtn){
                pattern = btns.get(0);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.twoBtn){
                pattern = btns.get(1);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.threeBtn){
                pattern = btns.get(2);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.fourBtn){
                pattern = btns.get(3);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.fiveBtn){
                pattern = btns.get(4);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.sixBtn){
                pattern = btns.get(5);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.sevenBtn){
                pattern = btns.get(6);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.eightBtn){
                pattern = btns.get(7);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.nineBtn){
                pattern = btns.get(8);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.tenBtn){
                pattern = btns.get(9);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.elevenBtn){
                pattern = btns.get(10);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.twelveBtn){
                pattern = btns.get(11);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.thirteenBtn){
                pattern = btns.get(12);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.fourteenBtn){
                pattern = btns.get(13);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.fifteenBtn){
                pattern = btns.get(14);
                cIR.transmit(frac, pattern);
            }
            else if(view.getId() == R.id.sixteenBtn){
                pattern = btns.get(15);
                cIR.transmit(frac, pattern);
            }
        }
        catch (Exception e){
            Log.e("error", "Data cannot be transmitted!");
        }
    }

    public void setButtonNotClickable(){
        b1 = findViewById(R.id.oneBtn);
        b1.setClickable(false);
        b2 = findViewById(R.id.twoBtn);
        b2.setClickable(false);
        b3 = findViewById(R.id.threeBtn);
        b3.setClickable(false);
        b4 = findViewById(R.id.fourBtn);
        b4.setClickable(false);
        b5 = findViewById(R.id.fiveBtn);
        b5.setClickable(false);
        b6 = findViewById(R.id.sixBtn);
        b6.setClickable(false);
        b7 = findViewById(R.id.sevenBtn);
        b7.setClickable(false);
        b8 = findViewById(R.id.eightBtn);
        b8.setClickable(false);
        b9 = findViewById(R.id.nineBtn);
        b9.setClickable(false);
        b10 = findViewById(R.id.tenBtn);
        b10.setClickable(false);
        b11 = findViewById(R.id.elevenBtn);
        b11.setClickable(false);
        b12 = findViewById(R.id.twelveBtn);
        b12.setClickable(false);
        b13 = findViewById(R.id.thirteenBtn);
        b13.setClickable(false);
        b14 = findViewById(R.id.fourteenBtn);
        b14.setClickable(false);
        b15 = findViewById(R.id.fifteenBtn);
        b15.setClickable(false);
        b16 = findViewById(R.id.sixteenBtn);
        b16.setClickable(false);
    }

    public void setButtonClickable(){
        b1.setClickable(true);
        b1.setText(buttonSigns.get(0));
        b2.setClickable(true);
        b2.setText(buttonSigns.get(1));
        b3.setClickable(true);
        b3.setText(buttonSigns.get(2));
        b4.setClickable(true);
        b4.setText(buttonSigns.get(3));
        b5.setClickable(true);
        b5.setText(buttonSigns.get(4));
        b6.setClickable(true);
        b6.setText(buttonSigns.get(5));
        b7.setClickable(true);
        b7.setText(buttonSigns.get(6));
        b8.setClickable(true);
        b8.setText(buttonSigns.get(7));
        b9.setClickable(true);
        b9.setText(buttonSigns.get(8));
        b10.setClickable(true);
        b10.setText(buttonSigns.get(9));
        b11.setClickable(true);
        b11.setText(buttonSigns.get(10));
        b12.setClickable(true);
        b12.setText(buttonSigns.get(11));
        b13.setClickable(true);
        b13.setText(buttonSigns.get(12));
        b14.setClickable(true);
        b14.setText(buttonSigns.get(13));
        b15.setClickable(true);
        b15.setText(buttonSigns.get(14));
        b16.setClickable(true);
        b16.setText(buttonSigns.get(15));
    }
}