package com.example.parsingcourse;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CourseParse extends AsyncTask<Void , Void , String> {

    private static  String BASE_URL="https://ru.investing.com/currencies/%s-%s";

    private String currency;
    private String currency1;

    public CourseParse(String currency , String currency1){
        this.currency=currency;
        this.currency1=currency1;
        BASE_URL = String.format(BASE_URL,currency,currency1);
    }

    @Override
    protected String doInBackground(Void... voids) {
        String content = getContent(BASE_URL);

        return content;
    }

    @Override
    protected void onPostExecute(String content){
        System.out.println(content);
    }

    private String getContent(String path){
        try {
            URL url = new URL(path);
            HttpsURLConnection httpsURLConnection = openConnection(url);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpsURLConnection.getInputStream()));

            String content = "";
            String line ="";

            while((line = bufferedReader.readLine())!=null){
                content += line;
            }
            return content;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private HttpsURLConnection openConnection(URL url) throws IOException {
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestMethod("GET");
        httpsURLConnection.setReadTimeout(20000);
        httpsURLConnection.connect();

        return httpsURLConnection;
    }

    public String getCurrency1() {
        return currency1;
    }

    public void setCurrency1(String currency1) {
        this.currency1 = currency1;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
