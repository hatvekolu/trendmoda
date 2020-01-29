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
 * Created by Lenovo- on 28.1.2020.
 */

public class OdemeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<BedenObject> mKisiListesi;
    int pos;
    public OdemeAdapter(Activity activity, List<BedenObject> bedenObjects, int yer ) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = bedenObjects;
        pos = yer;
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
        if(position == 0){
            beden.setBackgroundResource(R.drawable.grid_left);
        }
        else if (position == 2 ){
            beden.setBackgroundResource(R.drawable.grid_right);
        }
        if (position == (pos-1)) {
            beden.setTextColor(Color.WHITE);
            beden.setBackgroundColor(0xFFFF4081);
        }


        beden.setText(bedenObject.getBeden());

        return satirView;
    }

}
