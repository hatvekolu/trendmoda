package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.modayakamoz.lenovo_.trendmoda.Adapter.ParaAdapter;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.ParaDurumuObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParaDurumuFragment extends Fragment {

    ParaAdapter adapter;
    ListView para;
    List<ParaDurumuObject>paraDurumuObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_para_durumu, container, false);

        para=(ListView)view.findViewById(R.id.para);

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
        protected String doInBackground(String... values)
        {   paraDurumuObject=new ArrayList<ParaDurumuObject>();
            ReadURL readURL=new ReadURL();
            DBHelper dbHelper=new DBHelper(getContext());
            try{
                String data= readURL.readURL("http://modayakamoz.com/json/get_istek.php?ID="+dbHelper.getUser().getID());
                JSONArray array=new JSONArray(data);
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    paraDurumuObject.add(new ParaDurumuObject(Integer.parseInt(object.getString("ID")),Integer.parseInt(object.getString("komisyoncu_ID")),
                            Double.parseDouble(object.getString("tutar")),Integer.parseInt(object.getString("durum")),object.getString("tarih")));
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
                adapter=new ParaAdapter(getActivity(),paraDurumuObject,ParaDurumuFragment.this);
                para.setAdapter(adapter);
            }

            progress.dismiss();


        }
    }
    public void yenile(){
        new getData().execute();

    }
}
