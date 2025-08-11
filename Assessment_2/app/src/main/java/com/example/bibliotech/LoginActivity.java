package com.example.bibliotech;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // loads login.xml file to start off the program
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Finds the login button by ID
        Button loginButton = findViewById(R.id.loginButton);
        // Sets an onClickListener (when x is clicked, do y)
        loginButton.setOnClickListener(v -> {
            // Start up 'MemberHomeActivity.java' (activity for 'member_home.xml')
            Intent intent = new Intent(LoginActivity.this, MemberHomeActivity.class);
            startActivity(intent);
        });

        // Finds the staff login button by ID ## TEMPORARY - FOR THE LESS FUNCTIONAL STAGE
        Button staffLoginButton = findViewById(R.id.staffLoginButton);
        // Sets an onClickListener (when x is clicked, do y)
        staffLoginButton.setOnClickListener(v -> {
            // Start up 'StaffHomeActivity.java' (activity for 'staff_home.xml')
            Intent intent = new Intent(LoginActivity.this, StaffHomeActivity.class);
            startActivity(intent);
        });
    }
}
