package com.example.parsingcourse;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CourseParse extends AsyncTask<Void , Void , String> {

    private static final String BASE_URL="https://www.google.com/search?sxsrf=ALeKk03xlghZiU" +
            "--RuJLxEKJV1I-Oa0s9Q%3A1599750491316&source=hp&ei=W0FaX4fmD6qEwPAPzJWtyAE&q=доллар+" +
            "к+рублю&oq=доллар+&gs_lcp=CgZwc3ktYWIQARgAMgkIIxAnEEYQggIyBQgAELEDMggIABCxAx" +
            "CDATIFCAAQsQMyCAgAELEDEIMBMggIABCxAxCDATIFCAAQsQMyBQgAELEDMggIABCxAxCDATIFCAAQsQM6" +
            "BwguECcQkwI6BAgjECc6AggAOggILhCxAxCDAToFCC4QsQNQkgtY2hlgzyhoAHAAeACAAXKIAccFkgEDMy40" +
            "mAEAoAEBqgEHZ3dzLXdpeg&sclient=psy-ab";

    public CourseParse(){

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
}
