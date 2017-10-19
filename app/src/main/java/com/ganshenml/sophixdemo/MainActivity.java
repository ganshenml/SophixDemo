package com.ganshenml.sophixdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mainLayout;
    private Button turnBtn, popBtn;
    private AdPopupWindow adPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (LinearLayout) findViewById(R.id.activity_main);
        turnBtn = (Button) findViewById(R.id.turnBtn);
        popBtn = (Button) findViewById(R.id.popBtn);

        turnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondAct.class));
            }
        });

        popBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg";
                showPopWindow(url);
            }
        });
    }

    private void showPopWindow(String url) {
        if (adPopupWindow == null) {
            adPopupWindow = new AdPopupWindow(this, url);
        }
        adPopupWindow.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onDestroy() {
        if (adPopupWindow != null) {
            adPopupWindow.toDismiss();
        }
        super.onDestroy();
    }
}
