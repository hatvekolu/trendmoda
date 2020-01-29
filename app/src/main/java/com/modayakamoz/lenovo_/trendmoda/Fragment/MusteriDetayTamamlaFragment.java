package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Object.AliciObject;
import com.modayakamoz.lenovo_.trendmoda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusteriDetayTamamlaFragment extends DialogFragment {


    private AliciObject ao;
    public void setAlici(AliciObject ao) {
        this.ao = ao;
    }
    private SatisTamamlaFragment tamamlaFragment;

    public void setFragment(SatisTamamlaFragment tamamlaFragment) {
        this.tamamlaFragment = tamamlaFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_musteri_detay_tamamla, container, false);
        TextView adSoyad = (TextView) view.findViewById(R.id.textView13);
        TextView ulasan = (TextView) view.findViewById(R.id.textView19);
        TextView donen = (TextView) view.findViewById(R.id.textView20);
        Button tamam = (Button) view.findViewById(R.id.button17);
        Button iptal = (Button) view.findViewById(R.id.button18);
        adSoyad.setText(ao.getAd() + " " + ao.getSoyad());
        ulasan.setText(ao.getUlasan());
        donen.setText(ao.getDonen());
        tamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ao.getAd().length()>0&&ao.getSoyad().length()>0&&ao.getTel().length()>0&&ao.getIl().length()>0&&
                        ao.getIlce().length()>0&&ao.getAdres().length()>0){
                    tamamlaFragment.tamamla();
                    getDialog().dismiss();

                }
            }
        });
        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        return view;
    }

}
