package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.modayakamoz.lenovo_.trendmoda.Activity.MainActivity;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.ProfilObject;
import com.modayakamoz.lenovo_.trendmoda.R;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;


public class ProfilFragment extends Fragment {
    CircularProgressBar circularProgressBar;
    public static ProfilObject profilObject;
    TextView ulasan,yolda,bekleyen,donen,bakiye,yuzde;
    Button istek;
    ImageView info;
    FloatingActionButton edit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profil, container, false);

        istek=(Button)view.findViewById(R.id.button);
        info=(ImageView)view.findViewById(R.id.imageView5);
        yuzde = (TextView)view.findViewById(R.id.textView42);
        bakiye=(TextView)view.findViewById(R.id.atextView14);
        ulasan=(TextView)view.findViewById(R.id.atextView12);
        yolda=(TextView)view.findViewById(R.id.atextView10);
        bekleyen=(TextView)view.findViewById(R.id.atextView9);
        donen=(TextView)view.findViewById(R.id.atextView13);
        circularProgressBar = (CircularProgressBar)view.findViewById(R.id.circularProgressBar);

        edit=(FloatingActionButton)view.findViewById(R.id.floatingActionButton);

        istek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profilObject.getBakiye() > 0 && profilObject.getIban().length() > 23 && profilObject.getBankaAdi().length() > 0){
                    FragmentManager fm = getFragmentManager();
                    ParaFragment alertDialog = new ParaFragment();
                    alertDialog.setFragment(ProfilFragment.this);
                    alertDialog.setPara(profilObject.getBakiye());
                    alertDialog.show(fm,"fragment_alert");
               }
                else
                    TastyToast.makeText(getContext(), "Yeterli Bakiyeniz Yok ya da Ödeme Bilgileriniz Yanlış", TastyToast.LENGTH_LONG, TastyToast.ERROR);


            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profilObject.getKullaniciadi().length() > 0){
                    FragmentTransaction transaction=((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content,new KullaniciEditFragment()).commit();
                    transaction.addToBackStack(String.valueOf(MainActivity.class));
                }

            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction=((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content,new InfoFragment()).commit();
                transaction.addToBackStack(String.valueOf(MainActivity.class));


            }
        });


        new getData().execute();

        return view;
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

                DBHelper dbhelper=new DBHelper(getContext());

                ReadURL readURL=new ReadURL();
                try{
                    String gelenData=readURL.readURL("http://modayakamoz.com/json/get_profil.php?ID="+dbhelper.getUser().getID());
                    JSONObject object=new JSONObject(gelenData);

                        profilObject = (new ProfilObject(object.getString("ad"),object.getString("soyad"),object.getString("komisyoncu"),
                                object.getString("telefon"),Double.parseDouble(object.getString("bakiye")),Double.parseDouble(object.getString("yuzde"))
                                ,object.getString("banka"),
                                object.getString("iban"),object.getString("sifre"),
                                object.getString("android_version"),Integer.parseInt(object.getString("bekleyen")),Integer.parseInt(object.getString("yolda")),
                                Integer.parseInt(object.getString("ulasan")),Integer.parseInt(object.getString("donen"))));
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
                ulasan.setText("Ulaşan: "+profilObject.getUlasan());
                donen.setText("Dönen: "+profilObject.getDonen());
                yolda.setText("Yolda: "+profilObject.getYolda());
                bekleyen.setText("Bekleyen: "+profilObject.getBekleyen());
                bakiye.setText("Bakiye: "+(int)profilObject.getBakiye()+" TL");
                circularProgressBar.setProgressWithAnimation(Float.parseFloat(""+profilObject.getYuzde()));
                yuzde.setText("%"+(int)profilObject.getYuzde());
                try {
                    PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
                    String version = pInfo.versionName;
                    if (!profilObject.getVersion().equals(version)) {
                        Snackbar snackbar = Snackbar
                                .make(getActivity().findViewById(android.R.id.content), "Uygulamanız güncel değil. Güncellemek ister misiniz?", 5000).setActionTextColor(0xFF17EA0C)
                                .setAction("Evet", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final String appPackageName = getActivity().getPackageName();
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }
                                    }
                                });

                        snackbar.show();
                    }

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }


            }
            progress.dismiss();


        }
    }
    public void yenile(){
        new getData().execute();
    }
}
