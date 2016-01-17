package com.coder.who;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FaceDetectActivity extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detect);

        imageView = (ImageView) findViewById(R.id.image_face_detect);
        Intent intent =getIntent();
        Bitmap bitmap = BitmapFactory.decodeFile(intent.getStringExtra("FaceImage"));
        imageView.setImageBitmap(bitmap);

        //进入检测模式
        final SweetAlertDialog pDialog = new SweetAlertDialog(FaceDetectActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("检测中");
        pDialog.setCancelable(false);
        pDialog.show();

        //正儿八经检测逻辑

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();

                //检测到人脸是否确认识别，将custom_image改为检测到的图像
                new SweetAlertDialog(FaceDetectActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setCustomImage(R.mipmap.icon)
                        .setTitleText("已检测到人脸部分")
                        .setContentText("为提高准确率请保证网络畅通")
                        .setConfirmText("进行识别")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog
                                        .setTitleText("识别成功")
                                        .setContentText("点击查看具体信息")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                Intent i = new Intent(FaceDetectActivity.this,FaceRecognitionActivity.class);
                                                //传递识别结果json


                                                startActivity(i);
                                            }
                                        })
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
            }
        }, 3000);





    }
}
