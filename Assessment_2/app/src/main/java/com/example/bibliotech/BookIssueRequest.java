package com.example.bibliotech;

// ## TODO - more functionality that would be added if not for time constraints

public class BookIssueRequest {

    private String username;
    private String book_title;
    private String issue_date;
    private String return_date;


    public BookIssueRequest(String username, String book_title, String issue_date, String return_date) {
        this.username= username;
        this.book_title= book_title;
        this.issue_date = issue_date;
        this.return_date = return_date;
    }

}
