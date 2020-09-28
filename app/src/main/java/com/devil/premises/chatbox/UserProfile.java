package com.devil.premises.chatbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {
    private static final String TAG = "UserProfile";
    
    //widgets
    private TextInputLayout name, email, phone, password;
    private TextView fullName, username;

    //vars
    String user_name, user_username, user_email, user_phone, user_password;

    //firebase reference
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        //Hooks
        name = findViewById(R.id.full_name_profile);
        email = findViewById(R.id.email_profile);
        phone = findViewById(R.id.phone_profile);
        password = findViewById(R.id.password_profile);
        fullName = findViewById(R.id.full_name);
        username = findViewById(R.id.username);

        showAllData();
    }

    private void showAllData() {
        Intent intent = getIntent();
        user_name = intent.getStringExtra("name");
        user_username = intent.getStringExtra("username");
        user_email = intent.getStringExtra("email");
        user_phone = intent.getStringExtra("phone");
        user_password = intent.getStringExtra("password");

        fullName.setText(user_name);
        username.setText(user_username);
        name.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phone.getEditText().setText(user_phone);
        password.getEditText().setText(user_password);
    }

    public void update(View view){
        if(isNameChanged() | isPasswordChanged() | isEmailChanged()){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Data is same, can't be updated", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmailChanged() {
        if(!user_email.equals(email.getEditText().getText().toString())){
            reference.child(user_username).child("email").setValue(email.getEditText().getText().toString());
            user_email = email.getEditText().getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isPasswordChanged() {
        if(!user_password.equals(password.getEditText().getText().toString())){
            reference.child(user_username).child("password").setValue(password.getEditText().getText().toString());
            user_password = password.getEditText().getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!user_name.equals(name.getEditText().getText().toString())){
            reference.child(user_username).child("name").setValue(name.getEditText().getText().toString());
            user_name = name.getEditText().getText().toString();
            fullName.setText(user_name);
            return true;
        }else {
            return false;
        }
    }
}