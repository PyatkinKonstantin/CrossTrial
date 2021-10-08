package com.kos.util;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import static com.kos.crosstrial.activityes.SaveLoadActivity.dialogLoad;
import static com.kos.crosstrial.activityes.SaveLoadActivity.dialogLoading;
import static com.kos.crosstrial.activityes.SaveLoadActivity.dialogSaving;

public class SaveManager extends AppCompatActivity implements SaveLoad {
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

    String STITCHES = "stitches";
    String USER_NAME = "user_name";
    String SAVE = "save";
    String CURRENT_THREADS = "current_threads";
    String ALL_THREADS = "all_threads";
    String FABRIC = "fabric";
    String CUTS = "cuts";

    public void loadUsingUrl(Context context, File tempfile) {

        File dbOrig = new File("/data/data/com.kos.crosstrial/databases/cross_stitch_db.db");

        try {
            FileInputStream fileInputStream = new FileInputStream(tempfile);
            FileOutputStream fileOutputStream = new FileOutputStream(dbOrig);

            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer, 0, buffer.length);
            fileOutputStream.write(buffer, 0, buffer.length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialogLoading.dismiss();
        dialogLoad.dismiss();
        Toast.makeText(context, context.getResources().getText(R.string.loaded), Toast.LENGTH_SHORT).show();
    }

    public void loadFromFireBase(Context context) {

        File tempfile = new File("/sdcard/download/Accounting/cross_stitch_db_obl.db");
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

                        loadUsingUrl(context, tempfile);
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

    public void saveToDevice(Context context) {

        File dbOrig = new File("/data/data/com.kos.crosstrial/databases/cross_stitch_db.db");
        File dbCopy = new File("/sdcard/download/Accounting/cross_stitch_db.db");
        File path = new File("/sdcard/download/Accounting");

        if (!path.exists()) {
            path.mkdirs();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(dbOrig);
            FileOutputStream fileOutputStream = new FileOutputStream(dbCopy);

            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer, 0, buffer.length);
            fileOutputStream.write(buffer, 0, buffer.length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        saveToFireBaseStorage();

    }

    public void loadFromDevice(Context context) {

        File dbOrig = new File("/data/data/com.kos.crosstrial/databases/cross_stitch_db.db");
        File dbCopy = new File("/sdcard/download/Accounting/cross_stitch_db.db");

        try {
            FileInputStream fileInputStream = new FileInputStream(dbCopy);
            FileOutputStream fileOutputStream = new FileOutputStream(dbOrig);

            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer, 0, buffer.length);
            fileOutputStream.write(buffer, 0, buffer.length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveToFireBaseStorage() {

        File dbCopy = new File("/sdcard/download/Accounting/cross_stitch_db.db");

        mStorageRef = FirebaseStorage.getInstance().getReference(user.getUid());

        final StorageReference mRef = mStorageRef.child(user.getUid() + "save");

        UploadTask uploadTask = mRef.putFile(Uri.fromFile(dbCopy));

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