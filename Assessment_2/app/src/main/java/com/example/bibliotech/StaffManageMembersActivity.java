package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bibliotech.api.APIClient;
import com.example.bibliotech.api.LibraryAPI;
import com.example.bibliotech.api.MessageResponse;
import com.example.bibliotech.data.AppDatabase;
import com.example.bibliotech.data.Book;
import com.example.bibliotech.data.Member;
import com.example.bibliotech.data.MemberDao;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;



public class StaffManageMembersActivity extends AppCompatActivity {

    private MemberDao  memberDao;
    private RecyclerView  rv;
    private List<Member>  allMembers;








    private void loadMembers() {
        new Thread(() -> {
            allMembers = memberDao.getAll();
            runOnUiThread(() -> {
                MemberAdapter adapter = new MemberAdapter(allMembers,  member ->{
                    // opening the manage book screen
                    Intent intent = new Intent(this, StaffManageBookActivity.class);
                    intent.putExtra("MEMBER_USERNAME", member.username); // needs sorting here
                    startActivityForResult(intent, 1); // lets program know when it comes back
                });
                rv.setAdapter(adapter);
            });
        }).start();
    }

    private void showAddDialog() {
        // flipping inflating the item_book layout
        android.view.View dialogView = getLayoutInflater().inflate(R.layout.add_member, null);

        // TODO SOMEWHERE HERE - if time - make it so the user can only put in the right format stuff onadd new member

        EditText inputUsername = dialogView.findViewById(R.id.inputUsername);
        EditText inputFirst = dialogView.findViewById(R.id.inputFirstName);
        EditText inputLast  = dialogView.findViewById(R.id.inputLastName);
        EditText inputEmail = dialogView.findViewById(R.id.inputEmail);
        EditText inputContact = dialogView.findViewById(R.id.inputContact);
        EditText inputEndDate = dialogView.findViewById(R.id.input_membership_end_date);

        new android.app.AlertDialog.Builder(this)
                .setTitle("Add New Member")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {

                    String username = inputUsername.getText().toString().trim();
                    String first  = inputFirst.getText().toString().trim();
                    String last   = inputLast.getText().toString().trim();
                    String email = inputEmail.getText(). toString().trim();
                    String contact   = inputContact.getText().toString().trim();
                    String endDate   = inputEndDate.getText().toString().trim();

                    if (!username.isEmpty() &&  !first.isEmpty() && !last.isEmpty()) {
                        addMember(username, first, last, email, contact, endDate);
                    }
                    else {
                        Toast.makeText(this,
                        "Username, first name and last name are required",
                        Toast.LENGTH_SHORT).show();
                    }
                })

                .setNegativeButton("Cancel", null) // closes dialog if Cancel is clicked
                .show();
    }

    @Override
    protected void onActivityResult(int requestACode,  int resultCode,  Intent data) {
        super.onActivityResult(requestACode, resultCode, data);

        if (requestACode== 1 && resultCode== RESULT_OK) {
            // reload the list because a member was updated!!!
            loadMembers();

        }
    }


    private void addMember(String username, String first, String last,
                           String email, String contact, String endDate) {

        Member newMember = new Member(
                username,  first, last, email , contact, endDate
        );



        // call the API!
        APIClient.getApi().addMember(newMember)
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> c, Response<MessageResponse> r) {
                        if (r.isSuccessful()) {
                            // If it works, insert it into the local database & refresh the list
                            new Thread(() -> {
                                memberDao.insert(newMember);
                                runOnUiThread(() -> fetchMembersFromAPI());
                            }).start();

                        } else {
                            runOnUiThread(() -> Toast.makeText(

                                    StaffManageMembersActivity.this,
                                    "There was an API error: " + r.code(),
                                    Toast.LENGTH_SHORT).show());
                        }
                    }
                    @Override
                    public void onFailure(Call<MessageResponse>  c , Throwable t) {
                        runOnUiThread(() -> Toast.makeText(
                                StaffManageMembersActivity.this,
                                "Network error: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show());
                    }
                });
    }




























    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_manage_members);


        memberDao = AppDatabase.getInstance(this).memberDao();
        rv = findViewById(R.id.memberList);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fetchMembersFromAPI();


        // Fins the 'Add New Member' button! xo
        Button addButton = findViewById(R.id.addNewMemberButton);
        addButton.setOnClickListener(v ->
                showAddDialog()
        );


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