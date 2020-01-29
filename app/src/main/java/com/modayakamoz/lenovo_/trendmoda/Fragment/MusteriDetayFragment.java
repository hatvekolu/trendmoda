package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Object.AliciObject;
import com.modayakamoz.lenovo_.trendmoda.R;

public class MusteriDetayFragment extends DialogFragment {
    private AliciObject ao;
    public void setAlici(AliciObject ao) {
        this.ao = ao;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_musteri_detay, container, false);
        TextView adSoyad = (TextView) view.findViewById(R.id.textView13);
        TextView ulasan = (TextView) view.findViewById(R.id.textView19);
        TextView donen = (TextView) view.findViewById(R.id.textView20);
        Button tamam = (Button) view.findViewById(R.id.button17);
        adSoyad.setText(ao.getAd() + " " + ao.getSoyad());
        ulasan.setText(ao.getUlasan());
        donen.setText(ao.getDonen());

        tamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return  view;
    }

}
