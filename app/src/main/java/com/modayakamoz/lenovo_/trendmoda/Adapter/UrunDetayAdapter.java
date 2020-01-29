package com.modayakamoz.lenovo_.trendmoda.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.modayakamoz.lenovo_.trendmoda.Fragment.SepetFragment;
import com.modayakamoz.lenovo_.trendmoda.Object.SatisUrunObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by Lenovo- on 29.11.2017.
 */

public class UrunDetayAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<SatisUrunObject> mKisiListesi;
    private SepetFragment sepetFragment;
    public UrunDetayAdapter(Activity activity, List<SatisUrunObject> sepetObjects) {
        //XML'i alıp View'a çevirecek inflater'ı örnekleyelim
        context = activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        //gösterilecek listeyi de alalım
        mKisiListesi = sepetObjects;
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
        final SatisUrunObject satisUrunObject = mKisiListesi.get(position);
        ImageView resim=(ImageView)satirView.findViewById(R.id.imageView11);
        TextView tutar=(TextView)satirView.findViewById(R.id.textView31);
        TextView beden=(TextView)satirView.findViewById(R.id.textView32);
        TextView adet=(TextView)satirView.findViewById(R.id.textView33);
        ImageView sil=(ImageView)satirView.findViewById(R.id.imageView12);
        TextView kod = (TextView)satirView.findViewById(R.id.textView49);
        if(satisUrunObject.getResimObject().size()>0)
            Picasso.with(context).load("http://modayakamoz.com/resimler_k/"+satisUrunObject.getResimObject().get(0).getB_resim()).into(resim);
        else
            Picasso.with(context).load(R.drawable.yukleme).into(resim);


        tutar.setText("Tutar: "+satisUrunObject.getTl()+ " ₺");
        beden.setText("Beden: "+satisUrunObject.getBedenObject().getBeden());
        adet.setText("Adet: "+satisUrunObject.getBedenObject().getAdet());
        sil.setVisibility(View.GONE);
        kod.setText("Kod: "+ satisUrunObject.getKod());


        return satirView;
    }
}
