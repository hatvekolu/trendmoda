package com.modayakamoz.lenovo_.trendmoda.Activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.modayakamoz.lenovo_.trendmoda.Fragment.YuklemeFragment;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.R;

public class BasvurActivity extends AppCompatActivity {
    EditText ad,soyad,kullaniciAdi,tel,sifre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basvur);
        Button basvur = (Button) findViewById(R.id.button15);
        Button geri = (Button) findViewById(R.id.button16);
        ad = (EditText) findViewById(R.id.editText5);
        soyad = (EditText) findViewById(R.id.editText6);
        kullaniciAdi = (EditText) findViewById(R.id.editText7);
        tel = (EditText) findViewById(R.id.editText8);
        sifre = (EditText) findViewById(R.id.editText9);


        ad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ad.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ad.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ad.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        soyad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                soyad.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                soyad.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                soyad.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tel.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tel.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                tel.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        kullaniciAdi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                kullaniciAdi.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                kullaniciAdi.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                kullaniciAdi.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        sifre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sifre.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sifre.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                sifre.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        soyad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soyad.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        sifre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sifre.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tel.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        kullaniciAdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kullaniciAdi.setBackgroundColor(Color.TRANSPARENT);
            }
        });

        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        basvur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( ad.getText().length() > 0 && soyad.getText().length() >0 && tel.getText().length() > 9 && sifre.getText().length() > 5 && kullaniciAdi.getText().length() > 0)
                    new getData().execute(ad.getText().toString(),soyad.getText().toString(),tel.getText().toString(),sifre.getText().toString(),kullaniciAdi.getText().toString());
                else
                {
                    if (ad.getText().length() == 0)
                        ad.setBackgroundColor(Color.RED);
                    if (soyad.getText().length() == 0)
                        soyad.setBackgroundColor(Color.RED);
                    if (tel.getText().length() < 10)
                        tel.setBackgroundColor(Color.RED);
                    if (sifre.getText().length() < 6)
                        sifre.setBackgroundColor(Color.RED);
                    if (kullaniciAdi.getText().length() == 0){
                        kullaniciAdi.setBackgroundColor(Color.RED);
                        Toast.makeText(getApplicationContext(), "Geçersiz şifre, Min 6 karakter.", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm =getSupportFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {

            ReadURL readURL=new ReadURL();
            try{
                String data= readURL.readURL("http://modayakamoz.com/json/basvuru.php?kullaniciAdi="+values[4]+"&sifre="+values[3]+"&ad="+values[0]+"&soyad="+values[1]+"&tel="+values[2]);



                return data;
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (results.length() > 2){
                kullaniciAdi.setBackgroundColor(Color.RED);
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Kullanıcı adı kullanılmaktadır.", Toast.LENGTH_LONG).show();

            }
            else {

                progress.dismiss();
                finish();
            }





        }
    }
}
