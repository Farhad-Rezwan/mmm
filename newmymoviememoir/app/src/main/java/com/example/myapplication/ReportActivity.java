package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
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

//    pie everything
//    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 16.89f};
    private ArrayList<Integer> yAxisData = new ArrayList<>();
//    private String[] xData = {"Mitch", "Jessica", "Kelsey", "Sam", "Robert", "Farhad", "Adnan"};
    private ArrayList<String> xAxisData = new ArrayList<>();

    private PieChart pieChart;

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

//        everything on piechart

        Log.d(TAG, "onCreate: Starting to create chart");

        pieChart = (PieChart) findViewById(R.id.chart);

        Description description = new Description();
        description.setTextColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        description.setText(getString(R.string.pieDescription));
        pieChart.setDescription(description);
        pieChart.setRotationEnabled(true);

        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Super Cool Chart");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);

        pieChart.setTouchEnabled(true);
        MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
        pieChart.setMarker(mv);


//        addDataSet();


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value Selected form chart");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());


            }

            @Override
            public void onNothingSelected() {

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
            String json = result;
            WatchHistory[] resposne = new Gson().fromJson(json, WatchHistory[].class);


            for (WatchHistory s : resposne) {

                yAxisData.add(s.getCountMoviesWatched());
                xAxisData.add(s.getCinemaSuburb());
                Log.d(TAG, "createPie: " + s.getCinemaSuburb());
                Log.d(TAG, "createPie: " + s.getCountMoviesWatched());

            }
            addDataSet();
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

    private void addDataSet() {
        Log.d(TAG, "addDataSet: started");

        ArrayList<PieEntry> yEntriys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yAxisData.size(); i++) {
            yEntriys.add(new PieEntry(yAxisData.get(i), i));
        }
        for(int i = 1; i <xAxisData.size(); i++) {
            xEntrys.add(xAxisData.get(i));
        }
        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntriys, "Employee sales");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        // add colors
        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(Color.GRAY);
//        colors.add(Color.BLUE);
//        colors.add(Color.RED);
//        colors.add(Color.GREEN);
//        colors.add(Color.CYAN);
//        colors.add(Color.YELLOW);
//        colors.add(Color.MAGENTA);

//        pieDataSet.setColor(colors.get(0));


        final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
                Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};

        for(int c: MY_COLORS)
            colors.add(c);

        pieDataSet.setColors(colors);
        // add legend to chart

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);


        // Create pie data object

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }



}
