package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StaffManageCatalogueActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_manage_catalogue);

        // Finds the back button by ID
        Button backButton = findViewById(R.id.backButton);
        // Sets an onClickListener (when x is clicked, do y)
        backButton.setOnClickListener(v -> {
            // Start up 'StaffHomeActivity.java' (activity for 'staff_home.xml')
            Intent intent = new Intent(StaffManageCatalogueActivity.this, StaffHomeActivity.class);
            startActivity(intent);
        });
    }
}