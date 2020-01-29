package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.modayakamoz.lenovo_.trendmoda.Activity.MainActivity;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KullaniciEditFragment extends Fragment  {

    EditText ad,soyad,tel,bankaAdi,iban,sifre;
    Button kaydet;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_kullanici_edit, container, false);

        ad=(EditText)view.findViewById(R.id.editText15);
        soyad=(EditText)view.findViewById(R.id.editText16);
        tel=(EditText)view.findViewById(R.id.editText17);
        bankaAdi=(EditText)view.findViewById(R.id.editText18);
        iban=(EditText)view.findViewById(R.id.editText19);
        sifre=(EditText)view.findViewById(R.id.editText20);

        ad.setText(ProfilFragment.profilObject.getAd());
        soyad.setText(ProfilFragment.profilObject.getSoyad());
        tel.setText(ProfilFragment.profilObject.getTelNo());
        bankaAdi.setText(ProfilFragment.profilObject.getBankaAdi());
        iban.setText(ProfilFragment.profilObject.getIban());
        sifre.setText(ProfilFragment.profilObject.getSifre());


        kaydet=(Button)view.findViewById(R.id.button11);

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ad.getText().length()>0&&soyad.getText().length()>0&&tel.getText().length()>0&&bankaAdi.getText().length()>0&&
                        iban.getText().length()>0&&sifre.getText().length()>0)
                new getData().execute(ad.getText().toString(),soyad.getText().toString(),tel.getText().toString(),
                        bankaAdi.getText().toString(),iban.getText().toString(),sifre.getText().toString());
                else
                    Toast.makeText(getContext(),"Tüm Alanları Doldurunuz!",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm =getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {
            dbHelper=new DBHelper(getContext());
            ReadURL readURL=new ReadURL();
            try{


                readURL.readURL("http://modayakamoz.com/json/duzenle.php?ad="+values[0].replaceAll(" ","_")+"&soyad="+values[1].replaceAll(" ","_")+"&tel="+values[2].replaceAll(" ","_")+"&banka="+values[3].replaceAll(" ","_")+"&iban="+values[4].replaceAll(" ","_")+"&sifre="+values[5].replaceAll(" ","_")+"&ID="+dbHelper.getUser().getID());



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
                FragmentTransaction transaction=((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content,new ProfilTabHost()).commit();
                transaction.addToBackStack(String.valueOf(MainActivity.class));
            }
            else
                Toast.makeText(getContext(),"Hata",Toast.LENGTH_SHORT).show();

            progress.dismiss();


        }
    }

}
