package com.kos.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kos.crosstrial.R;
import com.kos.crosstrial.db.DbManager;
import com.kos.crosstrial.items.Cut;
import com.kos.crosstrial.items.FabricItem;
import com.kos.crosstrial.items.NitNew;
import com.kos.crosstrial.items.StitchItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static com.kos.crosstrial.activityes.SaveLoadActivity.dialogLoading;
import static com.kos.crosstrial.activityes.SaveLoadActivity.dialogSaving;

public class SaveManager implements SaveLoad {
    File tempfile;
    Boolean ok = false;
    String str = "";
    Uri upLoadUri;
    StorageReference mStorageRef;
    StorageReference httpReference;
    private Context context;
    DbManager dbManager;

    public SaveManager(Context context) {
        this.context = context;
        dbManager = new DbManager(context);
        dbManager.openDb();
    }

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(user.getUid());

    ArrayList<StitchItem> stitches;
    ArrayList<NitNew> nitNewArrayList;
    ArrayList<NitNew> currentArrayList;
    ArrayList<FabricItem> fabric;
    ArrayList<Cut> cuts;

    String STITCHES = "stitches";
    String USER_NAME = "user_name";
    String SAVE = "save";
    String CURRENT_THREADS = "current_threads";
    String ALL_THREADS = "all_threads";
    String FABRIC = "fabric";
    String CUTS = "cuts"; //

    public void reciveArrays() {
        stitches = (ArrayList<StitchItem>) dbManager.getStitchFromDb();
        nitNewArrayList = (ArrayList<NitNew>) dbManager.getAllThredsFromDb();
        currentArrayList = (ArrayList<NitNew>) dbManager.getAllCurrentListFromDb();
        fabric = (ArrayList<FabricItem>) dbManager.getFabricFromDb();
        cuts = (ArrayList<Cut>) dbManager.getAllCutsFromDb();
        Log.d("my", "reciveArrays complete. Size of base = " + nitNewArrayList.size());
    }


    public synchronized void loadUsingUrl(Context context, Boolean isStoragePermissionGrantedRead, Boolean isStoragePermissionGrantedWrite) {

        Log.d("my", "ok = " + ok);

        if (isStoragePermissionGrantedRead&&isStoragePermissionGrantedWrite) {

            Log.d("my", "--Loading--");

            try {
                Log.d("my", "five");
                FileInputStream fin = new FileInputStream(tempfile);

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
            Log.d("my", "fabric size = " + fabric.size());
            Log.d("my", "cuts size = " + cuts.size());
            Log.d("my", "--Load ok--");
            dialogLoading.dismiss();
            Toast.makeText(context, context.getResources().getText(R.string.loaded), Toast.LENGTH_SHORT).show();
        }


    }

    public void loadFromFireBase(Context context, Boolean isStoragePermissionGrantedRead, Boolean isStoragePermissionGrantedWrite) {
        tempfile = new File("/sdcard/documents/CrossStitchAccount/recover2.mp4");
        Log.d("my", "nol");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                str = dataSnapshot.child(SAVE).getValue(String.class);
                Log.d("my", "ras");
                // Log.d("my", str);
                Log.d("my", "dva");
                httpReference = FirebaseStorage.getInstance().getReferenceFromUrl(str);
                Log.d("my", "tri");
                httpReference.getFile(tempfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d("my", "chetire");
                        ok = true;
                        Log.d("my", "ok = " + ok);

                        loadUsingUrl(context, isStoragePermissionGrantedRead, isStoragePermissionGrantedWrite);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Ошибка сети. Повторите попытку загрузки.", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("my", "Failed to read value.", error.toException());
            }
        });
    }

    public void saveToDevice(Context context, Boolean isStoragePermissionGrantedRead, Boolean isStoragePermissionGrantedWrite) {
        if (isStoragePermissionGrantedRead&&isStoragePermissionGrantedWrite) {
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


            saveToFireBaseStorage();

        }
    }

    public void loadFromDevice(Context context, Boolean isStoragePermissionGrantedRead, Boolean isStoragePermissionGrantedWrite) {
        if (isStoragePermissionGrantedRead&&isStoragePermissionGrantedWrite) {
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
            Log.d("my", "fabric size = " + fabric.size());
            Log.d("my", "cuts size = " + cuts.size());
            Log.d("my", "--Load ok--");
        }
    }

    @Override
    public void saveToFireBase() {

    }

    void saveToFireBaseStorage() {

        File file = new File("/sdcard/documents/CrossStitchAccount/recover.mp4");

        mStorageRef = FirebaseStorage.getInstance().getReference(user.getUid());

        final StorageReference mRef = mStorageRef.child(user.getUid() + "save");

        UploadTask uploadTask = mRef.putFile(Uri.fromFile(file));

        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return mRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                upLoadUri = task.getResult();
                String str = upLoadUri.getUserInfo();
                mDatabase.child(SAVE).setValue(str);
                //Log.d("my", upLoadUri.toString());
                mDatabase.child(SAVE).setValue(upLoadUri.toString());
                Toast.makeText(context, "Сохранено", Toast.LENGTH_SHORT).show();
                dialogSaving.dismiss();
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Ошибка сети. Повторите попытку сохранения", Toast.LENGTH_LONG).show();
            }
        });
    }

}