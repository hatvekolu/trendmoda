package com.modayakamoz.lenovo_.trendmoda.Object;

import java.util.List;

/**
 * Created by Lenovo- on 28.11.2017.
 */

public class SepetObject {
    int id;
    private double tl;
    private List<com.modayakamoz.lenovo_.trendmoda.Object.ResimObject> resimObject;
    private com.modayakamoz.lenovo_.trendmoda.Object.BedenObject bedenObject;
    private String kod;
    public SepetObject(int id, double tl, List<com.modayakamoz.lenovo_.trendmoda.Object.ResimObject> resimObject, com.modayakamoz.lenovo_.trendmoda.Object.BedenObject bedenObject,String kod){
        this.id=id;
        this.tl=tl;
        this.resimObject=resimObject;
        this.bedenObject=bedenObject;
        this.kod = kod;
    }
    public int getId(){return id;}
    public double getTl(){return tl;}
    public List<com.modayakamoz.lenovo_.trendmoda.Object.ResimObject>getResimObject(){return resimObject;}
    public com.modayakamoz.lenovo_.trendmoda.Object.BedenObject getBedenObject(){return bedenObject;}
    public String getKod(){return kod;}
}
