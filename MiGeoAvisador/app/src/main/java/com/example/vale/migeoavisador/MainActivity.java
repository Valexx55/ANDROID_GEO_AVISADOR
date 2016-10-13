package com.example.vale.migeoavisador;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public static final String ID_MATCH_ACTION_RECIVER = "AvisadorPersonal";

    private Localizacion localizacion;

    private TextView editText_longitud;
    private TextView editText_latitud;
    private TextView textview_conta;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(getClass().getCanonicalName(), "ACTIVIDAD CREATE");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_latitud = (TextView) findViewById(R.id.coordenadas_latitud);
        editText_longitud = (TextView) findViewById(R.id.coordenadas_longuitud);
        textview_conta = (TextView) findViewById(R.id.nacts);

        IntentFilter filter = new IntentFilter(ID_MATCH_ACTION_RECIVER);
        ReciboAvisoLoc reciboAvisoLoc = new ReciboAvisoLoc(this, filter);

        Intent intent = new Intent(ID_MATCH_ACTION_RECIVER);
        intent.putExtra("DESTINO" , "Zona de Tigres");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 15, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        this.localizacion = new Localizacion(locationManager);

        Location location = this.localizacion.obtenerLocalizacion();

        dibujaPosicion(location);

        ListenerPosicion listenerPosicion = new ListenerPosicion(this);



        try
        {
            locationManager.addProximityAlert(location.getLatitude(), location.getLongitude(), 30, -1, pendingIntent);//-1 sin expiración 100 m del radio
            Log.d(getClass().getCanonicalName(), "ALERTA REGISTRADA");
            Log.d(getClass().getCanonicalName(), "latitud y longitud " + location.getLatitude() + " " + location.getLongitude());

           /* locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 120000, -1, listenerPosicion);
            Log.d(getClass().getCanonicalName(), "LISTENER REGISTRADO");*/


        }catch (SecurityException se)
        {
            Log.e(getClass().getCanonicalName(), "NO se puedo registrar la Alerta de proximidad", se);
        }

    }



    public void dibujarAlerta (String mensaje)
    {
        TextView textView = (TextView) findViewById(R.id.mensajismo);
        textView.setText(mensaje);

        Location location = this.localizacion.obtenerLocalizacion();
        String logstr = "NUEVA LOC = "+location.getLatitude() + "\n" + location.getLongitude() + "\n" + location.getAltitude();

        TextView textView1 = (TextView) findViewById(R.id.log);
        String nuevo_msj = textView1.getText() + "\n" + logstr;

        textView1.setText(nuevo_msj);

    }

    public void dibujaPosicion (Location location)
    {
        editText_latitud.setText(location.getLatitude()+"");
        editText_longitud.setText(location.getLongitude()+"");
    }

    public void actuaizaContador ()
    {
        Log.d(getClass().getCanonicalName(), "Actualizando contador por posición cambiada");

        String contador = (String)textview_conta.getText();

        int nuevo_conta = Integer.valueOf(contador)+1;

        textview_conta.setText(nuevo_conta+"");
    }


    public void actualizaBoton (View boton_actuliza)
    {

        Location location = this.localizacion.obtenerLocalizacion();
        dibujaPosicion(location);
        Log.d(getClass().getCanonicalName(), "Posicion obtenida = " + location.getLatitude() + " " + location.getLongitude());

    }

    public void cambiarColorFondo(String mensaje)
    {
        View root = editText_longitud.getRootView();

        boolean bsaliendo = mensaje.contains("SALIENDO");

        if (bsaliendo)
            root.setBackgroundColor(Color.BLUE);
        else root.setBackgroundColor(Color.RED);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(getClass().getCanonicalName(), "ACTIVIDAD START");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(getClass().getCanonicalName(), "ACTIVIDAD RESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(getClass().getCanonicalName(), "ACTIVIDAD PAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(getClass().getCanonicalName(), "ACTIVIDAD STOP");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(getClass().getCanonicalName(), "ACTIVIDAD REESART");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getCanonicalName(), "ACTIVIDAD DESTRUIDA");
    }
}
