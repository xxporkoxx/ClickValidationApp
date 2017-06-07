package com.example.diegomello.clickvalidationapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DiegoMello on 5/30/2017.
 */

public class PatientAdapter extends ArrayAdapter <Patient> {
    private Context mContext;
    private List<Patient> patients;
    private LayoutInflater mInflater;
    private ViewHolder mViewHolder;


    public PatientAdapter(Context context,List<Patient> objects) {
        super(context, 0, objects);
        this.mContext = context;
        patients = objects;
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
            mViewHolder.patientInfoTextView = (TextView) convertView.findViewById(R.id.list_item_info_textView);
            mViewHolder.patientSlectionButton = (Button) convertView.findViewById(R.id.list_item_call_selection_button);
            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder)convertView.getTag();
        }

        Patient p = patients.get(position);

        mViewHolder.patientNameTextView.setText(p.getName());
        mViewHolder.patientGenderTextView.setText((Character.toString(p.getGender())).toUpperCase());

        mViewHolder.patientInfoTextView.setText("Idade: "+p.getAge()+" / Cuidador: "+ (p.isCaretakersArrayEmpty() ? p.getCaretakers().get(0) : "Nenhum"));

        return convertView;
    }

    private static class ViewHolder{
        public LinearLayout patientLinearLayout;
        public TextView patientGenderTextView;
        public TextView patientNameTextView;
        public TextView patientInfoTextView;
        public Button patientSlectionButton;
    }
}
