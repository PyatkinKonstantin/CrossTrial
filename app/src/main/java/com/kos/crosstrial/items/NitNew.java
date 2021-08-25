package com.kos.crosstrial.items;

import java.io.Serializable;

public class NitNew implements Serializable {
    private static final long serialVersionUID = -183666019952720578L;
    private int idNit;
    private String firm;
    private String numberNit;
    private double lengthCurrent;
    private double lengthTotal;
    private double lengthNal;
    private double lengthOstatok;
    private String nameStitch;
    private String colorName;
    private int colorNumber;

    public NitNew(int idNit, String firm, String numberNit, double lengthCurrent, double lengthTotal, double lengthNal, double lengthOstatok, String nameStitch, String colorName, int colorNumber) {
        this.idNit = idNit;
        this.firm = firm;
        this.numberNit = numberNit;
        this.lengthCurrent = lengthCurrent;
        this.lengthTotal = lengthTotal;
        this.lengthNal = lengthNal;
        this.lengthOstatok = lengthOstatok;
        this.nameStitch = nameStitch;
        this.colorName = colorName;
        this.colorNumber = colorNumber;
    }

    public int getIdNit() {
        return idNit;
    }

    public void setIdNit(int idNit) {
        this.idNit = idNit;
    }

    public String getNumberNit() {
        return numberNit;
    }

    public void setNumberNit(String numberNit) {
        this.numberNit = numberNit;
    }

    public int getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(int colorNumber) {
        this.colorNumber = colorNumber;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public String getNameStitch() {
        return nameStitch;
    }

    public void setNameStitch(String nameStitch) {
        this.nameStitch = nameStitch;
    }

    public double getLengthCurrent() {
        return lengthCurrent;
    }

    public void setLengthCurrent(double lengthCurrent) {
        this.lengthCurrent = lengthCurrent;
    }

    public double getLengthTotal() {
        return lengthTotal;
    }

    public void setLengthTotal(double lengthTotal) {
        this.lengthTotal = lengthTotal;
    }

    public double getLengthNal() {
        return lengthNal;
    }

    public void setLengthNal(double lengthNal) {
        this.lengthNal = lengthNal;
    }

    public double getLengthOstatok() {
        return lengthOstatok;
    }

    public void setLengthOstatok(double lengthOstatok) {
        this.lengthOstatok = lengthOstatok;
    }
}
