package com.example.parsingcourse;

import androidx.appcompat.app.AppCompatActivity;

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
        fillingArrayList();
        initSpinners();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int currentId = view.getId();

        clickItemFromSpinner(currentId);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

        getDataFromEditTextCurrency();

    }

    private void fillingFields() {
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
        String currencySymbolFirst = editTextCurrencyFirst.getText().toString();
        String currencySymbolSecond = editTextCurrencySecond.getText().toString();

        addPairsData(currencySpinnerTextFirst,currencySpinnerTextSecond
                ,currencySymbolFirst,currencySymbolSecond);
    }

    private void addPairsData(String currencySpinnerText , String currencySpinnerText1
            , String currencySymbol , String currencySymbol1){
        Pair<String , String> pairSpinnerTexts =
                new Pair<String , String>(currencySpinnerText, currencySpinnerText1);

        Pair<String , String> pairSymbols =
                new Pair<String , String>(currencySymbol, currencySymbol1);

        CourseParse courseParse = new CourseParse(pairSpinnerTexts,pairSymbols);

        courseParse.execute();
    }

    private void fillingArrayList() {
        // добавляем в ArrayList массив
        Collections.addAll(spinnerListCurrencySymbols, currencySymbols);
    }

    private void initSpinners() {
        initSpinner();
        initSpinner1();
    }

    private void initSpinner() {
        spinnerCurrencyFirst.setOnItemSelectedListener(this);
        spinnerAdapterFirst = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerListCurrencySymbols);

        spinnerAdapterFirst.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrencyFirst.setAdapter(spinnerAdapterFirst);
    }

    private void initSpinner1() {
        spinnerCurrencySecond.setOnItemSelectedListener(this);
        spinnerAdapterSecond = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerListCurrencySymbols);

        spinnerAdapterSecond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurrencySecond.setAdapter(spinnerAdapterSecond);
    }

    private void clickItemFromSpinner(int currentId) {
        switch (currentId){
            case R.id.spinnerCurrency:
                break;

            case R.id.spinnerCurrency1:
                break;
        }
    }

}