package com.example.diegomello.clickvalidationapp;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DiegoMello on 5/31/2017.
 */

public class RestApiAdapter {
    protected static final String TAG = "RESTAPIADAPTER";
    protected static Retrofit mRestAdapter;
    static final String BASE_URL ="https://clickvalidationbackend.herokuapp.com";
    static final String OPEN_WEATHER_API = "51337ba29f38cb7a5664cda04d84f4cd";

    private static RestApi mRestApi;

    RestApiAdapter(){
        InstanciateRestApiAdapter();
    }

    public static RestApi InstanciateRestApiAdapter() {
        if(mRestApi==null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            // Add logging into retrofit 2.0
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.interceptors().add(logging);

            mRestAdapter = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            mRestApi = mRestAdapter.create(RestApi.class); // create the interface
            Log.d(TAG, "RestApi -- created");
        }

        return mRestApi;

    }

    public void testRestApi(Callback<ArrayList<Patient>> callback){
        //Log.d(TAG, "testWeatherApi: for city:");
        mRestApi.getPatientsFromApi().enqueue(callback);
    }
}