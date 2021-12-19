package com.kos.util;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.kos.crosstrial.R;

public class Utils {
    public static int textColorOfFirm(Context context, String firm){
        if (firm.equals("dmc")) {
            return ContextCompat.getColor(context, R.color.anchor0216);
        }
        if (firm.equals("cxc")) {
            return ContextCompat.getColor(context, R.color.anchor0309);
        }
        if (firm.equals("pnk")) {
            return ContextCompat.getColor(context, R.color.anchor0410);
        }
        if (firm.equals("gamma")) {
            return ContextCompat.getColor(context, R.color.anchor1030);
        }
        if (firm.equals("anchor")) {
            return ContextCompat.getColor(context, R.color.dmc15);
        }
        if (firm.equals("kreinik")) {
            return ContextCompat.getColor(context, R.color.dmc961);
        }
        return 0;
    }
}
