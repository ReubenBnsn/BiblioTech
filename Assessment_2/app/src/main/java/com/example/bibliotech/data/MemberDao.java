package com.example.bibliotech.data;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

import retrofit2.http.GET;

@Dao
public interface MemberDao {

    // Getting members from local database
    @Query("SELECT * FROM member")
    List<Member> getAll();


    //getting a members record from their username
    @Query("SELECT * FROM member WHERE username = :username LIMIT 1")
    Member getByUsername(String username);


    // inserting a member
    @Insert
    void insert(Member member);


    // updating a member
    @Update
    void update(Member member);

    // deleting a member
    @Delete
    void delete(Member member);




    /* ## MIGHT NOT NEED - DELETE IF UNNEEDED
    // inserting multiple members (partly for syncing API list)
    @Insert
    void insertAll(List<Member> members);



    // deleting all members  ## do i need this
    @Query("DELETE FROM member")
    void deleteAll();

     */


}
