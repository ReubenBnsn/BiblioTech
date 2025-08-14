package com.example.bibliotech.data;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "member")
public class Member {

    @PrimaryKey
    @NonNull

    public String username;

    @SerializedName("firstname")
    public String firstname;

    @SerializedName("lastname")
    public String lastname;
    public String email;
    public String contact;

    @SerializedName("membership_end_date")
    public String membership_end_date;

    // Constructors yay!
    public Member(String first, String last, String email, String contact, String endDate) {}

    public Member(String username, String firstname, String lastname,
                  String email, String contact, String membership_end_date) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contact = contact;
        this.membership_end_date = membership_end_date;
    }

    // Oh yeah getters and setters for all fields
    public String getUsername() {return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFirstname() {return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() {return lastname;}
    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() {return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContact() {return contact;}
    public void setContact(String contact) {this.contact = contact; }

    public String getMembershipEndDate() {return membership_end_date; }
    public void setMembershipEndDate(String membership_end_date) { this.membership_end_date = membership_end_date; }

}
