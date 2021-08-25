package com.kos.crosstrial.items;

import java.io.Serializable;

public class FabricItem implements Serializable {
    private static final long serialVersionUID = -3126551896749192351L;
    int id;
    String firmFabric;
    String nameFabric;
    String articulFabric;
    String kauntFabric;
    String colorFabric;
    String myNumberFabric;

    public FabricItem(int id, String firmFabric, String nameFabric, String articulFabric, String kauntFabric, String colorFabric, String myNumberFabric) {
        this.id = id;
        this.firmFabric = firmFabric;
        this.nameFabric = nameFabric;
        this.articulFabric = articulFabric;
        this.kauntFabric = kauntFabric;
        this.colorFabric = colorFabric;
        this.myNumberFabric = myNumberFabric;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirmFabric() {
        return firmFabric;
    }

    public void setFirmFabric(String firmFabric) {
        this.firmFabric = firmFabric;
    }

    public String getNameFabric() {
        return nameFabric;
    }

    public void setNameFabric(String nameFabric) {
        this.nameFabric = nameFabric;
    }

    public String getArticulFabric() {
        return articulFabric;
    }

    public void setArticulFabric(String articulFabric) {
        this.articulFabric = articulFabric;
    }

    public String getKauntFabric() {
        return kauntFabric;
    }

    public void setKauntFabric(String kauntFabric) {
        this.kauntFabric = kauntFabric;
    }

    public String getColorFabric() {
        return colorFabric;
    }

    public void setColorFabric(String colorFabric) {
        this.colorFabric = colorFabric;
    }

    public String getMyNumberFabric() {
        return myNumberFabric;
    }

    public void setMyNumberFabric(String myNumberFabric) {
        this.myNumberFabric = myNumberFabric;
    }
}