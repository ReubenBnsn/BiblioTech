package com.example.bibliotech.api;

import com.google.gson.annotations.SerializedName;

public class UpdateMemberRequest {
    @SerializedName("firstname")
    private String firstname;

    @SerializedName("lastname")
    private String lastname;

    @SerializedName("email")
    private String email;

    @SerializedName("contact")
    private String contact;

    @SerializedName("membership_end_date")
    private String membershipEndDate;

    public UpdateMemberRequest(String firstname, String lastname, String email, String contact, String membershipEndDate) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contact = contact;
        this.membershipEndDate = membershipEndDate;
    }
}
