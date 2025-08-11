package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MemberHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_home);


        // Finds the settings button by ID
        Button settingsButton = findViewById(R.id.settingsButton);
        // Sets an onClickListener (when x is clicked, do y)
        settingsButton.setOnClickListener(v -> {
            // Start up 'MemberSettingsActivity.java' (activity for 'member_settings.xml')
            Intent intent = new Intent(MemberHomeActivity.this, MemberSettingsActivity.class);
            startActivity(intent);
        });


        // Finds the book catalogue button by ID
        Button bookCatalogueButton = findViewById(R.id.bookCatalogueButton);
        // Sets an onClickListener (when x is clicked, do y)
        bookCatalogueButton.setOnClickListener(v -> {
            // Start up 'MemberBookCatalogueActivity.java' (activity for 'member_book_catalogue.xml')
            Intent intent = new Intent(MemberHomeActivity.this, MemberBookCatalogueActivity.class);
            startActivity(intent);
        });


        // Finds the personal information button by ID
        Button personalInformationButton = findViewById(R.id.personalInformationButton);
        // Sets an onClickListener (when x is clicked, do y)
        personalInformationButton.setOnClickListener(v -> {
            // Start up 'MemberPersonalInformationActivity.java' (activity for 'member_personal_information.xml')
            Intent intent = new Intent(MemberHomeActivity.this, MemberPersonalInformationActivity.class);
            startActivity(intent);
        });


        // Finds the book requests button by ID
        Button bookRequestsButton = findViewById(R.id.bookRequestsButton);
        // Sets an onClickListener (when x is clicked, do y)
        bookRequestsButton.setOnClickListener(v -> {
            // Start up 'MemberBookRequestsActivity.java' (activity for 'member_book_requests.xml')
            Intent intent = new Intent(MemberHomeActivity.this, MemberBookRequestsActivity.class);
            startActivity(intent);
        });

    }
}