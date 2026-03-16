package com.example.emergencyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private TextView tvUserName, tvUserEmail;
    private Button btnEraseData, btnSignOut, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        btnEraseData = findViewById(R.id.btnEraseData);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnBack = findViewById(R.id.btnBack);

        // Load User Info
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String name = prefs.getString("name", "User");
        String email = prefs.getString("email", "user@example.com");

        tvUserName.setText("Name: " + name);
        tvUserEmail.setText("Email: " + email);

        // Erase Data Logic
        btnEraseData.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Erase All Data")
                .setMessage("Are you sure you want to erase all user data? This cannot be undone.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.apply();
                    Toast.makeText(this, "Data Erased", Toast.LENGTH_SHORT).show();
                    // Go back to Login after erasing data
                    Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("No", null)
                .show();
        });

        // Sign Out Logic
        btnSignOut.setOnClickListener(v -> {
            // Usually, you might clear a "remember me" flag here
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Back Logic
        btnBack.setOnClickListener(v -> finish());
    }
}
