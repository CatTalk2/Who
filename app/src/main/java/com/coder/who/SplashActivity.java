package com.coder.who;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {


    //本地版本号
    private String mVersion = "1.0.0";
    private TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();

        //暂时
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                //启动主Activity后销毁自身
                finish();
            }
        }, 3000);

        /**
         * 判断是否更新
         *
         * 判断网络状态
         *
         *
         */


    }

    private void initView() {
        tvVersion= (TextView) findViewById(R.id.tv_version);
        tvVersion.setText("版本号:"+mVersion);
    }

}
