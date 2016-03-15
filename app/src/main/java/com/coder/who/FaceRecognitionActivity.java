package com.coder.who;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FaceRecognitionActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognition);

        imageView = (ImageView) findViewById(R.id.profile_image);
        textView = (TextView) findViewById(R.id.profile_name);
        listView = (ListView) findViewById(R.id.profile_info);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_capture_screen);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "图片信息以保存到本地", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.item_info,
                new String[]{"infoImage","infoTitle","infoDesc"},
                new int[]{R.id.info_image,R.id.info_title,R.id.info_desc});
        listView.setAdapter(adapter);
        listView.setVisibility(View.INVISIBLE);

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

                imageView.setImageResource(R.drawable.yyk);
                textView.setText("杨云凯");

                //listView显示
                listView.setVisibility(View.VISIBLE);

            }
        }, 3000);



    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("infoImage", R.drawable.ic_school_black_24dp);
        map.put("infoTitle", "学校");
        map.put("infoDesc","北京航空航天大学" );
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("infoImage", R.drawable.ic_face_black_24dp);
        map.put("infoTitle", "院系");
        map.put("infoDesc","软件学院四年级" );
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("infoImage", R.drawable.ic_code_black_24dp);
        map.put("infoTitle", "技能");
        map.put("infoDesc","Android,IOS开发" );
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("infoImage", R.drawable.ic_phone_black_24dp);
        map.put("infoTitle", "电话");
        map.put("infoDesc","13141459344" );
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("infoImage", R.drawable.ic_email_black_24dp);
        map.put("infoTitle", "邮箱");
        map.put("infoDesc","Yunkai@gmail.com" );
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("infoImage", R.drawable.ic_room_black_24dp);
        map.put("infoTitle", "地址");
        map.put("infoDesc","海淀区学院路37号" );
        list.add(map);

        return list;
    }

}
