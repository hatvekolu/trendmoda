package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.AylarObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinansFragment extends Fragment {
    List<AylarObject> aylarObject;

    BarChart mBarChart;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_finans, container, false);
        mBarChart = (BarChart)view.findViewById(R.id.barchart);

        mBarChart.startAnimation();
        new getData().execute();
        return view;
    }
    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values) {
            aylarObject = new ArrayList<AylarObject>();
            DBHelper dbhelper=new DBHelper(getContext());
            ReadURL readURL = new ReadURL();
            try {
                String gelenData = readURL.readURL("http://modayakamoz.com/json/get_komisyoncu_satis_aylar.php?ID="+dbhelper.getUser().getID());
                JSONArray array = new JSONArray(gelenData);


                for (int i = 0; i < array.length(); i++) {
                    JSONObject object1 = array.getJSONObject(i);
                    aylarObject.add(new AylarObject(object1.getInt("yil"), object1.getString("ay"),
                            object1.getInt("adet")));
                }

                return "0";
            } catch (Exception e) {
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results) {
            if (!results.equals("HATA")) {

                for (int i = 0; i < aylarObject.size(); i++) {
                    mBarChart.addBar(new BarModel(aylarObject.get(i).getAy(), Float.parseFloat("" + aylarObject.get(i).getAdet()), 0xFFFF4081));
                }
                mBarChart.startAnimation();
            }
            progress.dismiss();

        }
    }

}
