package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MemberBookRequestsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_book_requests);

        // Finds the back button by ID
        Button backButton = findViewById(R.id.backButton);
        // Sets an onClickListener (when x is clicked, do y)
        backButton.setOnClickListener(v -> {
            // Start up 'MemberHomeActivity.java' (activity for 'member_home.xml')
            Intent intent = new Intent(MemberBookRequestsActivity.this, MemberHomeActivity.class);
            startActivity(intent);
        });

    }
}