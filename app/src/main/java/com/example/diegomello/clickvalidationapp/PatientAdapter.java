package com.example.diegomello.clickvalidationapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diegomello.clickvalidationapp.utils.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DiegoMello on 5/30/2017.
 */

public class PatientAdapter extends ArrayAdapter <Patient> {
    private Context mContext;
    private List<Patient> patients;
    private LayoutInflater mInflater;
    private ViewHolder mViewHolder;
    private ArrayList<Caretaker> mCaretakers;

    public PatientAdapter(Context context,List<Patient> objects, ArrayList<Caretaker> caretakers) {
        super(context, 0, objects);
        this.mContext = context;
        patients = objects;
        this.mCaretakers = caretakers;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_patient, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.patientLinearLayout = (LinearLayout) convertView.findViewById(R.id.list_item_patient_linearLayout);
            mViewHolder.patientGenderTextView = (TextView) convertView.findViewById(R.id.list_item_patient_gender_textView);
            mViewHolder.patientNameTextView = (TextView) convertView.findViewById(R.id.list_item_patient_name_textView);
            mViewHolder.patientAgeTextView = (TextView) convertView.findViewById(R.id.list_item_age_textView);
            mViewHolder.patientFirstCareTakerTextView = (TextView) convertView.findViewById(R.id.list_item_first_caretaker_textView);
            mViewHolder.patientSlectionButton = (Button) convertView.findViewById(R.id.list_item_call_selection_button);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder)convertView.getTag();
        }

        final Patient p = patients.get(position);

        mViewHolder.patientNameTextView.setText(p.getName());
        mViewHolder.patientGenderTextView.setText((Character.toString(p.getGender())).toUpperCase());

        String firstCareTakerName = "Ninguem";
        if(!p.isCaretakersArrayEmpty()) {
            Caretaker c = findCaretakerFromID(p.getCaretakers().get(0), mCaretakers);
            if (c != null)
                firstCareTakerName = c.getName();
        }
        mViewHolder.patientFirstCareTakerTextView.setText("Cuidador: "+ firstCareTakerName);
        mViewHolder.patientAgeTextView.setText("Idade: "+p.getAge());

        mViewHolder.patientSlectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = mContext.getSharedPreferences(Constants.PATIENT_SHARED_PREF, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(Constants.PATIENT_SHARED_PREF_JSON_OBJECT, new Gson().toJson(p));
                editor.commit();

                Toast.makeText(mContext, p.getName()+" "+mContext.getResources().getString(R.string.select_patient_sucesfull_toast_text),Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    private Caretaker findCaretakerFromID(String id, ArrayList<Caretaker> caretakers){
        for (Caretaker caretaker : caretakers) {
            if (caretaker.get_id().equals(id)) {
                return caretaker;
            }
        }
        return null;
    }

    private static class ViewHolder{
        public LinearLayout patientLinearLayout;
        public TextView patientGenderTextView;
        public TextView patientNameTextView;
        public TextView patientAgeTextView;
        public TextView patientFirstCareTakerTextView;
        public Button patientSlectionButton;
    }
}
