package com.kos.crosstrial.activityes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import com.kos.crosstrial.R;
import com.kos.crosstrial.adapters.StitchAdapter;
import com.kos.crosstrial.db.DbManager;
import com.kos.crosstrial.items.StitchItem;
import com.kos.util.Trial;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.kos.crosstrial.adapters.StitchAdapter.stitchName;

// v2.0 Trial
public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    Trial trialClass = new Trial();
    static String endOfTrial;
    ArrayList<StitchItem> stitches;

    private DbManager dbManager;
    public EditText et_add_stich;
    private RecyclerView rvNewCrossStich;
    private StitchAdapter stitchAdapter;
    Dialog dialogAddStitch;
    public static Dialog deleteStitch;
    ImageButton bt_dialog_addStich_V;

    TextView tv_trial;
    TextView tv_trial2;

    //@RequiresApi(api = Build.VERSION_CODES.R)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //loadAd();
    }
    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-9744475608161908/1059970340",adRequest,new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        MainActivity.this.mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        //Toast.makeText(MainActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                        showInterstitial();
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity.this.mInterstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        MainActivity.this.mInterstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;

                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                        Toast.makeText(
                                MainActivity.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDb();

        stitchAdapter.updateStitchAdapter(dbManager.getStitchFromDb());

        endOfTrial = trialClass.trial(dbManager);
        if (endOfTrial.equals("end")) {
            tv_trial.setVisibility(View.VISIBLE);
            tv_trial2.setVisibility(View.VISIBLE);
            tv_trial2.setText("Время пробной версии истекло");
            tv_trial.setText("Ссылка на полную версию");
            tv_trial.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "https://play.google.com/store/apps/details?id=com.kos.crossstich";

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDb();
    }


    private void init() {
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        tv_trial = (TextView) findViewById(R.id.trial);
        tv_trial2 = (TextView) findViewById(R.id.trial2);


        stitches = new ArrayList<>();
        dbManager = new DbManager(this);

        dialogAddStitch = new Dialog(this);
        dialogAddStitch.setContentView(R.layout.dialog_add_stitch);
        bt_dialog_addStich_V = dialogAddStitch.findViewById(R.id.bt_dialog_addStich_V);
        et_add_stich = dialogAddStitch.findViewById(R.id.et_add_stich);

        deleteStitch = new Dialog(this);
        deleteStitch.setContentView(R.layout.dialog_delete_stitch);
        rvNewCrossStich = findViewById(R.id.rv_New_CrossStich);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvNewCrossStich.setLayoutManager(layoutManager);
        stitchAdapter = new StitchAdapter(stitches, this);
        getItemTouchHelper().attachToRecyclerView(rvNewCrossStich);
        rvNewCrossStich.setAdapter(stitchAdapter);


    }

    public void but_add(View view) {
        dialogAddStitch.show();
    }

    public void addStitch(View view) {
        String stitchName = et_add_stich.getText().toString();
        dbManager.insertStitchToDb(stitchName, "some text");
        et_add_stich.setText("");
        stitchAdapter.updateStitchAdapter(dbManager.getStitchFromDb());
        dialogAddStitch.dismiss();
    }

    private ItemTouchHelper getItemTouchHelper() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteStitch.show();
            }
        });
    }


    public void onClickDeleteStitchYes(View view) {
        Toast.makeText(getBaseContext(), "Процесс завершен", Toast.LENGTH_SHORT).show();
        dbManager.deleteStitchFromDb(stitchName);
        stitchAdapter.updateStitchAdapter(dbManager.getStitchFromDb());
        dbManager.deleteAllTreadsFromCurrentStitch(stitchName);
        deleteStitch.dismiss();
    }

    public void onClickDeleteStitchNo(View view) {
        deleteStitch.dismiss();
        stitchAdapter.updateStitchAdapter(dbManager.getStitchFromDb());
    }

    public void onClickallThreads(View view) {
        endOfTrial = trialClass.trial(dbManager);
        Log.d("my", "endOfTrial = " + endOfTrial);
        Intent intentGoTOThreadsActivity = new Intent(this, ThreadsActivity.class);
        Intent intentGoTOMainActivity = new Intent(this, MainActivity.class);

        if (endOfTrial.equals("end")) {
            startActivity(intentGoTOMainActivity);
        } else {
            startActivity(intentGoTOThreadsActivity);
        }
    }

    public void onClickGoToFabric(View view) {
        endOfTrial = trialClass.trial(dbManager);
        Intent intentGoTOMainActivity = new Intent(this, MainActivity.class);
        Intent intentGoToFabricActivity = new Intent(this, FabricActivity.class);

        if (endOfTrial.equals("end")) {
            startActivity(intentGoTOMainActivity);
        } else {
            startActivity(intentGoToFabricActivity);
        }
    }

    public void saveLoad(View view) {
        Intent intent = new Intent(this, SaveLoadActivity.class);
        startActivity(intent);
    }

    public void onclickSetup(View view) {
        Intent intent = new Intent(this, SetupActivity.class);
        startActivity(intent);
    }
}

