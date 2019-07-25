package com.pranavjayaraj.matic.network;
import java.math.BigInteger;
import java.security.SecureRandom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button SignUp,SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SecureRandom random = new SecureRandom();
        System.out.println(nextSessionId(random));
        SignUp = (Button) findViewById(R.id.signup);
        SignIn = (Button) findViewById(R.id.signin);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this,SignIn.class);
                startActivity(n);
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this,SignUp.class);
                startActivity(n);
            }
        });
    }
    public static String nextSessionId(SecureRandom random)
    {
        return new BigInteger(100, random).toString(32);
    }


}
