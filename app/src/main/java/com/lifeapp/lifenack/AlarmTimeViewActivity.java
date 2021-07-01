package com.lifeapp.lifenack;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AlarmTimeViewActivity extends AppCompatActivity {

    private Intent intent;
    private final String packageNames = "kr.go.hrd.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_time_view);


        intent = this.getPackageManager().getLaunchIntentForPackage(packageNames);
    }

    public void clickbtninstart(View view) {
        AlarmTimeViewActivity.this.startActivity(intent);

}
}