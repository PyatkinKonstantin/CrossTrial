package com.kos.util;

import android.content.Context;

public interface SaveLoad {
    void saveToDevice(Context context);
    void loadFromDevice(Context context);
    void loadFromFireBase(Context context);
}
