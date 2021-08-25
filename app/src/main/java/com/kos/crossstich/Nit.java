package com.kos.crossstich;

import java.io.Serializable;
import java.util.ArrayList;

public class Nit implements Serializable {
    //private static final long serialVersionUID = -183666019952720577L;

    private static final long serialVersionUID = -7308384909568281638L;
    public static ArrayList<Nit> allNits = new ArrayList<>();

    public int sortNumber;
    public String firma;
    public String numberNit;
    public double lengthCurrent;
    public double lengthTotal;
    public double lengthNal;
    public double lengthOstatokt;
    public String nameStich;
    public String color;
    public int colorNumber;


    public Nit(int sortNumber, String firma, String numberNit, double lengthCurrent, double lengthTotal, double lengthNal, double lengthOstatokt, String nameStich, String color, int colorNumber) {

        this.sortNumber = sortNumber;
        this.firma = firma;
        this.numberNit = numberNit;
        this.lengthCurrent = lengthCurrent;
        this.lengthTotal = lengthTotal;
        this.lengthNal = lengthNal;
        this.lengthOstatokt = lengthOstatokt;
        this.nameStich = nameStich;
        this.color = color;
        this.colorNumber = colorNumber;
    }

    public String getNumberNit() {
        return numberNit;
    }
}
