package com.kos.crosstrial.items;

import java.io.Serializable;

public class StitchItem implements Serializable {
    private static final long serialVersionUID = -183666019952720579L;
    private String stitchName;
    private String stitchNote;
    private int stitchId;

    public String getStitchName() {
        return stitchName;
    }

    public void setStitchName(String stitchName) {
        this.stitchName = stitchName;
    }

    public String getStitchNote() {
        return stitchNote;
    }

    public void setStitchNote(String stitchNote) {
        this.stitchNote = stitchNote;
    }

    public int getStitchId() {
        return stitchId;
    }

    public void setStitchId(int stitchId) {
        this.stitchId = stitchId;
    }
}
