package com.modayakamoz.lenovo_.trendmoda.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Object.BedenObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import java.util.List;

/**
 * Created by Lenovo- on 1.12.2017.
 */

public class BedenAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<BedenObject> mKisiListesi;
    public BedenAdapter(Activity activity, List<BedenObject> bedenObjects ) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = bedenObjects;
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
        satirView = mInflater.inflate(R.layout.beden_grid_item, null);
        final BedenObject bedenObject = mKisiListesi.get(position);
        TextView beden=(TextView)satirView.findViewById(R.id.textView30);
        if (position == 0 && mKisiListesi.size() == 1){
            beden.setTextColor(Color.WHITE);
            beden.setBackgroundResource(R.drawable.grid_both);
        }
        else if(position == 0) {
            beden.setTextColor(Color.WHITE);
            beden.setBackgroundResource(R.drawable.grid_left_renk);
        }

        else if (position == (mKisiListesi.size()-1))
            beden.setBackgroundResource(R.drawable.grid_right);
        beden.setText(bedenObject.getBeden());


        return satirView;
    }
}
