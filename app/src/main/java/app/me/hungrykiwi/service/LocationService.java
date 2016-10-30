package app.me.hungrykiwi.service;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import app.me.hungrykiwi.pages.restaurant.RestrFragment;

/**
 * location service to get latitude and longitude
 * Created by user on 10/18/2016.
 */
public class LocationService implements GoogleApiClient.ConnectionCallbacks{
    Context mContext;
    GoogleApiClient mClient;
    Fragment mFrag;
    Location mLocation;
    public LocationService(Context mContext, Fragment fragment) {
        this.mContext = mContext;
        this.mFrag = fragment;
        this.startService();
    }

    public boolean inConnected() {
        return this.mClient.isConnected();
    }

    /**
     * start service to fetch location info
     */
    public void startService() {
        if(this.mClient == null) {
            this.mClient = new GoogleApiClient.Builder(this.mContext)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        this.mClient.connect();
    }

    public void stopService() {
        if(this.mClient != null) this.mClient.disconnect();
    }

    /**
     * refetch data
     */
    public void refetch() {
        try {
            double latitude, longitude;
            if (this.mClient == null) {
                this.mClient = new GoogleApiClient.Builder(this.mContext)
                        .addConnectionCallbacks(this)
                        .addApi(LocationServices.API)
                        .build();
            } else {
                this.mLocation = LocationServices.FusedLocationApi.getLastLocation(this.mClient);
                latitude = this.mLocation.getLatitude();
                longitude = this.mLocation.getLongitude();
                if (this.mFrag instanceof RestrFragment) {
                    ((RestrFragment) this.mFrag).search(latitude, longitude);
                }
            }
        } catch(SecurityException ex) {
            Log.d("INFO", "LocationService 123 : "+ex.getMessage());
        } catch(Exception ex) {
            Log.d("INFO", "LocationService 456 : "+ex.getMessage());
        }
    }

    /**
     * when api is connected
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        double latitude;
        double longitude;
        try {
            this.mLocation = LocationServices.FusedLocationApi.getLastLocation(this.mClient);
            latitude = this.mLocation.getLatitude();
            longitude = this.mLocation.getLongitude();
            if (this.mFrag instanceof RestrFragment) {
                ((RestrFragment)this.mFrag).search(latitude, longitude);
            }
        }catch(SecurityException ex) {
            Log.d("INFO", "LocationService 123 : "+ex.getMessage());
        } catch(Exception ex) {
            Log.d("INFO", "LocationService 456 : "+ex.getMessage());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public Location getLocation() {
        try {
            if (this.mClient == null) {
                this.startService();
            } else if (this.mClient != null && this.mClient.isConnected() == true) {
                return LocationServices.FusedLocationApi.getLastLocation(this.mClient);
            }
        }catch(SecurityException ex) {
            Log.d("INFO", "LocationService 12344 : "+ex.getMessage());
        } catch(Exception ex) {
            Log.d("INFO", "LocationService 45655 : "+ex.getMessage());
        }
        return null;
    }
}
