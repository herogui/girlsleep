package com.girlsleep.giser.girlsleep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by giser on 2015/5/27.
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String action_boot="android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
       if (intent.getAction().equals(action_boot)){
            Intent s=new Intent(context,MyService.class);
            context.startService(s);
        }
    }
}