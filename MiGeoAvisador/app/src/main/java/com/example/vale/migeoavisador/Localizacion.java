package com.example.vale.migeoavisador;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by vale on 27/08/16.
 */
public class Localizacion



{

    private LocationManager locationManager;


    public Localizacion (LocationManager locationManager)
    {
        this.locationManager = locationManager;
    }
    /**
     * PRECONDICIONES: SUPONGO PERMISOS CONCECIDOS Y ACTIVACIÓN GPS (FINA) HABILITADA
     * @return lA LOCALIZACIÓN ACTUAL
     */
    public Location obtenerLocalizacion ()
    {
        Location location = null;


            try
            {
                location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//"gps"

            } catch (SecurityException se)
            {
                Log.e(getClass().getCanonicalName(), "Error obteniendo localización", se);
            }


        return  location;
    }
}


