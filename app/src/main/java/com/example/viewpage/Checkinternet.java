package com.example.viewpage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Checkinternet {
    private static ProgressDialog dialog;
    static Timer timer;
    static TimerTask timerTask;
    String TAG = "Timers";
    static int Your_X_SECS = 5;
    static Context context;

    public static void startTimer(Context context2) {

        context = context2;
        dialog = new ProgressDialog(context);
        stoptimertask();
        // timer.cancel();
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 5000, Your_X_SECS * 1000); //
        loadLocale();
    }


    public static void loadLocale(){
        SharedPreferences preferences = context.getSharedPreferences("settings",Activity.MODE_PRIVATE);
        String language = preferences.getString("my_lang","");
        setAppLocale(language);
    }

    private static void setAppLocale(String localeCode){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1){
            config.setLocale(new Locale(localeCode.toLowerCase()));
        } else {
            config.locale = new Locale(localeCode.toLowerCase());
        }
        resources.updateConfiguration(config, dm);
        SharedPreferences.Editor editor = context.getSharedPreferences("settings",context.MODE_PRIVATE).edit();
        editor.putString("my_lang",localeCode);
        editor.apply();
    }



    public static void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    static final Handler handler = new Handler();

    public static void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        YOURNOTIFICATIONFUNCTION();

                        Log.e("WWWWW", "WWWWWWWWW" + timer);

                    }
                });
            }
        };
    }

    private static void YOURNOTIFICATIONFUNCTION() {
        //executeCommand();

            if (!executeCommand()) {

                if (!dialog.isShowing()) {
                    if (!((Activity) context).isFinishing()) {
                        //show dialog
                        dialog.setMessage("Connecting internet...");
                        dialog.setCancelable(false);
                        dialog.show();
                    }
                }

            } else {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }


    }



    private static boolean executeCommand()  {

        System.out.println("executeCommand");
        Runtime runtime = Runtime.getRuntime();
        try {
            Process mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue " + mExitValue);
            if (mExitValue == 0) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
            System.out.println(" Exception:" + ignore);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" Exception:" + e);
        }
        return false;
    }
}


