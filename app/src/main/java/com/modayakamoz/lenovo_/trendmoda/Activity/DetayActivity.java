package com.modayakamoz.lenovo_.trendmoda.Activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.modayakamoz.lenovo_.trendmoda.Adapter.BedenAdapter;
import com.modayakamoz.lenovo_.trendmoda.Adapter.ResimAdapter;
import com.modayakamoz.lenovo_.trendmoda.Fragment.YuklemeFragment;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.Object.UrunObject;
import com.modayakamoz.lenovo_.trendmoda.R;
import com.vincan.medialoader.MediaLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.sephiroth.android.library.picasso.Callback;
import it.sephiroth.android.library.picasso.Picasso;
public class DetayActivity extends AppCompatActivity {
    public static UrunObject uo;
    String proxyUrl ;
    YuklemeFragment progress;
    ImageView resim;
    TextView aciklama, kalmadi,adetText;
    GridView beden, resimler;
    Button sepete_ekle,dl,share,close;
    public static VideoView videoView;
    BedenAdapter adapter;
    String extension="";
    int z = 0, y = 0;
    ResimAdapter resimAdapter;
    Button eksi,arti;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    LinearLayout adetLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);
        videoView = (VideoView)findViewById(R.id.videoview);
        resim = (ImageView)findViewById(R.id.imageView14);
        aciklama = (TextView)findViewById(R.id.textView38);
        kalmadi = (TextView) findViewById(R.id.textView37);
        resimler = (GridView) findViewById(R.id.resimler);
        sepete_ekle = (Button) findViewById(R.id.button3);
        share = (Button) findViewById(R.id.share);
        dl = (Button) findViewById(R.id.button8);
        beden = (GridView)findViewById(R.id.beden);
        adetText = (TextView)findViewById(R.id.textView50);
        adetLay = (LinearLayout)findViewById(R.id.aderLay);
        eksi = (Button)findViewById(R.id.button22);
        arti = (Button)findViewById(R.id.button23);
        close = (Button)findViewById(R.id.button24);
        int size=0;
        for (int i=0;i<uo.getBedenObject().size();i++){
            if (uo.getBedenObject().get(i).getAdet()>0)
                size++;
        }
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
        videoView.setZOrderOnTop(true);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.setVisibility(View.VISIBLE);
                mp.setLooping(true);
            }
        });
        resimler.setNumColumns(4);
        if (uo.getResimObject().size()>4)
            resimler.setNumColumns(uo.getResimObject().size());
        beden.setNumColumns(size);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) getApplicationContext()
                            .getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboardManager.setText(uo.getAciklama().toString());
                    Toast.makeText(getApplicationContext(), "Açıklama Kopyalandı", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){

                }
                if (Build.VERSION.SDK_INT >= 23)
                {
                    if (checkAndRequestPermissions())
                    {
                        shareImage();
                    } else {
                        checkAndRequestPermissions();
                    }
                }
                else
                {
                    shareImage();
                }
            }
        });


        arti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(adetText.getText().toString())<(uo.getBedenObject().get(z).getAdet()))
                    adetText.setText(""+(Integer.parseInt(adetText.getText().toString())+1));
                else
                    Toast.makeText(getApplicationContext(),"Maksimum Adet Girdiniz!",Toast.LENGTH_SHORT).show();
            }
        });
        eksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(adetText.getText().toString())>1)
                    adetText.setText(""+(Integer.parseInt(adetText.getText().toString())-1));
            }
        });

        if (uo.getBedenObject().size() > 0) {
            adapter = new BedenAdapter(DetayActivity.this, uo.getBedenObject());
            beden.setAdapter(adapter);
        } else {
            beden.setVisibility(View.GONE);
            kalmadi.setVisibility(View.VISIBLE);
            adetLay.setVisibility(View.GONE);
            sepete_ekle.setVisibility(View.GONE);
        }
        videoView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(DetayActivity.this, ImageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("type","video");
                intent.putExtra("link","http://modayakamoz.com/resimler/" + uo.getResimObject().get(y).getB_resim());
                startActivity(intent);
                return false;
            }
        });
        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetayActivity.this, ImageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("type","image");
                intent.putExtra("link","http://modayakamoz.com/resimler/" + uo.getResimObject().get(y).getB_resim());
                startActivity(intent);


            }
        });
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (extension.toLowerCase().equals(".mp4")){
                    if (Build.VERSION.SDK_INT >= 23)
                    {
                        if (checkAndRequestPermissions())
                        {
                            downloadFile("http://modayakamoz.com/resimler/" + uo.getResimObject().get(y).getB_resim());
                        } else {
                            checkAndRequestPermissions();
                        }
                    }
                    else
                    {
                        downloadFile("http://modayakamoz.com/resimler/" + uo.getResimObject().get(y).getB_resim());
                    }
                }
                else {
                    if (Build.VERSION.SDK_INT >= 23)
                    {
                        if (checkAndRequestPermissions())
                        {
                            new DownloadImageTask().execute("http://modayakamoz.com/resimler/" + uo.getResimObject().get(y).getB_resim());
                        } else {
                            checkAndRequestPermissions();
                        }
                    }
                    else
                    {
                        new DownloadImageTask().execute("http://modayakamoz.com/resimler/" + uo.getResimObject().get(y).getB_resim());
                    }
                }

            }
        });
        resimAdapter = new ResimAdapter(DetayActivity.this, uo.getResimObject());
        resimler.setAdapter(resimAdapter);

        aciklama.setText(uo.getAciklama());
        extension = uo.getResimObject().get(0).getB_resim().substring(uo.getResimObject().get(0).getB_resim().lastIndexOf("."));
        if (extension.toLowerCase().equals(".mp4"))    {
            videoView.setVisibility(View.VISIBLE);
            resim.setVisibility(View.GONE);
            proxyUrl = MediaLoader.getInstance(getApplicationContext()).getProxyUrl("http://modayakamoz.com/resimler/" + uo.getResimObject().get(0).getB_resim());
            videoView.setVideoPath(proxyUrl);
            videoView.start();
            share.setVisibility(View.GONE);
        }
        else {
            videoView.setVisibility(View.GONE);
            resim.setVisibility(View.VISIBLE);
            try {
                Picasso.with(getApplicationContext()).load("http://modayakamoz.com/resimler/" + uo.getResimObject().get(0).getB_resim()).into(resim, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });

            } catch (Exception e) {

            }
        }

        if (uo.getBedenObject().size() > 0 ){

        }

        beden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (uo.getBedenObject().size() > 0) {
                    z=i;
                    adetText.setText("1");

                    for(int x=0;x<beden.getChildCount();x++){
                        View c = beden.getChildAt(x);
                        TextView tv = (TextView) c.findViewById(R.id.textView30);
                        if (x==i){
                            if (x == 0) {
                                tv.setTextColor(Color.WHITE);
                                tv.setBackgroundResource(R.drawable.grid_left_renk);
                            }
                            else if(x == (uo.getBedenObject().size()-1) ) {
                                tv.setTextColor(Color.WHITE);
                                tv.setBackgroundResource(R.drawable.grid_right_renk);
                            }
                            else if (x != (uo.getBedenObject().size()-1) || x != 0 ){
                                tv.setTextColor(Color.WHITE);
                                tv.setBackgroundColor(0xFFFF4081);
                            }
                        }
                        else {
                            if (x == 0) {
                                tv.setTextColor(0xFFFF4081);
                                tv.setBackgroundResource(R.drawable.grid_left);
                            }
                            else if(x == (uo.getBedenObject().size()-1) ) {
                                tv.setTextColor(0xFFFF4081);
                                tv.setBackgroundResource(R.drawable.grid_right);
                            }
                            else if (x != (uo.getBedenObject().size()-1) || x != 0 ){
                                tv.setTextColor(0xFFFF4081);
                                tv.setBackgroundColor(Color.WHITE);
                            }


                        }

                    }
                }

            }
        });
        if (uo.getBedenObject() == null)
            beden.setVisibility(View.GONE);


        resimler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                extension = uo.getResimObject().get(i).getB_resim().substring(uo.getResimObject().get(i).getB_resim().lastIndexOf("."));
                if (extension.toLowerCase().equals(".mp4"))    {
                    resim.setVisibility(View.INVISIBLE);
                    videoView.setVisibility(View.VISIBLE);
                    share.setVisibility(View.GONE);
                    proxyUrl = MediaLoader.getInstance(getApplicationContext()).getProxyUrl("http://modayakamoz.com/resimler/" + uo.getResimObject().get(i).getB_resim());
                    videoView.setVideoPath(proxyUrl);
                    videoView.start();
                    y = i;

                }
                else {
                    share.setVisibility(View.VISIBLE);
                    videoView.setVisibility(View.INVISIBLE);
                    resim.setVisibility(View.VISIBLE);
                    try {

                        Picasso.with(getApplicationContext()).load("http://modayakamoz.com/resimler/" + uo.getResimObject().get(i).getB_resim()).into(resim, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
                        y = i;
                    } catch (Exception e) {

                    }
                }

            }
        });

        sepete_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uo.getBedenObject().size()>0 && adetLay.getVisibility() == View.VISIBLE)
                    new getData().execute(adetText.getText().toString());
            }
        });
    }
    private  boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int locationPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(DetayActivity.this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private class getData extends AsyncTask<String, String, String> {
        YuklemeFragment progress;

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getSupportFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        @Override
        protected String doInBackground(String... values) {

            ReadURL readURL = new ReadURL();
            DBHelper dbhelper = new DBHelper(getApplicationContext());
            try {
                readURL.readURL("http://modayakamoz.com/json/sepet_ekle.php?ID=" + uo.getId() + "&komisyoncu_ID=" + dbhelper.getUser().getID() + "&beden=" + uo.getBedenObject().get(z).getBeden() + "&adet=" + values[0]);


                return "0";
            } catch (Exception e) {
                return "HATA";
            }

        }

        @Override
        protected void onPostExecute(String results) {
            if (!results.equals("HATA")) {

                MainActivity.sepetAdet.setText(""+(Integer.parseInt(MainActivity.sepetAdet.getText().toString())+1));
                finish();
                overridePendingTransition(0, 0);


            }
            progress.dismiss();

        }
    }
    public Bitmap getBitmapFromURL(String imageUrl) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveImageToExternalStorage(Bitmap finalBitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/Moda Yakamoz");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Tell the media scanner about the new file so that it is
        // immediately available to the user.
        MediaScannerConnection.scanFile(DetayActivity.this, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {

            FragmentManager fm = getSupportFragmentManager();
            progress = new YuklemeFragment();
            progress.show(fm, "");
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = getBitmapFromURL(urldisplay);

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            saveImageToExternalStorage(result);
            progress.dismiss();
            Toast.makeText(getApplicationContext(),"Resim Kaydedildi.", Toast.LENGTH_LONG).show();
        }

    }
    private  void  shareImage(){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        resim.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) resim.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + uo.getResimObject().get(y).getB_resim());
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/"+uo.getResimObject().get(y).getB_resim()));
        startActivity(Intent.createChooser(share, "Share Image"));

    }

    public void downloadFile(final String url) {

        FragmentManager fm = getSupportFragmentManager();
        progress = new YuklemeFragment();
        progress.show(fm, "");
        try {
            if (url != null && !url.isEmpty()) {
                Uri uri = Uri.parse(url);
                DetayActivity.this.registerReceiver(attachmentDownloadCompleteReceive, new IntentFilter(
                        DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setMimeType(getMimeType(uri.toString()));
                request.setTitle("Video");
                request.setDescription("İndiriliyor");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uo.getResimObject().get(y).getB_resim());
                DownloadManager dm = (DownloadManager) DetayActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);
                dm.enqueue(request);
            }
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "Please insert an SD card to download file", Toast.LENGTH_SHORT).show();
        }
    }


    private String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }


    BroadcastReceiver attachmentDownloadCompleteReceive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            progress.dismiss();
            Toast.makeText(getApplicationContext(),"Video İndirildi.",Toast.LENGTH_SHORT).show();

        }
    };
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);

    }
}
