package com.kos.util;

import android.content.Context;

public interface SaveLoad {
    void reciveArrays();

    void saveToDevice(Context context, Boolean isStoragePermissionGrantedRead);
    void loadFromDevice(Context context, Boolean isStoragePermissionGrantedRead);

    void saveToFireBase();
    void loadFromFireBase(Context context, Boolean isStoragePermissionGrantedRead);
}
