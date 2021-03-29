package com.novucentral.customnotifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ButtonListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //make the notification disappear
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(intent.getExtras().getInt("id"));
        Toast.makeText(context, "Button Clicked!", Toast.LENGTH_SHORT).show();
    }

}
