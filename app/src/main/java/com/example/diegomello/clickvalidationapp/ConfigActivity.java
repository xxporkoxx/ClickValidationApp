package com.example.diegomello.clickvalidationapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DiegoMello on 5/30/2017.
 */

public class ConfigActivity extends AppCompatActivity {

    private Context mContext;

    private ListView mListView;

    private ArrayList<Patient> mPatientArrayList;

    private RestApi mRestApi;
    private RestApiAdapter mRestApiAdapter;
    private Callback<List<Patient>> mPatientList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        mContext = (ConfigActivity)this;

        mListView = (ListView) findViewById(R.id.activity_config_listView);
        mRestApiAdapter =  new RestApiAdapter();

        retriveModelsFromAPI();
/*
        List<String> testecuidador = new ArrayList<String>();
        List<String> testecalls = new ArrayList<String>();

        testecuidador.add("Cuidador 1");
        testecuidador.add("Cuidador 2");


        mPatientArrayList = new ArrayList<>();
        mPatientArrayList.add(new Patient("Paciente 1",50,'M',"Probleminha",2,testecuidador,testecalls));
        mPatientArrayList.add(new Patient("Paciente 2",30,'F',"Artrite",1,testecuidador,testecalls));
        mPatientArrayList.add(new Patient("Paciente 3",40,'M',"Artrose",3,testecuidador,testecalls));
        mPatientArrayList.add(new Patient("Paciente 4",70,'F',"Outra coisa ae",1,testecuidador,testecalls));
*/
    }

    private void retriveModelsFromAPI(){
        mRestApiAdapter.testRestApi(new Callback<ArrayList<Patient>>() {
            @Override
            public void onResponse(Call<ArrayList<Patient>> call, Response<ArrayList<Patient>> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    Log.d("RESPONSE","REsposta");
                    ArrayList<Patient> patients = response.body();

                    mListView.setAdapter(new PatientAdapter(mContext,patients));


                    /*Patient data = response.body();
                    Log.d(TAG, "Async success: Weather: Name:" + data.getName() + ", cod:" + data.getCod()
                            + ",Coord: (" + data.getLat() + "," + data.getLon()
                            + "), Temp:" + data.getTemp()
                            + "\nSunset:" + data.getSunset() + ", " + data.getSunrise()
                            + ", Country:" + data.getCountry());
                    mData = data;
                    if (mActivityRef.get() != null) {
                        mActivityRef.get().updateUXWithWeather(mData);
                        mActivityRef.get().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mActivityRef.get().mProgressBar = (ProgressBar) mActivityRef.get().
                                        findViewById(R.id.progress_bar_id);
                                mActivityRef.get().mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                    mInProgress.set(false);*/
                } else {
                    int statusCode = response.code();

                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    Log.d("ERRORONRESPONSE","Error code:" + statusCode + ", Error:" + errorBody);
                    Toast.makeText(mContext,"Error: "+ errorBody,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Patient>> call, Throwable t) {
                Toast.makeText(mContext,"Error: "+ t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("RESPONSE",t.getMessage());
            }
        });

    }

}
