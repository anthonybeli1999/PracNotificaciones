package com.example.pracnotificaciones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ButtonClose extends BroadcastReceiver {

    public interface OnMyBroadcastListener{
        void onButtonClose();
    }

    public ButtonClose(OnMyBroadcastListener onMyBroadcastListener) {
        this.onMyBroadcastListener = onMyBroadcastListener;
    }

    private final OnMyBroadcastListener onMyBroadcastListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().contentEquals(MainActivity.BUTTON_CERRAR)){
            onMyBroadcastListener.onButtonClose();
        }
    }
}
