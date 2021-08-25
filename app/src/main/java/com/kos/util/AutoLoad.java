package com.kos.util;

import android.app.Activity;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.kos.crossstich.Nit;
import com.kos.crosstrial.activityes.SaveLoadActivity;
import com.kos.crosstrial.db.DbManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class AutoLoad extends AppCompatActivity {

    ArrayList<Nit> recoveryNit;
    ArrayList<String> allCrossStich;
    ArrayList<Nit> allNits;

    public void autoloadOldVersion(DbManager dbManager) {

        File file = new File("/sdcard/documents/CrossStitchAccount/recover.mp4");
        if (file.exists()) {
            Log.d("my", "--AutoLoading--");

            try {
                FileInputStream fin = new FileInputStream(file);

                ObjectInputStream ois = new ObjectInputStream(fin);
                allCrossStich = (ArrayList<String>) ois.readObject();
                allNits = (ArrayList<Nit>) ois.readObject();
                recoveryNit = (ArrayList<Nit>) ois.readObject();
                fin.close();
                ois.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("my", "--No file--");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("my", "-IO error--");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Log.d("my", "-Class Not Found--");
            }
            Log.d("my", "Stitch size = " + allCrossStich.size());
            Log.d("my", "Current size = " + allNits.size());
            Log.d("my", "Threads size = " + recoveryNit.size());

            for (String item : allCrossStich) {
                dbManager.insertStitchToDb(item, "someText");
            }

            for (Nit it : allNits) {
                String numberNit = it.numberNit;
                String colorName = it.color;
                String firm = it.firma;
                String nameStitch = it.nameStich;
                int colorNumber = dbManager.searchColorNumberFromDb(numberNit, firm);
                double lengthCurrent = it.lengthCurrent;
                dbManager.insertCurrenThreadToDb(numberNit, colorNumber, colorName, firm, nameStitch, lengthCurrent);
            }

            for (int x = 0; x < 900; x++) {
                String num = recoveryNit.get(x).numberNit;
                String firm = recoveryNit.get(x).firma;
                int id = dbManager.searchIdThreadFromDb(num, firm);
                double lengthOstatok = recoveryNit.get(x).lengthOstatokt;
                if (lengthOstatok > 0) {
                    dbManager.updateThreadOstatokToDb(lengthOstatok, String.valueOf(id));
                }
            }
            for (int x = 900; x < 1800; x++) {
                String num = recoveryNit.get(x).numberNit;
                String firm = recoveryNit.get(x).firma;
                int id = dbManager.searchIdThreadFromDb(num, firm);
                double lengthOstatok = recoveryNit.get(x).lengthOstatokt;
                if (lengthOstatok > 0) {
                    dbManager.updateThreadOstatokToDb(lengthOstatok, String.valueOf(id));
                }
            }
            for (int x = 1800; x < recoveryNit.size(); x++) {
                String num = recoveryNit.get(x).numberNit;
                String firm = recoveryNit.get(x).firma;
                int id = dbManager.searchIdThreadFromDb(num, firm);
                double lengthOstatok = recoveryNit.get(x).lengthOstatokt;
                if (lengthOstatok > 0) {
                    dbManager.updateThreadOstatokToDb(lengthOstatok, String.valueOf(id));
                }
            }

            Log.d("my", "Stitch size = " + allCrossStich.size());
            Log.d("my", "Current size = " + allNits.size());
            Log.d("my", "Threads size = " + recoveryNit.size());

            Log.d("my", "--AutoLoad ok--");
        }
        finish();
    }

}
