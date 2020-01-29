package com.modayakamoz.lenovo_.trendmoda.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by Lenovo- on 14.1.2018.
 */

public class DataPackager {

    String name,lastname,phone,city,country,address,explain,id,tur;
    /*
    SECTION 1.RECEIVE ALL DATA WE WANNA SEND
     */
    public DataPackager(String name,String lastname,String phone,String city,String country,String address,String explain,String id,String tur) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.city = city;
        this.country = country;
        this.address = address;
        this.explain = explain;
        this.id = id;
        this.tur = tur;
    }
    /*
   SECTION 2
   1.PACK THEM INTO A JSON OBJECT
   2. READ ALL THIS DATA AND ENCODE IT INTO A FROMAT THAT CAN BE SENT VIA NETWORK
    */
    public String packData()
    {
        JSONObject jo=new JSONObject();
        StringBuffer packedData=new StringBuffer();
        try
        {
            jo.put("ad",name);
            jo.put("soyad",lastname);
            jo.put("tel",phone);
            jo.put("il",city);
            jo.put("ilce",country);
            jo.put("adres",address);
            jo.put("aciklama",explain);
            jo.put("komisyoncu_ID",id);
            jo.put("odeme_turu",tur);
            Boolean firstValue=true;
            Iterator it=jo.keys();
            do {
                String key=it.next().toString();
                String value=jo.get(key).toString();
                if(firstValue)
                {
                    firstValue=false;
                }else
                {
                    packedData.append("&");
                }
                packedData.append(URLEncoder.encode(key,"UTF-8"));
                packedData.append("=");
                packedData.append(URLEncoder.encode(value,"UTF-8"));
            }while (it.hasNext());
            return packedData.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
