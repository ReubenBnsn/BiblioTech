package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bibliotech.data.AppDatabase;
import com.example.bibliotech.data.Book;
import com.example.bibliotech.data.BookDao;
import java.util.List;


public class StaffManageCatalogueActivity extends AppCompatActivity {
    private BookDao dao;
    private RecyclerView rv;
    private List<Book> allBooks; // saving books as a variable

    private void loadBooks() {
        new Thread(() -> {
            allBooks = dao.getAll();
            runOnUiThread(() -> {
                BookAdapter adapter = new BookAdapter(allBooks,  book ->{
                    // opening the manage book screen
                    Intent intent = new Intent(this, StaffManageBookActivity.class);
                    intent.putExtra("BOOK_ID", book.id);
                    startActivityForResult(intent, 1); // lets program know when it comes back
                });
                rv.setAdapter(adapter);
            });
        }).start();
    }

    private void showAddDialog() {
        // flipping inflating the item_book layout
        android.view.View dialogView = getLayoutInflater().inflate(R.layout.add_book, null);

        EditText inputBookTitle = dialogView.findViewById(R.id.inputBookTitle);
        EditText inputBookAuthor = dialogView.findViewById(R.id.inputBookAuthor);

        new android.app.AlertDialog.Builder(this)
                .setTitle("Add New Book")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = inputBookTitle.getText().toString().trim();
                    String author = inputBookAuthor.getText().toString().trim();

                    if (!title.isEmpty() && !author.isEmpty()) {
                        insertBook(title, author);
                    }
                })
                .setNegativeButton("Cancel", null) // closes dialog if Cancel is clicked
                .show();
    }

    @Override
    protected void onActivityResult(int requestACode,  int resultCode,  Intent data) {
        super.onActivityResult(requestACode, resultCode, data);


        if (requestACode== 1 && resultCode== RESULT_OK) {
            // reload the list because a book was updated!!!
            loadBooks();
        }
    }


    private void insertBook(String title, String author) {
        new Thread(() -> {
            dao.insert(new Book(title, author, false));
            loadBooks();
        }).start();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_manage_catalogue);


        dao = AppDatabase.getInstance(this).bookDao();

        // recycler view for book list setup
        rv = findViewById(R.id.bookList);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // adding a divider between books in the catalogue!
        DividerItemDecoration lilDivider= new DividerItemDecoration(rv.getContext(), LinearLayoutManager.VERTICAL);
        rv.addItemDecoration(lilDivider);


        loadBooks();

        // 'Add Book' button! xo
        Button addButton = findViewById(R.id.addBookButton);
        addButton.setOnClickListener(v ->
                showAddDialog()
        );


        // finds the back button by ID
        Button backButton = findViewById(R.id.backButton);
        // Sets an onClickListener (when x is clicked, do y)
        backButton.setOnClickListener(v -> {
            // starting up 'StaffHomeActivity.java' (activity for 'staff_home.xml')
            Intent intent = new Intent(StaffManageCatalogueActivity.this, StaffHomeActivity.class);
            startActivity(intent);
        });



        // finds the search bar by ID
        SearchView search = findViewById(R.id.searchBar);


        // expanding the search view when clicked
        search.setOnClickListener(v -> {
            search.setIconified(false);  // opening the search input
            search.requestFocus();
        });



        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit (String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Book> filteredList;
                if (newText.isEmpty()) {
                    filteredList = allBooks; // if the user has nothing typed, show all books
                }
                else {
                    filteredList = new java.util.ArrayList<>();
                    for (Book b : allBooks) {
                        if (b.title.toLowerCase().contains(newText.toLowerCase())   ||    b.author.toLowerCase().contains(newText.toLowerCase()))  {
                            filteredList.add(b);
                        }
                    }
                }

                // stops crashing if filtering happens before the data is loaded (or if it loads wrong)
                if (rv.getAdapter() != null) {
                    // telling the adapter to use the new list (if no issues)
                    ((BookAdapter) rv.getAdapter()).filter(filteredList);

                    // puts the user to the top of the results when  search results change
                    rv.scrollToPosition(0);
                }

                return true;


            }

        });


    }




}