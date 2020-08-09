package com.example.pracnotificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements ButtonNavegador.OnMyBroadcastListener, ButtonClose.OnMyBroadcastListener {

    private Button button_notify;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;
    protected static final String BUTTON_NAVEGADOR = "Navegador";
    protected static final String BUTTON_CERRAR = "Cerrar";
    private static final int REQUEST_BUTTON = 200;
    private ButtonNavegador buttonNavegador;
    private ButtonClose buttonClose;
    int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_notify = findViewById(R.id.notify);
        button_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

        buttonNavegador = new ButtonNavegador(this);
        buttonClose = new ButtonClose(this);

        IntentFilter filter = new IntentFilter(BUTTON_NAVEGADOR);
        registerReceiver(buttonNavegador, filter);

        IntentFilter filter2 = new IntentFilter(BUTTON_CERRAR);
        registerReceiver(buttonClose, filter2);

        createNotificationChannel();

    }

    private void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        Intent botonNavegador = new Intent(BUTTON_NAVEGADOR);
        Intent botonCerrar = new Intent(BUTTON_CERRAR);
        PendingIntent acciones1PendingIntent = PendingIntent.getBroadcast(this, REQUEST_BUTTON, botonNavegador, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent acciones2PendingIntent = PendingIntent.getBroadcast(this, REQUEST_BUTTON, botonCerrar, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android)
                .setContentTitle("Primer mensaje")
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tono_mensaje))
                .setContentText("Este es el contenido del primer mensaje")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_android, "Abrir Navegador", acciones1PendingIntent)
                .addAction(R.drawable.ic_android, "Cerrar", acciones2PendingIntent);
        return notifyBuilder;
    }

    private void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tono_mensaje), Notification.AUDIO_ATTRIBUTES_DEFAULT);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Practica notificaiones");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public void onButtonNavegador() {
        String url = "https://www.google.com.pe/";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onButtonClose() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyManager.cancelAll();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (buttonNavegador != null) unregisterReceiver(buttonNavegador);
        else if (buttonClose != null) unregisterReceiver(buttonClose);
    }
}
