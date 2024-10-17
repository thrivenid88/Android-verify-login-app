package com.example.dummyproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;



public class LoginActivity extends AppCompatActivity {
    private TextView Aid, Acode;
    private EditText etId, etCode;
    private ImageButton btnTogglePasswordVisibility;
    private DatabaseHelper databaseHelper;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Aid = findViewById(R.id.textview1);
        Acode = findViewById(R.id.textview2);
        etId = findViewById(R.id.edittextID);
        etCode = findViewById(R.id.edittextCode);
        btnTogglePasswordVisibility = findViewById(R.id.btnTogglePasswordVisibility);
        databaseHelper = new DatabaseHelper(this);  // Initialize your DatabaseHelper instance

        Button loginButton = findViewById(R.id.btn_login);
        Button requestAccessButton = findViewById(R.id.btn_request_access);
        Button accessButton = findViewById(R.id.btn_access);

        // Button color management
        loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.Pink));
        requestAccessButton.setBackgroundColor(ContextCompat.getColor(this, R.color.DRed));
        loginButton.setTag(R.color.Pink);
        requestAccessButton.setTag(R.color.DRed);

        // Set button colors
        loginButton.setBackgroundColor(ContextCompat.getColor(this, R.color.Pink));
        requestAccessButton.setBackgroundColor(ContextCompat.getColor(this, R.color.DRed));

        // Store the initial colors in the button tags
        loginButton.setTag(R.color.Pink);
        requestAccessButton.setTag(R.color.DRed);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int colorButton1 = (int) loginButton.getTag();
                int colorButton2 = (int) requestAccessButton.getTag();

                // Swap the colors
                loginButton.setBackgroundColor(ContextCompat.getColor(view.getContext(), colorButton2));
                requestAccessButton.setBackgroundColor(ContextCompat.getColor(view.getContext(), colorButton1));

                // Update the tags with the new colors
                loginButton.setTag(colorButton2);
                requestAccessButton.setTag(colorButton1);

//                boolean isAuthenticated = checkLoginCredentials(etId.getText().toString(), etCode.getText().toString());
                // Implement your login logic here
            }
        });

        requestAccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color1 = (int) loginButton.getTag();
                int color2 = (int) requestAccessButton.getTag();

                loginButton.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, color2));
                requestAccessButton.setBackgroundColor(ContextCompat.getColor(LoginActivity.this, color1));

                // Update the tags
                requestAccessButton.setTag(color2);
                requestAccessButton.setTag(color1);
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String userId = etId.getText().toString();
//                String userCode = etCode.getText().toString();
//
//                if (checkLoginCredentials(userId, userCode)) {
//                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                    // Proceed to the next activity
////                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                    startActivity(intent);
//                } else {
//                    Toast.makeText(LoginActivity.this, "Invalid ID or Access Code", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        btnTogglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    etCode.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnTogglePasswordVisibility.setImageResource(R.drawable.ic_eye_code);
                    isPasswordVisible = false;
                } else {
                    etCode.setInputType(InputType.TYPE_CLASS_TEXT);
                    btnTogglePasswordVisibility.setImageResource(R.drawable.ic_eye_code);
                    isPasswordVisible = true;
                }
                etCode.setSelection(etCode.getText().length());
            }
        });

        accessButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, VerificationActivity.class);
            startActivity(intent);
        });
    }

    // Method to check login credentials against the database
//    private boolean checkLoginCredentials(String id, String code) {
//        // Query the database using DatabaseHelper
////        boolean userExists = databaseHelper.checkUser(id, code);  // Assuming you have this method in DatabaseHelper
//
//        if (userExists) {
//            return true; // Credentials are valid
//        } else {
//            return false; // Invalid credentials
//        }
//    }
}

