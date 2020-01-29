package com.modayakamoz.lenovo_.trendmoda.Adapter;

import android.app.Activity;
import android.content.Context;
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

import com.modayakamoz.lenovo_.trendmoda.Object.SatisObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 27.11.2017.
 */

public class SatisAdapter extends BaseAdapter implements Filterable {
    private LayoutInflater mInflater;
    private Context context;
    private List<SatisObject> mKisiListesi;
    private List<SatisObject> mYedek;
    private ItemFilter mFilter = new ItemFilter();
    public SatisAdapter(Activity activity, List<SatisObject> satisObjects ) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = satisObjects;
        mYedek=satisObjects;
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
        LinearLayout back;
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
            viewHolder.back = (LinearLayout) rowView.findViewById(R.id.layout);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }

        final SatisObject satisObject = mKisiListesi.get(position);
        viewHolder.adSoyad.setText(satisObject.getAd()+" "+satisObject.getSoyad());
        viewHolder.tel.setText(satisObject.getTel());
        if (satisObject.getDays()>2 && satisObject.getDays()<4 && satisObject.getDurum() == 2)
            viewHolder.back.setBackgroundColor(0xFFFFAC40);
        else if (satisObject.getDays()>3 && satisObject.getDurum() == 2)
            viewHolder.back.setBackgroundColor(0xFFF8454E);
        else
            viewHolder.back.setBackgroundColor(0x00000000);
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

        if(satisObject.getDurum()==2){
            Picasso.with(context).load(R.drawable.durum2).into(viewHolder.durum);
            viewHolder.iptal.setVisibility(View.GONE);
        }

        else if(satisObject.getDurum()==3){
            Picasso.with(context).load(R.drawable.durum3).into(viewHolder.durum);
            viewHolder.iptal.setVisibility(View.GONE);
        }
        else if(satisObject.getDurum()==4){
            Picasso.with(context).load(R.drawable.durum4).into(viewHolder.durum);
            viewHolder.iptal.setVisibility(View.GONE);
        }
        viewHolder.tutar.setText("Tutar: "+satisObject.getTutar()+"₺");
        return rowView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mKisiListesi.clear();
        if (charText.length() == 0) {
            mKisiListesi.addAll(mYedek);
        } else {
            for (SatisObject wp : mYedek) {
                if ((wp.getAd()+" "+wp.getSoyad()).toLowerCase(Locale.getDefault())
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
                filterableString = list.get(i).getAd()+" "+list.get(i).getSoyad();
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
}
