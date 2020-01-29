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
import android.widget.ImageView;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Fragment.SepetFragment;
import com.modayakamoz.lenovo_.trendmoda.Fragment.YuklemeFragment;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.SepetObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 28.11.2017.
 */

public class SepetAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<SepetObject> mKisiListesi;
    private SepetFragment sepetFragment;
    public SepetAdapter(Activity activity, List<SepetObject> sepetObjects, SepetFragment sepetFragment) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = sepetObjects;
        this.sepetFragment=sepetFragment;
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
        satirView = mInflater.inflate(R.layout.sepet_list_item, null);
        final SepetObject sepetObject = mKisiListesi.get(position);
        ImageView resim=(ImageView)satirView.findViewById(R.id.imageView11);
        ImageView sil=(ImageView)satirView.findViewById(R.id.imageView12);
        TextView tutar=(TextView)satirView.findViewById(R.id.textView31);
        TextView beden=(TextView)satirView.findViewById(R.id.textView32);
        TextView adet=(TextView)satirView.findViewById(R.id.textView33);
        TextView kod = (TextView)satirView.findViewById(R.id.textView49);

        if(sepetObject.getResimObject().size()>0)
            Picasso.with(context).load("http://modayakamoz.com/resimler_k/"+sepetObject.getResimObject().get(0).getB_resim()).into(resim);
        else
            Picasso.with(context).load(R.drawable.yukleme).into(resim);


        tutar.setText("Tutar: "+sepetObject.getTl()+ " ₺");
        beden.setText("Beden: "+sepetObject.getBedenObject().getBeden());
        adet.setText("Adet: "+sepetObject.getBedenObject().getAdet());
        kod.setText("Kod: "+ sepetObject.getKod());
        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new getData().execute(""+sepetObject.getId());
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
        DBHelper dbHelper=new DBHelper(context);
            ReadURL readURL=new ReadURL();
            try{
                readURL.readURL("http://modayakamoz.com/json/sepet_sil.php?ID="+values[0]+"&komisyoncu_ID="+dbHelper.getUser().getID());



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
                sepetFragment.yenile();
            }
            progress.dismiss();


        }
    }
}
