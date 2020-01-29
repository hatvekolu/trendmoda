package com.modayakamoz.lenovo_.trendmoda.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Fragment.ParaDurumuFragment;
import com.modayakamoz.lenovo_.trendmoda.Fragment.YuklemeFragment;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.ParaDurumuObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 27.11.2017.
 */

public class ParaAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<ParaDurumuObject> mKisiListesi;
    private ParaDurumuFragment paraDurumuFragment;
    public ParaAdapter(Activity activity, List<ParaDurumuObject> paraDurumuObjects ,ParaDurumuFragment paraDurumuFragment) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = paraDurumuObjects;
        this.paraDurumuFragment=paraDurumuFragment;
    }
    @Override
    public int getCount() {
        return mKisiListesi.size();
    }

    @Override
    public Object getItem(int position) {
        return mKisiListesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View satirView;
        satirView = mInflater.inflate(R.layout.para_list_item, null);
        final ParaDurumuObject paraDurumuObject = mKisiListesi.get(position);
        TextView tutar=(TextView)satirView.findViewById(R.id.textView22);
        TextView tarih=(TextView)satirView.findViewById(R.id.textView23);
        ImageView durum=(ImageView)satirView.findViewById(R.id.imageView8);
        Button iptal=(Button)satirView.findViewById(R.id.button9);

        tutar.setText("Tutar: "+paraDurumuObject.getTutar()+" ₺");
        tarih.setText(paraDurumuObject.getTarih());




        if(paraDurumuObject.getDurum()==0){
            Picasso.with(context).load(R.drawable.durum1).into(durum);

        }

        else if(paraDurumuObject.getDurum()==1){
            Picasso.with(context).load(R.drawable.durum3).into(durum);
            iptal.setVisibility(View.GONE);

        }

        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new getData().execute(""+paraDurumuObject.getId());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("İptal edilsin mi?").setPositiveButton("Evet", dialogClickListener)
                        .setNegativeButton("Hayır", dialogClickListener).show();
            }
        });





        return satirView;
    }
    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentActivity activity = (FragmentActivity)(context);
            FragmentManager fm = activity.getSupportFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {
            ReadURL readURL=new ReadURL();
            try{
                readURL.readURL("http://modayakamoz.com/json/istek_sil.php?ID="+values[0]);



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
                paraDurumuFragment.yenile();
            }
            progress.dismiss();


        }
    }
}
