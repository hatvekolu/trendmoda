package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.modayakamoz.lenovo_.trendmoda.Activity.SatisDetayActivity;
import com.modayakamoz.lenovo_.trendmoda.Adapter.DegisimAdapter;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.BedenObject;
import com.modayakamoz.lenovo_.trendmoda.Object.ResimObject;
import com.modayakamoz.lenovo_.trendmoda.Object.SatisObject;
import com.modayakamoz.lenovo_.trendmoda.Object.SatisUrunObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DegisimDonen extends Fragment {

    List<SatisObject> satisObject;
    List<ResimObject>resimObject;
    BedenObject bedenObject;
    List<SatisUrunObject>satisUrunObject;
    DegisimAdapter adapter;
    EditText arama;
    ListView degisim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_degisim_bekleyen, container, false);

        arama=(EditText)view.findViewById(R.id.editText12);
        degisim=(ListView)view.findViewById(R.id.degisim);

        degisim.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                arama.setText("");
                SatisObject data= (SatisObject) adapter.getItem(i);
                Intent intent = new Intent(getActivity(), SatisDetayActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                SatisDetayActivity.so = data;
                SatisDetayActivity.degisimBekleyen = null;
                startActivity(intent);

            }


        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    getActivity().finish();
                }
                return false;
            }
        } );

        new getData().execute();


        return view;
    }
    public class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {
            satisObject=new ArrayList<SatisObject>();
            DBHelper dbhelper=new DBHelper(getContext());
            ReadURL readURL=new ReadURL();
            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/json/get_degisim.php?ID="+dbhelper.getUser().getID() + "&durum=4");
                JSONArray array=new JSONArray(gelenData);
                if (array.length()==0)
                    return "HATA";
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    JSONArray urunler=object.getJSONArray("urunler");

                    satisUrunObject=new ArrayList<SatisUrunObject>();
                    for (int j=0;j<urunler.length();j++){
                        resimObject=new ArrayList<ResimObject>();
                        JSONObject object1=urunler.getJSONObject(j);
                        JSONArray resimler=object1.getJSONArray("resimler");
                        JSONArray bedenler=object1.getJSONArray("beden");
                        for (int k=0;k<resimler.length();k++){
                            JSONObject resim= resimler.getJSONObject(k);
                            resimObject.add(new ResimObject(resim.getString("b_resim"),resim.getString("k_resim")));
                        }
                        for (int l=0;l<bedenler.length();l++){
                            JSONObject beden=bedenler.getJSONObject(l);
                            bedenObject=(new BedenObject(beden.getString("beden"),Integer.parseInt(beden.getString("adet"))));
                        }
                        satisUrunObject.add(new SatisUrunObject(Double.parseDouble(object1.getString("cikisTL")),object1.getString("aciklama"),
                                resimObject,bedenObject, object1.getString("kod")));
                    }
                    satisObject.add(new SatisObject(Integer.parseInt(object.getString("id")),Double.parseDouble(object.getString("tutar")),
                            Double.parseDouble(object.getString("kesilen_tutar")),4,0,Integer.parseInt(object.getString("odeme_int")),Double.parseDouble(object.getString("hakedis")),
                            object.getString("ad"),object.getString("soyad"),
                            object.getString("tel"),object.getString("il"),object.getString("ilce"),object.getString("adres"),
                            object.getString("aciklama"),object.getString("kargo_no"),object.getString("kargo_firma"),object.getString("tarih"),
                            object.getString("odeme_turu"),object.getString("url"),satisUrunObject));
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
                adapter=new DegisimAdapter(getActivity(),satisObject);
                degisim.setAdapter(adapter);
                arama.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        System.out.println("Text ["+s+"]");

                        adapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
            else {

            }

            progress.dismiss();

        }
    }
}
