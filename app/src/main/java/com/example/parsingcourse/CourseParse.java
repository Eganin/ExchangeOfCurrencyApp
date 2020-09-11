package com.example.parsingcourse;

import android.content.Context;
import android.content.Intent;
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
    private String spinnerTextFirst;
    private String spinnerTextSecond;
    private Double coefficient;
    private Context context;

    public CourseParse(Pair<String, String> pairSpinnerTexts, Pair<String, String> pairsTextUser,
                       Context context) {
        this.pairSpinnerTexts = pairSpinnerTexts;
        this.pairsTextUser = pairsTextUser;
        this.context=context;

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
            startFalseActivity();
        }else{
            findCoefficientCurrency(content);
            transformationsDataCurrency();
            startTrueActivity();
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

        spinnerTextFirst = pairSpinnerTexts.first;
        spinnerTextSecond = pairSpinnerTexts.second;
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
        // передача результатов если парсинг произошел успешно
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("resultCurrency",textUserSecond);
        intent.putExtra("userTextFirst",textUserFirst);
        intent.putExtra("spinnerResultFirst",spinnerTextFirst);
        intent.putExtra("spinnerResultSecond",spinnerTextSecond);

        context.startActivity(intent);
    }

    private void startFalseActivity(){
        // передача результатов если во парсинг произошла  какая-то ошибка
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra("resultCurrency",MESSAGE_FAIL);
        intent.putExtra("userTextFirst",textUserFirst);
        intent.putExtra("spinnerResultFirst",spinnerTextFirst);
        intent.putExtra("spinnerResultSecond",spinnerTextSecond);

        context.startActivity(intent);
    }

}
