package com.example.bibliotech;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import com.example.bibliotech.data.AppDatabase;
import com.example.bibliotech.data.BookDao;
import com.example.bibliotech.data.Book;
import android.widget.Toast;
import java.util.List;


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




        BookDao dao = AppDatabase.getInstance(this).bookDao();

        /*
        //Old code to add books for testing ## remove later
        new Thread(() -> {
            dao.insert(new Book("1984","Orwell",false));
            List<Book> books = dao.getAll();
            runOnUiThread(() -> {
                StringBuilder titles = new StringBuilder();
                for (Book b : books) titles.append(b.title).append("\n");
                Toast.makeText(this, titles.toString(), Toast.LENGTH_LONG).show();
            });
        }).start();

         */




    }
}
