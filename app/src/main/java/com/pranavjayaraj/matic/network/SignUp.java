package com.pranavjayaraj.matic.network;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;
import com.pranavjayaraj.matic.network.KeyStoreHelper.Crypto;
import com.pranavjayaraj.matic.network.KeyStoreHelper.Options;
import com.pranavjayaraj.matic.network.KeyStoreHelper.Store;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@RequiresApi(api = Build.VERSION_CODES.M)
public class SignUp extends AppCompatActivity {
    private AppCompatEditText USERNAME;
    final KeyGenerator keyGenerator = null;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String keyStoreName = "maticstore";
    String keyStorePass = "#maticnetwork123";
    private AppCompatEditText PASSWORD;
    private String encrypted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ActivityCompat.requestPermissions(SignUp.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        PASSWORD = (AppCompatEditText) findViewById(R.id.pass);
        USERNAME = (AppCompatEditText) findViewById(R.id.user);
        final Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

    }

    public static String nextSessionId(SecureRandom random) {
        return new BigInteger(100, random).toString(32);
    }

    public void signup() {
        SecureRandom random = new SecureRandom();
        String hash = nextSessionId(random);
        try {
            encrypted = AESCrypt.encrypt(USERNAME.getText().toString()+PASSWORD.getText().toString(), hash);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        Log.i("pj", "before after encryption " + hash);
        Log.i("pj", "after encryption " + encrypted);
        try {
            Log.i("pj", "after decryption " + AESCrypt.decrypt(USERNAME.getText().toString()+PASSWORD.getText().toString(),encrypted));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        editor.commit();
        storePassKey();

    }

    void storePassKey() {
        Store store = new Store(getApplicationContext(), USERNAME.getText().toString(), PASSWORD.getText().toString().toCharArray());
        if (!store.hasKey(USERNAME.getText().toString())) {
            store.generateSymmetricKey(USERNAME.getText().toString(), null);
            SecretKey key = store.getSymmetricKey(USERNAME.getText().toString(), null);
            Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);
            String text = PASSWORD.getText().toString();

            String encryptedData = crypto.encrypt(text, key);
            Log.i("pj", "Encrypted data: " + encryptedData);
            editor.putString("pass" + USERNAME.getText().toString(), encryptedData);
            editor.commit();
            storeHashKey();
        } else
            {
            Toast.makeText(SignUp.this, "User already exists.Please try logging in", Toast.LENGTH_LONG).show();
        }
    }
    void storeHashKey() {
        Store store = new Store(getApplicationContext(), USERNAME.getText().toString(), PASSWORD.getText().toString().toCharArray());
            store.generateSymmetricKey("hash" + USERNAME.getText(), null);
            SecretKey key = store.getSymmetricKey("hash" + USERNAME.getText(), null);

            Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);
            String encryptedData = crypto.encrypt(encrypted, key);
            Log.i("pj", "after encrypted hash encryption " + encryptedData);
            editor.putString("encrypted" + USERNAME.getText().toString(), encryptedData);
            editor.commit();
            Intent n = new Intent(SignUp.this,HomeScreen.class);
            n.putExtra("USERNAME",USERNAME.getText().toString());
            n.putExtra("PASSWORD",PASSWORD.getText().toString());
            n.putExtra("AES_HASH"+USERNAME.getText().toString(),encrypted);
            startActivity(n);
    }
}



