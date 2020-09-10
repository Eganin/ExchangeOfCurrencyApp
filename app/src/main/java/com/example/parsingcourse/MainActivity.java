package com.example.parsingcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener ,
        AdapterView.OnItemSelectedListener{

    private static final String[] currencySymbols = new String[]{"rub", "usd","eur","chf",
    "gbp","jpy","uah","kzt","byn","try","cny","aud","cad","pln"};
    private ArrayList<String> spinnerListCurrencySymbols = new ArrayList<>(14);

    private EditText editTextCurrency;
    private EditText editTextCurrency1;
    private Spinner spinnerCurrency;
    private Spinner spinnerCurrency1;
    private Button buttonTranslate;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayAdapter<String> spinnerAdapter1;

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

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int idItem = view.getId();

        switch (idItem){
            case R.id.buttonTranslate:
                getDataFromEditTextCurrency();
                break;

            case R.id.spinnerCurrency:
                break;

            case R.id.spinnerCurrency1:
                break;
        }
    }

    private void fillingFields(){
        editTextCurrency = findViewById(R.id.editTextCurrency);
        editTextCurrency1 = findViewById(R.id.editTextCurrency1);
        spinnerCurrency = findViewById(R.id.spinnerCurrency);
        spinnerCurrency1 = findViewById(R.id.spinnerCurrency1);
        buttonTranslate = findViewById(R.id.buttonTranslate);
        buttonTranslate.setOnClickListener(this);
    }

    private void getDataFromEditTextCurrency(){
        String currency = editTextCurrency.getText().toString();
        String currency1 = editTextCurrency1.getText().toString();

        CourseParse courseParse = new CourseParse(currency , currency1);

        courseParse.execute();
    }

    private void fillingArrayList(){
        // добавляем в ArrayList массив
        Collections.addAll(spinnerListCurrencySymbols,currencySymbols);
    }

    private void initSpinners(){

    }

}