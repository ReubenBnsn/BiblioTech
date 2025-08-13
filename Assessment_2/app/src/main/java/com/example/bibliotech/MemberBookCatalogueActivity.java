package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bibliotech.data.AppDatabase;
import com.example.bibliotech.data.Book;
import com.example.bibliotech.data.BookDao;
import java.util.List;

public class MemberBookCatalogueActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_book_catalogue);

        // Finds the back button by ID
        Button backButton = findViewById(R.id.backButton);
        // Sets an onClickListener (when x is clicked, do y)
        backButton.setOnClickListener(v -> {
            // Start up 'MemberHomeActivity.java' (activity for 'member_home.xml')
            Intent intent = new Intent(MemberBookCatalogueActivity.this, MemberHomeActivity.class);
            startActivity(intent);
        });



        RecyclerView rv = findViewById(R.id.bookList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        BookDao dao = AppDatabase.getInstance(this).bookDao();

        new Thread(() -> {
            List<Book> books = dao.getAll();
            runOnUiThread(() -> rv.setAdapter(new BookAdapter(books)));
        }).start();



    }
}