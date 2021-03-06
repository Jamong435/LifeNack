package com.lifeapp.lifenack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;

    private TimePicker timePicker;
    private AlarmManager alarmManager;
    private int hour, minute;
    CheckBox cbSun, cbMon, cbTue, cbWed, cbThu, cbFri, cbSat;

    static String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3032504580461279/2028303247");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        timePicker=findViewById(R.id.tp_timepicker);
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager= (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        cbSun=findViewById(R.id.cb_sun);
        cbMon=findViewById(R.id.cb_mon);
        cbTue=findViewById(R.id.cb_tue);
        cbWed=findViewById(R.id.cb_wed);
        cbThu=findViewById(R.id.cb_thu);
        cbFri=findViewById(R.id.cb_fri);
        cbSat=findViewById(R.id.cb_sat);

    }// onCreate()..

    public void regist(View view) {

        boolean[] week = { false, cbSun.isChecked(), cbMon.isChecked(), cbTue.isChecked(), cbWed.isChecked(),
                cbThu.isChecked(), cbFri.isChecked(), cbSat.isChecked() }; // cbSun??? 1????????? ???????????? ?????? ?????? 0?????? false??? ??????

        if(!cbSun.isChecked() &&  !cbMon.isChecked() &&  !cbTue.isChecked() && !cbWed.isChecked() &&  !cbThu.isChecked() && !cbFri.isChecked() && !cbSat.isChecked()){
            Toast.makeText(this, "????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(cbSun.isChecked() || cbMon.isChecked() ||  cbTue.isChecked() || cbWed.isChecked() ||  cbThu.isChecked() || cbFri.isChecked() || cbSat.isChecked()){
            Toast.makeText(this, "???????????????????????????.", Toast.LENGTH_SHORT).show();

        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour=timePicker.getHour();
            minute=timePicker.getMinute();
        }else{
            Toast.makeText(this, "????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, Alarm.class);
        intent.putExtra("weekday", week);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0,intent, 0); //PendingIntent.FLAG_UPDATE_CURRENT

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date today = new Date();
        long intervalDay = 24 * 60 * 60 * 1000;// 24??????

        long selectTime=calendar.getTimeInMillis();
        long currenTime=System.currentTimeMillis();

        //?????? ????????? ?????????, ?????? ???????????? ????????? ????????? ??????????????? ????????? ????????? ????????? ????????? ??????
        if(currenTime>selectTime){
            selectTime += intervalDay;
        }

        Log.e(TAG,"?????? ????????? ?????? ?????? : "+today+"  ????????? ?????? : "+calendar.getTime());

        Log.d(TAG,"calendar.getTimeInMillis()  : "+calendar.getTimeInMillis());

        // ????????? ????????? ?????? ??????
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, selectTime,  intervalDay, pIntent);

    }// regist()..

    public void unregist(View view) {
        Toast.makeText(this, "???????????????????????????.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.cancel(pIntent);
    }// unregist()..

    public void clickbtn2(View view) {
        Intent intent = new Intent(this,AlarmTimeViewActivity.class);
        startActivity(intent);
    }
}// MainActivity class..