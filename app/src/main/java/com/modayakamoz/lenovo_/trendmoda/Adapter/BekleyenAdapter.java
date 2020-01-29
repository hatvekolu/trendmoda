package com.modayakamoz.lenovo_.trendmoda.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Activity.MainActivity;
import com.modayakamoz.lenovo_.trendmoda.Activity.SatisDetayActivity;
import com.modayakamoz.lenovo_.trendmoda.Fragment.BekleyenFragment;
import com.modayakamoz.lenovo_.trendmoda.Fragment.YuklemeFragment;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.SatisObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 27.11.2017.
 */

public class BekleyenAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater mInflater;
    private Activity context;
    private List<SatisObject> mKisiListesi;
    private List<SatisObject> mYedek;
    private BekleyenFragment bekleyenFragment;
    private ItemFilter mFilter = new ItemFilter();
    public BekleyenAdapter(Activity activity, List<SatisObject> satisObjects, BekleyenFragment bekleyenFragment) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = satisObjects;
        mYedek=satisObjects;
        this.bekleyenFragment=bekleyenFragment;
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
    private class ViewHolder {
        TextView adSoyad;
        TextView tel;
        TextView tutar;
        ImageView uResim;
        ImageView durum;
        Button iptal;
        Button aktar;
        LinearLayout back;
        LinearLayout layout;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.degisim_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.uResim = (ImageView) rowView.findViewById(R.id.imageView2);
            viewHolder.durum = (ImageView) rowView.findViewById(R.id.imageView3);
            viewHolder.adSoyad = (TextView) rowView.findViewById(R.id.textView3);
            viewHolder.tel = (TextView) rowView.findViewById(R.id.textView2);
            viewHolder.tutar = (TextView) rowView.findViewById(R.id.textView51);
            viewHolder.iptal = (Button) rowView.findViewById(R.id.button14);
            viewHolder.aktar = (Button) rowView.findViewById(R.id.button21);
            viewHolder.layout = (LinearLayout) rowView.findViewById(R.id.layout);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }
        final SatisObject satisObject = mKisiListesi.get(position);
        viewHolder.adSoyad.setText(satisObject.getAd()+" "+satisObject.getSoyad());
        viewHolder.tel.setText(satisObject.getTel());
        viewHolder.tutar.setText("Tutar: "+satisObject.getTutar()+"₺");
        String extension = satisObject.getSatisUrunObject().get(0).getResimObject().get(0).getB_resim().substring(satisObject.getSatisUrunObject().get(0).getResimObject().get(0).getB_resim().lastIndexOf("."));
        if (extension.toLowerCase().equals(".mp4"))    {
            Picasso.with(context).load(R.drawable.images).into(viewHolder.uResim);
        }
        else {
            if(satisObject.getSatisUrunObject().get(0).getResimObject().size()>0)
                Picasso.with(context).load("http://modayakamoz.com/resimler_k/"+satisObject.getSatisUrunObject().get(0).getResimObject().get(0).getB_resim()).into(viewHolder.uResim);
            else
                Picasso.with(context).load(R.drawable.yukleme).into(viewHolder.uResim);
        }

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SatisDetayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                SatisDetayActivity.so = satisObject;
                SatisDetayActivity.bekleyenFragment = bekleyenFragment;
                SatisDetayActivity.pos = ""+position;
                context.startActivity(intent);
            }
        });
        if (satisObject.getDurum() == 0){
            viewHolder.aktar.setVisibility(View.VISIBLE);
            viewHolder.iptal.setVisibility(View.VISIBLE);
            viewHolder.layout.setBackgroundColor(0x00000000);
        }
        else{
            viewHolder.aktar.setVisibility(View.GONE);
            viewHolder.iptal.setVisibility(View.GONE);
            viewHolder.layout.setBackgroundColor(0xff99cc00);
        }
        viewHolder.aktar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new getData().execute(""+satisObject.getId(),"2");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Tüm ürünleri sepetinize aktarır ve siparişi iptal eder!").setPositiveButton("Evet", dialogClickListener)
                        .setNegativeButton("Hayır", dialogClickListener).show();
            }
        });
        viewHolder.iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                new getData().execute(""+satisObject.getId(),"1");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(satisObject.getAd().toUpperCase()+" "+satisObject.getSoyad().toUpperCase()+" İptal edilsin mi?").setPositiveButton("Evet", dialogClickListener)
                        .setNegativeButton("Hayır", dialogClickListener).show();
            }
        });





        return rowView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mKisiListesi.clear();
        if (charText.length() == 0) {
            mKisiListesi.addAll(mYedek);
        } else {
            for (SatisObject wp : mYedek) {
                if ((wp.getAd()+" "+wp.getSoyad()+" "+wp.getTel()).toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    mKisiListesi.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<SatisObject> list = mYedek;

            int count = list.size();
            final ArrayList<SatisObject> nlist = new ArrayList<SatisObject>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getAd()+" "+list.get(i).getSoyad()+" "+list.get(i).getTel();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mKisiListesi = (ArrayList<SatisObject>) results.values;
            notifyDataSetChanged();
        }

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
            DBHelper dbHelper=new DBHelper(context);
            try{
                if (values[1].equals("1")){
                    readURL.readURL("http://modayakamoz.com/json/siparis_iptal.php?ID="+values[0]+"&komisyoncu_ID="+dbHelper.getUser().getID());
                    return "1";
                }
                else if (values[1].equals("2")){
                    readURL.readURL("http://modayakamoz.com/json/sepete_aktar.php?ID="+values[0]+"&komisyoncu_ID="+dbHelper.getUser().getID());
                    return "2";
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
                bekleyenFragment.yenile();
                if (results.equals("2"))
                    ((MainActivity) context).yenile();
            }
            progress.dismiss();


        }
    }
}
