package com.modayakamoz.lenovo_.trendmoda.Activity;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.modayakamoz.lenovo_.trendmoda.Adapter.OdemeAdapter;
import com.modayakamoz.lenovo_.trendmoda.Adapter.UrunDetayAdapter;
import com.modayakamoz.lenovo_.trendmoda.Fragment.BekleyenFragment;
import com.modayakamoz.lenovo_.trendmoda.Fragment.DegisimBekleyen;
import com.modayakamoz.lenovo_.trendmoda.Fragment.YuklemeFragment;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Helper.Sender;
import com.modayakamoz.lenovo_.trendmoda.Object.BedenObject;
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

public class SatisDetayActivity extends AppCompatActivity {
    UrunDetayAdapter adapter;
    public static SatisObject so;
    public static BekleyenFragment bekleyenFragment;
    public static DegisimBekleyen degisimBekleyen;
    YuklemeFragment progress;
    EditText adE, soyadE, ilE, ilceE, adresE, aciklamaE, telE;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    TextView adSoyad, adres, tel, toplamTutar, hakedis, kesilenTutar, tarih, kargo, odeme_turu;
    ListView urunler;
    Button detay, edit, save, call, whats;
    LinearLayout linear, linearAna;
    int x = 0;
    SpinnerDialog spinnerDialog;
    SpinnerDialog spinnerDialog1;
    ArrayList<IlObject> ilArray = new ArrayList<IlObject>();
    ArrayList<String> iller = new ArrayList<>();
    ArrayList<String> ilceler = new ArrayList<>();
    Button share,close;
    LinearLayout relativeLayout;
    FragmentManager fm;
    GridView odeme ;
    ArrayList<BedenObject> odeme_tur = new ArrayList<BedenObject>();
    OdemeAdapter bedenAdapter;
    public static String pos = "";
    int odemeSecenek;
    String odeme_turu_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satis_detay);
        relativeLayout = (LinearLayout) findViewById(R.id.back);
        share = (Button) findViewById(R.id.floatingActionButton2);
        aciklamaE = (EditText) findViewById(R.id.editText11);
        odeme=(GridView)findViewById(R.id.odemeE);
        adE = (EditText) findViewById(R.id.editText26);
        soyadE = (EditText) findViewById(R.id.editText21);
        telE = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ilE = (EditText) findViewById(R.id.editText22);
        ilceE = (EditText) findViewById(R.id.editText24);
        adresE = (EditText) findViewById(R.id.editText25);
        call = (Button) findViewById(R.id.call);
        whats = (Button) findViewById(R.id.whats);
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
        close = (Button)findViewById(R.id.button25);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
        odemeSecenek=so.getOdeme_int();

        odeme.setNumColumns(3);
        odeme_tur.add(0,new BedenObject("Kapıda K. Kartı",0));
        odeme_tur.add(1,new BedenObject("Kapıda Nakit",0));
        odeme_tur.add(2,new BedenObject("Havale",0));
        odeme_turu_text = so.getOdeme_turu();
        bedenAdapter = new OdemeAdapter(SatisDetayActivity.this, odeme_tur,odemeSecenek);
        odeme.setAdapter(bedenAdapter);
        fm = getSupportFragmentManager();
        progress = new YuklemeFragment();
        final DBHelper dbHelper = new DBHelper(getApplicationContext());
        if (so.getUrl().length() > 0)
            detay.setVisibility(View.VISIBLE);
        if (so.getDurum() < 1)
            edit.setVisibility(View.VISIBLE);
        if (degisimBekleyen != null)
            x = 2;
        if (bekleyenFragment != null)
            x = 1;
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkAndRequestPermissions()) {
                        shareImage();
                    } else {
                        checkAndRequestPermissions();
                    }
                } else {
                    shareImage();
                }

            }
        });

        odeme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for(int y=0;y<odeme.getChildCount();y++){
                    View c = odeme.getChildAt(y);
                    TextView tv = (TextView) c.findViewById(R.id.textView30);
                    if (y==i){
                        if (y == 0) {
                            tv.setTextColor(Color.WHITE);
                            tv.setBackgroundResource(R.drawable.grid_left_renk);
                        }
                        else if(y == 2 ) {
                            tv.setTextColor(Color.WHITE);
                            tv.setBackgroundResource(R.drawable.grid_right_renk);
                        }
                        else if (y == 1 ){
                            tv.setTextColor(Color.WHITE);
                            tv.setBackgroundColor(0xFFFF4081);
                        }
                    }
                    else {
                        if (y == 0) {
                            tv.setTextColor(0xFFFF4081);
                            tv.setBackgroundResource(R.drawable.grid_left);
                        }
                        else if(y == 2) {
                            tv.setTextColor(0xFFFF4081);
                            tv.setBackgroundResource(R.drawable.grid_right);
                        }
                        else if (y == 1){
                            tv.setTextColor(0xFFFF4081);
                            tv.setBackgroundColor(Color.WHITE);
                        }


                    }

                }
                if (i == 0)
                    odeme_turu_text = "KAPIDA K. KARTI";
                else if (i ==1)
                    odeme_turu_text = "KAPIDA NAKIT";
                else
                    odeme_turu_text = "HAVALE";
                odemeSecenek = i+1;


            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearAna.setVisibility(View.GONE);
                linear.setVisibility(View.VISIBLE);
                save.setVisibility(View.VISIBLE);
                edit.setVisibility(View.INVISIBLE);
                share.setVisibility(View.GONE);
                adE.setText(so.getAd());
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
                intent.putExtra("link", so.getUrl());
                startActivity(intent);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (x == 1) {
                    x = 0;
                    Sender s = new Sender(SatisDetayActivity.this, "http://modayakamoz.com/json/satis_duzenle1.php", "" + so.getId(), "" + odemeSecenek,adE, soyadE, telE, ilE, ilceE, adresE, aciklamaE);
                    s.execute();
                    SatisObject satisObject = new SatisObject(so.getId(),so.getTutar(),so.getKesilen_tutar(),so.getDurum(),so.getDays(), odemeSecenek,so.getHakedis(),adE.getText().toString(),soyadE.getText().toString(),telE.getText().toString(),ilE.getText().toString(),ilceE.getText().toString(),adresE.getText().toString(),aciklamaE.getText().toString(),so.getKargo_no(),so.getKargo_firma(),so.getTarih(),odeme_turu_text,so.getUrl(),so.getSatisUrunObject());
                    BekleyenFragment.satisObject.set(Integer.parseInt(pos),satisObject);
                    finish();
                    overridePendingTransition(0, 0);
                    bekleyenFragment.yenile();
                } else if (x == 2) {
                    x = 0;
                    Sender s = new Sender(SatisDetayActivity.this, "http://modayakamoz.com/json/degisim_duzenle1.php", "" + so.getId(), "" + odemeSecenek, adE, soyadE, telE, ilE, ilceE, adresE, aciklamaE);
                    s.execute();
                    SatisObject satisObject = new SatisObject(so.getId(),so.getTutar(),so.getKesilen_tutar(),so.getDurum(),so.getDays(), odemeSecenek,so.getHakedis(),adE.getText().toString(),soyadE.getText().toString(),telE.getText().toString(),ilE.getText().toString(),ilceE.getText().toString(),adresE.getText().toString(),aciklamaE.getText().toString(),so.getKargo_no(),so.getKargo_firma(),so.getTarih(),odeme_turu_text,so.getUrl(),so.getSatisUrunObject());
                    DegisimBekleyen.satisObject.set(Integer.parseInt(pos),satisObject);
                    finish();
                    overridePendingTransition(0, 0);
                    degisimBekleyen.yenile();
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
                if (ilE.getText().length() > 0 && ilceler.size() > 0)
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

        if (so.getTel().length() > 0) {
            whats.setVisibility(View.VISIBLE);
            call.setVisibility(View.VISIBLE);
        }
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SatisDetayActivity.this);


                builder.setMessage("Whatsapp'dan Yaz!");

                builder.setPositiveButton("Yaz", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String contact = "+90" + so.getTel(); // use country code with your phone number
                        String url = "https://api.whatsapp.com/send?phone=" + contact;
                        try {
                            PackageManager pm = getApplication().getPackageManager();
                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {

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

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    final Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:+90" + so.getTel()));
                    if (ContextCompat.checkSelfPermission(SatisDetayActivity.this,
                            Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(SatisDetayActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                1);

                        // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SatisDetayActivity.this);


                        builder.setMessage(so.getAd().toUpperCase() +" "+so.getSoyad().toUpperCase() + " Aranacak!");

                        builder.setPositiveButton("Ara", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                startActivity(callIntent);
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Do nothing
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                    }

                } catch (Exception e) {
                    return;
                }

            }
        });

        adapter = new UrunDetayAdapter(SatisDetayActivity.this, so.getSatisUrunObject());
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

        @Override
        protected void onPreExecute() {

            progress.show(fm, "");

        }

        @Override
        protected String doInBackground(String... values) {
            ReadURL readURL = new ReadURL();
            try {
                ilArray.clear();
                iller.clear();
                String data = readURL.readURL("http://modayakamoz.com/json/get_il.php");
                JSONArray array = new JSONArray(data);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    ilArray.add(new IlObject(object.getString("il_kod"), object.getString("il_adi")));
                    iller.add(object.getString("il_adi"));
                }


                return "0";
            } catch (Exception e) {
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results) {
            spinnerDialog = new SpinnerDialog(SatisDetayActivity.this, iller, "İLLER", R.style.DialogAnimations_SmileWindow, "KAPAT");// With 	Animation

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

        @Override
        protected void onPreExecute() {
            progress.show(fm, "");

        }

        @Override
        protected String doInBackground(String... values) {
            ReadURL readURL = new ReadURL();
            try {
                String data = readURL.readURL("http://modayakamoz.com/json/get_ilce.php?il=" + values[0]);
                JSONArray array = new JSONArray(data);
                ilceler.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    ilceler.add(object.getString("ilce_adi"));
                }


                return "0";
            } catch (Exception e) {
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results) {
            spinnerDialog1 = new SpinnerDialog(SatisDetayActivity.this, ilceler, "İLÇELER", R.style.DialogAnimations_SmileWindow, "KAPAT");// With 	Animation

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

    private void shareImage() {
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

    private boolean checkAndRequestPermissions() {
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
            ActivityCompat.requestPermissions(SatisDetayActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);

    }
    public View getViewByPosition(int pos, GridView gridView) {
        final int firstListItemPosition = gridView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + gridView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return gridView.getAdapter().getView(pos, null, gridView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return gridView.getChildAt(childIndex);
        }
    }

}