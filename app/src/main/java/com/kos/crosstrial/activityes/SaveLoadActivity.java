package com.kos.crosstrial.activityes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kos.crossstich.Nit;
import com.kos.crosstrial.items.Cut;
import com.kos.crosstrial.items.FabricItem;
import com.kos.crosstrial.items.NitNew;
import com.kos.crosstrial.R;
import com.kos.crosstrial.items.StitchItem;
import com.kos.crosstrial.db.DbManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class SaveLoadActivity extends AppCompatActivity {
    ArrayList<Nit> recoveryNit;
    ArrayList<String> allCrossStich;
    ArrayList<Nit> allNits;

    ArrayList<StitchItem> stitches;
    ArrayList<NitNew> nitNewArrayList;
    ArrayList<NitNew> currentArrayList;

    ArrayList<FabricItem> fabric;
    ArrayList<Cut> cuts;

    DbManager dbManager;
    Dialog dialogLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_load);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();
    }

    public void init() {
        dbManager = new DbManager(this);

        if (Build.VERSION.SDK_INT <= 29) {
            isStoragePermissionGrantedWrite();
        }

        if (Build.VERSION.SDK_INT >= 30) {
            isStoragePermissionGrantedRead();
        }
        dialogLoad = new Dialog(this);
        dialogLoad.setContentView(R.layout.dialog_load);
    }

    public boolean isStoragePermissionGrantedWrite() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public boolean isStoragePermissionGrantedRead() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public void load(View view) {
        dialogLoad.show();
    }

    public void autoSave(Context context, DbManager dbManager) {


        Log.d("my", "--save--");

        File path = new File("/sdcard/documents/CrossStitchAccount");
        File file = new File("/sdcard/documents/CrossStitchAccount/autoSave.mp4");

        stitches = (ArrayList<StitchItem>) dbManager.getStitchFromDb();
        currentArrayList = (ArrayList<NitNew>) dbManager.getAllCurrentListFromDb();
        nitNewArrayList = (ArrayList<NitNew>) dbManager.getAllThredsFromDb();
        fabric = (ArrayList<FabricItem>) dbManager.getFabricFromDb();
        cuts = (ArrayList<Cut>) dbManager.getAllCutsFromDb();

        Log.d("my", "Stitch size = " + stitches.size());
        Log.d("my", "Current size = " + currentArrayList.size());
        Log.d("my", "Threads size = " + nitNewArrayList.size());
        Log.d("my", "FabricItem size = " + fabric.size());
        Log.d("my", "cuts size = " + cuts.size());


        try {
            if (!path.exists()) {
                path.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(file);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(stitches);
            oos.writeObject(currentArrayList);
            oos.writeObject(nitNewArrayList);
            oos.writeObject(fabric);
            oos.writeObject(cuts);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("my", "--No file--");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("my", "-IO error--");
        }

        //Toast.makeText(context, context.getResources().getText(R.string.recovery_copy), Toast.LENGTH_SHORT).show();

        Log.d("my", "--saved ok--");


    }

    public void save(View view) {

        if (isStoragePermissionGrantedRead()) {
            Log.d("my", "--save--");

            File path = new File("/sdcard/documents/CrossStitchAccount");
            File file = new File("/sdcard/documents/CrossStitchAccount/recover.mp4");

            stitches = (ArrayList<StitchItem>) dbManager.getStitchFromDb();
            currentArrayList = (ArrayList<NitNew>) dbManager.getAllCurrentListFromDb();
            nitNewArrayList = (ArrayList<NitNew>) dbManager.getAllThredsFromDb();
            fabric = (ArrayList<FabricItem>) dbManager.getFabricFromDb();
            cuts = (ArrayList<Cut>) dbManager.getAllCutsFromDb();

            Log.d("my", "Stitch size = " + stitches.size());
            Log.d("my", "Current size = " + currentArrayList.size());
            Log.d("my", "Threads size = " + nitNewArrayList.size());
            Log.d("my", "FabricItem size = " + fabric.size());
            Log.d("my", "cuts size = " + cuts.size());


            try {
                if (!path.exists()) {
                    path.mkdirs();
                }
                FileOutputStream fos = null;

                fos = new FileOutputStream(file);

                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(stitches);
                oos.writeObject(currentArrayList);
                oos.writeObject(nitNewArrayList);
                oos.writeObject(fabric);
                oos.writeObject(cuts);
                fos.close();
                oos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("my", "--No file--");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("my", "-IO error--");
            }

            Toast.makeText(this, this.getResources().getText(R.string.recovery_copy), Toast.LENGTH_SHORT).show();

            Log.d("my", "--saved ok--");
        }

    }

    public void loadOldVersion(View view) {
        if (isStoragePermissionGrantedRead()) {
            Log.d("my", "--Loading--");
            File file = new File("/sdcard/documents/CrossStitchAccount/recover.mp4");
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

        Log.d("my", "--Load ok--");
        finish();
    }

    public void onClickLoadNo(View view) {
        dialogLoad.dismiss();
    }

    public void onClickLoadYes(View view) {
        if (isStoragePermissionGrantedRead()) {
            Log.d("my", "--Loading--");
            File file = new File("/sdcard/documents/CrossStitchAccount/recover.mp4");
            try {
                FileInputStream fin = new FileInputStream(file);

                ObjectInputStream ois = new ObjectInputStream(fin);
                stitches = (ArrayList<StitchItem>) ois.readObject();
                currentArrayList = (ArrayList<NitNew>) ois.readObject();
                nitNewArrayList = (ArrayList<NitNew>) ois.readObject();
                fabric = (ArrayList<FabricItem>) ois.readObject();
                cuts = (ArrayList<Cut>) ois.readObject();
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

            Log.d("my", "Stitch size = " + stitches.size());
            Log.d("my", "Current size = " + currentArrayList.size());
            Log.d("my", "Threads size = " + nitNewArrayList.size());
            Log.d("my", "fabric size = " + fabric.size());
            Log.d("my", "cuts size = " + cuts.size());

            dbManager.deleteAllStitchFromDb();
            for (StitchItem item : stitches) {
                dbManager.insertStitchToDb(item.getStitchName(), "someText");
            }

            dbManager.deleteAllCurrentTreadFromDb();
            for (NitNew it : currentArrayList) {
                String numberNit = it.getNumberNit();
                String colorName = it.getColorName();
                String firm = it.getFirm();
                String nameStitch = it.getNameStitch();
                int colorNumber = dbManager.searchColorNumberFromDb(numberNit, firm);
                double lengthCurrent = it.getLengthCurrent();
                dbManager.insertCurrenThreadToDb(numberNit, colorNumber, colorName, firm, nameStitch, lengthCurrent);
            }
            for (int x = 0; x < 900; x++) {
                String num = nitNewArrayList.get(x).getNumberNit();
                String firm = nitNewArrayList.get(x).getFirm();
                int id = dbManager.searchIdThreadFromDb(num, firm);
                double lengthOstatok = nitNewArrayList.get(x).getLengthOstatok();
                if (lengthOstatok > 0) {
                    dbManager.updateThreadOstatokToDb(lengthOstatok, String.valueOf(id));
                }
            }
            for (int x = 900; x < 1800; x++) {
                String num = nitNewArrayList.get(x).getNumberNit();
                String firm = nitNewArrayList.get(x).getFirm();
                int id = dbManager.searchIdThreadFromDb(num, firm);
                double lengthOstatok = nitNewArrayList.get(x).getLengthOstatok();
                if (lengthOstatok > 0) {
                    dbManager.updateThreadOstatokToDb(lengthOstatok, String.valueOf(id));
                }
            }
            for (int x = 1800; x < nitNewArrayList.size(); x++) {
                String num = nitNewArrayList.get(x).getNumberNit();
                String firm = nitNewArrayList.get(x).getFirm();
                int id = dbManager.searchIdThreadFromDb(num, firm);
                double lengthOstatok = nitNewArrayList.get(x).getLengthOstatok();
                if (lengthOstatok > 0) {
                    dbManager.updateThreadOstatokToDb(lengthOstatok, String.valueOf(id));
                }
            }

            dbManager.deleteAllFabricFromDb();
            for (FabricItem it : fabric) {
                String nameFabric = it.getNameFabric();
                String firmFabric = it.getFirmFabric();
                String articulFabric = it.getArticulFabric();
                String kauntFabric = it.getKauntFabric();
                String colorFabric = it.getColorFabric();
                String myNumberFabric = it.getMyNumberFabric();
                dbManager.insertFabricToDb(firmFabric, nameFabric, articulFabric, kauntFabric, colorFabric, myNumberFabric);
            }

            dbManager.deleteAllCutsFromDb();
            for (Cut it : cuts) {
                int idCut = it.getIdCut();
                String nameFabricCut = it.getNameFabricCut();
                String firmFabricCut = it.getFirmFabricCut();
                String articul = it.getArticul();
                int lengthCut = it.getLengthCut();
                int widthCut = it.getWidthCut();
                dbManager.insertCutToDbLoad(idCut, nameFabricCut, firmFabricCut, articul, lengthCut, widthCut);
            }
            Log.d("my", "Stitch size = " + stitches.size());
            Log.d("my", "Current size = " + currentArrayList.size());
            Log.d("my", "Threads size = " + nitNewArrayList.size());
            Log.d("my", "Threads size = " + fabric.size());
            Log.d("my", "Threads size = " + cuts.size());
            Log.d("my", "--Load ok--");
            finish();
        }
    }

    public void onClickHomeFromSaveLoad(View view) {
        finish();
    }
}