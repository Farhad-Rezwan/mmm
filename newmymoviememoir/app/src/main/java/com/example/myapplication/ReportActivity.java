package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.networkconnection.NetworkConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportActivity<WatchHistory> extends AppCompatActivity {


    NetworkConnection networkConnection = null;

    private static final String TAG = "ReportActivity";
    Button startButton, endButton;
    TextView startTextView, endTextView, resultTextView;
    String startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        networkConnection = new NetworkConnection();

        startButton = findViewById(R.id.bt_start_Date);
        endButton = findViewById(R.id.bt_end_date);
        startTextView = findViewById(R.id.tv_starting_date);
        endTextView = findViewById(R.id.tv_ending_date);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleStartDateButton();
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEndDateButton();
            }
        });


        resultTextView = findViewById(R.id.pie_result);
        final Button showPieButton = findViewById(R.id.rep_show_pie);
        showPieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerDate();
                String[] details = {startDate, endDate};
                Log.d(TAG, "onClick: " + details.length);
                if (details.length == 2) {
                    ShowPieTask showPieTask = new ShowPieTask();
                    showPieTask.execute(details);
                }
                
            }
        });
    }

    public void registerDate() {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "Registration has failed", Toast.LENGTH_SHORT).show();
        }
        else {
            onValidationSuccess();
        }

    }
    public void onValidationSuccess() {
        Log.d(TAG, "onRegisterSuccess: ");

    }

    public boolean validate() {
        boolean valid = true;

        try {
            Date start = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
                    .parse(startDate);
            Date end = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
                    .parse(endDate);
            
            Log.d(TAG, "validate: " + start);
            Log.d(TAG, "validate: " + end);
            
            if (start.compareTo(end) < 0) {
                startTextView.setError(null);
                endTextView.setError(null);
                valid = true;
            } else if (start.compareTo(end) > 0) {
                startTextView.setError("Start should be after end");
                valid = false;
            } else if (start.compareTo(end) == 0) {
                startTextView.setError("Start and end should not be same date");
                endTextView.setError("Start and end should not be same date");
                valid = false;
            } else {
                startTextView.setError("Something went wrong");
                endTextView.setError("Something went wrong");
                valid = false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return valid;

    }

    public void initialize() {
        startDate = startTextView.getText().toString();
        endDate = endTextView.getText().toString();
    }



    private void handleStartDateButton() {
        Calendar c = Calendar.getInstance();


        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int date = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String startDateString= year + "/" + month + "/" + dayOfMonth;
                startTextView.setText(startDateString);
            }
        }, year, month, date);
        datePickerDialog.show();
    }

    private void handleEndDateButton() {
        Calendar c = Calendar.getInstance();


        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int date = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String endDateString= year + "/" + month + "/" + dayOfMonth;
                endTextView.setText(endDateString);
            }
        }, year, month, date);
        datePickerDialog.show();

    }

    private class ShowPieTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "The person with name: " + params[0] + " was added";
            Log.d(TAG, "doInBackground: " + result);
            return networkConnection.moviesWatchPerSuburb(params);
        }

        @Override
        protected void onPostExecute(String result) {
            resultTextView.setText(result);
            createPie(result);
        }

        private void createPie(String result) {

            String json = result;
            WatchHistory[] respone = new Gson().fromJson(json, WatchHistory[].class);
            for (WatchHistory s : respone) {
                Log.d(TAG, "createPie: " + s.getCinemaSuburb());
                Log.d(TAG, "createPie: " + s.getCountMoviesWatched());
            }
        }

        public class WatchHistory {
            private String cinemaSuburb;
            private int countMoviesWatched;


            public WatchHistory(String cinemaSuburb, int countMoviesWatched) {
                this.cinemaSuburb = cinemaSuburb;
                this.countMoviesWatched = countMoviesWatched;
            }

            public String getCinemaSuburb() {
                return cinemaSuburb;
            }

            public void setCinemaSuburb(String cinemaSuburb) {
                this.cinemaSuburb = cinemaSuburb;
            }

            public int getCountMoviesWatched() {
                return countMoviesWatched;
            }

            public void setCountMoviesWatched(int countMoviesWatched) {
                this.countMoviesWatched = countMoviesWatched;
            }
        }

    }
}
