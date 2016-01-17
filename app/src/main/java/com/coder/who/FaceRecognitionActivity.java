package com.coder.who;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FaceRecognitionActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);

        imageView = (ImageView) findViewById(R.id.profile_image);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        ImageView header = (ImageView) findViewById(R.id.htab_header);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.header);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {

                int vibrantColor = palette.getVibrantColor(R.color.primary_500);
                int vibrantDarkColor = palette.getDarkVibrantColor(R.color.primary_700);
                collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
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

                imageView.setImageResource(R.drawable.tx);

            }
        }, 3000);

    }

}
