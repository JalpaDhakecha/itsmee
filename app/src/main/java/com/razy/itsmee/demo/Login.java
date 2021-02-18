package com.razy.itsmee.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.gson.Gson;
import com.razy.itsmee.demo.Models.Model_Success;
import com.razy.itsmee.demo.Retrofit.ApiHandler;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 7;
    Utils utils;
    EditText etMail, etPwd;
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
                .requestIdToken(getString(R.string.default_web_client_id))
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
            mno_login(true, false, mail, pwd, mail);
    }

    public void mno_login(boolean is, boolean isgoogle, String mail, String pwd, String uid) {
        if (is) utils.preExecute(Login.this);
        HashMap<String, String> param = new HashMap<>();
        if (!isgoogle) {
            param.put("MailAddress", mail);
            param.put("AuthType", "1");
            param.put("Password", pwd);
        }
//        param.put("Uid", "i4kLllmuFwSICkZL0hGIVWqRoXN2");
        param.put("Uid", uid);

        utils.showParamLog(param + "");

        ApiHandler.getApiService().login(param, "application/json").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                utils.postExecute();
                utils.showResponseLog(response + "");

                Model_Success resData = (Model_Success) response.body();
                if (resData != null && resData.getSuccess()) {

                    utils.editor.putString("user_detail", new Gson().toJson(resData.getData())).commit();
                    utils.showLog("Model", resData.getData().getId() + "*");

                    startActivity(new Intent(Login.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                    finishAffinity();
                } else {
                    if (resData != null && resData.getStatus().equalsIgnoreCase("400"))
                        utils.showToast(resData.getMessage() != null ? resData.getMessage() : resData.getErrorMessage());
                    else
                        utils.showToast("something went wrong!!!");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                mno_login(false, isgoogle, mail, pwd, uid);
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

            mno_login(true, true, "", "", acct.getId());

        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
            utils.showToast("Something went wrong!!!");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        utils.showLog("onActivityResult", requestCode + "");
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