package com.modayakamoz.lenovo_.trendmoda.Object;



public class AliciObject {
    private String ad,soyad,tel,il,ilce,adres,ulasan,donen;
    private int yuzde, toplam;
    public AliciObject(String ad,String soyad, String tel, String il, String ilce, String adres, int yuzde, int toplam, String ulasan, String donen){

        this.ad=ad;
        this.soyad=soyad;
        this.tel=tel;
        this.il=il;
        this.ilce=ilce;
        this.adres=adres;
        this.yuzde=yuzde;
        this.toplam=toplam;
        this.ulasan=ulasan;
        this.donen=donen;

    }
    public  String getAd(){return  ad;}
    public  String getSoyad(){return  soyad;}
    public  String getTel(){return  tel;}
    public  String getIl(){return  il;}
    public  String getIlce(){return  ilce;}
    public  String getAdres(){return  adres;}
    public  int getYuzde(){return  yuzde;}
    public  int getToplam(){return  toplam;}
    public  String getUlasan(){return  ulasan;}
    public  String getDonen(){return  donen;}

}
