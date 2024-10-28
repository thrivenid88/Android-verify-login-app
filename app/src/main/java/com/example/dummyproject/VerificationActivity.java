package com.example.dummyproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast; // Import Toast
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.security.cert.Certificate;

public class VerificationActivity extends AppCompatActivity {

    private EditText certificateIdInput;
    private Button clearButton;
    private Button verifyButton;
    private TextView dataFoundLabel, imageLabel, idLabel, nameLabel, titleLabel, issuedOnLabel, expiryDateLabel, statusLabel, descriptionLabel,img_urlLabel;
    private ImageView imagePlaceholder;
    private LinearLayout dataFields, layoutDataFound;
    private TextView Logout;
    private DatabaseHelper dbHelper; // Database Helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        // Initialize UI components
        certificateIdInput = findViewById(R.id.certificateID);
        clearButton = findViewById(R.id.clearButton);
        verifyButton = findViewById(R.id.verifyButton);
        dataFoundLabel = findViewById(R.id.dataFoundLabel);
//        imageLabel = findViewById(R.id.imageLabel);
        imagePlaceholder = findViewById(R.id.imagePlaceholder);
        dataFields = findViewById(R.id.dataFields);
        layoutDataFound = findViewById(R.id.layout_data_found);
        idLabel = findViewById(R.id.labelId);
        nameLabel = findViewById(R.id.labelName);
        titleLabel = findViewById(R.id.labelTitle);
        issuedOnLabel = findViewById(R.id.labelIssued_on);
        expiryDateLabel = findViewById(R.id.label_expiry_date);
        statusLabel = findViewById(R.id.labelStatus);
        descriptionLabel = findViewById(R.id.label_Description);
//        img_urlLabel=findViewById(R.id.label_url);
        Logout = findViewById(R.id.logoutLink);

        // Initialize Database Helper
//        dbHelper = new DatabaseHelper(this);


        // Clear button functionality
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                certificateIdInput.setText(""); // Clear the input field
                hideData(); // Hide data when the field is cleared
            }
        });

        // Verify button functionality
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String certificateId = certificateIdInput.getText().toString();
                if (!certificateId.isEmpty()) {
                    // Create API Service
                    ApiService apiService = RetrofitClient.getClient("https://internship.irinfotech.com/verification/api/").create(ApiService.class);
                    // Make API call
                    // Make API call
                    Call<CertificateResponse> call = apiService.getCertificateById(certificateId);

                    call.enqueue(new Callback<CertificateResponse>() {  // Use CertificateResponse here
                        @Override
                        public void onResponse(Call<CertificateResponse> call, Response<CertificateResponse> response) {
                            Log.d("API Response", "Response Code: " + response.code());
                            if (response.isSuccessful() && response.body() != null) {
                                CertificateResponse certificateResponse = response.body();
                                Log.d("API Response", "Response Body: " + certificateResponse.toString());

                                MyCertificate certificate = certificateResponse.getData(); // Fetch MyCertificate from CertificateResponse

                                if (certificate != null) { // Check if certificate is not null
                                    // Set data to UI
                                    layoutDataFound.setVisibility(View.VISIBLE);
                                    dataFields.setVisibility(View.VISIBLE);

                                    idLabel.setText("ID: " + certificate.getCert_id());
                                    nameLabel.setText("Name: " + certificate.getName());
                                    titleLabel.setText("Title: " + certificate.getTitle());
                                    issuedOnLabel.setText("Issued On: " + certificate.getIssue_date());
                                    expiryDateLabel.setText("Expiry Date: " + certificate.getExpiry_date());
                                    statusLabel.setText("Status: " + certificate.getStatus());
                                    descriptionLabel.setText("Description: " + certificate.getDescription());

                                    // Load the image with Glide
                                    Glide.with(VerificationActivity.this)
                                            .load(certificate.getImg_url())
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .placeholder(R.drawable.certificate)
                                            .into(imagePlaceholder);
                                } else {
                                    hideData();
                                    Toast.makeText(VerificationActivity.this, "No certificate found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                hideData();
                                Toast.makeText(VerificationActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<CertificateResponse> call, Throwable t) {
                            hideData();
                            Toast.makeText(VerificationActivity.this, "Failed to connect to API", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    hideData();  // If certificate ID is empty, hide all data
                }
            }
        });

        // Logout functionality
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("user_session", MODE_PRIVATE).edit().clear().apply();

                // Redirect to login
                Intent intent = new Intent(VerificationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        });
    }

    // Method to hide data when needed
    private void hideData() {
        layoutDataFound.setVisibility(View.GONE);
        dataFields.setVisibility(View.GONE);
    }
}

