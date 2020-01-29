package com.modayakamoz.lenovo_.trendmoda.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Fragment.AnalizTabHost;
import com.modayakamoz.lenovo_.trendmoda.Fragment.DegisimTabHost;
import com.modayakamoz.lenovo_.trendmoda.Fragment.ProfilTabHost;
import com.modayakamoz.lenovo_.trendmoda.Fragment.SepetFragment;
import com.modayakamoz.lenovo_.trendmoda.Fragment.SiparisTabHost;
import com.modayakamoz.lenovo_.trendmoda.Fragment.UrunlerTabHost;
import com.modayakamoz.lenovo_.trendmoda.Fragment.YuklemeFragment;
import com.modayakamoz.lenovo_.trendmoda.Helper.BottomNavigationViewHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.BedenObject;
import com.modayakamoz.lenovo_.trendmoda.Object.ResimObject;
import com.modayakamoz.lenovo_.trendmoda.Object.SepetObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<SepetObject>sepetObject;
    List<ResimObject>resimObject;
    BedenObject bedenObject;
    DBHelper dbhelper;
    ImageView sepet,cikis;
    public static TextView sepetAdet,ad;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()) {
                case R.id.profil:
                    transaction.replace(R.id.content,new ProfilTabHost()).commit();
                    return true;
                case R.id.siparis:
                    transaction.replace(R.id.content,new SiparisTabHost()).commit();
                    return true;
                case R.id.urunler:
                    transaction.replace(R.id.content,new UrunlerTabHost()).commit();
                    return true;
                case R.id.degisim:
                    transaction.replace(R.id.content,new DegisimTabHost()).commit();
                    return true;
                case R.id.grafik:
                    transaction.replace(R.id.content,new AnalizTabHost()).commit();
                    return true;

            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sepet=(ImageView)findViewById(R.id.imageView7);
        sepetAdet=(TextView)findViewById(R.id.textView8);
        ad=(TextView)findViewById(R.id.textView11);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        cikis=(ImageView)findViewById(R.id.imageView6);

        dbhelper=new DBHelper(getApplicationContext());
        String value = "0";
        if (getIntent().getExtras().getString("adet") != null)
            value = getIntent().getExtras().getString("adet");
        sepetAdet.setText(value);
        if(dbhelper.getUser().getKullaniciAdi().length()>0)
            ad.setText(dbhelper.getUser().getKullaniciAdi().toUpperCase());

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new ProfilTabHost()).commit();

        sepet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content,new SepetFragment()).commit();
            transaction.addToBackStack(String.valueOf(MainActivity.class));


            }
        });


        cikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                builder.setMessage("Çıkmak istediğinize emin misiniz?");

                builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dbhelper.deleteUser();
                        Intent intent = new Intent(MainActivity.this, GirisActivity.class);
                        startActivity(intent);

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });




    }

    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getSupportFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {   sepetObject=new ArrayList<SepetObject>();

            ReadURL readURL=new ReadURL();
            try{
                String data= readURL.readURL("http://modayakamoz.com/json/get_sepet.php?ID="+dbhelper.getUser().getID());
                JSONArray array=new JSONArray(data);
                for (int i=0;i<array.length();i++){
                    resimObject=new ArrayList<ResimObject>();
                    JSONObject object=array.getJSONObject(i);
                    JSONArray resimler=object.getJSONArray("resimler");
                    JSONArray bedenler=object.getJSONArray("beden");
                    for (int j=0;j<resimler.length();j++){
                        JSONObject resimobj=resimler.getJSONObject(j);
                        resimObject.add(new ResimObject(resimobj.getString("b_resim"),resimobj.getString("k_resim")));
                    }
                    for (int j=0;j<bedenler.length();j++){
                        JSONObject bedenobj=bedenler.getJSONObject(j);
                        bedenObject=(new BedenObject(bedenobj.getString("beden"),Integer.parseInt(bedenobj.getString("adet"))));
                    }
                    sepetObject.add(new SepetObject(Integer.parseInt(object.getString("id")),Double.parseDouble(object.getString("cikisTL")),
                            resimObject,bedenObject,object.getString("kod")));

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
            if (!results.equals("HATA")){
                sepetAdet.setText(""+sepetObject.size());

            }

            progress.dismiss();


        }
    }

    /*- Check permission Write ---------------------------------------------------------- */
// Pops up message to user for writing

    public void yenile(){
        new getData().execute();
    }




}
