package com.example.myapplication.fragment;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MyMarkerView;
import com.example.myapplication.R;
import com.example.myapplication.networkconnection.NetworkConnection;
import com.github.lzyzsd.randomcolor.RandomColor;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ReportFragment extends Fragment implements AdapterView.OnItemSelectedListener{


    private NetworkConnection networkConnection = null;

    private static final String TAG = "ReportActivity";
    private Button startButton, endButton;
    private TextView startTextView, endTextView;
    private String startDate, endDate, yearSelected;

    //    pie everything
    private ArrayList<Integer> yAxisDataPie = new ArrayList<>();
    private ArrayList<String> xAxisDataPie = new ArrayList<>();
    private PieChart pieChart;


    //    bar everything
    private ArrayList<Integer> yAxisDataBar = new ArrayList<>();
    private ArrayList<String> xAxisDataBar = new ArrayList<>();
    private Button barButton;
    private Spinner barSpinner;
    private BarChart barChart;


    public ReportFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.report_fragment, container, false);

        networkConnection = new NetworkConnection();

        startButton = view.findViewById(R.id.bt_start_Date);
        endButton = view.findViewById(R.id.bt_end_date);
        startTextView = view.findViewById(R.id.tv_starting_date);
        endTextView = view.findViewById(R.id.tv_ending_date);

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


        final Button showPieButton = view.findViewById(R.id.rep_show_pie);
        showPieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xAxisDataPie.clear();
                yAxisDataPie.clear();
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
        pieChart = (PieChart) view.findViewById(R.id.chart);
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




        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
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



        //  Everything of Bar Chart
        // bar button to show the bar chart
        barButton = view.findViewById(R.id.bar_button);


        //  year spinner to add assign year
        barSpinner = view.findViewById(R.id.year_spinner);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.year_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        barSpinner.setAdapter(adapter);
        barSpinner.setOnItemSelectedListener(this);
        barChart = (BarChart) view.findViewById(R.id.bar_chart);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);


        return view;
    }


    //  validates the date rages after innitializing the values before taking data for chart

    public void registerDate() {
        initialize();
        if (!validate()) {
            Toast.makeText(this.getActivity(), "Invalid Date Range", Toast.LENGTH_SHORT).show();
        }
        else {
            onValidationSuccess();
        }

    }

    public void onValidationSuccess() {
        Log.d(TAG, "onRegisterSuccess: ");

    }


    // validates the date range.
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


    // innitializes the values of dates.
    public void initialize() {
        startDate = startTextView.getText().toString();
        endDate = endTextView.getText().toString();
    }



    private void handleStartDateButton() {
        Calendar c = Calendar.getInstance();


        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        final int date = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String endDateString= year + "/" + month + "/" + dayOfMonth;
                endTextView.setText(endDateString);
            }
        }, year, month, date);
        datePickerDialog.show();

    }


    // Async task for fetching the data form the Rest server, and when post executes, the pie chart
    private class ShowPieTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "task name: " + params[0];
            Log.d(TAG, "doInBackground: " + result);
            return networkConnection.moviesWatchPerSuburb(params);
        }

        @Override
        protected void onPostExecute(String result) {
            String json = result;
            ShowPieTask.WatchHistory[] resposne = new Gson().fromJson(json, ShowPieTask.WatchHistory[].class);


            for (ShowPieTask.WatchHistory s : resposne) {

                yAxisDataPie.add(s.getCountMoviesWatched());
                xAxisDataPie.add(s.getCinemaSuburb());
                Log.d(TAG, "createPie: " + s.getCinemaSuburb());
                Log.d(TAG, "createPie: " + s.getCountMoviesWatched());

            }
            if (resposne.length > 0 ) {
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


                // getting random color values
                RandomColor randomColor = new RandomColor();
                int[] colors = randomColor.randomColor(yEntriys.size());
                pieDataSet.setColors(colors);

                // defining the legend for chart
                Legend legend = pieChart.getLegend();
                List<LegendEntry> legendEntries = new ArrayList<>();

                // assigning color to separate labels
                for (int i = 0; i < xEntrys.size(); i++) {
                    LegendEntry legendEntry = new LegendEntry();
                    legendEntry.formColor = colors[i];
                    legendEntry.label = (xEntrys.get(i));
                    legendEntries.add(legendEntry);
                }



                legend.setEnabled(true);
                legend.setCustom(legendEntries);
                Description description = new Description();
                description.setText("Analysing movie watched by suburb");
                pieChart.setDescription(description);


                // Create pie data object

                PieData pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieChart.invalidate();
            }
        }



        // to save data of cinema suburb and watch count
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



    // for bar chart spinner on item selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

//        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();

        yearSelected = text;
        yAxisDataBar.clear();
        xAxisDataBar.clear();

        String[] details = {yearSelected};
        if (details.length == 1) {
            ShowBar showBar = new ShowBar();
            showBar.execute(details);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    // async task to show the bar, gets data from the Rest and when success assigns data, and lagend for the chart
    private class ShowBar extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "Task Name" + params[0];
            Log.d(TAG, "doInBackground: " + result);
            return networkConnection.moviesWatchedPerMonth(params);
        }

        @Override
        protected void onPostExecute(String result) {


//            persing the json object
            String json = result;
            MonthHistory[] response = new Gson().fromJson(json, MonthHistory[].class);


            for (MonthHistory s : response) {

                yAxisDataBar.add(s.getCountMovieWatched());
                xAxisDataBar.add(s.getMonth());
                Log.d(TAG, "createBar: " + s.getMonth());
                Log.d(TAG, "createBar: " + s.getCountMovieWatched());
            }



            ArrayList<BarEntry> yVals = new ArrayList<>();

            for (int i = 0; i < yAxisDataBar.size(); i++){
                yVals.add(new BarEntry(i, yAxisDataBar.get(i)));
            }

            BarDataSet barDataSet = new BarDataSet(yVals, "Month count");
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
            barDataSet.setDrawValues(true); // values will be shown above the graph






            Legend legend = barChart.getLegend();
            List<LegendEntry> legendEntries = new ArrayList<>();

            //   adding legend entries
            for (int i = 0; i < xAxisDataBar.size(); i++) {
                LegendEntry legendEntry = new LegendEntry();
                legendEntry.label = (xAxisDataBar.get(i));
                legendEntries.add(legendEntry);
            }
            legend.setEnabled(true);
            legend.setCustom(legendEntries);


            // Assigning bar data into chart
            BarData barData = new BarData(barDataSet);

            barChart.setData(barData);
            barChart.invalidate();
            barChart.animateY(500);
        }


    }

    // for saving data for month and movie watch per month for that year
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

}
