package com.modayakamoz.lenovo_.trendmoda.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.modayakamoz.lenovo_.trendmoda.Helper.DBHelper;
import com.modayakamoz.lenovo_.trendmoda.Helper.ReadURL;
import com.modayakamoz.lenovo_.trendmoda.R;

public class LuncherActivity extends AppCompatActivity {
    String deviceId="";
    String token="";
    DBHelper dbHelper;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luncher);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                dbHelper = new DBHelper(getApplicationContext());
                deviceId= Settings.Secure.getString(LuncherActivity.this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                token= FirebaseInstanceId.getInstance().getToken();
                Log.d("TOKEN", "Token: " + token);
                if (token != null && deviceId != null && dbHelper.getUser().getKullaniciAdi().length() > 0)
                    new checkVersiyon().execute(token,deviceId);
                else {
                    Intent intent = new Intent(LuncherActivity.this, GirisActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
    class checkVersiyon extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            ReadURL readURL=new ReadURL();
            try {
                if(params[0].length() > 30){


                 String gelen =   readURL.readURL("http://modayakamoz.com/json/send_token.php?ID="+dbHelper.getUser().getID()+"&token="+params[0]+"&phone_id="+params[1]);

                    return gelen;

                }

            }
            catch (Exception e){
                return "HATAaaaaaaaa";
            }
            return "HATAaaaaaaaa";

        }

        @Override
        protected void onPostExecute(String s) {
            if (s.length() > 1) {
                dbHelper.deleteUser();
                Intent intent = new Intent(LuncherActivity.this, GirisActivity.class);
                startActivity(intent);
                finish();
            }
            else {

                Intent intent = new Intent(LuncherActivity.this, MainActivity.class);
                intent.putExtra("adet", s);
                startActivity(intent);
                finish();
            }

        }
    }
}
