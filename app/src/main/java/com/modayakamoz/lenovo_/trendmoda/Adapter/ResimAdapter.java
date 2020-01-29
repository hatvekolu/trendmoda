package com.modayakamoz.lenovo_.trendmoda.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.modayakamoz.lenovo_.trendmoda.Object.ResimObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 1.12.2017.
 */

public class ResimAdapter  extends BaseAdapter{
    String extension;
    private LayoutInflater mInflater;
    private Context context;
    private List<ResimObject> mKisiListesi;
    public ResimAdapter(Activity activity, List<ResimObject> resimObjects ) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = resimObjects;
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
        satirView = mInflater.inflate(R.layout.resim_grid_item, null);
        final ResimObject resimObject = mKisiListesi.get(position);
        ImageView resim=(ImageView)satirView.findViewById(R.id.imageView13);

        extension = resimObject.getB_resim().substring(resimObject.getB_resim().lastIndexOf("."));
        if (!extension.toLowerCase().equals(".mp4")){
            try{
                Picasso.with(context).load("http://modayakamoz.com/resimler_k/"+resimObject.getB_resim()).into(resim);
            }
            catch (Exception e){

            }
        }
        else {
            Picasso.with(context).load(R.drawable.images).into(resim);
        }
        return satirView;
    }
}
