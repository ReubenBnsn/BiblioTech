package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bibliotech.data.AppDatabase;
import com.example.bibliotech.data.Book;
import com.example.bibliotech.data.BookDao;

public class StaffManageBookActivity extends AppCompatActivity {


    private BookDao dao;
    private Book currentBook;
    private EditText editTitle;
    private EditText editAuthor;
    private EditText editIssueDate;
    private EditText editReturnDate;
    private EditText editBorrower;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // loads staff)manage_book.xml file to start off the program
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_manage_book);

        dao = AppDatabase.getInstance(this).bookDao();
        bookId = getIntent().getIntExtra("BOOK_ID", -1); // getting ID from intent


        // linking the views to the variables
        editTitle = findViewById(R.id.editBookTitle);
        editAuthor = findViewById(R.id.editBookAuthor);
        editIssueDate = findViewById(R.id.editBookIssueDate);
        editReturnDate = findViewById(R.id.editBookReturnDate);
        editBorrower = findViewById(R.id.editCurrentBookBorrower);


        // Finds the delete, save and back buttons by ID
        Button deleteBookButton = findViewById(R.id.deleteBookButton);
        Button saveChangesButton = findViewById(R.id.saveChangesButton);
        Button backButton = findViewById(R.id.backButton);

        loadBook();

        saveChangesButton.setOnClickListener(v -> updateBook());

        // Sets an onClickListener (when back button is clicked, take to x page)
        backButton.setOnClickListener(v -> {
            // Take back to 'StaffManageCatalogueActivity.java' (activity for 'staff_manage_catalogue.xml')
            Intent intent = new Intent(StaffManageBookActivity.this, StaffManageCatalogueActivity.class);
            startActivity(intent);
        });


        // Sets an onClickListener (when delete book is clicked, give a warning. If they confirm deletion - give popup)
        deleteBookButton.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Delete Book")
                    .setMessage("Are you sure you want to delete this book? You won't be able to restore its' record")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // if they click yes — call deletion
                        deleteBook();
                    })
                    .setNegativeButton("No", null) // if they say no - close the popup
                    .show();
        });


    }

    private void loadBook() {
        new Thread(() -> {
            //finding the ID manually
            for (Book b : dao.getAll()) {
                if (b.id ==  bookId) {
                    currentBook= b;
                    break;
                }
            }
            runOnUiThread(() -> {
                if (currentBook !=  null) {
                    editTitle.setText(currentBook.title);
                    editAuthor.setText(currentBook.author);
                    // ## NO Issue/return dates or borrowers yet- add later
                }
            });
        }).start();
    }


     // Updating the books details
    private void updateBook() {
        new Thread(() -> {
            currentBook.title = editTitle.getText().toString();
            currentBook.author = editAuthor.getText().toString();
            dao.update(currentBook);

            runOnUiThread(() -> {
                // alerting other activities the book catalogue should update
                setResult(RESULT_OK);
                finish();
            });

        }).start();
    }



    private void deleteBook() {
        new Thread(() -> {
            dao.delete(currentBook); // delete the book from the database
            runOnUiThread(() -> {
                // close this page and take user back to 'StaffManageCatalogueActivity.java' (activity for 'staff_manage_catalogue.xml')
                Intent intent = new Intent(StaffManageBookActivity.this, StaffManageCatalogueActivity.class);
                startActivity(intent);
            });
        }).start();
    }


}
