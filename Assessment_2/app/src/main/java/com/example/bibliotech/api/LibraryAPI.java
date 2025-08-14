package com.example.bibliotech.api;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;
import com.example.bibliotech.data.Member;

public interface LibraryAPI {


    @GET("members")
    Call<List<Member>> getAllMembers();

}
