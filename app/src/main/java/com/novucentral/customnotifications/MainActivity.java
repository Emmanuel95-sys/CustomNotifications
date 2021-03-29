package com.novucentral.customnotifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationHelper = new NotificationHelper(this);
    }

    public void sendNotification(View view) {
        //for API 26 Oreo and higer we need to specify a chanel
        notificationHelper.sendHighPriorityNotification("Emma's Title","Emma's awesome notification body",
                MainActivity.class);
    }

    public void sendCustomNotification(View view) {
        //for API 26 Oreo and higer we need to specify a chanel
//        notificationHelper.sendHighPriorityNotification("Emma's Title","Emma's awesome notification body",
//                MainActivity.class);
        notificationHelper.sendCustomNotification("title", MainActivity.class);
    }

}