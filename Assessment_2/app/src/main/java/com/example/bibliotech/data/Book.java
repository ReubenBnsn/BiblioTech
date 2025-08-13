package com.example.bibliotech.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "books")
public class Book {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String author;
    public boolean isCheckedOut;


    public Book(String title, String author, boolean isCheckedOut) {
        this.title = title;
        this.author = author;
        this.isCheckedOut = isCheckedOut;
    }
}
