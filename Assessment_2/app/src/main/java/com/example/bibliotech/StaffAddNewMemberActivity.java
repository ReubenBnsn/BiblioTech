package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;

public class StaffAddNewMemberActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_add_new_member);

        // Finds the back button by ID
        Button backButton = findViewById(R.id.backButton);
        // Sets an onClickListener (when x is clicked, do y)
        backButton.setOnClickListener(v -> {
            // Take back to 'StaffManageMembersActivity.java' (activity for 'staff_manage_members.xml')
            Intent intent = new Intent(StaffAddNewMemberActivity.this, StaffManageMembersActivity.class);
            startActivity(intent);
        });

        // Finds the add Member button by ID
        Button addMemberButton = findViewById(R.id.addMemberButton);
        // YET TO ADD FUNCTIONALITY FOR THIS ##


        addMemberButton.setOnClickListener(v -> {

            // Code for the entry fields - checking they're formatted properly and aren't empty

            EditText addContactEmail = findViewById(R.id.addContactEmail);
            String addContactEmailText = addContactEmail.getText().toString().trim();

            EditText addFirstName = findViewById(R.id.addFirstName);
            String addFirstNameText = addFirstName.getText().toString().trim();

            EditText addLastName = findViewById(R.id.addLastName);
            String addLastNameText = addLastName.getText().toString().trim();


            // Validating the email!
            if (!Patterns.EMAIL_ADDRESS.matcher(addContactEmailText).matches()) {
                addContactEmail.setError("Please enter a valid email address!");
                return;
            }
            // Validating the first name!
            if (addFirstNameText.isEmpty()) {
                addFirstName.setError("Please enter a valid first name!");
                return;
            }
            // Validating the last name!
            if (addLastNameText.isEmpty()) {
                addLastName.setError("Please enter a valid last name!");
                return;
            }


            // after this, all inputs should be validated! yay!
            // after this I will want to save the data to the API or a database

        });


    }
}