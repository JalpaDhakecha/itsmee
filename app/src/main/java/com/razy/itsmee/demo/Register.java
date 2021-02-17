package com.razy.itsmee.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
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
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;
    Utils utils;
    GoogleApiClient mGoogleApiClient;

    EditText etFName, etLname, etUname, etEmail, etPwd, etConfrimPwd;
    boolean passwordShown = false;
    boolean passwordShown1 = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        utils = new Utils(this);

        etFName = findViewById(R.id.etFnameId);
        etLname = findViewById(R.id.etLnameId);
        etUname = findViewById(R.id.etUnameId);
        etEmail = findViewById(R.id.etMailId);
        etPwd = findViewById(R.id.etPassId);
        etConfrimPwd = findViewById(R.id.etRetypePassId);

        (findViewById(R.id.llGmail)).setOnClickListener(view -> signIn());
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        etPwd.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2; // index
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (etPwd.getRight() - etPwd.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (passwordShown) {
                        passwordShown = false;
                        etPwd.setInputType(129);
                        etPwd.setTypeface(Typeface.SANS_SERIF);
                        etPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                    } else {
                        passwordShown = true;
                        etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        etPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
                    }
                    return true;
                }
            }
            return false;
        });

        etConfrimPwd.setOnTouchListener((v, event) -> {
            final int DRAWABLE_RIGHT = 2; // index
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (etConfrimPwd.getRight() - etConfrimPwd.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    if (passwordShown1) {
                        passwordShown1 = false;
                        etConfrimPwd.setInputType(129);
                        etConfrimPwd.setTypeface(Typeface.SANS_SERIF);
                        etConfrimPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                    } else {
                        passwordShown1 = true;
                        etConfrimPwd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        etConfrimPwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
                    }
                    return true;
                }
            }
            return false;
        });

        findViewById(R.id.btnRegisterId).setOnClickListener(v -> {
            String fnm = etFName.getText().toString().trim();
            String lnm = etLname.getText().toString().trim();
            String unm = etUname.getText().toString().trim();
            String mail = etEmail.getText().toString().trim();
            String pwd = etPwd.getText().toString().trim();
            String Cpwd = etConfrimPwd.getText().toString().trim();

            if (fnm == null || fnm.trim().length() == 0)
                utils.showToast("please enter first name");
            else if (unm == null || unm.trim().length() == 0)
                utils.showToast("please enter user name");
            else if (mail == null || mail.trim().length() == 0)
                utils.showToast("please enter mail ID");
            else if (!utils.isValidEmail(mail))
                utils.showToast("please enter valid mail ID");
            else if (pwd == null || pwd.trim().length() == 0)
                utils.showToast("please enter password");
            else if (Cpwd == null || Cpwd.trim().length() == 0)
                utils.showToast("please retype Password");
            else if (!Cpwd.equalsIgnoreCase(pwd))
                utils.showToast("retype password not matches");
            else {
                if (utils.isNetworkAvailable())
                    register(fnm, lnm, unm, mail, pwd);
                else utils.noInternet(Register.this);
            }
        });

    }

    public void register(String fnm, String lnm, String unm, String mail, String pwd) {
        utils.preExecute(Register.this);
        char[] chars = "0123456789".toCharArray();
        final StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            char c1 = chars[random.nextInt(chars.length)];
            sb.append(c1);
        }

        HashMap<String, String> param = new HashMap<>();
        param.put("AuthType", "1");
        param.put("Birthday", "null");
        param.put("EmailAddress", mail);
        param.put("FirstName", fnm);
        param.put("LastName", lnm);
        param.put("Password", pwd);
        param.put("PasswordRepeat", pwd);
        param.put("Uid", mail);
        param.put("UserName", unm);

        utils.showParamLog(param + "");

        ApiHandler.getApiService().register(param, "application/json").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                utils.postExecute();
                utils.showResponseLog(response + "");
                Model_Success resData = (Model_Success) response.body();

                utils.showResponseLog(resData + "");
                if (resData != null && resData.getSuccess()) {
                    utils.editor.putString("user_detail", new Gson().toJson(resData.getData())).commit();
                    utils.showLog("Model", resData.getData().getId() + "*");

                    startActivity(new Intent(Register.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                    finishAffinity();

                } else {
                    assert resData != null;
                    utils.showToast(resData.getMessage() != null ? resData.getMessage() : resData.getErrorMessage());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                utils.print("error t " + t.toString());
                utils.postExecute();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        utils.showLog("signin ", "" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            try {

                utils.showLog("Param", acct.getId() + "*");

                /*mail_id = acct.getId();
                mail = acct.getEmail();
                mail_name = acct.getDisplayName();*/

                utils.preExecute(Register.this);

                HashMap<String, String> param = new HashMap<>();
                param.put("AuthType", "2");
                param.put("Uid", acct.getId());
                ApiHandler.getApiService().register(param, "application/json").enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        utils.postExecute();
                        utils.showResponseLog(response + "");
                        Model_Success resData = (Model_Success) response.body();

                        utils.showResponseLog(resData + "");
                        if (resData != null && resData.getSuccess()) {
                            utils.editor.putString("user_detail", new Gson().toJson(resData.getData())).commit();
                            utils.showLog("Model", resData.getData().getId() + "*");

                            startActivity(new Intent(Register.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                            finishAffinity();

                        } else {
                            assert resData != null;
                            utils.showToast(resData.getMessage() != null ? resData.getMessage() : resData.getErrorMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        utils.print("error t " + t.toString());
                        utils.postExecute();
                    }
                });
            } catch (Exception e) {
                utils.showLog("Exception in Google ", "" + e);
            }
        } else utils.showToast("Something went wrong!!!");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        utils.showToast("Google Login failed. Please try again!");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        utils.showToast("Google Login failed. Please try again!");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            int statusCode = result.getStatus().getStatusCode();
            utils.showLog("Code ", "" + (statusCode));
            handleSignInResult(result);
        }
    }
}