package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView username;
    private TextView password;
    public static String loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (TextView) findViewById(R.id.txtUser);
        password = (TextView) findViewById(R.id.txtPass);
    }

    public void login(View v) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String requestURL = "https://studev.groept.be/api/a21pt115/customerList";
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                response -> {
                    try {
                        boolean login = false;
                        int i = 0;
                        while (i < response.length()) {
                            JSONObject curObject = response.getJSONObject(i);
                            String givenUsername = username.getText().toString();
                            String dbUsername = curObject.getString("Username");
                            String givenPassword = password.getText().toString();
                            String dbPassword = curObject.getString("Password");
                            String givenPassWordHashed = PwHasher.getMd5(givenPassword);
                            if (givenUsername.equals(dbUsername) && givenPassWordHashed.equals(dbPassword)) {
                                login = true;
                                loggedUser = dbUsername;
                            }
                            i++;
                        }
                        if (login) {
                            Intent intent = new Intent(MainActivity.this, Shop.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Password and username do not match", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(MainActivity.this, "Unable to log in", Toast.LENGTH_LONG).show()
        );
        requestQueue.add(submitRequest);
    }

    public void createAccBtn(View caller) {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }

    public static String getLoggedUser() {
        return loggedUser;
    }
}
