package be.kuleuven.androidapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Shop extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        openFragment(FirstFragment.newInstance());
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    openFragment(FirstFragment.newInstance());
                    return true;
                case R.id.profile:
                    openFragment(SecondFragment.newInstance());
                    return true;
                case R.id.settings:
                    openFragment(ThirdFragment.newInstance());
                    return true;
            }
            return false;
        });
    }

    public void openFragment(Fragment fragment) {
        Log.d(TAG, "openFragment: ");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //this is a helper class that replaces the container with the fragment. You can replace or add fragments.
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null); //if you add fragments it will be added to the backStack. If you replace the fragment it will add only the last fragment
        transaction.commit(); // commit() performs the action
    }

    public void setBtnOrder(View caller) {
        Intent intent = new Intent(this, OrderPage.class);
        startActivity(intent);
    }

    public void btnLogOut(View caller) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
