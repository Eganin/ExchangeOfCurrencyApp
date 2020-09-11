package com.example.parsingcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // массив с основними инициалами валют
    private static String[] currencySymbols = new String[]{"rub", "usd", "eur", "chf",
            "gbp", "jpy", "uah", "kzt", "byn", "try", "cny", "aud", "cad", "pln"};
    private ArrayList<String> spinnerListCurrencySymbols = new ArrayList<>(14);

    private EditText editTextCurrencyFirst;
    private EditText editTextCurrencySecond;
    private Spinner spinnerCurrencyFirst;
    private Spinner spinnerCurrencySecond;
    private Button buttonTranslate;
    private ArrayAdapter<String> spinnerAdapterFirst;
    private ArrayAdapter<String> spinnerAdapterSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataFromCourseParse();
    }


    @Override
    public void onClick(View view) {
        // обрабатываем кнопку которая запускает остальные процессы
        getDataFromEditTextCurrency();

    }

    private void fillingFields() {
        // заполнение полей
        editTextCurrencyFirst = findViewById(R.id.editTextCurrency);
        editTextCurrencySecond = findViewById(R.id.editTextCurrency1);
        spinnerCurrencyFirst = findViewById(R.id.spinnerCurrency);
        spinnerCurrencySecond = findViewById(R.id.spinnerCurrency1);
        buttonTranslate = findViewById(R.id.buttonTranslate);
        buttonTranslate.setOnClickListener(this);

        fillingArrayList();
        initSpinners(spinnerCurrencyFirst, spinnerAdapterFirst);
        initSpinners(spinnerCurrencySecond, spinnerAdapterSecond);
    }

    private void getDataFromEditTextCurrency() {
        // получаем текщие значения от пользователя

        String currencySpinnerTextFirst = spinnerCurrencyFirst.getSelectedItem().toString();
        String currencySpinnerTextSecond = spinnerCurrencySecond.getSelectedItem().toString();
        String currencyTextUserFirst = editTextCurrencyFirst.getText().toString();
        String currencyTextUserSecond = editTextCurrencySecond.getText().toString();

        System.out.println(currencySpinnerTextFirst);
        System.out.println(currencySpinnerTextSecond);

        // добавляем данные в пары
        addPairsData(currencySpinnerTextFirst, currencySpinnerTextSecond
                , currencyTextUserFirst, currencyTextUserSecond);
    }

    private void addPairsData(String currencySpinnerText, String currencySpinnerText1
            , String currencyTextUser, String currencyTextUserSecond) {
        /*
        в этом методе для структуризации
        добавляем полученные значения в Pair
        который модет содержать по два элемента
         */
        Pair<String, String> pairsSpinnerText =
                new Pair<String, String>(currencySpinnerText, currencySpinnerText1);

        Pair<String, String> pairsTextUser =
                new Pair<String, String>(currencyTextUser, currencyTextUserSecond);

        // создаем и запускаем асинхронную задачу
        CourseParse courseParse = new CourseParse(pairsSpinnerText, pairsTextUser,
                getApplicationContext());

        courseParse.execute();
    }

    private void fillingArrayList() {
        // добавляем в ArrayList массив
        Collections.addAll(spinnerListCurrencySymbols, currencySymbols);
    }

    private void initSpinners(Spinner spinner, ArrayAdapter<String> adapter) {
        // инициализация и заполнение обоих спиннеров
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerListCurrencySymbols);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    private void getDataFromCourseParse() {
        Intent currentIntent = getIntent();

        fillingFields();

        String resultCurrency = currentIntent.getStringExtra("resultCurrency");
        if (resultCurrency == null) {
            // если это первый запуск выходим из метода
            return;
        }
        String userTextFirst = currentIntent.getStringExtra("userTextFirst");
        String spinnerResultFirst = currentIntent.getStringExtra("spinnerResultFirst");
        String spinnerResultSecond = currentIntent.getStringExtra("spinnerResultSecond");

        fillingEditTextsCourseParse(resultCurrency, userTextFirst);
        fillingSpinnersCourseParse(spinnerResultFirst,spinnerCurrencyFirst);
        fillingSpinnersCourseParse(spinnerResultSecond,spinnerCurrencySecond);
    }

    private void fillingEditTextsCourseParse(String resultCurrency, String userTextFirst) {
        editTextCurrencySecond.setText(resultCurrency);
        editTextCurrencyFirst.setText(userTextFirst);
    }

    private void fillingSpinnersCourseParse(String spinnerResultText , Spinner spinner){
        int position = Arrays.asList(currencySymbols).indexOf(spinnerResultText);

        spinner.setSelection(position);
    }
}