package com.novucentral.customnotifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ButtonListener extends BroadcastReceiver {

    private static final String TAG = "ButtonListener";

    @Override
    public void onReceive(Context context, Intent intent) {
        //make the notification disappear
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(intent.getExtras().getInt("id"));
        Log.d(TAG, "ButtonListener BroadcastReceiver onReceive called");
        Toast.makeText(context, "Button Clicked!", Toast.LENGTH_SHORT).show();
    }

}
