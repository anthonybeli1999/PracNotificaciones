package com.example.pracnotificaciones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ButtonNavegador extends BroadcastReceiver {

    public interface OnMyBroadcastListener{
        void onButtonNavegador();
    }

    public ButtonNavegador(OnMyBroadcastListener onMyBroadcastListener) {
        this.onMyBroadcastListener = onMyBroadcastListener;
    }

    private final OnMyBroadcastListener onMyBroadcastListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().contentEquals(MainActivity.BUTTON_NAVEGADOR)){
            onMyBroadcastListener.onButtonNavegador();
        }
    }
}
