package com.razy.itsmee.demo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.razy.itsmee.demo.Helper.Utils;
import com.razy.itsmee.demo.Models.ListResponse;
import com.razy.itsmee.demo.Models.data;
import com.razy.itsmee.demo.Models.picmee;
import com.razy.itsmee.demo.Retrofit.ApiHandler;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Utils utils;
    data d;
    ListView lvData;
    ArrayList<picmee> al = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        utils = new Utils(this);
        d = utils.getUser();

        ((TextView) findViewById(R.id.tvNameId)).setText("Hello " + (d.getUserName() == null ? "World" : d.getUserName()));

        findViewById(R.id.llLogout).setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Do you really want to logout?")
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        utils.editor.remove("user_detail").commit();
                        utils.editor.clear().commit();
                        startActivity(new Intent(MainActivity.this, Splash.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });

        lvData = findViewById(R.id.lvData);

        if (utils.isNetworkAvailable())
            getdata(true);
        else utils.noInternet(MainActivity.this);

    }

    public void getdata(boolean is) {
        if (is)
            utils.preExecute(MainActivity.this);
        HashMap<String, String> param = new HashMap<>();

        param.put("relationshipType", "0");
        param.put("PageIndex", "0");
        param.put("PageSize", "20");

        utils.showParamLog(param + "");
//        ApiHandler.getApiService().getdata(param, "Query Params").enqueue(new Callback() {
        ApiHandler.getApiService().getdata(0, 0, 20).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                utils.postExecute();
                utils.showResponseLog(response + "");
                ListResponse jsonObj = (ListResponse) response.body();

                if (jsonObj != null && jsonObj.isSuccess()) {
                    utils.showLog("TEST", jsonObj.getData().get(0).getId() + "*");

                    for (int i = 0; i < jsonObj.getData().size(); i++) {
                        picmee p = new picmee();
                        p.setId(jsonObj.getData().get(i).getId());
                        p.setTitle(jsonObj.getData().get(i).getTitle());
                        p.setText(jsonObj.getData().get(i).getText());
                        p.setFile(jsonObj.getData().get(i).getFile());
                        p.setDateCreated(jsonObj.getData().get(i).getDateCreated());
                        p.setDateCreatedDisplay(jsonObj.getData().get(i).getDateCreatedDisplay());
                        p.setTimeOut(jsonObj.getData().get(i).getTimeOut());
                        p.setRemainTime(jsonObj.getData().get(i).getRemainTime());
                        p.setUser(jsonObj.getData().get(i).getUser());

                        al.add(p);
                    }

                    Adapter adapter = new Adapter(MainActivity.this, al);
                    lvData.setAdapter(adapter);
                } else utils.showToast("No data found");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
//                getdata(false);
                utils.showExcpLog("error t " + t.toString());
            }
        });
    }

}