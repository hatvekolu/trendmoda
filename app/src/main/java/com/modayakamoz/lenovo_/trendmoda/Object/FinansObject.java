package com.modayakamoz.lenovo_.trendmoda.Object;

/**
 * Created by Lenovo- on 29.11.2017.
 */

public class FinansObject {
    int yil,adet;
    String ay;
    public FinansObject(int yil, int adet,String ay){
        this.yil=yil;
        this.adet=adet;
        this.ay=ay;
    }

    public int getYil(){return  yil;}
    public int getAdet(){return  adet;}
    public String getAy(){return  ay;}

}
