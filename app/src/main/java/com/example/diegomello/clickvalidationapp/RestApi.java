package com.example.diegomello.clickvalidationapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by DiegoMello on 5/31/2017.
 */

public interface RestApi {
    @GET("patients/")
    Call<ArrayList<Patient>> getPatientsFromApi();

    @GET("caretakers/")
    Call<ArrayList<Caretaker>> getCareTakersFromApi();

    @POST("calls/")
    @FormUrlEncoded
    Call<Patient> postCreateCall(@Field("calltype") Integer calltype,
                                 @Field("callstatus") Integer callstatus,
                                 @Field("patientid") String patientid);

    @POST("solvecall/")
    @FormUrlEncoded
    Call<Calling> putSolveCall(@Field("calltype") Integer calltype,
                                 @Field("callstatus") Integer callstatus,
                                 @Field("callid") String callId);
}
