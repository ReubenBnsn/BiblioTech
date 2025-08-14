package com.example.bibliotech.data;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface MemberDao {

    // 1) Get all members from local DB
    @Query("SELECT * FROM member")
    List<Member> getAll();



    // 2) Insert a member
    @Insert
    void insert(Member member);

    // 3) Insert multiple members at once (useful when syncing API list)
    @Insert
    void insertAll(List<Member> members);

    // updating a member
    @Update
    void update(Member member);

    // deleting a member
    @Delete
    void delete(Member member);

    // deleting all members  ## do i need this
    @Query("DELETE FROM member")
    void deleteAll();


}
