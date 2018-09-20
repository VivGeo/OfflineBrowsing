package com.not.offline;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnSendSMS;
    String txtPhoneNo = "2893128934";
    EditText txtMessage;
    public static WebView mWebView;
    public static StringBuilder html = new StringBuilder();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        // Here, thisActivity is the current activity

        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = txtMessage.getText().toString();
                if (message.length() > 0) {
                    sendSMS(txtPhoneNo, message);
                } else {
                    Toast.makeText(getBaseContext(),
                            "Please enter both phone number and message.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendSMS(String phoneNumber, String message) {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }

    public static void setUrl(String encodeValue) {

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        Log.v("here", "1");
        byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
        Log.v("here", "2");

        String htmlData = new String(decodeValue);
        Log.v("here", htmlData);

        // htmlData = "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" />" + htmlData;
        // mWebView.loadUrl("file:///android_asset/index.html");
        //mWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
        //mWebView.loadData(htmlData, "text/html; charset=utf-8", "UTF-8");
        //mWebView.loadData(htmlData, "text/html", "charset=UTF-8");
        try {
            //mWebView.loadDataWithBaseURL("file:///android_asset/", htmlData, "text/html", "UTF-8", null);
            mWebView.loadData(htmlData, "text/html; charset=utf-8", "UTF-8");
        } catch (Exception e){
            Log.v("Exception caught", e.getMessage());
        }
        //mWebView.loadUrl("http://www.nytimes.com");
        //set textview

    }
}
