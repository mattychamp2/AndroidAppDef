package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {
    private Button btnLog;
    private TextView username;
    private TextView password;
    private RequestQueue requestQueue;
    private TextView txtResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLog = (Button) findViewById(R.id.btnLog);
        username = (TextView) findViewById(R.id.txtUser);
        password = (TextView) findViewById(R.id.txtPass);
    }

    public void login( View v )
    {
        requestQueue = Volley.newRequestQueue( this );
        String requestURL = "https://studev.groept.be/api/a21pt115/customerList";

        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {
                            boolean login = false;
                            int i=0;
                            while (!login) {
                                JSONObject curObject = response.getJSONObject( i );
                                String givenUsername= username.getText().toString();
                                String dbUsername=curObject.getString("Username");
                                System.out.println(dbUsername);
                                String givenPassword = password.getText().toString();
                                String dbPassword = curObject.getString("Password");

                                if (givenUsername.equals(dbUsername) && givenPassword.equals(dbPassword)){
                                    login=true;
                                }
                                i++;
                            }

                            if (login){
                                Intent intent=new Intent(MainActivity.this,ShopScreen.class);
                                startActivity(intent);                            }
                            else{
                                Toast.makeText(MainActivity.this, "Username and password do not match", Toast.LENGTH_LONG).show();
                            }

                        }
                        catch( JSONException e )
                        {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                       //txtResponse.setText( error.getLocalizedMessage() );
                        Toast.makeText(MainActivity.this, "Unable to log in", Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(submitRequest);
    }

    public void onBtnLog_Clicked(View caller) {
        Intent intent = new Intent(this, ShopScreen.class);
        startActivity(intent);
    }

    public void createAccBtn(View caller){
        Intent intent = new Intent(this,CreateAccount.class);
        startActivity(intent);
    }
}
