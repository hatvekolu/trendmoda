package com.modayakamoz.lenovo_.trendmoda.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.R;

import org.json.JSONObject;


public class ParaFragment extends DialogFragment {

    EditText tutar;
    Button istek;
    double para;
    public void setPara(double para){this.para=para;}
    private ProfilFragment profilFragment;
    public void setFragment(ProfilFragment profilFragment) {
        this.profilFragment = profilFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view= inflater.inflate(R.layout.fragment_para, container, false);

        tutar=(EditText)view.findViewById(R.id.editText4);
        istek=(Button)view.findViewById(R.id.button6);
        tutar.setText(""+(int)para);
        istek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tutar.getText().toString().length()>0){
                    if (Double.parseDouble(tutar.getText().toString())>para){
                        Toast.makeText(getContext(),"Lütfen bakiyenize göre tutar giriniz.",Toast.LENGTH_SHORT).show();
                    }
                    else if (Double.parseDouble(tutar.getText().toString())<=para&&Double.parseDouble(tutar.getText().toString())>0){
                        new getData().execute(tutar.getText().toString());
                    }
                }
                else
                    Toast.makeText(getContext(),"Lütfen tutar giriniz.",Toast.LENGTH_SHORT).show();

            }
        });

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
        {
            ReadURL readURL=new ReadURL();
            DBHelper dbHelper=new DBHelper(getContext());

            try{
                String gelenData=readURL.readURL("http://modayakamoz.com/json/istek_ekle.php?ID="+dbHelper.getUser().getID()+"&tutar="+values[0]);
                JSONObject object=new JSONObject(gelenData);

                return object.getString("durum");
            }
            catch (Exception e){
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results)
        {
            if (!results.equals("HATA")){
                profilFragment.yenile();
                if (results.equals("var"))
                    Toast.makeText(getContext(),"Bekleyen İsteğiniz Var!",Toast.LENGTH_SHORT).show();
                else if (results.equals("eklendi"))
                    Toast.makeText(getContext(),"Para İsteğiniz İletilmiştir.",Toast.LENGTH_SHORT).show();
                else if (results.equals("sınır"))
                    Toast.makeText(getContext(),"Aylık İstek Sınırına Ulaştınız!",Toast.LENGTH_SHORT).show();
                else if (results.equals("tutar"))
                    Toast.makeText(getContext(),"Limit Altı İstek Gönderemezsiniz!",Toast.LENGTH_SHORT).show();
                else if (results.equals("HATA"))
                    Toast.makeText(getContext(),"Beklenmeyen Bir Hata Oluştu!",Toast.LENGTH_SHORT).show();



                getDialog().dismiss();
            }
            progress.dismiss();


        }
    }

}
