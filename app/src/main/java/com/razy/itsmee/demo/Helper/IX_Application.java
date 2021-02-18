package com.razy.itsmee.demo.Helper;

import android.app.Activity;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


public class IX_Application extends MultiDexApplication {

    public static int pcoutner = 0;
    public static Progress pd;
    public static Activity act;
    public static Utils use;

    public IX_Application(Activity a) {
        this.act = a;
    }

    public IX_Application() {
    }

    public static void preExecute(Activity acti) {
        act = acti;
        setProgressDlg(act);
        pcoutner++;

    }

    public static void postExecute() {

        pcoutner--;
        try {
            if (pcoutner == 0) {
                pd.dismiss();
                pd = null;
            }
        } catch (Exception e) {
        }
    }

    public static void setProgressDlg(Activity mActibity) {

        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
        try {
            if (pd == null) {
                pd = pd.show(act, "", true, false, dialog -> {
                });
            }
            pd.setCancelable(false);
            if (!pd.isShowing())
                pd.show();
        } catch (Exception e) {
            use.showExcpLog(e + "");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }

}
