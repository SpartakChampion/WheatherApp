package com.example.ivanchepelkin.wheatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerCities;
    private SharedPreferences shareP;
    private CheckBox pressureChek;
    private CheckBox weatherDayChek;
    private CheckBox weatherWeekChek;
    private TextView countText;
    final String SAVED_CHECK_BOX1 = "saved_chek_box1";
    final String SAVED_CHECK_BOX2 = "saved_chek_box2";
    final String SAVED_CHECK_BOX3 = "saved_chek_box3";

    static final int cnt_requestCode = 1;

    private static final String KEY_inputCount = "KEY_inputCount";
    private String inputCount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "запуск onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnClickListeners();
        loadCheckBoxPosition();
        chekBundle(savedInstanceState); //метод проверки bundle
    }
    // Метод сохранения данных при пересоздании Activity
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_inputCount,inputCount); //сохраняю по ключу переменнуюю count
    }
    // метод вывода данных после сохранения при пересоздании Activity
    private void chekBundle(Bundle savedInstanceState){
        if (savedInstanceState != null){
            inputCount = savedInstanceState.getString(KEY_inputCount); //извлекаем данные счетчика
            countText.setText(inputCount);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "запуск onStart MainActivity");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "запуск onResume MainActivity");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "запуск onPause MainActivity");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "запуск onStop MainActivity");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "запуск onDestroy MainActivity");
    }

    // метод инициаизирует вьюшки через id
    private void initViews() {
        recyclerCities = findViewById(R.id.recycler_Cities);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerCities.setLayoutManager(linearLayoutManager);
        recyclerCities.setAdapter(new ListRecyclerAdapter(new WeakReference(this)));

        countText = findViewById(R.id.count);
        pressureChek = findViewById(R.id.pressureCheck);
        weatherDayChek = findViewById(R.id.weatherDayCheck);
        weatherWeekChek = findViewById(R.id.weatherWeekCheck);
    }

    private void setOnClickListeners(){
        pressureChek.setOnClickListener(MainActivity.this);
        weatherWeekChek.setOnClickListener(MainActivity.this);
        weatherDayChek.setOnClickListener(MainActivity.this);
    }

    void saveChekBoxPosition(boolean position, CheckBox checkBox){
        shareP = getPreferences(MODE_PRIVATE);//Константа MODE_PRIVATE используется для настройки доступа
        SharedPreferences.Editor ed = shareP.edit(); //чтобы редактировать данные, необходим объект Editor

        if (checkBox == pressureChek) {
            ed.putBoolean(SAVED_CHECK_BOX1, position);
        } else if (checkBox == weatherDayChek) {
            ed.putBoolean(SAVED_CHECK_BOX2, position);
        } else if (checkBox == weatherWeekChek) {
            ed.putBoolean(SAVED_CHECK_BOX3, position);
        }
        ed.apply();
    }

    void loadCheckBoxPosition() {
        shareP = getPreferences(MODE_PRIVATE);

        CheckBox [] arrCheckBox = {pressureChek,weatherDayChek,weatherWeekChek};
        String [] arrSAVED_CHECk_BOX = {SAVED_CHECK_BOX1,SAVED_CHECK_BOX2,SAVED_CHECK_BOX3};
        for (int i = 0; i < arrSAVED_CHECk_BOX.length ; i++) {
            Boolean savedCheckBoxPosition = shareP.getBoolean(arrSAVED_CHECk_BOX[i], false);
            if (!savedCheckBoxPosition.equals(false) && arrCheckBox[i].equals(pressureChek)) {
                arrCheckBox[i].setChecked(true);
                WeatherController.getInstance().setPressureStatus(true);
            } else if (!savedCheckBoxPosition.equals(false) && arrCheckBox[i].equals(weatherDayChek)) {
                arrCheckBox[i].setChecked(true);
                WeatherController.getInstance().setWeatherDayStatus(true);
            } else if (!savedCheckBoxPosition.equals(false) && arrCheckBox[i].equals(weatherWeekChek)) {
                arrCheckBox[i].setChecked(true);
                WeatherController.getInstance().setWeatherWeekStatus(true);
            }
        }
    }
    @Override
    public void onClick(View v) {

            if (pressureChek.isChecked()){
                WeatherController.getInstance().setPressureStatus(pressureChek.isChecked());
                saveChekBoxPosition(pressureChek.isChecked(),pressureChek);
            }
            else if (!pressureChek.isChecked()){
                WeatherController.getInstance().setPressureStatus(pressureChek.isChecked());
                saveChekBoxPosition(pressureChek.isChecked(),pressureChek);
            }

            if (weatherDayChek.isChecked()){
                WeatherController.getInstance().setWeatherDayStatus(weatherDayChek.isChecked());
                saveChekBoxPosition(weatherDayChek.isChecked(),weatherDayChek);
            }
            else if (!weatherDayChek.isChecked()){
                WeatherController.getInstance().setWeatherDayStatus(weatherDayChek.isChecked());
                saveChekBoxPosition(weatherDayChek.isChecked(),weatherDayChek);
            }

            if (weatherWeekChek.isChecked()){
                WeatherController.getInstance().setWeatherWeekStatus(weatherWeekChek.isChecked());
                saveChekBoxPosition(weatherWeekChek.isChecked(),weatherWeekChek);
            }
            else if (!weatherWeekChek.isChecked()){
                WeatherController.getInstance().setWeatherWeekStatus(weatherWeekChek.isChecked());
                saveChekBoxPosition(weatherWeekChek.isChecked(),weatherWeekChek);
            }
            //startActivityForResult(intent, 1);
    }

    //метод ожидает ответ от 2 экрана, он переопределенный
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == cnt_requestCode) {
            if (resultCode == RESULT_OK) {
                inputCount = data.getStringExtra("name");
                countText.setText(inputCount);
            }
        }
    }
}
