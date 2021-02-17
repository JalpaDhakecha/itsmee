package com.razy.itsmee.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.gson.Gson;
import com.razy.itsmee.demo.Retrofit.ApiHandler;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Utils utils;
    EditText etMail, etPwd;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int RC_SIGN_IN = 7;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        utils = new Utils(this);

        etMail = findViewById(R.id.etMailId);
        etPwd = findViewById(R.id.etPassId);
        etPwd.setImeOptions(EditorInfo.IME_ACTION_DONE);
        findViewById(R.id.tvSignupId).setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));

        etPwd.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (utils.isNetworkAvailable())
                    login();
                else
                    utils.noInternet(Login.this);
            }
            return false;
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        findViewById(R.id.llGLoginId).setOnClickListener(view -> {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
        findViewById(R.id.btnLoginId).setOnClickListener(view -> login());
    }
    public void login() {

        String mail = etMail.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        if (mail == null || !utils.isValidEmail(mail))
            utils.showToast("please enter valid mail Id");
        else if (pwd == null || pwd.trim().length() == 0)
            utils.showToast("please enter password");
        else
            mno_login(true, mail, pwd);
     }

    public void mno_login(boolean is, String mail, String pwd) {
        if (is) utils.preExecute(Login.this);
        HashMap<String, String> param = new HashMap<>();
        param.put("Uid", "i4kLllmuFwSICkZL0hGIVWqRoXN2");
        ApiHandler.getApiService().login(param,"application/json").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                utils.postExecute();
                utils.showResponseLog(response + "");

                Model_Success resData = (Model_Success) response.body();
                /*if (resData != null && resData.getSuccess().equalsIgnoreCase("1")) {

                    utils.editor.putString("user_detail", new Gson().toJson(resData.getModel_User())).commit();
                    utils.showLog("Model", utils.getUser().getId() + "*");

                    startActivity(new Intent(Login.this, Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                    finishAffinity();
                } else {
                    utils.showToast("Invalid Id or password");
                }*/
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mno_login(false, mail, pwd);
                utils.print("error t " + t.toString());
            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        utils.showLog(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            utils.showLog(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            utils.showLog(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

            etMail.setText(email);

        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        utils.showLog("onActivityResult",requestCode+"");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            int statusCode = result.getStatus().getStatusCode();
            utils.showLog("Code ", "" + (statusCode));
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        utils.showLog(TAG, "onConnectionFailed:" + connectionResult);
    }

}