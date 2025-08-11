package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StaffHomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_home);



        // Finds the settings button by ID
        Button settingsButton = findViewById(R.id.settingsButton);
        // Sets an onClickListener (when x is clicked, do y)
        settingsButton.setOnClickListener(v -> {
            // Start up 'StaffSettingsActivity.java' (activity for 'staff_settings.xml')
            Intent intent = new Intent(StaffHomeActivity.this, StaffSettingsActivity.class);
            startActivity(intent);
        });


        // Finds the manage catalogue button by ID
        Button manageCatalogueButton = findViewById(R.id.manageCatalogueButton);
        // Sets an onClickListener (when x is clicked, do y)
        manageCatalogueButton.setOnClickListener(v -> {
            // Start up 'StaffManageCatalogueActivity.java' (activity for 'staff_manage_catalogue.xml')
            Intent intent = new Intent(StaffHomeActivity.this, StaffManageCatalogueActivity.class);
            startActivity(intent);
        });


        // Finds the manage members button by ID
        Button manageMembersButton = findViewById(R.id.manageMembersButton);
        // Sets an onClickListener (when x is clicked, do y)
        manageMembersButton.setOnClickListener(v -> {
            // Start up 'StaffManageMembersActivity.java' (activity for 'staff_manage_members.xml')
            Intent intent = new Intent(StaffHomeActivity.this, StaffManageMembersActivity.class);
            startActivity(intent);
        });

        // Finds the manage book requests button by ID
        Button manageBookRequestsButton = findViewById(R.id.manageBookRequestsButton);
        // Sets an onClickListener (when x is clicked, do y)
        manageBookRequestsButton.setOnClickListener(v -> {
            // Start up 'StaffManageBookRequestsActivity.java' (activity for 'staff_manage_book_requests.xml')
            Intent intent = new Intent(StaffHomeActivity.this, StaffManageBookRequestsActivity.class);
            startActivity(intent);
        });
    }
}