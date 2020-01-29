package com.modayakamoz.lenovo_.trendmoda.Object;

/**
 * Created by Lenovo- on 20.11.2017.
 */

public class ProfilObject {
    private String ad, soyad,kullaniciadi, telNo,bankaAdi,iban,sifre,version;
    private double bakiye,yuzde;
    private int bekleyen,yolda,ulasan,donen;
    public ProfilObject(String ad,String soyad,String kullaniciadi, String telNo,double bakiye,double yuzde,String bankaAdi,String iban,
                        String sifre,String version,int bekleyen,int yolda,int ulasan,int donen)
    {   this.ad = ad;
        this.soyad = soyad;
        this.kullaniciadi = kullaniciadi;
        this.telNo = telNo;
        this.bakiye = bakiye;
        this.yuzde = yuzde;
        this.bankaAdi = bankaAdi;
        this.iban = iban;
        this.sifre = sifre;
        this.version = version;
        this.bekleyen=bekleyen;
        this.yolda=yolda;
        this.ulasan=ulasan;
        this.donen=donen;
    }
    public String getAd() {return ad;}
    public String getSoyad() {return soyad;}
    public String getKullaniciadi() {return kullaniciadi;}
    public String getTelNo(){return telNo;}
    public double getBakiye(){return bakiye;}
    public double getYuzde(){return yuzde;}
    public String getBankaAdi(){return bankaAdi;}
    public String getIban(){return iban;}
    public String getSifre(){return sifre;}
    public String getVersion(){return version;}
    public int getBekleyen(){return bekleyen;}
    public int getYolda(){return yolda;}
    public int getUlasan(){return ulasan;}
    public int getDonen(){return donen;}
    public void setAd(String ad){ this.ad = ad;}
    public void setSoyad(String soyad){ this.kullaniciadi = soyad;}
    public void setKullaniciadi(String kullaniciadi){ this.kullaniciadi = kullaniciadi;}
    public void setTelNo(String telNo){ this.telNo = telNo;}
    public void setBakiye(double bakiye){ this.bakiye = bakiye;}
    public void setYuzde(double yuzde){ this.yuzde = yuzde;}
    public void setBankaAdi(String bankaAdi){ this.bankaAdi = bankaAdi;}
    public void setIban(String iban){ this.iban = iban;}
    public void setBekleyen(int bekleyen){ this.bekleyen = bekleyen;}
    public void setYolda(int yolda){ this.yolda = yolda;}
    public void setUlasan(int ulasan){ this.ulasan = ulasan;}
    public void setDonen(int donen){ this.donen = donen;}
}
