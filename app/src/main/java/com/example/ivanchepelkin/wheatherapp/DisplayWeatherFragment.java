package com.example.ivanchepelkin.wheatherapp;

import android.support.v4.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DisplayWeatherFragment extends Fragment implements View.OnClickListener {

    private int length = Weather.getLength();// количество выводимых строк и количесвто экземпляров Weather[];
    private int position;
    private TextView textView;
    private TextView displayPressure;
    private TextView displayWeatherDay;
    private TextView displayWeatherWeek;

    private Button button;

    static final String keySendResultPerson = "keySendResult";
    static final String textInputKey = "textInputKey";
    static int cnt = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // находим наж layout под фрагмент, создаем фрагмент
        return inflater.inflate(R.layout.fragment_display_wheather, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);// инициализируем наши вьюшки
        setOnClickListeners();
        displayText(position);
    }


//    public  void onBackPressed(){
//        cnt = cnt +1;
//        String cntString = String.valueOf(cnt);
//        Intent intent = new Intent();
//        intent.putExtra("name",cntString);
//        setResult(RESULT_OK,intent); //передаем RESULT OK - константа успешного вызова и наш intent
//        finish();//закрываем активность
//    }

    private void initViews(View view) {
        textView = view.findViewById(R.id.dispayWheather);
        button = view.findViewById(R.id.button_send_Message);
        displayPressure = view.findViewById(R.id.dispayPressure);
        displayWeatherDay = view.findViewById(R.id.dispayWeatherDay);
        displayWeatherWeek = view.findViewById(R.id.dispayWeatherWeek);
    }

    private void setOnClickListeners() {
        button.setOnClickListener(this);
    }

    // Вывод прогноза в textVeiw
    private void displayText(int position) {
        Weather[] weathersForCitiesArr = new Weather[length];
        weathersForCitiesArr = Weather.setWeathers(weathersForCitiesArr);

        String category = weathersForCitiesArr[position].getWeatherCity();

        textView.setText(category);

        boolean a = WeatherController.getInstance().isPressureStatus();
        boolean b = WeatherController.getInstance().isWeatherDayStatus();
        boolean c = WeatherController.getInstance().isWeatherWeekStatus();

        if (a) {
            displayPressure.setText(getString(R.string.давление) + getString(R.string.двоеточие) + weathersForCitiesArr[position].getPressure());
        }
        if (b) {
            displayWeatherDay.setText(getString(R.string.погодаНаЗавтра) + getString(R.string.двоеточие)  +weathersForCitiesArr[position].getWeatherDay());
        }
        if (c) {
            displayWeatherWeek.setText(getString(R.string.ПогодаНаНеделю) + getString(R.string.двоеточие)  +weathersForCitiesArr[position].getWetherWeek());
        }
    }

    public void setWeatherDispay(int position){
        this.position = position;
    }
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_send_Message) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String sendResult = textView.getText().toString();
            intent.setType("text/plain");//задаем тип передаваемых данных
            intent.putExtra(keySendResultPerson, sendResult);
            String chooserTitle = getString(R.string.chooser_title);
            Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
            try {
                startActivity(chosenIntent);
            } catch (ActivityNotFoundException e) {
                //Toast.makeText(DisplayWheatherFragment.this, R.string.no_app, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
