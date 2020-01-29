package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Adapter.BedenAdapter;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Helper.Sender;
import com.modayakamoz.lenovo_.trendmoda.Object.AliciObject;
import com.modayakamoz.lenovo_.trendmoda.Object.BedenObject;
import com.modayakamoz.lenovo_.trendmoda.Object.IlObject;
import com.modayakamoz.lenovo_.trendmoda.R;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


public class SatisTamamlaFragment extends DialogFragment {

    AliciObject aliciObject;
    Button tamamla,getAlici;
    CheckBox degisim;
    EditText aciklama;
    private SepetFragment sepetFragment;
    public void setFragment(SepetFragment sepetFragment) {
        this.sepetFragment = sepetFragment;
    }
    EditText ad,soyad,il,ilce,adres;
    AutoCompleteTextView  tel;
    boolean isCheck;
    ListView aa;
    SpinnerDialog spinnerDialog;
    SpinnerDialog spinnerDialog1;
    ArrayList<IlObject> ilArray=new ArrayList<IlObject>();
    ArrayList<String> iller=new ArrayList<>();
    ArrayList<String> ilceler=new ArrayList<>();
    ArrayList<BedenObject> odeme_tur = new ArrayList<BedenObject>();
    GridView odeme ;
    BedenAdapter adapter;
    int odemeSecenek = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
         View view= inflater.inflate(R.layout.fragment_satis_tamamla, container, false);

        odeme=(GridView)view.findViewById(R.id.odeme);
        degisim=(CheckBox)view.findViewById(R.id.checkBox);
        aa=(ListView)view.findViewById(R.id.aaaa);
        getAlici=(Button)view.findViewById(R.id.searhAlici);
        odeme.setNumColumns(3);
        odeme_tur.add(0,new BedenObject("Kapıda K. Kartı",0));
        odeme_tur.add(1,new BedenObject("Kapıda Nakit",0));
        odeme_tur.add(2,new BedenObject("Havale",0));
        adapter = new BedenAdapter(getActivity(), odeme_tur);
        odeme.setAdapter(adapter);

