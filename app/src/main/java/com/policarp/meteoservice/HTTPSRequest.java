package com.policarp.meteoservice;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class HTTPSRequest implements Runnable{
    static final String KEY = "a0908e3afaa440ecb6395423231612";
    static final String APIREQUEST = "https://api.weatherapi.com/v1/current.json";
    static final String CITY = "Chelyabinsk";
    URL url;
    Handler handler;

    public HTTPSRequest(Handler handler) {
        try {
            url = new URL(APIREQUEST + "?key=" + KEY + "&" + "q=" + CITY +"&"+"lang=ru");
            this.handler = handler;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            Scanner in = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (in.hasNext()){
                response.append(in.nextLine());
            }
            in.close();
            connection.disconnect();
            Message msg = Message.obtain();
            msg.obj = response.toString();
            handler.sendMessage(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
