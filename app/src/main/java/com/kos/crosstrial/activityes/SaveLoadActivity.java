package com.kos.crosstrial.activityes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kos.crosstrial.R;
import com.kos.util.SaveLoad;
import com.kos.util.SaveManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class SaveLoadActivity extends AppCompatActivity {
    public static Dialog dialogLoading;
    public static Dialog dialogSaving;
    public static Dialog dialogLoad;
    Button bt_dialog_load_no;
    Button bt_dialog_load_yes;
    TextView tv_user_name;
    ImageView googleAccountLPicture;

    private static final int RC_SIGN_IN = 9001;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_load);
        init();
    }
    void init(){
        /*if (Build.VERSION.SDK_INT <= 29) {
            Log.d("my","sdk <11");
            isStoragePermissionGrantedWrite();
        }

        if (Build.VERSION.SDK_INT >= 30) {
            Log.d("my","sdk =11");
            isStoragePermissionGrantedRead();
        }*/

        dialogLoad = new Dialog(this);
        dialogLoad.setContentView(R.layout.dialog_load);
        bt_dialog_load_no = dialogLoad.findViewById(R.id.bt_dialog_load_no);
        bt_dialog_load_yes = dialogLoad.findViewById(R.id.bt_dialog_load_yes);
        bt_dialog_load_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLoad.dismiss();
            }
        });
        bt_dialog_load_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLoading.show();
                SaveLoad load = new SaveManager(SaveLoadActivity.this);
                load.loadFromFireBase(SaveLoadActivity.this);
            }
        });

        dialogLoading = new Dialog(this);
        dialogLoading.setContentView(R.layout.dialog_loading_in_progress);
        dialogSaving = new Dialog(this);
        dialogSaving.setContentView(R.layout.dialog_saving_in_progress);
        tv_user_name = findViewById(R.id.tv_user_name);
        googleAccountLPicture = findViewById(R.id.googleAccountLPicture);

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser==null){
            signIn();
        }
        else {
            tv_user_name.setText(cUser.getDisplayName());
            Picasso.get().load( cUser.getPhotoUrl()).into(googleAccountLPicture);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //Log.d("my", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                //Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Toast.makeText(getApplicationContext(),"Вы вошли как " + user.getDisplayName(),Toast.LENGTH_SHORT).show();
                            updateUI(user);

                        } else {
                            Toast.makeText(getApplicationContext(),"Авторизация провалена. Нет подключения к интернету.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser cUser) {
        tv_user_name.setText(cUser.getDisplayName());
        Picasso.get().load( cUser.getPhotoUrl()).into(googleAccountLPicture);
    }
    public void onClickHomeFromSaveLoad(View view) {
        finish();
    }

    /*public boolean isStoragePermissionGrantedWrite() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d("my","write");
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
                Log.d("my","read");
                return true;

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }*/

    public void saveToDevic(View view) {
        dialogSaving.show();
        SaveLoad save = new SaveManager(this);
        save.saveToDevice(this);
    }

    public void loadFromDevic(View view) {
        //dialogLoading.show();
        SaveLoad load = new SaveManager(getApplicationContext());
        load.loadFromDevice(getApplicationContext());
        /*new Thread(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        }).start();*/
    }

    public void loadFromFBase(View view) {
        dialogLoad.show();
    }

    public void onClickLogoff(View view) {
        mGoogleSignInClient.signOut();
        signIn();
    }
}