        odeme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    for(int x=0;x<odeme.getChildCount();x++){
                        View c = odeme.getChildAt(x);
                        TextView tv = (TextView) c.findViewById(R.id.textView30);
                        if (x==i){
                            if (x == 0) {
                                tv.setTextColor(Color.WHITE);
                                tv.setBackgroundResource(R.drawable.grid_left_renk);
                            }
                            else if(x == 2 ) {
                                tv.setTextColor(Color.WHITE);
                                tv.setBackgroundResource(R.drawable.grid_right_renk);
                            }
                            else if (x == 1 ){
                                tv.setTextColor(Color.WHITE);
                                tv.setBackgroundColor(0xFFFF4081);
                            }
                        }
                        else {
                            if (x == 0) {
                                tv.setTextColor(0xFFFF4081);
                                tv.setBackgroundResource(R.drawable.grid_left);
                            }
                            else if(x == 2) {
                                tv.setTextColor(0xFFFF4081);
                                tv.setBackgroundResource(R.drawable.grid_right);
                            }
                            else if (x == 1){
                                tv.setTextColor(0xFFFF4081);
                                tv.setBackgroundColor(Color.WHITE);
                            }


                        }

                    }
                    odemeSecenek = i+1;


            }
        });


        getAlici.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tel.getText().length() > 9)
                    new getData().execute(tel.getText().toString());
                else
                    TastyToast.makeText(getContext(), "Geçersiz telefon numarası.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        });

        degisim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (degisim.isChecked()){
                    TastyToast.makeText(getContext(), "Değişim", TastyToast.LENGTH_SHORT, TastyToast.WARNING);
                    isCheck=true;
                }

                else{
                    TastyToast.makeText(getContext(), "Normal Satış", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
                    isCheck=false;
                }

            }
        });

        aciklama=(EditText)view.findViewById(R.id.editText11);
        ad=(EditText) view.findViewById(R.id.editText26);
        soyad=(EditText)view.findViewById(R.id.editText21);
        tel=(AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        il=(EditText)view.findViewById(R.id.editText22);
        ilce=(EditText)view.findViewById(R.id.editText24);
        adres=(EditText)view.findViewById(R.id.editText25);
        tamamla=(Button)view.findViewById(R.id.button7);
        final DBHelper dbHelper=new DBHelper(getContext());

        il.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();

            }
        });
        ilce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (il.getText().length()>0)
                    spinnerDialog1.showSpinerDialog();
                else
                    TastyToast.makeText(getContext(), "İl Seçmelisiniz!", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }
        });
        tamamla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ad.getText().length()>0&&soyad.getText().length()>0&&tel.getText().length()>0&&il.getText().length()>0&&
                        ilce.getText().length()>0&&adres.getText().length()>0&& odemeSecenek>0){
                    if (aliciObject != null) {
                        FragmentActivity activity = (FragmentActivity)(getContext());
                        FragmentManager fm = activity.getSupportFragmentManager();
                        MusteriDetayTamamlaFragment alertDialog = new MusteriDetayTamamlaFragment();
                        alertDialog.setAlici(aliciObject);
                        alertDialog.setFragment(SatisTamamlaFragment.this);
                        alertDialog.show(fm,"fragment_alert");
                    }
                    else {
                        new getData1().execute(tel.getText().toString());
                    }

                }
                else
                    TastyToast.makeText(getContext(), "Alıcı Bilgileri ya da Ödeme Türü Eksik", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            }
        });



        new getIl().execute();

        return view;
    }
    public void tamamla(){
        final DBHelper dbHelper=new DBHelper(getContext());
        if (ad.getText().length()>0&&soyad.getText().length()>0&&tel.getText().length()>0&&il.getText().length()>0&&
                ilce.getText().length()>0&&adres.getText().length()>0&& odemeSecenek>0){


            if (!isCheck){
                Sender s=new Sender(getContext(),"http://modayakamoz.com/json/satis_tamamla1.php",dbHelper.getUser().getID(),""+odemeSecenek,ad,soyad,tel,il,ilce,adres,aciklama);
                s.execute();
            }
            if (isCheck){
                Sender s=new Sender(getContext(),"http://modayakamoz.com/json/degisim_tamamla1.php",dbHelper.getUser().getID(),""+odemeSecenek,ad,soyad,tel,il,ilce,adres,aciklama);
                s.execute();
            }



            sepetFragment.yenile();
            getDialog().dismiss();


        }
        else
            TastyToast.makeText(getContext(), "Alıcı Bilgileri ya da Ödeme Türü Eksik.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
    }
    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");

        }

        @Override
        protected String doInBackground(String... values)
        {
            ReadURL readURL=new ReadURL();
            try{
                String data=readURL.readURL("http://modayakamoz.com/json/get_alici.php?tel="+ values[0]);
                JSONObject object=new JSONObject(data);
                aliciObject = new AliciObject(object.getString("ad"),object.getString("soyad"),object.getString("telefon"),object.getString("il"),
                        object.getString("ilce"),object.getString("adres"), Integer.parseInt(object.getString("ulasma")), Integer.parseInt(object.getString("toplam")),
                        object.getString("ulasan"),object.getString("donen"));


                return "0";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (!results.equals("HATA")){
                ad.setText(aliciObject.getAd().toString());
                soyad.setText(aliciObject.getSoyad().toString());
                tel.setText(aliciObject.getTel().toString());
                il.setText(aliciObject.getIl().toString());
                ilce.setText(aliciObject.getIlce().toString());
                adres.setText(aliciObject.getAdres().toString());

                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                MusteriDetayFragment alertDialog = new MusteriDetayFragment();
                alertDialog.setAlici(aliciObject);
                alertDialog.show(fm,"fragment_alert");

            }
            else {
                TastyToast.makeText(getContext(), "Bu numaraya ait sipariş bulunamadı", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }

            progress.dismiss();


        }

    }
    private class getData1 extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");

        }

        @Override
        protected String doInBackground(String... values)
        {
            ReadURL readURL=new ReadURL();
            try{
                String data=readURL.readURL("http://modayakamoz.com/json/get_alici.php?tel="+ values[0]);
                JSONObject object=new JSONObject(data);
                aliciObject = new AliciObject(object.getString("ad"),object.getString("soyad"),object.getString("telefon"),object.getString("il"),
                        object.getString("ilce"),object.getString("adres"), Integer.parseInt(object.getString("ulasma")), Integer.parseInt(object.getString("toplam")),
                        object.getString("ulasan"),object.getString("donen"));


                return "0";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (!results.equals("HATA")){


                FragmentActivity activity = (FragmentActivity)(getContext());
                FragmentManager fm = activity.getSupportFragmentManager();
                MusteriDetayTamamlaFragment alertDialog = new MusteriDetayTamamlaFragment();
                alertDialog.setAlici(aliciObject);
                alertDialog.setFragment(SatisTamamlaFragment.this);
                alertDialog.show(fm,"fragment_alert");

            }
            else {
                tamamla();
            }

            progress.dismiss();


        }

    }
    private class getIl extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");

        }

        @Override
        protected String doInBackground(String... values)
        {
            ReadURL readURL=new ReadURL();
            try{
                ilArray.clear();
                iller.clear();
                String data=readURL.readURL("http://modayakamoz.com/json/get_il.php");
                JSONArray array=new JSONArray(data);
                for(int i =0; i<array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    ilArray.add(new IlObject(object.getString("il_kod"),object.getString("il_adi")));
                    iller.add(object.getString("il_adi"));
                }


                return "0";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            spinnerDialog=new SpinnerDialog((Activity) getContext(),iller,"İLLER",R.style.DialogAnimations_SmileWindow,"KAPAT");// With 	Animation

            spinnerDialog.setCancellable(true); // for cancellable
            spinnerDialog.setShowKeyboard(false);// for open keyboard by default
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    il.setText(item);
                    ilce.setText("");
                    new getIlce().execute(ilArray.get(position).getKod());

                }
            });

            progress.dismiss();


        }

    }
    private class getIlce extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");

        }

        @Override
        protected String doInBackground(String... values)
        {
            ReadURL readURL=new ReadURL();
            try{
                String data=readURL.readURL("http://modayakamoz.com/json/get_ilce.php?il="+values[0]);
                JSONArray array=new JSONArray(data);
                ilceler.clear();
                for(int i =0; i<array.length();i++){
                    JSONObject object = array.getJSONObject(i);
                    ilceler.add(object.getString("ilce_adi"));
                }


                return "0";
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            spinnerDialog1=new SpinnerDialog((Activity) getContext(),ilceler,"İLÇELER",R.style.DialogAnimations_SmileWindow,"KAPAT");// With 	Animation

            spinnerDialog1.setCancellable(true); // for cancellable
            spinnerDialog1.setShowKeyboard(false);// for open keyboard by default
            spinnerDialog1.showSpinerDialog();
            spinnerDialog1.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    ilce.setText(item);
                }
            });

            progress.dismiss();


        }

    }


}
