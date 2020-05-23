package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.networkconnection.NetworkConnection;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "SignupActivity";
    NetworkConnection networkConnection = null;

    private EditText etFName, etSName, etAddress, etPostCode, etEmail, etPassword, etconfirmPassword;
    private String fName, sName, address, postCode, email, password, confirmPassword, dateOfBirth, state, gender;
    private TextView dobDisplayDate, resultTextView;
    private Button regButton, signButton;
    private Spinner spinner;
    private RadioGroup radioG;
    private RadioButton radioB;

    private DatePickerDialog.OnDateSetListener dobDataSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkConnection = new NetworkConnection();

        etFName = (EditText) findViewById(R.id.reg_f_name);
        etSName = (EditText) findViewById(R.id.reg_s_name);
        etAddress = (EditText) findViewById(R.id.reg_address);
        etPostCode = (EditText) findViewById(R.id.reg_post_code);
        etEmail = (EditText) findViewById(R.id.reg_email_id);
        etPassword = (EditText) findViewById(R.id.reg_password);
        etconfirmPassword = (EditText) findViewById(R.id.reg_confirm_password);
        resultTextView = findViewById(R.id.tvAdd);

        // Gender
        radioG = (RadioGroup) findViewById(R.id.reg_gender_rg);


        dobDisplayDate = (TextView) findViewById(R.id.tvDate);
        dobDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Material_Light_Voice,
                        dobDataSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                dialog.show();


            }
        });


        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        state = spinner.getSelectedItem().toString();

        //dates
        dobDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month  = month + 1;
                Log.d(TAG, "onDateSet: " + "/" + year + "/" + month + "/" + dayOfMonth);
                String date = month + "/" + dayOfMonth + "/" + year;
                dobDisplayDate.setText(date);
            }
        };

        //dates
        dobDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month  = month + 1;
                Log.d(TAG, "onDateSet: " + "/" + year + "/" + month + "/" + dayOfMonth);
                String date = month + "/" + dayOfMonth + "/" + year;
                dobDisplayDate.setText(date);
            }
        };



        resultTextView = findViewById(R.id.tvAdd);
        final Button getAllPersonbtn = findViewById(R.id.reg_register_button);
        getAllPersonbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                GetAllPersonTask getAllPersonTask = new GetAllPersonTask();
//                getAllPersonTask.execute();

                String fakeID = "0";
                register();
                String[] details = {fakeID, fName, sName, gender, dateOfBirth, address, state, postCode, email, password};
                if (details.length == 10) {
                    AddPeopleTask addStudentTask = new AddPeopleTask();
                    addStudentTask.execute(details);
                }
            }
        });


    }
    public void register() {
        initialize();
        if (!validate()) {
            Toast.makeText(this, "Registration has failed", Toast.LENGTH_SHORT).show();
        }
        else {
            onRegisterSuccess();
        }

    }
    public void onRegisterSuccess() {
        Log.d(TAG, "onRegisterSuccess: Name: " + fName);
        Log.d(TAG, "onRegisterSuccess: Surname: " + sName);
        Log.d(TAG, "onRegisterSuccess: gender: " + gender);
        Log.d(TAG, "onRegisterSuccess: dob: " + dateOfBirth);
        Log.d(TAG, "onRegisterSuccess: address: " + address);
        Log.d(TAG, "onRegisterSuccess: postCode: " + postCode);
        Log.d(TAG, "onRegisterSuccess: state: " + state);
        Log.d(TAG, "onRegisterSuccess: password: " + password);
        Log.d(TAG, "onRegisterSuccess: confirmPassword: " + confirmPassword);
        // T
    }

    public boolean validate() {
        boolean valid = true;
        if (fName.isEmpty() || fName.length() > 32){
            etFName.setError("Please enter valid first name");
        }
        if (sName.isEmpty() || sName.length() > 32){
            etSName.setError("Please enter valid sur name");
        }
        if (address.isEmpty() || address.length() > 70){
            etAddress.setError("Please enter valid address");
        }
        if (postCode.isEmpty() || !(200 <= Integer.parseInt(postCode)) || !((9729 >= Integer.parseInt(postCode)))) {
            etPostCode.setError("Please enter valid post code");
        }
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter valid email address");
        }
        if(password.isEmpty()) {
            etPassword.setError("Password cannot be empty");
        } else if (confirmPassword.isEmpty()){
            etconfirmPassword.setError("Confirm password cannot be empty");
        } else if (!password.equals(confirmPassword)){
            etPassword.setError("Password is not matched");
            etconfirmPassword.setError("Password is not matched");
        }

        return valid;

    }

    public void initialize() {
        fName = etFName.getText().toString().trim();
        sName = etSName.getText().toString().trim();
        int selectedId = radioG.getCheckedRadioButtonId();
        radioB = (RadioButton) findViewById(selectedId);
        gender = radioB.getText().toString().trim();
        dateOfBirth = dobDisplayDate.getText().toString();
//        state = spinner.getSelectedItem().toString();
        address = etAddress.getText().toString().trim();
        postCode = etPostCode.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        confirmPassword = etconfirmPassword.getText().toString().trim();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class AddPeopleTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String result= "The person with name: " + params[0] + " was added";
            Log.d(TAG, "doInBackground: " + result);
            return networkConnection.addPerson(params);
        }
        @Override
        protected void onPostExecute (String result) {
            resultTextView.setText(result);
        }
    }




//    private class GetAllPersonTask extends AsyncTask<Void, Void, String> {
//        @Override
//        protected String doInBackground(Void... params) {
//            return networkConnection.getAllPerson();
//        }
//        protected void onPostExecute(String person) {
//            resultTextView.setText(person);
//        }
//    }
}
