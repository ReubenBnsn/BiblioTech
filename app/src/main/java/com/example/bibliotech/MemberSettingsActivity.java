package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MemberSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_settings);


        // Finds the back button by ID
        Button backButton = findViewById(R.id.backButton);
        // Sets an onClickListener (when x is clicked, do y)
        backButton.setOnClickListener(v -> {
            // Start up 'MemberHomeActivity.java' (activity for 'member_home.xml')
            Intent intent = new Intent(MemberSettingsActivity.this, MemberHomeActivity.class);
            startActivity(intent);
        });


        // Finds the logout button by ID
        Button logoutButton = findViewById(R.id.logoutButton);
        // Sets an onClickListener (when x is clicked, do y)
        logoutButton.setOnClickListener(v -> {
            // Takes the user back to 'LoginActivity.java' (activity for 'login.xml')
            Intent intent = new Intent(MemberSettingsActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }
}