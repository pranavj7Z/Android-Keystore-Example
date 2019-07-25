package com.pranavjayaraj.matic.network.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pranavjayaraj.matic.network.R;

public class MainActivity extends AppCompatActivity {
Button SignUp,SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignUp = (Button) findViewById(R.id.signup);
        SignIn = (Button) findViewById(R.id.signin);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, com.pranavjayaraj.matic.network.UI.SignIn.class);
                startActivity(n);
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this, com.pranavjayaraj.matic.network.UI.SignUp.class);
                startActivity(n);
            }
        });
    }


}
