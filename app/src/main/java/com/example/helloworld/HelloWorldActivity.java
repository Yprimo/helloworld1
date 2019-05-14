package com.example.helloworld;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class HelloWorldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello_world_layout);
        EventBus.getDefault().register(this);
        initPermission();

        Button buttonks = (Button) findViewById(R.id.kaishihecheng);
        Button buttonjs = (Button) findViewById(R.id.jieshu);
        Button buttonkssb = (Button) findViewById(R.id.kaishishibie);
        Button buttonjssb = (Button) findViewById(R.id.jieshushibie);
        buttonks.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SpeakVoiceUtil.getInstance(getApplicationContext()).speak("欢迎使用百度语音合成");

                //Intent intent=new Intent(HelloWorldActivity.this, MiniActivity.class);
                //startActivity(intent);
                //Toast.makeText(HelloWorldActivity.this,"开始使用百度语音合成",Toast.LENGTH_SHORT ).show();
            }
        });
        buttonkssb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Recog.getInstance(getApplicationContext()).start();
            }
        });
        buttonjssb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Recog.getInstance(getApplicationContext()).stop();
            }
        });
        buttonjs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HelloWorldActivity.this, ShiBieActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initPermission() {
        String permissions[] = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            Object context;
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void onMessageEventMain(TestEvent event){
        if(event.getMsg().equals("你好")) {
            Toast.makeText(HelloWorldActivity.this, event.getMsg(), Toast.LENGTH_SHORT).show();
                Recog.getInstance(getApplicationContext()).stop();
       }
        else{
            Recog.getInstance(getApplicationContext()).stop();
            Recog.getInstance(getApplicationContext()).start();
        }
    }
}