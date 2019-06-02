package com.example.aishwarryavarshney.project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
WebView w1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       w1=(WebView)findViewById(R.id.webbb);
        w1.getSettings().setJavaScriptEnabled(true);
        w1.getSettings().setAllowFileAccess(true);
        w1.setWebViewClient(new WebViewClient());
        Toast.makeText(getApplicationContext(),finaldata.getUrl(),Toast.LENGTH_LONG).show();

        w1.loadUrl("https://ruralroadinfo.000webhostapp.com/img.php"+finaldata.getUrl());
    }
}
