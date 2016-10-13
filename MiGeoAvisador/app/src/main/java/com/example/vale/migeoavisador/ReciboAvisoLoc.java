package com.example.vale.migeoavisador;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ReciboAvisoLoc extends BroadcastReceiver {


    private Context context;

    public ReciboAvisoLoc ()
    {//constructor por defecto, obigatorio, para que el SIStema pueda crear instancias de esta clase cuando lo necesite

    }
    public ReciboAvisoLoc(Context context, IntentFilter intentFilter)
    {
        context.registerReceiver(this, intentFilter);
        this.context = context;
    }

    public static void lanzarNotificiacion (String mensaje, Context context)
    {


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("¡AVISO DE ZONA!")
                        .setContentText(mensaje)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL);

        Intent resultIntent = new Intent(context, Main2Activity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity (context, (int) System.currentTimeMillis(), resultIntent, PendingIntent.FLAG_ONE_SHOT);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(537, mBuilder.build());//537 id al azar
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean entrando = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
        String destino = intent.getStringExtra("DESTINO");

        Log.d(getClass().getCanonicalName(), "RECIBIENDO ALERTA!!!! :)");

        String mensaje = entrando ? "ENTRANDO EN " + destino : "SALIENDO de " + destino;

        if (entrando)
        {

            Log.d(getClass().getCanonicalName(), mensaje);

        }
            else
            {
                Log.d(getClass().getCanonicalName(), mensaje);
            }

        ((MainActivity)this.context).dibujarAlerta(mensaje);
        ((MainActivity)this.context).cambiarColorFondo(mensaje);
        ((MainActivity)this.context).cambiarColorFondo(mensaje);
        lanzarNotificiacion(mensaje, this.context);


    }
}
