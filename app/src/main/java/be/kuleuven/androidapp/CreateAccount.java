package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
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





//        createAccBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // validating if the text field is empty or not.
//                if (!(password.getText().toString().equals(passwordRepeat.getText().toString()))) {
//                    Toast.makeText(CreateAccount.this, "Passwords must be the same!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                // calling a method to post the data and passing our name and job.
//                createAccountBtn(firstName.getText().toString(), lastName.getText().toString(), username.getText().toString(),
//                        password.getText().toString());
//            }
//        });
//
//    }
//
//    public void createAccountBtn(String firstName, String lastName, String username, String password){
//        String url = "https://studev.groept.be/api/a21pt115/insertAccount";
//        requestQueue = Volley.newRequestQueue(this);
//        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // inside on response method we are
//                // hiding our progress bar
//                // and setting data to edit text as empty
//                //loadingPB.setVisibility(View.GONE);
//
//                // on below line we are displaying a success toast message.
//
//                try {
//                    // on below line we are parsing the response
//                    // to json object to extract data from it.
//                    JSONObject respObj = new JSONObject(response);
//
//                    // below are the strings which we
//                    // extract from our json object.
//                    String firstName = respObj.getString("firstName");
//                    String lastName = respObj.getString("lastName");
//                    String username = respObj.getString("username");
//                    String password = respObj.getString("password");
//
//                    // on below line we are setting this string s to our text view.
//                    //responseTV.setText("Name : " + name + "\n" + "Job : " + job);
//                    Toast.makeText(CreateAccount.this, "You successfully created an account", Toast.LENGTH_SHORT).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(CreateAccount.this, "Fail", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // method to handle errors.
//                Toast.makeText(CreateAccount.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // below line we are creating a map for
//                // storing our values in key and value pair.
//                Map<String, String> params = new HashMap<String, String>();
//
//                // on below line we are passing our key
//                // and value pair to our parameters.
//                params.put("userName", username);
//                params.put("password", password);
//                params.put("lastName", lastName);
//                params.put("firstName", firstName);
//
//                // at last we are
//                // returning our params.
//                return params;
//            }
//        };
//        // below line is to make
//        // a json object request.
//        requestQueue.add(request);
    }
}