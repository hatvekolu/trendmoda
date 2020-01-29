package com.modayakamoz.lenovo_.trendmoda.Object;

/**
 * Created by Lenovo- on 31.7.2019.
 */

public class InfoObject {
    private String id,tel,aciklama,tag,mesaj,link;
    public InfoObject(String id,String tel, String aciklama, String tag, String mesaj, String link){

        this.id=id;
        this.tel=tel;
        this.aciklama=aciklama;
        this.tag=tag;
        this.mesaj=mesaj;
        this.link=link;

    }
    public  String getId(){return  id;}
    public  String getTel(){return  tel;}
    public  String getAciklama(){return  aciklama;}
    public  String getTag(){return  tag;}
    public  String getMesaj(){return  mesaj;}
    public  String getLink(){return  link;}

}
