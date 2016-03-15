package com.coder.who;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,RecyclerViewAdapter.OnRecyclerViewItemClickListener {

    private RecyclerView itemList;
    private List<Item> items;
    private RecyclerViewAdapter adapter;

    private final int PHOTO_REQUEST_GALLERY = 0;
    private final int PHOTO_REQUEST_CAMERA = 1;
    private final int PHOTO_REQUEST_VIDEO = 2;
    private final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";


    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private static final String CAMERA_DIR = "/dcim/";
    private static final String albumName ="CameraSample";

    //获得ImageView的width height
    private int targetW = 200;
    private int targetH = 300;

    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /**
         * recyclerview相关
         */
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        itemList= (RecyclerView) findViewById(R.id.recyclerview);

        //初始化数据
        initItemData();
        adapter = new RecyclerViewAdapter(items, MainActivity.this);
        itemList.setHasFixedSize(true);
        itemList.setLayoutManager(layoutManager);
        itemList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        try {
            photoFile = createFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //获得文件路径,这里以public为例

    private File getPhotoDir(){
        File storDirPrivate = null;
        File storDirPublic = null;

        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){

            //private,只有本应用可访问
            storDirPrivate = new File (
                    Environment.getExternalStorageDirectory()
                            + CAMERA_DIR
                            + albumName
            );

            //public 所有应用均可访问
            storDirPublic = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    albumName);

            if (storDirPublic != null) {
                if (! storDirPublic.mkdirs()) {
                    if (! storDirPublic.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }
        }else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storDirPublic;//或者return storDirPrivate;

    }

    private File createFile() throws IOException {
        photoFile = null;

        String fileName;
        //通过时间戳区别文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        fileName = JPEG_FILE_PREFIX+timeStamp+"_";

        photoFile = File.createTempFile(fileName,JPEG_FILE_SUFFIX,getPhotoDir());

        return photoFile;
    }



    @Override
    public void onItemClick(View view, int pos) {
        switch (pos){
            case 0:
                //从相册获取
                gallery();
                break;
            case 1:
                //从相机获取
                camera();
                break;
            case 2:
                //从视频中获取
                Toast.makeText(MainActivity.this,"程序员正在加班加点...",Toast.LENGTH_SHORT).show();
                break;
        }
        //拿到获取到的图片


    }

    private void camera() {
        //传入File的uri
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(takePictureIntent,PHOTO_REQUEST_CAMERA);
    }
    private void gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //用于保存结果
        String imageUri = null;


        if (requestCode == PHOTO_REQUEST_GALLERY && resultCode == RESULT_OK && null != data) {

            //Toast.makeText(MainActivity.this,"xuanqudaole ",Toast.LENGTH_SHORT).show();
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //bitmap=BitmapFactory.decodeFile(picturePath);
            imageUri = picturePath;

            System.out.println("MainActivity"+"imageUri "+imageUri);

        }
        if (requestCode == PHOTO_REQUEST_CAMERA && resultCode == RESULT_OK && null != data) {
            // Toast.makeText(MainActivity.this,"xuanqudaole ",Toast.LENGTH_SHORT).show();
            //两种方式 获取拍好的图片
            setPic();
            galleryAddPic();
            imageUri = photoFile.getPath();
        }

        if (requestCode == PHOTO_REQUEST_VIDEO && resultCode == RESULT_OK && null != data) {

        }
        //Toast.makeText(MainActivity.this,imageUri,Toast.LENGTH_LONG).show();
        if(imageUri!=null){
            Intent intent = new Intent(MainActivity.this,FaceDetectActivity.class);
            intent.putExtra("FaceImage",imageUri);
            //Toast.makeText(MainActivity.this,imageUri,Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    private void setPic() {

        //获得图像的尺寸
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFile.getAbsolutePath(),bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH =bmOptions.outHeight;

        //计算缩放
        int scaleFactor = 1;
        if((targetW>0)||(targetH>0)){
            scaleFactor = Math.min(photoW/targetW,photoH/targetH);
        }

        //将保存的文件解码
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;


        Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);
        Log.i("MainActivity","imageUri "+photoFile.getAbsolutePath());
        System.out.println("MainActivity"+"photoFile "+photoFile.getAbsolutePath());

    }


    //将图片文件添加至相册（便于浏览）
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photoFile);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


    private void initItemData() {
        items=new ArrayList<>();
        //添加Item
        items.add(new Item(R.mipmap.gallery,R.drawable.ic_menu_gallery,"相册"));
        items.add(new Item(R.mipmap.camera,R.drawable.ic_menu_camera,"拍照"));
        items.add(new Item(R.mipmap.video,R.drawable.ic_menu_slideshow,"视频"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            //打开设置Activity'
            Intent i = new Intent(MainActivity.this,SettingsActivity.class);
            this.startActivity(i);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}