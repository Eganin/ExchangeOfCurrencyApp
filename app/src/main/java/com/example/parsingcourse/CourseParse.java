package com.example.parsingcourse;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URL;

public class CourseParse extends AsyncTask<Void, Void, Document> {

    private static String BASE_URL = "https://ru.investing.com/currencies/%s-%s";

    private Pair<String, String> pairSpinnerTexts;
    private Pair<String, String> pairsTextUser;
    private String textUserFirst;
    private String textUserSecond;
    private Double coefficient;

    public CourseParse(Pair<String, String> pairSpinnerTexts, Pair<String, String> pairsTextUser) {
        this.pairSpinnerTexts = pairSpinnerTexts;
        this.pairsTextUser = pairsTextUser;
        unpackingPairs();
    }

    @Override
    protected Document doInBackground(Void... voids) {
        try {
            Document page = getPage();
            return page;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Document content) {
        /*
        в данном методе получаем коэфициент валют посредсвом парсинга
         */
        if(content == null){
            startFalseActivity();
        }else{
            findCoefficientCurrency(content);
            transformationsDataCurrency();
            startTrueActivity();
        }
    }

    private void findCoefficientCurrency(Document content){
        String coefficientSymbol = content.select("span[id=last_last]").first().text();
        coefficientSymbol=coefficientSymbol.replace(",",".");
        coefficient = Double.parseDouble(coefficientSymbol);
    }

    private void transformationsDataCurrency(){
        Double textUser = Double.parseDouble(textUserFirst) ;
        Double result = coefficient * textUser;
        textUserSecond = String.valueOf(result);
    }

    private void unpackingPairs() {
        // распаковываем данные полученные из activity
        textUserFirst = pairSpinnerTexts.first;
        textUserSecond = pairSpinnerTexts.second;

        String spinnerTextFirst = pairSpinnerTexts.first;
        String spinnerTextSecond = pairSpinnerTexts.second;
        // формируем url
        BASE_URL = String.format(BASE_URL, spinnerTextFirst, spinnerTextSecond);
    }


    private Document getPage() throws IOException {
        /*
        получаем содержимое страницы с помощью Jsoup
         */
        Document page = Jsoup.parse(new URL(BASE_URL), 30000);
        return page;
    }

    private void startTrueActivity(){
        Intent intent = new Intent(,MainActivity.class);
        intent.putExtra("ResultParsing",textUserSecond);

    }

    private void startFalseActivity(){
        Intent intent = new Intent(,MainActivity.class);
        intent.putExtra("ResultParsing","Произошла ошибка проверьте правильность данных");
    }

}
