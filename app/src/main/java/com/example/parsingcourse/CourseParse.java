package com.example.parsingcourse;

import android.os.AsyncTask;
import android.util.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URL;

public class CourseParse extends AsyncTask<Void, Void, Document> {

    private static String BASE_URL = "https://ru.investing.com/currencies/%s-%s";
    private static final String MESSAGE_FAIL = "Произошла ошибка проверьте правильность данных";

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
            // получаем html страницу
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
            startFalseSetText();
        }else{
            findCoefficientCurrency(content);
            transformationsDataCurrency();
            startTrueSetText();
        }
    }

    private void findCoefficientCurrency(Document content){
        // ищем кофффициент валют
        String coefficientSymbol = content.select("span[id=last_last]").first().text();
        coefficientSymbol=coefficientSymbol.replace(",",".");
        coefficient = Double.parseDouble(coefficientSymbol);
    }

    private void transformationsDataCurrency(){
        // перемножаем числа на кофффициент валют
        Double textUser = Double.parseDouble(textUserFirst) ;
        Double result = coefficient * textUser;
        textUserSecond = String.valueOf(result);
    }

    private void unpackingPairs() {
        // распаковываем данные полученные из activity
        textUserFirst = pairsTextUser.first;
        textUserSecond = pairsTextUser.second;

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

    private void startTrueSetText(){
        // передача результатов если парсинг произошел успешно
        MainActivity mainActivity = new MainActivity();
        mainActivity.setCurrencyResult(textUserSecond);
    }

    private void startFalseSetText(){
        // передача результатов если во парсинг произошла  какая-то ошибка
        MainActivity mainActivity = new MainActivity();
        mainActivity.setCurrencyResult(MESSAGE_FAIL);
    }

}
