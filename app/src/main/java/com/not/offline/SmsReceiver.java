package com.not.offline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by hussain on 2017-12-02.
 */

public class SmsReceiver extends BroadcastReceiver {
    /*@Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        if (bundle != null)
        {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            msgs[0] = SmsMessage.createFromPdu((byte[])pdus[0]);
            str = msgs[0].getMessageBody().toString();
            //    str += "SMS from " + msgs[i].getOriginatingAddress();
            //for (int i=0; i<msgs.length; i++){
            //   msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            //    str += "SMS from " + msgs[i].getOriginatingAddress();
            //    str += " :";
            //    str += msgs[i].getMessageBody().toString();
            //    str += "";
            //}
            Log.v("help", str);
            str = str.replace("Sent from your Twilio trial account - ", "");
            Log.v("help", str);
            str = str.replace("SMS from +12893128934 :", "");
            Log.v("help", str);
            //---display the new SMS message---
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            MainActivity.setUrl(str);
        }
    }*/
    private SharedPreferences preferences;
    public static Boolean ready = new Boolean(false);
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        //Log.v("help", msgBody);
                        if (msgBody.contains("end")) {
                            ready = true;
                            Log.v("total", "true");

                        } else {
                            MainActivity.html.append(msgBody);
                            Log.v("part", MainActivity.html.toString());
                        }

                    }
                    //Log.v("?", html.toString());
                    if (ready == true) {
                        String str = MainActivity.html.toString();
                        Log.v("total", str);
                        str = str.replace("Sent from your Twilio trial account - ", "");
                        Log.v("total", str);
                        MainActivity.setUrl(str);
                        MainActivity.html = new StringBuilder();
                        ready = false;
                    }
                } catch(Exception e){
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }
}
