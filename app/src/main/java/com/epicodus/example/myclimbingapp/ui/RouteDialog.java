package com.epicodus.example.myclimbingapp.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.epicodus.example.myclimbingapp.R;


public class RouteDialog extends DialogFragment implements View.OnClickListener{

    Button ok;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        setCancelable(false);
        ok = (Button) getView().findViewById(R.id.ok);
        ok.setOnClickListener(this);
        View view = inflater.inflate(R.layout.route_dialog, null);
        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.ok) {
            dismiss();
        } else {
        }
    }
}
