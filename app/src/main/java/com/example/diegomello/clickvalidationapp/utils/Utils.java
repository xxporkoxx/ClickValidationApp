package com.example.diegomello.clickvalidationapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.diegomello.clickvalidationapp.Patient;
import com.google.gson.Gson;

/**
 * Created by DiegoMello on 6/8/2017.
 */

public class Utils {

    public static Context mContext;

    public static void setContext(Context context) {

        mContext = context;
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static Patient checkForDeviceIdentification(){
        Patient patient= new Gson().fromJson(Utils.mContext.getSharedPreferences(Constants.PATIENT_SHARED_PREF, 0)
                .getString(Constants.PATIENT_SHARED_PREF_JSON_OBJECT, null),Patient.class);

        if(patient==null){
            Toast.makeText(Utils.mContext,"Nenhum Paciente foi Selecionado ainda, porfavor configure o dispositivo",Toast.LENGTH_LONG).show();
            return null;
        }
        return patient;
    }
}
