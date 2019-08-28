package com.example.myapplication;

class LatLangdata {

    private int number;
    private double lat;
    private double lang;

    public int getnumber() {
        return number;
    }

    public void setnumber(String srnumber) {
        this.number = number;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "LatLangdata{" +
                "number=" + number +
                ", lat=" + lat +
                ", lang=" + lang +
                '}';
    }
}

