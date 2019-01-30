package com.example.test.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.ui.MainActivity;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class LoginActivity extends AppCompatActivity {

    private EditText numberEditText;
    private EditText passwordEditText;
    private Button sendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSharedPreferences("loginPrefs", MODE_PRIVATE).getBoolean("login", false)) {
            openMainActivity();
        }

        numberEditText = findViewById(R.id.et_number);
        passwordEditText = findViewById(R.id.et_password);
        sendButton = findViewById(R.id.btn_login);
        sendButton.setOnClickListener(v -> onClickLogin());
    }

    private void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void onClickLogin() {
        String number = numberEditText.getText().toString();

        PhoneNumberUtil numberUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = numberUtil.parse(number, "RU");
            boolean isValid = numberUtil.isValidNumber(phoneNumber);

            if (isValid) {
                String password = passwordEditText.getText().toString();

                if (password.equals(number.substring(number.length() - 5))) {
                    Toast.makeText(this, "Login is successful!", Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("login", true);
                    editor.putString("number", "+7" + number);
                    editor.apply();

                    openMainActivity();
                } else {
                    passwordEditText.setError("Incorrect password");
                }
            } else {
                numberEditText.setError("Number is not valid");
            }

        } catch (NumberParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
