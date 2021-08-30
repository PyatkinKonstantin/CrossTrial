package com.kos.util;

import android.content.Context;

public interface SaveLoad {
    void reciveArrays();

    void saveToDevice(Context context, Boolean isStoragePermissionGrantedRead, Boolean isStoragePermissionGrantedWrite);
    void loadFromDevice(Context context, Boolean isStoragePermissionGrantedRead, Boolean isStoragePermissionGrantedWrite);

    void saveToFireBase();
    void loadFromFireBase(Context context, Boolean isStoragePermissionGrantedRead, Boolean isStoragePermissionGrantedWrite);
}
