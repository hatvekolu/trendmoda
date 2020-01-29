package com.modayakamoz.lenovo_.trendmoda.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Fragment.YuklemeFragment;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.UserObject;
import com.modayakamoz.lenovo_.trendmoda.R;

public class GirisActivity extends AppCompatActivity {
    EditText kullaniciAdi,sifre;
    TextView basvur,hatali;
    Button giris;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        basvur=(TextView)findViewById(R.id.textView64);
        hatali=(TextView)findViewById(R.id.textView16);
        kullaniciAdi=(EditText)findViewById(R.id.editText2);
        sifre=(EditText)findViewById(R.id.editText14);
        giris=(Button)findViewById(R.id.button10);

        dbHelper=new DBHelper(getApplicationContext());

        UserObject uo=dbHelper.getUser();
        if (uo.getKullaniciAdi().length()>0){
            Intent intent = new Intent(GirisActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        basvur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(GirisActivity.this, BasvurActivity.class);
                GirisActivity.this.startActivity(myIntent);
            }
        });

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kullaniciAdi.getText().length()>0 && sifre.getText().length()>0)
                    new getData().execute(kullaniciAdi.getText().toString(),sifre.getText().toString());
                else
                    hatali.setVisibility(View.VISIBLE);
            }
        });


        basvur.setPaintFlags(basvur.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

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
                String data= readURL.readURL("http://modayakamoz.com/json/check.php?kullaniciAdi="+values[0]+"&sifre="+values[1]);



                return data;
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (!results.equals("HATA")){
                dbHelper.deleteUser();
                dbHelper.insertUser(kullaniciAdi.getText().toString(),sifre.getText().toString(),"",results);
                Intent intent = new Intent(GirisActivity.this, MainActivity.class);
                intent.putExtra("adet", "0");
                startActivity(intent);
                finish();

            }
            else
                hatali.setVisibility(View.VISIBLE);

            progress.dismiss();


        }
    }
}
