package com.example.bibliotech;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bibliotech.data.Book;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private final List<Book> books;

    public BookAdapter(List<Book> books) {
        this.books = books;
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
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    // keeps references to the views for each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText;

        public ViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.textBookTitle);
        }
    }
}
