package com.example.bibliotech;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bibliotech.data.AppDatabase;
import com.example.bibliotech.data.Book;
import com.example.bibliotech.data.BookDao;
import java.util.List;


public class StaffManageCatalogueActivity extends AppCompatActivity {
    private BookDao dao;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_manage_catalogue);


        dao = AppDatabase.getInstance(this).bookDao();

        // recycler view for book list setup
        rv = findViewById(R.id.bookList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        loadBooks();

        // 'Add Book' button! xo
        Button addButton = findViewById(R.id.addBookButton);
        addButton.setOnClickListener(v ->
                showAddDialog()
        );


        // Finds the back button by ID
        Button backButton = findViewById(R.id.backButton);
        // Sets an onClickListener (when x is clicked, do y)
        backButton.setOnClickListener(v -> {
            // Start up 'StaffHomeActivity.java' (activity for 'staff_home.xml')
            Intent intent = new Intent(StaffManageCatalogueActivity.this, StaffHomeActivity.class);
            startActivity(intent);
        });
    }


    private void loadBooks() {
        new Thread(() -> {
            List<Book> books = dao.getAll();
            runOnUiThread(() -> rv.setAdapter(new BookAdapter(books)));
        }).start();
    }


    private void showAddDialog() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("New Book Title");
        EditText input = new EditText(this);
        input.setHint("Enter title");
        b.setView(input);
        b.setPositiveButton("Add", (d, i) -> {
            String title = input.getText().toString().trim();
            if (!title.isEmpty()) insertBook(title);
        });
        b.setNegativeButton("Cancel", null);
        b.show();
    }

    private void insertBook(String title) {
        new Thread(() -> {
            dao.insert(new Book(title, "Unknown", false));
            loadBooks();
        }).start();
    }
}