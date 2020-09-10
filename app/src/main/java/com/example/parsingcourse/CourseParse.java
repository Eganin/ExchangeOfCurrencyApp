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
    private Pair<String,String> pairsTextUser;
    private String textUserFirst ;
    private String textUserSecond ;

    public CourseParse(Pair<String,String> pairSpinnerTexts ,Pair<String,String> pairsTextUser){
        this.pairSpinnerTexts=pairSpinnerTexts;
        this.pairsTextUser=pairsTextUser;
        unpackingPairs();
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
        textUserFirst = pairSpinnerTexts.first;
        textUserSecond = pairSpinnerTexts.second;

        String spinnerTextFirst = pairSpinnerTexts.first;
        String spinnerTextSecond = pairSpinnerTexts.second;
        BASE_URL = String.format(BASE_URL,spinnerTextFirst,spinnerTextSecond);
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

}
