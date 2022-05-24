package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

public class CreateAccount extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView username;
    private TextView password;
    private TextView passwordRepeat;
    private RequestQueue requestQueue;
    private Button createAccBtn;
    private String postUrl;
    private String checkUnUrl;
    private boolean sameUn;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        username = (TextView) findViewById(R.id.userName);
        password = (TextView) findViewById(R.id.Password);
        passwordRepeat = (TextView) findViewById(R.id.Password2);
        createAccBtn = (Button) findViewById(R.id.createAccBtn);
        postUrl = "https://studev.groept.be/api/a21pt115/insertAccount";
        checkUnUrl = "https://studev.groept.be/api/a21pt115/getUsername";

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1; //januari = 0
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public String getInfo() {
        String fn = firstName.getText().toString();
        String ln = lastName.getText().toString();
        String un = username.getText().toString();
        String pw = password.getText().toString();
        String pwHashed = PwHasher.getMd5(pw);
        int day = datePickerDialog.getDatePicker().getDayOfMonth();
        int month = datePickerDialog.getDatePicker().getMonth() + 1;
        int year = datePickerDialog.getDatePicker().getYear();
        String bd = day + "-" + month + "-" + year;
        //(Username, Password, LastName, FirstName, Birthdate)
        return postUrl + "/" + un + "/" + pwHashed + "/" + ln + "/" + fn+"/"+bd;
    }


    public void existingUsername(View v){
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, checkUnUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            String un = username.getText().toString();
                            int i = 0;
                            while (i < response.length()) {
                                sameUn = false;
                                JSONObject curObject = response.getJSONObject(i);
                                String dbUsername = curObject.getString("Username");

                                if (un.equals(dbUsername)) {
                                    sameUn = true;
                                    Toast.makeText(CreateAccount.this, "This username already exists", Toast.LENGTH_SHORT).show();
                                    System.out.println(sameUn);
                                    break;
                                }
                                i++;
                            }
                            if (!sameUn){
                                checkRequirements(v);
                                if (checkRequirements(v)){
                                    createAccount(v);
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateAccount.this, "Server error, try again later", Toast.LENGTH_SHORT).show();
                    }
                }

        );
        requestQueue.add(submitRequest);
    }

    public boolean checkRequirements(View v){
        String givenPw = password.getText().toString();
        String givenPwRp = passwordRepeat.getText().toString();
        String fn = firstName.getText().toString();
        String ln = lastName.getText().toString();
        String un = username.getText().toString();
        boolean check = true;

        if (fn.equals("") || ln.equals("") || un.equals("") || givenPw.equals("") || givenPwRp.equals("")){
            Toast.makeText(CreateAccount.this, "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
            check = false;
        }

        if (!(givenPw.equals(givenPwRp))) {
            Toast.makeText(CreateAccount.this, "Passwords must be the same!", Toast.LENGTH_SHORT).show();
            check = false;
        }
        else{
            if (givenPw.length()<7){
                Toast.makeText(CreateAccount.this, "Password must be at least 7 charachters long", Toast.LENGTH_SHORT).show();
                check = false;
            }

            if (!(checkPwReq(givenPw))){
                Toast.makeText(CreateAccount.this, "Password must be contain a mix of letters, symbols and numbers", Toast.LENGTH_SHORT).show();
                check = false;
            }
        }

        if (un.contains(" ")){
            Toast.makeText(CreateAccount.this, "No spaces allowed in username", Toast.LENGTH_SHORT).show();
            check = false;
        }

        if (ln.contains(" ")){
            Toast.makeText(CreateAccount.this, "Write your last name in one word or use underscores", Toast.LENGTH_SHORT).show();
            check = false;
        }

        return check;
    }

    public void createAccount(View v) {
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, getInfo(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (checkRequirements(v)){
                            Toast.makeText(CreateAccount.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateAccount.this, Shop.class);
                            startActivity(intent);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateAccount.this, "Could not create account, try again later", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(submitRequest);
    }

    public boolean checkPwReq(String pw){
        boolean check = true;
        boolean onlyLetters = true;
        boolean onlyDigits = true;
        boolean onlySymbols = false;
        boolean noSymbols = false;
        char[] chars = pw.toCharArray();
        for (char c: chars){
            if (!(Character.isLetter(c))){
                // $ en _ zijn letters in java
                onlyLetters = false;
            }
        }
        for (char c: chars){
            if (!(Character.isDigit(c))){
                onlyDigits = false;
            }
        }
        int counter = 0;
        for (char c: chars){
            if ((Character.isDigit(c) || (Character.isLetter(c)))){
                counter++;
            }
        }
        if (counter==pw.length()){
            onlySymbols=true;
        }
        else if (counter==0){
            noSymbols = true;
        }

        if (onlyDigits || onlyLetters || onlySymbols || noSymbols){
            check = false;
        }
        return check;
    }

}


