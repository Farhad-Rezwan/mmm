package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.memoirpersoncinemacred.Person;
import com.example.myapplication.networkconnection.NetworkConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private boolean isUser;
    private static final String TAG = "LoginActivity";

    NetworkConnection networkConnection = null;

    private String userName, password;
    private EditText userNameEt, passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        isUser = false;

        networkConnection = new NetworkConnection();

        userNameEt = findViewById(R.id.sign_username);
        passwordEt = findViewById(R.id.sign_password);






        // on button click for login in login page
        Button loginBtn = findViewById(R.id.sign_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();





            }
        });

        // on button click for register in login page
        Button regesterBtn = findViewById(R.id.buttonRegester);
        regesterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                // here main activity refers to the register page
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void register() {
        initialize();
        if (!inputValidate()) {
            Toast.makeText(this, "login has failed", Toast.LENGTH_SHORT).show();
        }
        else {
            String[] details = {userName, password};
            if (details.length == 2) {
                AsyncValid asyncValid = new AsyncValid();
                asyncValid.execute(details);
            }
        }


    }

    private void initialize() {
        userName = userNameEt.getText().toString().trim();
        password = passwordEt.getText().toString();
    }

    private boolean inputValidate() {

        boolean valid = true;
        if (userName.isEmpty()){
            userNameEt.setError("User name cannot be empty");
            valid = false;
        }
        if (password.isEmpty()){
            passwordEt.setError("Password cannot be empty");
            valid = false;
        }


        return valid;
    }







    private class AsyncValid extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = "Task Name" + params[0];
            Log.d(TAG, "doInBackground: " + result);
            return networkConnection.credentialCheck(params);

        }
        @Override
        protected void onPostExecute(String result) {

            isUser = false;
            Person person = null;
            try {

                if (!(result == null)){
                    JsonElement jelement = new JsonParser().parse(result);
                    JsonObject jObject = jelement.getAsJsonObject();
                    int personid = jObject.get("personid").getAsInt();
                    String firstname = jObject.get("firstname").getAsString();
                    String surname = jObject.get("surname").getAsString();
                    Character gender = jObject.get("gender").getAsString().charAt(0);
                    String dateInString = jObject.get("dob").getAsString();


                    String string = dateInString.replace("T00:00:00+10:00", "");
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    Date date = format.parse(string);


                    Date dob = date;



                    String address = jObject.get("address").getAsString();
                    String stateau = jObject.get("stateau").getAsString();
                    int postcode = jObject.get("postcode").getAsInt();

                    person = new Person(personid, firstname, surname, gender, dob, address, stateau, postcode);

                } else {
                    Toast.makeText(LoginActivity.this, "invalid user name and password", Toast.LENGTH_SHORT).show();
                }

                if (person == null){
                    Toast.makeText(LoginActivity.this, "login has failed", Toast.LENGTH_SHORT).show();
                } else  {
                    onLoginSuccess();
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

    }


    private void onLoginSuccess() {

        Intent intent;
        intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
