package com.example.diegomello.clickvalidationapp.utils;

/**
 * Created by DiegoMello on 6/7/2017.
 */

public class Constants {

    public static String PATIENT_SHARED_PREF = "PATIENT_SHARED_PREF";
    public static String PATIENT_SHARED_PREF_JSON_OBJECT = "PATIENT_SHARED_PREF_JSON_OBJECT";

    public static final Integer CALL_STATUS_INITIALIZATION = 0;     //0 : initialization code for remote control
    public static final Integer CALL_STATUS_WATING_TO_SERVE = 1;     //1 : waiting to be served
    public static final Integer CALL_STATUS_ON_THE_WAY = 2;     //2 : Someone is o the way
    public static final Integer CALL_STATUS_SERVED = 3;     //3 : already served

    public static Integer Ok_CALL_NUMBER = 0;
    public static Integer EMERGENCY_CALL_NUMBER = 1;
    public static Integer BATHROOM_CALL_NUMBER = 2;
    public static Integer DISCOMFORT_CALL_NUMBER = 3;
    public static Integer WATER_CALL_NUMBER = 4;


}
