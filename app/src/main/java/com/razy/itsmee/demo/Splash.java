package com.razy.itsmee.demo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.razy.itsmee.demo.Helper.Utils;

public class Splash extends AppCompatActivity {

    Utils utils;
    boolean isGranted = false, isValid = false;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        utils = new Utils(this);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.GET_ACCOUNTS,
        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        } else
            isGranted = true;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isGranted) {
                    startActivity(new Intent(Splash.this, utils.prefs.getString("user_detail", null) == null ? Login.class : MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                } else
                    isValid = true;
            }
        }, 2000);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    isGranted = true;

                    if (isGranted && isValid) {
                        startActivity(new Intent(Splash.this, Login.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }

                } else {
                    //If user presses deny
                    Toast.makeText(Splash.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                }
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
    }
}