package com.example.bibliotech.data;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM books")
    List<Book> getAll();


    @Insert
    void insert(Book book);


    @Update
    void update(Book book);


    @Delete
    void delete(Book book);

}