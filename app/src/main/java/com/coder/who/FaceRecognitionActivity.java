package com.coder.who;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FaceRecognitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * 得到传递过来的图片地址，启动网络传输模块上传图片并识别
         */

//        Intent intent = getIntent();
//        String images = intent.getStringExtra("Faces");

        //开始识别弹窗
        final SweetAlertDialog pDialog = new SweetAlertDialog(FaceRecognitionActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("识别中");
        pDialog.setCancelable(false);
        pDialog.show();

        //网络模块识别并解析Json文件


        final SweetAlertDialog sDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();

                //识别成功弹窗
                sDialog.setTitleText("识别成功");
                sDialog.setContentText("请勿泄露对方隐私");
                sDialog.show();
            }
        }, 3000);

    }

}
