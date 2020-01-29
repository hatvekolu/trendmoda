package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.modayakamoz.lenovo_.trendmoda.Activity.WebViewActivity;
import com.modayakamoz.lenovo_.trendmoda.Adapter.UrunDetayAdapter;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Helper.Sender;
import com.modayakamoz.lenovo_.trendmoda.Object.IlObject;
import com.modayakamoz.lenovo_.trendmoda.Object.SatisObject;
import com.modayakamoz.lenovo_.trendmoda.R;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;


public class SatisDetayFragment extends AppCompatActivity {
    UrunDetayAdapter adapter;
    public static SatisObject so;
    public static BekleyenFragment bekleyenFragment;
    public static DegisimBekleyen degisimBekleyen;

    EditText adE,soyadE,ilE,ilceE, adresE, aciklamaE,telE;

    TextView adSoyad, adres, tel, toplamTutar, hakedis, kesilenTutar, tarih, kargo, odeme_turu;
    ListView urunler;
    Button detay, edit, save, call, whats;
    LinearLayout linear, linearAna;
    int x=0;
    SpinnerDialog spinnerDialog;
    SpinnerDialog spinnerDialog1;
    ArrayList<IlObject> ilArray=new ArrayList<IlObject>();
    ArrayList<String> iller=new ArrayList<>();
    ArrayList<String> ilceler=new ArrayList<>();
    Button share;
    LinearLayout relativeLayout;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_satis_detay);
        relativeLayout = (LinearLayout) findViewById(R.id.back);
        share = (Button)findViewById(R.id.floatingActionButton2);
        aciklamaE=(EditText)findViewById(R.id.editText11);
        adE=(EditText) findViewById(R.id.editText26);
        soyadE=(EditText)findViewById(R.id.editText21);
        telE=(AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ilE=(EditText)findViewById(R.id.editText22);
        ilceE=(EditText)findViewById(R.id.editText24);
        adresE=(EditText)findViewById(R.id.editText25);
        call = (Button)findViewById(R.id.call);
        whats = (Button)findViewById(R.id.whats);
        adSoyad = (TextView) findViewById(R.id.textView4);
        adres = (TextView) findViewById(R.id.textView7);
        tel = (TextView) findViewById(R.id.textView44);
        kargo = (TextView) findViewById(R.id.textView43);
        toplamTutar = (TextView) findViewById(R.id.textView45);
        hakedis = (TextView) findViewById(R.id.textView46);
        kesilenTutar = (TextView) findViewById(R.id.textView47);
        tarih = (TextView) findViewById(R.id.textView48);
        urunler = (ListView) findViewById(R.id.urunler);
        odeme_turu = (TextView) findViewById(R.id.textView55);
        detay = (Button) findViewById(R.id.button12);
        edit = (Button) findViewById(R.id.button19);
        save = (Button) findViewById(R.id.button20);
        linear = (LinearLayout) findViewById(R.id.linear);
        linearAna = (LinearLayout) findViewById(R.id.linearAna);
        final DBHelper dbHelper=new DBHelper(getApplicationContext());
        if (so.getUrl().length() > 0)
            detay.setVisibility(View.VISIBLE);
        if (so.getDurum() < 1)
            edit.setVisibility(View.VISIBLE);
        if(degisimBekleyen != null)
           x=2;
        if(bekleyenFragment != null)
            x=1;
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (checkAndRequestPermissions())
                    {
                        shareImage();
                    } else {
                        checkAndRequestPermissions();
                    }
                }
                else
                {
                    shareImage();
                }

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearAna.setVisibility(View.GONE);
                linear.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);
                adE.setText(so.getAd());
                share.setVisibility(View.GONE);
                soyadE.setText(so.getSoyad());
                telE.setText(so.getTel());
                ilE.setText(so.getIl());
                ilceE.setText(so.getIlce());
                adresE.setText(so.getAdres());
                aciklamaE.setText(so.getAciklama());
                new getIl().execute();
            }
        });
        detay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WebViewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("link",so.getUrl());
                startActivity(intent);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (x == 1) {
                    Sender s=new Sender(getApplicationContext(),"http://modayakamoz.com/json/satis_duzenle.php",dbHelper.getUser().getID(),""+so.getId(),adE,soyadE,telE,ilE,ilceE,adresE,aciklamaE);
                    s.execute();
                    bekleyenFragment.yenile();
                    finish();
                    overridePendingTransition(0, 0);
                }
                else if (x == 2) {
                    Sender s=new Sender(getApplicationContext(),"http://modayakamoz.com/json/degisim_duzenle.php",dbHelper.getUser().getID(),""+so.getId(),adE,soyadE,telE,ilE,ilceE,adresE,aciklamaE);
                    s.execute();
                    degisimBekleyen.yenile();
                    finish();
                    overridePendingTransition(0, 0);
                }
            }
        });
        ilE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();

            }
        });
        ilceE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ilE.getText().length()>0 && ilceler.size() > 0)
                    spinnerDialog1.showSpinerDialog();
                else
                    TastyToast.makeText(getApplicationContext(), "İl Seçmelisiniz!", TastyToast.LENGTH_LONG, TastyToast.WARNING);
            }
        });
        odeme_turu.setText("Ödeme Türü: " + so.getOdeme_turu());
        adSoyad.setText("Ad Soyad: " + so.getAd() + " " + so.getSoyad());
        adres.setText("Adres: " + so.getAdres() + " " + so.getIlce() + "/" + so.getIl());
        tel.setText("Telefon: " + so.getTel());
        toplamTutar.setText("Tutar: " + so.getTutar() + " ₺");
        hakedis.setText("Hakediş: " + so.getHakedis() + " ₺");
        kesilenTutar.setText("Kesilen Tutar: " + so.getKesilen_tutar() + " ₺");
        tarih.setText("Tarih: " + so.getTarih());
        kargo.setText(so.getKargo_firma() + "-" + so.getKargo_no());

        if ( so.getTel().length()>0) {
            whats.setVisibility(View.VISIBLE);
            call.setVisibility(View.VISIBLE);
        }
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                String contact = "+90"+so.getTel(); // use country code with your phone number
                                String url = "https://api.whatsapp.com/send?phone=" + contact;
                                try {
                                    PackageManager pm = getApplicationContext().getPackageManager();
                                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    getApplicationContext().startActivity(i);
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setMessage(so.getAd()+ " " + so.getSoyad() + " Whatsapp'dan Yaz!").setPositiveButton("Yaz", dialogClickListener)
                        .setNegativeButton("Vazgeç", dialogClickListener).show();

            }

        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    final Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+90" + so.getTel()));
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);

                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    } else {
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        getApplicationContext().startActivity(callIntent);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setMessage(so.getAd() + " Aranacak!").setPositiveButton("Ara", dialogClickListener)
                                .setNegativeButton("Vazgeç", dialogClickListener).show();

                    }

                }
                catch (Exception e){
                    return;
                }

            }
        });

        adapter = new UrunDetayAdapter(SatisDetayFragment.this, so.getSatisUrunObject());
        urunler.setAdapter(adapter);
        kargo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager cm = (ClipboardManager) getApplicationContext().getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                cm.setText(kargo.getText());
                Toast.makeText(getApplicationContext(), "Kopyalandı", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }
    private class getIl extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getSupportFragmentManager();
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
            spinnerDialog=new SpinnerDialog((Activity) getApplicationContext(),iller,"İLLER",R.style.DialogAnimations_SmileWindow,"KAPAT");// With 	Animation

            spinnerDialog.setCancellable(true); // for cancellable
            spinnerDialog.setShowKeyboard(false);// for open keyboard by default
            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    ilE.setText(item);
                    ilceE.setText("");
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

            FragmentManager fm = getSupportFragmentManager();
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
            spinnerDialog1=new SpinnerDialog((Activity) getApplicationContext(),ilceler,"İLÇELER",R.style.DialogAnimations_SmileWindow,"KAPAT");// With 	Animation

            spinnerDialog1.setCancellable(true); // for cancellable
            spinnerDialog1.setShowKeyboard(false);// for open keyboard by default
            spinnerDialog1.showSpinerDialog();
            spinnerDialog1.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    ilceE.setText(item);
                }
            });

            progress.dismiss();


        }

    }
    private  void  shareImage(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        relativeLayout.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getDrawingCache());
        relativeLayout.setDrawingCacheEnabled(false);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "siapris.png");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/siapris.png"));
        startActivity(Intent.createChooser(share, "Share Image"));

    }
    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int locationPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(SatisDetayFragment.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
}
