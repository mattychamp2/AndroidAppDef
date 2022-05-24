package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

public class OrderPage extends AppCompatActivity {

    private Button timeButton, dateButton, order;
    int hour, minute;
    private DatePickerDialog datePickerDialog;
    private TextView totalPrice;
    private RequestQueue requestQueue;
    private String itemList;
    private static String priceToPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        initDatePicker();
        order = findViewById(R.id.btnOrder);
        dateButton = findViewById(R.id.datePickerButton);
        timeButton = (Button) findViewById(R.id.btnTime);
        dateButton.setText(getTodaysDate());
        totalPrice = findViewById(R.id.txtTotalPrice);
        queryTotalPrice();
    }

    private void queryTotalPrice() {
        requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt115/cartValue";
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onResponse(JSONArray response) {
                        boolean finished = false;
                        int index = 0;
                        while (!finished) {
                            try {
                                JSONObject curObject = response.getJSONObject(index);
                                priceToPass = String.format("%.2f", curObject.getDouble("pricesum"));
                                totalPrice.setText("€" + String.format("%.2f", curObject.getDouble("pricesum")) + " total price");
                                index++;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                finished = true;
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(submitRequest);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1){
            return "JAN";
        }
        if(month == 2){
            return "FEB";
        }
        if(month == 3){
            return "MAR";
        }
        if(month == 4){
            return "APR";
        }
        if(month == 5){
            return "MAY";
        }
        if(month == 6){
            return "JUN";
        }
        if(month == 7){
            return "JUL";
        }
        if(month == 8){
            return "AUG";
        }
        if(month == 9){
            return "SEP";
        }
        if(month == 10){
            return "OCT";
        }
        if(month == 11){
            return "NOV";
        }
        if(month == 12){
            return "DEC";
        }
        else{
            return "JAN";
        }
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = (view1, selectedHour, selectedMinute) -> {
             hour = selectedHour;
             minute = selectedMinute;
             timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void OpenDatePicker(View view) {
        datePickerDialog.show();
    }

    public void emptyCart(View caller){
        requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt115/emptyCart";
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                //TODO: Possibly make this a lambda expression if we still understand what happens.
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                    }
                }
        );
        requestQueue.add(submitRequest);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void insertOrder(View v){
        int pickupDay = datePickerDialog.getDatePicker().getDayOfMonth();
        int pickupMonth = datePickerDialog.getDatePicker().getMonth() + 1;
        int pickupYear = datePickerDialog.getDatePicker().getYear();
        String dateToPass = pickupDay + "-" + pickupMonth + "-" + pickupYear;
        String hourToPass = hour + ":" + minute;
        String orderToPass = "";
        for(int i = 0; i < SecondFragment.getCart().size(); i++){
            orderToPass = orderToPass + SecondFragment.getQty().get(i) + "x" + SecondFragment.getCart().get(i) + ",";
        }
        String userToPass = MainActivity.getLoggedUser();
        if(hour > 7 && hour < 17) {
            String urlToPass = "https://studev.groept.be/api/a21pt115/pushOrder/" + dateToPass + "/" + hourToPass + "/" + orderToPass + "/" + priceToPass + "/" + userToPass;
            requestQueue = Volley.newRequestQueue(this);
            JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, urlToPass, null,
                    //TODO: Possibly make this a lambda expression if we still understand what happens.
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Toast.makeText(OrderPage.this, "Thank you for you order", Toast.LENGTH_SHORT).show();
                            emptyCart(v);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(OrderPage.this, "Server error, try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            requestQueue.add(submitRequest);
        }
        else{
            Toast.makeText(OrderPage.this, "Sorry, the bakery is shut at the selected time", Toast.LENGTH_SHORT).show();
        }
    }
}