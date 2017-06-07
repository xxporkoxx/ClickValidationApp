package com.example.diegomello.clickvalidationapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
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

    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        mContext = (ConfigActivity)this;

        mListView = (ListView) findViewById(R.id.activity_config_listView);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_config_progressBar);
        mProgressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

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
        mProgressBar.setVisibility(View.VISIBLE);
        mRestApiAdapter.getPatientsRestApi(new Callback<ArrayList<Patient>>() {
            @Override
            public void onResponse(Call<ArrayList<Patient>> call, Response<ArrayList<Patient>> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    final ArrayList<Patient> patients = response.body();

                    mRestApiAdapter.getCaretakersRestApi(new Callback<ArrayList<Caretaker>>() {
                        @Override
                        public void onResponse(Call<ArrayList<Caretaker>> call, Response<ArrayList<Caretaker>> response) {
                                if (response.isSuccessful()) {
                                    ArrayList<Caretaker> caretakers = response.body();

                                    Log.d("RESPONSE","REsposta");
                                    mProgressBar.setVisibility(View.INVISIBLE);

                                    mListView.setAdapter(new PatientAdapter(mContext,patients,caretakers));
                                }
                            }

                        @Override
                        public void onFailure(Call<ArrayList<Caretaker>> call, Throwable t) {

                        }
                    });

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
