package com.firman.zodiaq.model;

/**
 * Created by Firman on 3/22/2017.
 */

public class MsgBusPesan {

    private int kode;
    private String mStringPesan;

    private boolean dataAda;

    public MsgBusPesan() {

    }

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getStringPesan(){
        return mStringPesan;
    }

    public void setStringPesan(String stringPesan){
        mStringPesan = stringPesan;
    }

    public boolean isDataAda() {
        return dataAda;
    }

    public void setDataAda(boolean dataAda){
        this.dataAda = dataAda;
    }
}
