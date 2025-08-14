package com.example.bibliotech.api;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

import com.example.bibliotech.BookIssueRequest;
import com.example.bibliotech.data.Member;

public interface LibraryAPI {

    @GET("members")
    Call<List<Member>> getAllMembers();

    @POST("members")
    Call<MessageResponse> addMember(@Body Member m);

    @PUT("members/{username}")
    Call<MessageResponse> updateMember(@Path("username") String username, @Body UpdateMemberRequest body);

    @DELETE("members/{username}")
    Call<MessageResponse> deleteMember(@Path("username") String username);


    @POST("books")
    Call<MessageResponse> issueBook(@Body BookIssueRequest request);

    @HTTP(method = "DELETE", path = "books", hasBody = true)
    Call<MessageResponse> returnBook(@Body BookIssueRequest request);



}
