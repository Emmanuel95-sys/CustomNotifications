package com.novucentral.customnotifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Random;

public class FirstAttemptActivity extends AppCompatActivity {

    //to create notificacins we need
    private Notification notification;
    private NotificationManager notificationManager;
    private int notification_id;
    //create a custom layout and combine it with the notificacion
    private RemoteViews remoteViews;
    private Context context; //we can use getApplication context

    private String CHANNEL_NAME = "High priority channel";
    private String CHANNEL_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_attempt);
        context = this;
        CHANNEL_ID = getPackageName() + CHANNEL_NAME;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification);

        //CHANGE IMAGE INT HE IMAGE VIEW
        remoteViews.setImageViewResource(R.id.notif_icon, R.mipmap.ic_launcher);
        remoteViews.setTextViewText(R.id.notif_title, "Emma's custom text");
        remoteViews.setProgressBar(R.id.progressBar, 100,50,true);

        //we need to create the broadcast receiver in order to listen to the button click

        findViewById(R.id.bt_show_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstAttemptActivity.this, "button clicked", Toast.LENGTH_SHORT).show();
                //provide unique id of the notifiaction so that it closes when the user responds to it.
                notification_id = (int) System.currentTimeMillis();
                //Here we are not on the activity context anymore, broadcast receiver
                //class which runs in background when the buttojn is clicked we use the broacast
                //receiver class to capture the users action.
                //UNIQUE filter for our broadcast receiver so taht it knows for which actions to listen for
                //broadcast receiver will only listen to the intents with this custom action buttonClickedIntent
                Intent buttonIntent = new Intent("buttonClickedIntent");
                buttonIntent.putExtra("id", notification_id);
                //in order to broadcas this intent we need to use the Pending intetn class
                //PendingIntent.FLAG_UPDATE_CURRENT
                PendingIntent p_button_intent = PendingIntent.getBroadcast(context , 0,
                        buttonIntent, 0);
                //bind this pending intent with the actual button click
                remoteViews.setOnClickPendingIntent(R.id.bt_show_notification, p_button_intent);
                //create a new notificaiton and bd the remote views
                Intent notificationIntent = new Intent(context, FirstAttemptActivity.class);
                //if the user click the notification anywhere else than the button this activity will
                //be launched
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        //notification will dismiss itself when touched
                        .setAutoCancel(true)
                        //for 64 height notification
                        //for grater set customBigcontetnviw method
                        .setCustomBigContentView(remoteViews)
                        .setContentIntent(pendingIntent)
                        .build();
                NotificationManagerCompat.from(context).notify(new Random().nextInt(), notification);
            }
        });

    }
}