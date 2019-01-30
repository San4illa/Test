package com.example.test.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.R;
import com.example.test.ui.map.MapFragment;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class LoginFragment extends Fragment {

    private EditText numberEditText;
    private EditText passwordEditText;
    private Button sendButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        numberEditText = view.findViewById(R.id.et_number);
        passwordEditText = view.findViewById(R.id.et_password);
        sendButton = view.findViewById(R.id.btn_login);
        sendButton.setOnClickListener(v -> onClickLogin());
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
                    Toast.makeText(getContext(), "Login is successful!!!", Toast.LENGTH_SHORT).show();

                    getFragmentManager().beginTransaction().replace(R.id.fragment_containter, new MapFragment()).commit();
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
}
