package com.wyb.briefnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class StartupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        TextView lblVersion = (TextView) findViewById(R.id.lblVersion);

        try {
            PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = this.getString(R.string.version, info.versionName);
            lblVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                switchActivity();
            }

        }, 4000);

    }

    private void switchActivity() {
        SharedPreferences sp = this.getSharedPreferences("Translation.cfg", Context.MODE_PRIVATE);
        //sp.getBoolean("isFirst", true)

        if (sp.getBoolean("isFirst", true)) {
            Intent intent = new Intent(this, GuideActivity.class);
            this.startActivity(intent);
            sp.edit().putBoolean("isFirst", false).commit();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }
        finish();
    }

}
