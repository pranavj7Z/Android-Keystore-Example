package com.pranavjayaraj.matic.network.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import com.pranavjayaraj.matic.network.KeyStoreHelper.Crypto;
import com.pranavjayaraj.matic.network.KeyStoreHelper.Options;
import com.pranavjayaraj.matic.network.KeyStoreHelper.Store;
import com.pranavjayaraj.matic.network.R;

import javax.crypto.SecretKey;

public class SignIn extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    AppCompatEditText USERNAME,PASSWORD;
    private ImageButton eraseUser,erasePass;
    SharedPreferences.Editor editor;
    //the keystore name and password is written here only for demo
    String keyStoreName = "maticstore";
    String KeyStorePass = "#maticnetwork123";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        Button signin = (Button) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCredentials();
            }
        });
        USERNAME = (AppCompatEditText) findViewById(R.id.user);
        PASSWORD = (AppCompatEditText) findViewById(R.id.pass);
        erasePass= (ImageButton) findViewById(R.id.erasepass);
        erasePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PASSWORD.getText().clear();
            }
        });
        eraseUser =(ImageButton) findViewById(R.id.eraseuser);
        eraseUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                USERNAME.getText().clear();
            }
        });
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public void getCredentials(){
        try {

            if(getPassKey().equals(PASSWORD.getText().toString()))
            {
                Intent n = new Intent(SignIn.this, HomeScreen.class);
                n.putExtra("USERNAME",USERNAME.getText().toString());
                n.putExtra("PASSWORD",PASSWORD.getText().toString());
                n.putExtra("AES_HASH"+USERNAME.getText().toString(),getHashkey());
                startActivity(n);
            }
            else
            {
                Toast.makeText(SignIn.this, "Username or Password incorrect", Toast.LENGTH_LONG).show();

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    String getPassKey() {

        Store store = new Store(getApplicationContext(), keyStoreName,KeyStorePass.toCharArray());
        // Get key
        if (store.hasKey(USERNAME.getText().toString())) {
            SecretKey key = store.getSymmetricKey(USERNAME.getText().toString(), null);
            // Encrypt/Decrypt data
            Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);
            String encryptedData = sharedpreferences.getString("pass" + USERNAME.getText().toString(), "");
            String decryptedData = crypto.decrypt(encryptedData, key);
            Log.i("pj", "Decrypted data: " + decryptedData);
            return decryptedData;
        }
        else
            {
                Toast.makeText(SignIn.this, "Username or password incorrect", Toast.LENGTH_LONG).show();
        }
        return null;
    }


    String getHashkey() {

        Store store = new Store(getApplicationContext(),keyStoreName,KeyStorePass.toCharArray());
        // Get key
        SecretKey key = store.getSymmetricKey("hash" + USERNAME.getText(), null);
        // Encrypt/Decrypt data
        Crypto crypto = new Crypto(Options.TRANSFORMATION_SYMMETRIC);
        String encryptedData = sharedpreferences.getString("encrypted"+USERNAME.getText().toString(),"");
        String decryptedData = crypto.decrypt(encryptedData, key);
        Log.i("pj", "after encrypted data decryption : " + decryptedData);
        return  decryptedData;
    }

}
