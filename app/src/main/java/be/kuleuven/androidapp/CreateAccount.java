package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

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
    }

    public String getInfo() {
        String fn = firstName.getText().toString();
        String ln = lastName.getText().toString();
        String un = username.getText().toString();
        String pw = password.getText().toString();
        //(Username, Password, LastName, FirstName)
        return postUrl + "/" + un + "/" + pw + "/" + ln + "/" + fn;
    }

    public void createAccount(View v) {
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, getInfo(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String givenPw = password.getText().toString();
                        String givenPwRp = passwordRepeat.getText().toString();
                        String fn = firstName.getText().toString();
                        String ln = lastName.getText().toString();
                        String un = username.getText().toString();

                        if (checkRequirements() && !existingUsername(v)){
                            Toast.makeText(CreateAccount.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateAccount.this, "Could not create account", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(submitRequest);
    }

    public boolean checkRequirements(){
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

        if (un.contains(" ")){
            Toast.makeText(CreateAccount.this, "No spaces allowed in username", Toast.LENGTH_SHORT).show();
            check = false;
        }
        return check;
    }

    public boolean existingUsername(View v){
        requestQueue = Volley.newRequestQueue(this);
        sameUn = false;
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, checkUnUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            String un = username.getText().toString();
                            int i = 0;
                            while (i < response.length()) {

                                JSONObject curObject = response.getJSONObject(i);
                                String dbUsername = null;
                                dbUsername = curObject.getString("Username");

                                if (un.equals(dbUsername)) {
                                    sameUn = true;
                                    Toast.makeText(CreateAccount.this, "This username already exists", Toast.LENGTH_SHORT).show();
                                }
                                i++;
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
                        Toast.makeText(CreateAccount.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(submitRequest);
        return sameUn;
    }
}


