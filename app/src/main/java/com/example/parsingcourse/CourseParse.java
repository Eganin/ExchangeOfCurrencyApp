package com.example.parsingcourse;

import android.os.AsyncTask;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CourseParse extends AsyncTask<Void , Void , String> {

    private static  String BASE_URL="https://ru.investing.com/currencies/%s-%s";

    private Pair<String,String> pairSpinnerTexts;
    private Pair<String,String> pairSymbols;
    private String spinnerText ;
    private String spinnerText1 ;

    public CourseParse(Pair<String,String> pairSpinnerTexts ,Pair<String,String> pairSymbols){
        this.pairSpinnerTexts=pairSpinnerTexts;
        this.pairSymbols=pairSymbols;
        //BASE_URL = String.format(BASE_URL,currency,currency1);
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

    private void unpackingPairs(){
        spinnerText = pairSpinnerTexts.first;
        spinnerText1 = pairSpinnerTexts.second;

        String symbol = pairSymbols.first;
        String sumbol1 = pairSymbols.second;
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

    public Pair<String, String> getPairSpinnerTexts() {
        return pairSpinnerTexts;
    }

    public void setPairSpinnerTexts(Pair<String, String> pairSpinnerTexts) {
        this.pairSpinnerTexts = pairSpinnerTexts;
    }

    public Pair<String, String> getPairSymbols() {
        return pairSymbols;
    }

    public void setPairSymbols(Pair<String, String> pairSymbols) {
        this.pairSymbols = pairSymbols;
    }
}
