package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.modayakamoz.lenovo_.trendmoda.Adapter.InfoAdapter;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.InfoObject;
import com.modayakamoz.lenovo_.trendmoda.Object.ResimObject;
import com.modayakamoz.lenovo_.trendmoda.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class InfoFragment extends Fragment {
    InfoAdapter adapter;
    ListView listView;
    List<InfoObject> infoObject;
    ResimObject resimObject;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        listView = (ListView)view.findViewById(R.id.infoList);
        resimObject = new ResimObject("beden.jpeg","beden.jpeg");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    String url = infoObject.get(position).getLink();
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(infoObject.get(position).getAciklama()).setPositiveButton("Devam", dialogClickListener)
                            .setNegativeButton("Vazgeç", dialogClickListener).show();

                //display value here
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
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values)
        {   infoObject=new ArrayList<InfoObject>();
            ReadURL readURL=new ReadURL();
            DBHelper dbHelper=new DBHelper(getContext());
            try{

                String data= readURL.readURL("http://modayakamoz.com/json/get_info.php?ID="+dbHelper.getUser().getID());
                JSONArray array=new JSONArray(data);
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    infoObject.add(new InfoObject(object.getString("ID"),object.getString("tel"),
                            object.getString("aciklama"),object.getString("tag"),object.getString("mesaj"),object.getString("link")));
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
                adapter=new InfoAdapter(getActivity(),infoObject);
                listView.setAdapter(adapter);
            }

            progress.dismiss();


        }
    }


}
