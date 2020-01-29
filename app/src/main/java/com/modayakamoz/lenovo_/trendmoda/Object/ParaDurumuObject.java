package com.modayakamoz.lenovo_.trendmoda.Object;

/**
 * Created by Lenovo- on 27.11.2017.
 */

public class ParaDurumuObject {
    private int id,komisyoncu_ID,durum;
    private double tutar;
    private String tarih;
    public ParaDurumuObject(int id,int komisyoncu_ID,double tutar,int durum,String tarih){
        this.id=id;
        this.komisyoncu_ID=komisyoncu_ID;
        this.tutar=tutar;
        this.durum=durum;
        this.tarih=tarih;
    }

    public int getId(){return  id;}
    public int getKomisyoncu_ID(){return  komisyoncu_ID;}
    public double getTutar(){return  tutar;}
    public int getDurum(){return  durum;}
    public String getTarih(){return  tarih;}
}
