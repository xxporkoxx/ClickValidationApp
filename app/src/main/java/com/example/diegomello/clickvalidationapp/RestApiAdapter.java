package com.example.diegomello.clickvalidationapp;

import android.util.Log;

import com.example.diegomello.clickvalidationapp.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DiegoMello on 5/31/2017.
 */

public class RestApiAdapter {
    protected static final String TAG = "RESTAPIADAPTER";
    protected static Retrofit mRestAdapter;
    //static final String OPEN_WEATHER_API = "51337ba29f38cb7a5664cda04d84f4cd";

    private static RestApiAdapter mRestApiAdapter = new RestApiAdapter( );

    private static RestApi mRestApi;

    private RestApiAdapter(){
        InstanciateRestApiAdapter();
    }

    public static RestApiAdapter getInstance(){
        return mRestApiAdapter;
    }

    public static RestApi InstanciateRestApiAdapter() {
        if(mRestApi==null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            // Add logging into retrofit 2.0
           /* HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();
*/
            mRestAdapter = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
 //                   .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            mRestApi = mRestAdapter.create(RestApi.class); // create the interface
            Log.d(TAG, "RestApi -- created");
        }

        return mRestApi;

    }

    public void getPatientsRestApi(Callback<ArrayList<Patient>> callback){
        mRestApi.getPatientsFromApi().enqueue(callback);
    }

    public void getCaretakersRestApi(Callback<ArrayList<Caretaker>> callback){
        mRestApi.getCareTakersFromApi().enqueue(callback);
    }

    public void postCreateCallRestApi(Callback<Patient> callback,Integer calltype,
                                      Integer callstatus,String patientid){
        mRestApi.postCreateCall(calltype,callstatus,patientid).enqueue(callback);
    }

    public void putSolveCallRestApi(Callback<Calling> callback, Integer calltype,
                                    Integer callstatus, String callId, String patient_id){
        mRestApi.putSolveCall(calltype,callstatus,callId,patient_id).enqueue(callback);
    }
}
