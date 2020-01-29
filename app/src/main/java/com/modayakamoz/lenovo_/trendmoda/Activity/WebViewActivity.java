package com.modayakamoz.lenovo_.trendmoda.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.modayakamoz.lenovo_.trendmoda.R;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    Button back;
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView=(WebView)findViewById(R.id.web);
        back=(Button)findViewById(R.id.button13);
        webView.getSettings().setJavaScriptEnabled(true) ;
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().getDisplayZoomControls();
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        try {
            webView.loadUrl(link);

        }
        catch (Exception e){

        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });


        webView.setWebViewClient(new myWebViewClient());


    }
    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            link=webView.getUrl();
            try {
                view.loadUrl(url);
            }
            catch (Exception e){

            }
            return true;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }
}
