package com.novucentral.customnotifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.List;
import java.util.Random;

//we are going to use
public class NotificationHelper extends ContextWrapper {

    private static final String TAG = "NotificationHelper";
    private String CHANNEL_NAME = "High priority channel";
    private String CHANNEL_ID = "com.novucentral.customnotifications" + CHANNEL_NAME;

    public NotificationHelper(Context base) {
        super(base);
        //you should only create channels when api is 26 and above
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannels(){
        //you can implement the same logic to create more channels
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        //provide description of the chaneel
        notificationChannel.setDescription("This is the description of Emma's custom channel");
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        //Another chanel default and high priority

    }

    public void sendHighPriorityNotification(String title, String body, Class activityName){
        //in order to open an activity using a notification we use a Pending Intent.
        //we also use pendign intents to manage click events in notifiactions

        Intent intent = new Intent(this, activityName);

        //we can keep a track of the request code
        PendingIntent pendingIntent =  PendingIntent.getActivity(this, 123,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                //mandatory
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                //mandatory for notification for API 25 and below
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().setSummaryText("Emma's Summary")
                        .setSummaryText(title).setBigContentTitle("").bigText(body))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat.from(this).notify(new Random().nextInt(), notification);
    }

    public void sendCustomNotification(String title , Class activityName){

        //Trying to handle notification button click
        int notification_id = (int) System.currentTimeMillis();
        Intent buttonIntent = new Intent("buttonClickedIntent");
        buttonIntent.putExtra("id", notification_id);

        //Implicit broadcasts are not allowed anymore in Android Oreo and later.
        // An implicit broadcast is a broadcast that does not target that app specifically.
        //Instead of just using the intent-filter action name for constructing the Intent search
        // the package manager for the component providing the broadcast receiver like this:
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> infos = packageManager.queryBroadcastReceivers(buttonIntent, 0);
        for (ResolveInfo info : infos) {
            ComponentName cn = new ComponentName(info.activityInfo.packageName,
                    info.activityInfo.name);
            buttonIntent.setComponent(cn);
        }

        PendingIntent p_button_intent = PendingIntent.getBroadcast(this , 0, buttonIntent,
               0);

        //in order to open an activity using a notification we use a Pending Intent.
        //we also use pending intents to manage click events in notifiactions

        Intent intent = new Intent(this, activityName);

        //we can keep a track of the request code
        PendingIntent pendingIntent =  PendingIntent.getActivity(this, 123,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews collapseView = new RemoteViews(getPackageName(), R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_expanded);

        //Button click event
        expandedView.setOnClickPendingIntent(R.id.button_notification, p_button_intent);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                //layout for small notification
                .setCustomContentView(collapseView)
                //layout for big notification
                .setCustomBigContentView(expandedView)
                //mandatory
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                //mandatory for notification for API 25 and below
                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setStyle(new NotificationCompat().DecoratedCustomViewStyle())
                .setContentIntent(pendingIntent)
                .build();
        NotificationManagerCompat.from(this).notify(new Random().nextInt(), notification);
    }

}
