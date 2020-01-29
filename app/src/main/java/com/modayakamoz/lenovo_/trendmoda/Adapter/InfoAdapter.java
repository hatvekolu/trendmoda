package com.modayakamoz.lenovo_.trendmoda.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Object.InfoObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import java.util.List;

/**
 * Created by Lenovo- on 31.7.2019.
 */

public class InfoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<InfoObject> infoObject;
    public InfoAdapter(Activity activity, List<InfoObject> infoObject1 ) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        infoObject = infoObject1;
    }
    @Override
    public int getCount() {
        return infoObject.size();
    }

    @Override
    public Object getItem(int position) {
        return infoObject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View satirView;
        satirView = mInflater.inflate(R.layout.row_people, null);
        final InfoObject paraDurumuObject = infoObject.get(position);
        TextView aciklama=(TextView)satirView.findViewById(R.id.lbl_name);

        aciklama.setText(paraDurumuObject.getTag());
        return satirView;
    }

}
