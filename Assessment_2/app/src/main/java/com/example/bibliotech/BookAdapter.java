package com.example.bibliotech;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bibliotech.data.Book;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Book> books;
    private OnItemClickListener listener;

    // adding an interface for any click events :)
    public interface OnItemClickListener {
        void onItemClick(Book  book);
    }


    // adapter taking in books and helping assign click listeners - so the user can click on books to
    public BookAdapter(List<Book> books, OnItemClickListener listener) {
        this.books = books;
        this.listener = listener;
    }


    public void filter(List<Book> filteredBooks) {
        this.books = filteredBooks;  // replacing the list with the filtered one
        notifyDataSetChanged();      // telling the recyclerView to refresh (data has changed)
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // turning item_book.xml into a viewObject
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // binding the book data to the  textView
        Book book = books.get(position);
        holder.titleText.setText(book.title);
        holder.authorText.setText(book.author);

        // calling listener when this row's clicked
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(book);
            }
        });
    }
    //

    @Override
    public int getItemCount() {
        return books.size();
    }

    // keeps references to the views for each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Initialising the title and author
        TextView titleText;
        TextView authorText;

        public ViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.textBookTitle);
            authorText = itemView.findViewById(R.id.textBookAuthor);
        }
    }
}
