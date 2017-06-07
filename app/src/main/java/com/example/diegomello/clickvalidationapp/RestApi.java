package com.example.diegomello.clickvalidationapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DiegoMello on 5/31/2017.
 */

public interface RestApi {
    @GET("/patients")
    Call<ArrayList<Patient>> getPatientsFromApi();
}
