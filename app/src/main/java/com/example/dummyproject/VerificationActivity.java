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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
        dbHelper = new DatabaseHelper(this);


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
                Log.d("Verification", "Certificate ID entered: " + certificateId);

                if (!certificateId.isEmpty()) {
                    Cursor cursor = dbHelper.getCertificateById(certificateId);
                    if (cursor != null) {
                        Log.d("Verification", "Cursor count: " + cursor.getCount());
                        if (cursor.moveToFirst()) {
                            // Show data layout when verification is successful
                            layoutDataFound.setVisibility(View.VISIBLE);
                            dataFields.setVisibility(View.VISIBLE);

                            // Set data fetched from the database
                            idLabel.setText("ID: " + cursor.getString(cursor.getColumnIndexOrThrow("cert_id")));
                            nameLabel.setText("Name: " + cursor.getString(cursor.getColumnIndexOrThrow("name")));
                            titleLabel.setText("Title: " + cursor.getString(cursor.getColumnIndexOrThrow("title")));
                            issuedOnLabel.setText("Issued On: " + cursor.getString(cursor.getColumnIndexOrThrow("issue_date")));
                            expiryDateLabel.setText("Expiry Date: " + cursor.getString(cursor.getColumnIndexOrThrow("expiry_date")));
                            statusLabel.setText("Status: " + cursor.getString(cursor.getColumnIndexOrThrow("status")));
                            descriptionLabel.setText("Description: " + cursor.getString(cursor.getColumnIndexOrThrow("description")));
                            // Get the image URL based on the certificate ID
                            String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow("img_url"));
                            Log.d("Verification", "Image URL for ID " + certificateId + ": " + imageUrl);  // Log the image URL
                            Glide.with(VerificationActivity.this)
                                    .load(imageUrl)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Disable caching
                                    .skipMemoryCache(true) // Skip memory cache
                                    .placeholder(R.drawable.img_2) // Placeholder while loading
                                    .into(imagePlaceholder); // ImageView where the image will be displayed
                            cursor.close();  // Always close the cursor when done
                        } else {
                            Log.d("Verification", "No data found for certificate ID");
                            hideData();  // Hide if no data found
                        }
                    } else {
                        Log.d("Verification", "Cursor is null");
                        hideData();  // Hide if cursor is null
                    }
                } else {
                    Log.d("Verification", "Certificate ID is empty");
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

