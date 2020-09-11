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
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    // массив с основними инициалами валют
    private static final String[] currencySymbols = new String[]{"rub", "usd", "eur", "chf",
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
        fillingFields();
        getDataFromCourseParse();
        fillingArrayList();
        initSpinners(spinnerCurrencyFirst, spinnerAdapterFirst);
        initSpinners(spinnerCurrencySecond, spinnerAdapterSecond);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // обработчик  spinner
        int currentId = view.getId();
        clickItemFromSpinner(currentId);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        spinner.setOnItemSelectedListener(this);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerListCurrencySymbols);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void clickItemFromSpinner(int currentId) {
        // оюработчик обоих spinner
        switch (currentId) {
            case R.id.spinnerCurrency:
                break;

            case R.id.spinnerCurrency1:
                break;
        }
    }

    private void getDataFromCourseParse() {
        Intent currentIntent = getIntent();

        String resultCurrency = currentIntent.getStringExtra("resultCurrency");
        if (resultCurrency == null) {
            return;
        }
        String userTextFirst = currentIntent.getStringExtra("userTextFirst");
        String spinnerResultFirst = currentIntent.getStringExtra("spinnerResultFirst");
        String spinnerResultSecond = currentIntent.getStringExtra("spinnerResultSecond");

        fillingEditTextsCourseParse(resultCurrency, userTextFirst);
        fillingSpinnersCourseParse(spinnerResultFirst, spinnerCurrencyFirst);
        fillingSpinnersCourseParse(spinnerResultSecond, spinnerCurrencySecond);
    }

    private void fillingEditTextsCourseParse(String resultCurrency, String userTextFirst) {
        editTextCurrencySecond.setText(resultCurrency);
        editTextCurrencyFirst.setText(userTextFirst);
    }

    private void fillingSpinnersCourseParse(String spinnerResultText, Spinner spinner) {
        //  замена элементов в spinner
        int positionTestSpinner = spinnerListCurrencySymbols.indexOf(spinnerResultText);
        spinner.setSelection(positionTestSpinner);
    }

}