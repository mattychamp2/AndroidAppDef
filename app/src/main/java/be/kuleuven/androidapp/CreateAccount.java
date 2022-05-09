package be.kuleuven.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.json.*;

public class CreateAccount extends AppCompatActivity {

    private TextView firstName;
    private TextView lastName;
    private TextView username;
    private TextView password;
    private TextView passwordRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        firstName = (TextView) findViewById(R.id.firstName);
        lastName = (TextView) findViewById(R.id.lastName);
        username = (TextView) findViewById(R.id.userName);
        password = (TextView) findViewById(R.id.Password);
        passwordRepeat = (TextView) findViewById(R.id.Password2);

    }

    public void createAccountBtn(View caller){
        JSONArray newAccountInfo = new JSONArray();

    }
}