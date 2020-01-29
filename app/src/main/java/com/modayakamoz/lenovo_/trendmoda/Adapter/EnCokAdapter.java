package com.modayakamoz.lenovo_.trendmoda.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Object.EnCokUrunObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 15.1.2019.
 */

public class EnCokAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<EnCokUrunObject> mKisiListesi;
    private List<EnCokUrunObject> mYedek;
    public EnCokAdapter(Activity activity, List<EnCokUrunObject> urunObjects) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = urunObjects;
        mYedek=urunObjects;
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
        ImageView resim;
        ImageView tukendi;
        TextView kod;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if(rowView==null)
        {
            rowView = mInflater.inflate(R.layout.en_cok_item,null);
            viewHolder = new ViewHolder();
            viewHolder.resim = (ImageView) rowView.findViewById(R.id.imageView9);
            viewHolder.tukendi = (ImageView) rowView.findViewById(R.id.imageView16);
            viewHolder.kod = (TextView) rowView.findViewById(R.id.textView21);
            rowView.setTag(viewHolder);
        }
        else {
            viewHolder  = (ViewHolder) rowView.getTag();
        }

        final EnCokUrunObject uo = mKisiListesi.get(position);


        String extension = uo.getResimObject().get(0).getB_resim().substring(uo.getResimObject().get(0).getB_resim().lastIndexOf("."));
        if (extension.toLowerCase().equals(".mp4"))    {
            Picasso.with(context).load(R.drawable.images).into(viewHolder.resim);
        }
        else {
            if(uo.getResimObject().size()>0)
                Picasso.with(context).load("http://modayakamoz.com/resimler_k/"+uo.getResimObject().get(0).getB_resim()).into(viewHolder.resim);
            else
                Picasso.with(context).load(R.drawable.yukleme).into(viewHolder.resim);
        }


        viewHolder.kod.setText("Ürün Kodu: "+uo.getKod());

        if (uo.getBedenObjects().size() == 0)
            viewHolder.tukendi.setVisibility(View.VISIBLE);
        else
            viewHolder.tukendi.setVisibility(View.GONE);
        return rowView;
    }
}
