package com.devil.premises.chatbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";

    //widgets
    private TextInputLayout regName, regUsername, regEmail, regPhone, regPassword;
    private Button reg_loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //initializing widgets
        regName = findViewById(R.id.fullName);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhone = findViewById(R.id.phoneNumber);
        regPassword = findViewById(R.id.password);
        reg_loginBtn = findViewById(R.id.reg_login_btn);
        reg_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();
        if(val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if (val.length() < 4) {
            regUsername.setError("Username too short");
            return false;
        }else if (val.length() >= 15) {
            regUsername.setError("Username too long");
            return false;
        } else if(!val.matches(noWhiteSpace)){
            regUsername.setError("Spaces are not allowed");
            return false;
        }else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)) {
            regEmail.setError("Invalid email pattern");
            return false;
        }else {
            regEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePhone(){
        String val = regPhone.getEditText().getText().toString();
        if(val.isEmpty()){
            regPhone.setError("Field cannot be empty");
            return false;
        }else {
            regPhone.setError(null);
            regPhone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordPattern = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=]).{6,}$";
        if(val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(passwordPattern)) {
            regPassword.setError("Password is too weak");
            return false;
        }else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }


    //save data in firebase on Button click
    public void registerUser(View view){
        if(!validateName() | !validateEmail() | !validateUsername() | !validatePhone() | !validatePassword()){
            return;
        }

        InputMethodManager imm =(InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        //get all values in string
        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phone = regPhone.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        Intent verifyIntent = new Intent(getApplicationContext(), VerifyPhoneNumber.class);
        verifyIntent.putExtra("phone", phone);
        verifyIntent.putExtra("name", name);
        verifyIntent.putExtra("username", username);
        verifyIntent.putExtra("email", email);
        verifyIntent.putExtra("password", password);
        startActivity(verifyIntent);

    }
}