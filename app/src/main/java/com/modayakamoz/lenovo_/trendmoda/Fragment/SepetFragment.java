package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.modayakamoz.lenovo_.trendmoda.Activity.MainActivity;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.BedenObject;
import com.modayakamoz.lenovo_.trendmoda.Object.ResimObject;
import com.modayakamoz.lenovo_.trendmoda.Object.SepetObject;
import com.modayakamoz.lenovo_.trendmoda.R;
import com.modayakamoz.lenovo_.trendmoda.Adapter.SepetAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SepetFragment extends Fragment {
    List<SepetObject>sepetObject;
    List<ResimObject>resimObject;
    BedenObject bedenObject;
    SepetAdapter adapter;
    ListView sepet;
    TextView toplam;
    Button tamamla;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_sepet, container, false);
        sepet=(ListView)view.findViewById(R.id.sepet);
        toplam=(TextView)view.findViewById(R.id.textView41);
        tamamla=(Button)view.findViewById(R.id.button5);

        tamamla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sepetObject.size()>0){
                    FragmentManager fm = getFragmentManager();
                    SatisTamamlaFragment alertDialog = new SatisTamamlaFragment();
                    alertDialog.setFragment(SepetFragment.this);
                    alertDialog.show(fm,"fragment_alert");
                }
                else
                    Toast.makeText(getContext(),"Lütfen Sepete Ürün Ekleyin.",Toast.LENGTH_SHORT).show();


            }
        });



        new getData().execute();
        return view;
    }
    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getFragmentManager();
            progress = new com.modayakamoz.lenovo_.trendmoda.Fragment.YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {   sepetObject=new ArrayList<SepetObject>();
            sepetObject.clear();
            DBHelper dbHelper=new DBHelper(getContext());
            ReadURL readURL=new ReadURL();
            try{
                String data= readURL.readURL("http://modayakamoz.com/json/get_sepet.php?ID="+dbHelper.getUser().getID());
                JSONArray array=new JSONArray(data);
                for (int i=0;i<array.length();i++){
                    resimObject=new ArrayList<ResimObject>();
                    JSONObject object=array.getJSONObject(i);
                    JSONArray resimler=object.getJSONArray("resimler");
                    JSONArray bedenler=object.getJSONArray("beden");
                    for (int j=0;j<resimler.length();j++){
                        JSONObject resimobj=resimler.getJSONObject(j);
                        resimObject.add(new ResimObject(resimobj.getString("b_resim"),resimobj.getString("k_resim")));
                    }
                    for (int j=0;j<bedenler.length();j++){
                        JSONObject bedenobj=bedenler.getJSONObject(j);
                        bedenObject=(new BedenObject(bedenobj.getString("beden"),Integer.parseInt(bedenobj.getString("adet"))));
                    }
                    sepetObject.add(new SepetObject(Integer.parseInt(object.getString("id")),Double.parseDouble(object.getString("cikisTL")),
                            resimObject,bedenObject,object.getString("kod")));

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
                if (sepetObject.size()>0){
                    double top=0;
                    for (int i=0;i<sepetObject.size();i++){
                        top=top+(sepetObject.get(i).getTl()*sepetObject.get(i).getBedenObject().getAdet());
                    }
                    toplam.setText("Toplam Tutar: "+String.format("%.2f", top)+" ₺");
                    ((MainActivity)getActivity()).sepetAdet.setText(""+sepetObject.size());
                    adapter=new SepetAdapter(getActivity(),sepetObject,SepetFragment.this);
                    sepet.setAdapter(adapter);
                }
                else{
                    double top=0;
                    toplam.setText("Toplam Tutar: "+String.format("%.2f", top)+" ₺");
                    ((MainActivity)getActivity()).sepetAdet.setText(""+sepetObject.size());
                    adapter=new SepetAdapter(getActivity(),sepetObject,SepetFragment.this);
                    sepet.setAdapter(adapter);
                }



            }

            progress.dismiss();


        }
    }
   public  void yenile(){
       new getData().execute();
   }

}
