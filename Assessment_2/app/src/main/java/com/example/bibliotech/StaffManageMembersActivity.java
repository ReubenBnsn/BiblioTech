package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bibliotech.api.APIClient;
import com.example.bibliotech.api.LibraryAPI;
import com.example.bibliotech.data.AppDatabase;
import com.example.bibliotech.data.Member;
import com.example.bibliotech.data.MemberDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffManageMembersActivity extends AppCompatActivity {

    private MemberDao  memberDao;
    private RecyclerView  rv;
    private List<Member>  allMembers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_manage_members);


        memberDao = AppDatabase.getInstance(this).memberDao();
        rv = findViewById(R.id.memberList);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fetchMembersFromAPI();


        // Finds the back button by ID
        Button backButton = findViewById(R.id.backButton);
        // Sets an onClickListener (when x is clicked, do y)
        backButton.setOnClickListener(v -> {
            // Start up 'StaffHomeActivity.java' (activity for 'staff_home.xml')
            Intent intent = new Intent(StaffManageMembersActivity.this, StaffHomeActivity.class);
            startActivity(intent);
        });
    }


    private void fetchMembersFromAPI() {
        LibraryAPI api = APIClient.getApi();
        api.getAllMembers().enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Member> members = response.body();

                    runOnUiThread(() -> {
                        MemberAdapter adapter = new MemberAdapter(members, member -> {
                            // handle item click if you want ## later maybe?
                        });
                        rv.setAdapter(adapter);
                    });

                    /*
                    new Thread(() -> {
                        // clearing old members & inserting new ones
                        memberDao.deleteAll();
                        memberDao.insertAll(members);

                        runOnUiThread(() -> {
                            // supplying the  adapter with new data
                            MemberAdapter adapter = new MemberAdapter(members, member -> {

                                // ## TODO - Handle member click - OPEN EDIT PAGE FOR Individual MEMBER
                            });
                            rv.setAdapter(adapter);
                        });
                    }).start();

                     */

                } else
                {
                    runOnUiThread(() -> {
                        // Error message and code here - with Toast (yummy)
                        android.widget.Toast.makeText(StaffManageMembersActivity.this,
                                "Couldn't fetch members: " + response.code(),
                                android.widget.Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                runOnUiThread(() -> {
                    // Error message and code here - with more toast (mmmm)
                    android.widget.Toast.makeText(StaffManageMembersActivity.this,
                            "There was a network error: " + t.getMessage(),
                            android.widget.Toast.LENGTH_SHORT).show();

                });
            }
        });
    }
}