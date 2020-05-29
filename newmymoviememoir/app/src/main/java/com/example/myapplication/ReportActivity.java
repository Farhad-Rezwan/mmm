package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.networkconnection.NetworkConnection;
import com.github.lzyzsd.randomcolor.RandomColor;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    NetworkConnection networkConnection = null;

    private static final String TAG = "ReportActivity";
    Button startButton, endButton;
    TextView startTextView, endTextView, resultTextView;
    String startDate, endDate, yearSelected;

    //    pie everything
    private ArrayList<Integer> yAxisDataPie = new ArrayList<>();
    private ArrayList<String> xAxisDataPie = new ArrayList<>();
    private PieChart pieChart;


    //    bar everything
    private ArrayList<Integer> yAxisDataBar = new ArrayList<>();
    private ArrayList<String> xAxisDataBar = new ArrayList<>();
    private TextView barText;
    private Button barButton;
    private Spinner barSpinner;
    private BarChart barChart;

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
//        Description description = new Description();
//        description.setTextColor(ColorTemplate.VORDIPLOM_COLORS[2]);
//        description.setText(getString(R.string.pieDescription));
//        pieChart.setDescription(description);
        pieChart.setRotationEnabled(true);

        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Cinema Suburb Preference");
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






    //        Everything of Bar Chart
        barText = findViewById(R.id.bar_result);
        barButton = findViewById(R.id.bar_button);
        barSpinner = findViewById(R.id.year_spinner);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        barSpinner.setAdapter(adapter);
        barSpinner.setOnItemSelectedListener(this);

        barChart = (BarChart) findViewById(R.id.bar_chart);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);



    }

    public void registerDate() {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "Invalid Date Range", Toast.LENGTH_SHORT).show();
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
            String result = "task name: " + params[0];
            Log.d(TAG, "doInBackground: " + result);
            return networkConnection.moviesWatchPerSuburb(params);
        }

        @Override
        protected void onPostExecute(String result) {
            resultTextView.setText(result);
            String json = result;
            WatchHistory[] resposne = new Gson().fromJson(json, WatchHistory[].class);


            for (WatchHistory s : resposne) {

                yAxisDataPie.add(s.getCountMoviesWatched());
                xAxisDataPie.add(s.getCinemaSuburb());
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
        Log.d(TAG, "addDataSet: yAxisData" + yAxisDataPie);

        for(int i = 0; i < yAxisDataPie.size(); i++) {
            yEntriys.add(new PieEntry((yAxisDataPie.get(i)), i));
        }
        for(int i = 0; i < xAxisDataPie.size(); i++) {
            xEntrys.add(xAxisDataPie.get(i));
        }
        // create the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntriys, "Employee sales");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setValueFormatter(new PercentFormatter(pieChart));
        pieChart.setUsePercentValues(true);

        RandomColor randomColor = new RandomColor();
        int[] colors = randomColor.randomColor(yEntriys.size());
//
        pieDataSet.setColors(colors);
//
//
        Description description = new Description();
        description.setText("Analysing movie watched by suburb");
        pieChart.setDescription(description);
//


        // Create pie data object

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }


    //for spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();


//        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        yearSelected = text;

        String[] details = {yearSelected};
        if (details.length == 1) {
            ShowBar showBar = new ShowBar();
            showBar.execute(details);
        }






    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    private class ShowBar extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "Task Name" + params[0];
            Log.d(TAG, "doInBackground: " + result);
            return networkConnection.moviesWatchedPerMonth(params);
        }

        @Override
        protected void onPostExecute(String result) {
            barText.setText(result);


            String json = result;
            MonthHistory[] response = new Gson().fromJson(json, MonthHistory[].class);


            for (MonthHistory s : response) {

                yAxisDataBar.add(s.getCountMovieWatched());
                xAxisDataBar.add(s.getMonth());
                Log.d(TAG, "createBar: " + s.getMonth());
                Log.d(TAG, "createBar: " + s.getCountMovieWatched());
            }

//            ArrayList<String> months = new ArrayList<String>();
//            months.add("January");
//            months.add("February");
//            months.add("March");
//            months.add("April");
//            months.add("May");
//            months.add("June");
//            months.add("July");
//            months.add("August");
//            months.add("September");
//            months.add("October");
//            months.add("November");
//            months.add("December");
//
//            ArrayList<MonthHistory> orderedMonthHistory= new ArrayList<>();
//
//            for (int i = 0; i < months.size(); i++) {
//                orderedMonthHistory.add(new MonthHistory(months.get(i)));
//            }
//            Log.d(TAG, "onPostExecute: orderedMonthHistory populated" + orderedMonthHistory);
//
//            for (int i = 0; i < orderedMonthHistory.size(); i++){
//                for (int j = 0; j < response.length; j++) {
//                    if (orderedMonthHistory.get(i).getMonth().equals(response[j].getMonth())){
//                        orderedMonthHistory.get(i).setCountMovieWatched(response[j].getCountMovieWatched());
//                        break;
//                    }
//                    else {
//                        orderedMonthHistory.get(i).setCountMovieWatched(0);
//                    }
//                }
//            }




            addDataSetForBar();
        }


    }
    public class MonthHistory {
        private String month;
        private int countMovieWatched;


        public MonthHistory(String month) {
            this.month = month;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public int getCountMovieWatched() {
            return countMovieWatched;
        }

        public void setCountMovieWatched(int countMovieWatched) {
            this.countMovieWatched = countMovieWatched;
        }
    }

    private void addDataSetForBar() {
        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 0; i < yAxisDataBar.size(); i++){
            yVals.add(new BarEntry(i, yAxisDataBar.get(i)));
        }

        BarDataSet barDataSet = new BarDataSet(yVals, "Month count");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setDrawValues(true); // values will be shown above the graph

        BarData barData = new BarData(barDataSet);

        barChart.setData(barData);
        barChart.invalidate();
        barChart.animateY(500);

    }


}

