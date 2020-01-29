package com.modayakamoz.lenovo_.trendmoda.Object;


import java.util.List;

public class UrunObject {
    private int id;
    private int kod;
    private String aciklama,onsiparis;
    private double cikisTL;
    private List<ResimObject> resimObject;
    private List<BedenObject> bedenObject;

    public UrunObject(int id,int kod, String aciklama,String onsiparis,double cikisTL,List<ResimObject> resimObject,
                      List<BedenObject>bedenObject){
        this.id=id;
        this.kod=kod;
        this.aciklama=aciklama;
        this.onsiparis=onsiparis;
        this.cikisTL=cikisTL;
        this.resimObject=resimObject;
        this.bedenObject=bedenObject;
    }
    public int getId(){return id;}
    public int getKod(){return kod;}
    public String getAciklama(){return  aciklama;}
    public String getOnsiparis(){return  onsiparis;}
    public double getCikisTL(){return cikisTL;}
    public List<ResimObject> getResimObject(){return resimObject;}
    public List<BedenObject> getBedenObject(){return bedenObject;}


}
