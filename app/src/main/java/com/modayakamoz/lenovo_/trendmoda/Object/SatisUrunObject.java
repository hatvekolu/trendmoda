package com.modayakamoz.lenovo_.trendmoda.Object;

import java.util.List;

/**
 * Created by Lenovo- on 27.11.2017.
 */

public class SatisUrunObject {
    private double tl;
    private String aciklama;
    private List<ResimObject>resimObject;
    private BedenObject bedenObject;
    private String kod;
    public SatisUrunObject(double tl,String aciklama,List<ResimObject> resimObject,BedenObject bedenObject,String kod){
        this.tl=tl;
        this.aciklama=aciklama;
        this.resimObject=resimObject;
        this.bedenObject=bedenObject;
        this.kod = kod;
    }
    public double getTl(){return tl;}
    public String getAciklama(){return  aciklama;}
    public List<ResimObject>getResimObject(){return resimObject;}
    public BedenObject getBedenObject(){return bedenObject;}
    public String getKod(){return kod;}
}
