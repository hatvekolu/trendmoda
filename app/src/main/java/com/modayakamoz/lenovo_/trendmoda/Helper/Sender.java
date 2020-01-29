package com.modayakamoz.lenovo_.trendmoda.Helper;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

/**
 * Created by Lenovo- on 14.1.2018.
 */

public class Sender extends AsyncTask<Void,Void,String> {
    Context c;
    String urlAddress,id,tur;
    EditText ad,soyad,tel,il,ilce,adres,aciklama;
    String name,lastname,phone,city,country,address,explain;
    /*
            1.OUR CONSTRUCTOR
    2.RECEIVE CONTEXT,URL ADDRESS AND EDITTEXTS FROM OUR MAINACTIVITY
    */
    public Sender(Context c, String urlAddress,String id,String tur,EditText...editTexts) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.id = id;
        this.tur = tur;
        //INPUT EDITTEXTS
        this.ad=editTexts[0];
        this.soyad=editTexts[1];
        this.tel=editTexts[2];
        this.il=editTexts[3];
        this.ilce=editTexts[4];
        this.adres=editTexts[5];
        this.aciklama=editTexts[6];
        //GET TEXTS FROM EDITEXTS
        name=ad.getText().toString().replaceAll("'","");
        lastname=soyad.getText().toString().replaceAll("'","");
        phone=tel.getText().toString().replaceAll("'","");
        city=il.getText().toString().replaceAll("'","");
        country=ilce.getText().toString().replaceAll("'","");
        address=adres.getText().toString().replaceAll("'","");
        explain=aciklama.getText().toString().replaceAll("'","");


    }
    /*
   1.SHOW PROGRESS DIALOG WHILE DOWNLOADING DATA
    */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    /*
    1.WHERE WE SEND DATA TO NETWORK
    2.RETURNS FOR US A STRING
     */
    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }
    /*
  1. CALLED WHEN JOB IS OVER
  2. WE DISMISS OUR PD
  3.RECEIVE A STRING FROM DOINBACKGROUND
   */
    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        if(response != null)
        {
            //SUCCESS

        }else
        {
            //NO SUCCESS
            Toast.makeText(c,"Hata ",Toast.LENGTH_LONG).show();
        }
    }
    /*
    SEND DATA OVER THE NETWORK
    RECEIVE AND RETURN A RESPONSE
     */
    private String send()
    {
        //CONNECT
        HttpURLConnection con=Connector.connect(urlAddress);
        if(con==null)
        {
            return null;
        }
        try
        {
            OutputStream os=con.getOutputStream();
            //WRITE
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            bw.write(new DataPackager(name,lastname,phone,city,country,address,explain,id,tur).packData());
            bw.flush();
            //RELEASE RES
            bw.close();
            os.close();
            //HAS IT BEEN SUCCESSFUL?
            int responseCode=con.getResponseCode();
            if(responseCode==con.HTTP_OK)
            {
                //GET EXACT RESPONSE
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response=new StringBuffer();
                String line;
                //READ LINE BY LINE
                while ((line=br.readLine()) != null)
                {
                    response.append(line);
                }
                //RELEASE RES
                br.close();
                return response.toString();
            }else
            {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
