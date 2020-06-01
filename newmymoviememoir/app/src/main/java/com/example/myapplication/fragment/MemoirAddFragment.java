package com.example.myapplication.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.memoirpersoncinemacred.Cinema;
import com.example.myapplication.memoirpersoncinemacred.MovieSearch;
import com.example.myapplication.memoirpersoncinemacred.Person;
import com.example.myapplication.networkconnection.NetworkConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MemoirAddFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "MemoirAddFragment";


    NetworkConnection networkConnection = null;

    private MovieSearch movieSearch;
    private Cinema cinema;

    private TextView name, releaseYear, rating, watchDate, watchTime, choosenCinema;
    private Spinner cinemaChoise;
    private EditText addComment;
    private Button addCinemaBtn, addMemoirBtn;
    private String movieNameStr, releaseDateStr, ratingStr, dateTimeWatchedStr, cinemaNameStr, commentStr;

    private DatePickerDialog.OnDateSetListener memoirDateSetListener;

    ArrayList<String> cinemaName;
//    private TimePickerDialog.OnTimeSetListener memoirTimeSetListener;
    public MemoirAddFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (null != bundle) {
            movieSearch = (MovieSearch) bundle.getSerializable("bundleForMemoir");
        }
        if (null != bundle) cinema = (Cinema) bundle.getSerializable("CinemaAdd");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memoir_add_fragment, container, false);

        networkConnection = new NetworkConnection();

        name = view.findViewById(R.id.memoir_add_movienameTV);
        releaseYear = view.findViewById(R.id.memoir_add_date_tv);
        rating = view.findViewById(R.id.memoir_add_rating_tv);
        addComment = view.findViewById(R.id.memoir_add_comment);
        choosenCinema = view.findViewById(R.id.choosen_cinema);


        name.setText(movieSearch.getMovieName());
        releaseYear.setText(movieSearch.getReleaseYear());
        rating.setText("IMDb rating: " + movieSearch.getRate());

        // date picker for adding watch date

        watchDate = view.findViewById(R.id.memoir_add_date_picker);
        watchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Objects.requireNonNull(getActivity()),
                        android.R.style.Theme_Material_Light_Voice,
                        memoirDateSetListener,
                        year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();


            }
        });

        memoirDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month  = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                watchDate.setText(date);
            }
        };


        // time picker for adding time

        watchTime = view.findViewById(R.id.memoir_add_time_picker);
        watchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        watchTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        if (cinema != null) {
            choosenCinema.setText(cinema.getCinemaname());
        }

        cinemaName = new ArrayList<>();


        cinemaChoise = view.findViewById(R.id.memoir_add_cinema_spinner);

        loadSpinnerData();

        cinemaChoise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String country=   cinemaChoise.getItemAtPosition(cinemaChoise.getSelectedItemPosition()).toString();
                Toast.makeText(getActivity(),country,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        addCinemaBtn = view.findViewById(R.id.memoir_add_new_cinema_btn);
        addCinemaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = (getActivity()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();




                CinemaAddFragment cinemaAddFragment = new CinemaAddFragment();

                Bundle bundleForMemoir = new Bundle();
                bundleForMemoir.putSerializable("movieSearchForMem", movieSearch);
                cinemaAddFragment.setArguments(bundleForMemoir);

                fragmentTransaction.replace(R.id.content_frame, cinemaAddFragment);
                fragmentTransaction.commit();

            }
        });

        addMemoirBtn = view.findViewById(R.id.memoir_add_button);
        addMemoirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();

            }
        });


        return view;

    }

    public void register() {
        initialize();
        if (!validate()) {
            Toast.makeText(getActivity(), "Memory add has failed", Toast.LENGTH_SHORT).show();
        }
        else {
            onRegisterSuccess();
        }

    }

    public void initialize() {
        movieNameStr = name.getText().toString().trim();
        releaseDateStr = releaseYear.getText().toString().trim();
        ratingStr = rating.getText().toString().trim();
        dateTimeWatchedStr = watchDate.getText().toString().trim() + watchTime.getText().toString().trim();
        cinemaNameStr = cinemaName.toString();
        commentStr = choosenCinema.getText().toString();
    }

    public void onRegisterSuccess() {

        String[] details = {movieNameStr, releaseDateStr, ratingStr, dateTimeWatchedStr, cinemaNameStr, commentStr};
        if (details.length == 6) {
            AddMemoirTask addMemoirTask = new AddMemoirTask();
            addMemoirTask.execute(details);
        }
    }

    private class AddMemoirTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String result= "The movie with name: " + params[0] + " was added";
            Log.d(TAG, "doInBackground: " + result);
            return networkConnection.addMemoir(params);
        }
        @Override
        protected void onPostExecute (String result) {
            Person person = null;

        }
    }



    public boolean validate() {
        boolean valid = true;
        if (movieNameStr.isEmpty() || movieNameStr.length() > 32){
            name.setError("Please enter valid first name");
            valid = false;
        }
        if (releaseDateStr.isEmpty() || releaseDateStr.length() > 32){
            releaseYear.setError("Please enter valid sur name");
            valid = false;
        }
        if (cinemaNameStr.isEmpty() || cinemaNameStr.length() > 70){
            choosenCinema.setError("Please enter valid address");
            valid = false;
        }
        if (commentStr.isEmpty() || commentStr.length() > 70){
            addComment.setError("Please enter valid address");
            valid = false;
        }
        return valid;

    }

    private void loadSpinnerData() {
        String[] methodName = {"getAllCinemaNameAndSuburb"};
        GetCinemas getCinemaSuburb = new GetCinemas();
        getCinemaSuburb.execute(methodName);
        }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class GetCinemas extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = "task name: " + params[0];
            Log.d(TAG, "doInBackground: " + result);
            return networkConnection.getAllCinemaNameSuburb(params);
        }

        @Override
        protected void onPostExecute(String result) {


            JsonArray jsonArray = (JsonArray) new JsonParser().parse(result);

            JsonObject jsonObject;

            int arraySize = jsonArray.size();
            for (int i = 0; i < arraySize; i++) {
                jsonObject = jsonArray.get(i).getAsJsonObject();
                Log.d(TAG, "onPostExecute: Json Objects" + jsonObject);
                String cinemaname = jsonObject.get("cinemaname").getAsString();
                cinemaName.add(cinemaname);

                cinemaChoise.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cinemaName));
            }


        }

    }
}





