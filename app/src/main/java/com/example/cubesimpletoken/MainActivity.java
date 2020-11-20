package com.example.cubesimpletoken;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Button _singIn;
    TextView _login, _password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _singIn = findViewById(R.id.loginButton);
        _password = findViewById(R.id.password);
        _login = findViewById(R.id.login);

        _singIn.setOnClickListener(x->{

            String login = _login.getText().toString();
            String password = _password.getText().toString();
            if (!(login.equals(""))||(password.equals("")))
            {
                if (tryLogin(login,password))
                    startActivity(new Intent(MainActivity.this, PrimeActivity.class));
                else new AlertDialog.Builder(
                    MainActivity.this).setMessage("Неверный логин или пароль!")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // TODO Auto-generated method stub
                    }).create().show();
            }
        });
    }
    boolean tryLogin(String login, String password) {
        return login.equals("root")&&password.equals("admin");
    }

}