package com.kos.crosstrial.items;

import java.io.Serializable;

public class Cut implements Serializable {
    private static final long serialVersionUID = -8859442104057640388L;
    int idCut;
    String nameFabricCut;
    String firmFabricCut;
    String articul;
    int lengthCut;
    int widthCut;

    public Cut(int idCut, String nameFabricCut, String firmFabricCut, String articul, int lengthCut, int widthCut) {
        this.idCut = idCut;
        this.nameFabricCut = nameFabricCut;
        this.firmFabricCut = firmFabricCut;
        this.articul = articul;
        this.lengthCut = lengthCut;
        this.widthCut = widthCut;
    }

    public int getIdCut() {
        return idCut;
    }

    public void setIdCut(int idCut) {
        this.idCut = idCut;
    }

    public String getNameFabricCut() {
        return nameFabricCut;
    }

    public void setNameFabricCut(String nameFabricCut) {
        this.nameFabricCut = nameFabricCut;
    }

    public String getFirmFabricCut() {
        return firmFabricCut;
    }

    public void setFirmFabricCut(String firmFabricCut) {
        this.firmFabricCut = firmFabricCut;
    }

    public String getArticul() {
        return articul;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public int getLengthCut() {
        return lengthCut;
    }

    public void setLengthCut(int lengthCut) {
        this.lengthCut = lengthCut;
    }

    public int getWidthCut() {
        return widthCut;
    }

    public void setWidthCut(int widthCut) {
        this.widthCut = widthCut;
    }
}
