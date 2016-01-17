package com.coder.who;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FaceDetectActivity extends AppCompatActivity {

    private FaceOverlayView faceOverlayView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detect);

        faceOverlayView = (FaceOverlayView) findViewById(R.id.face_overlay);
        Intent intent =getIntent();
        Bitmap bitmap = BitmapFactory.decodeFile(intent.getStringExtra("FaceImage"));
        faceOverlayView.setBitmap(bitmap,FaceDetectActivity.this);

        //进入检测模式
        final SweetAlertDialog pDialog = new SweetAlertDialog(FaceDetectActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("检测中");
        pDialog.setCancelable(false);
        pDialog.show();

        /**
         * 检测出人脸部分，并进行灰度等预处理
         *
         * mFaces.size记录检测出的人脸数
         *
         * String[] faceImage存储预处理好的人脸图片
         */

//       if(Detect_Sucess){
//
//
//           pDialog.dismiss();
//           /**
//            * 对检测到人脸是否确认识别，将custom_image改为检测到的图像
//            *
//            * 如果检测到多张脸，则用群组头像替代
//            */
//           new SweetAlertDialog(FaceDetectActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
//                   .setCustomImage(R.mipmap.icon)
//                   .setTitleText("已检测到人脸部分")
//                   .setContentText("为提高准确率请保证网络畅通")
//                   .setConfirmText("进行识别")
//                   .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                       @Override
//                       public void onClick(SweetAlertDialog sDialog) {
//                           Intent i = new Intent(FaceDetectActivity.this,FaceRecognitionActivity.class);
//                           //传递检测出，并已做了预处理的人脸图片
//                           i.putExtra("Faces",faceImage);
//                           startActivity(i);
//
//                       }
//                   })
//                   .show();
//       }

        //暂时
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
                                Intent i = new Intent(FaceDetectActivity.this,FaceRecognitionActivity.class);

                                 startActivity(i);
                            }
                        })
                        .show();
            }
        }, 3000);





    }
}
