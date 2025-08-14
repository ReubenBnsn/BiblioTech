package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bibliotech.api.APIClient;
import com.example.bibliotech.api.LibraryAPI;
import com.example.bibliotech.api.MessageResponse;
import com.example.bibliotech.api.UpdateMemberRequest;
import com.example.bibliotech.data.AppDatabase;
import com.example.bibliotech.data.Book;
import com.example.bibliotech.data.Member;
import com.example.bibliotech.data.MemberDao;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;

// DONERS FOR NOW!

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;



public class StaffManageMembersActivity extends AppCompatActivity {

    // Setting up some variables
    private MemberDao  memberDao;
    private RecyclerView rv;
    private List<Member>  allMembers;



    // Loads members (duh)
    private void loadMembers() {
        new Thread(() -> {
            allMembers = memberDao.getAll();
            runOnUiThread(() -> {
                MemberAdapter adapter = new MemberAdapter(allMembers,  member ->{
                    // opening the manage member  screen

                    Intent intent = new Intent(StaffManageMembersActivity.this, StaffManageIndividualActivity.class);
                    intent.putExtra("MEMBER_USERNAME", member.getUsername()); // needs sorting here
                    startActivityForResult(intent, 1); // lets program know when it comes back
                });

                rv.setAdapter(adapter);
            });

        }).start();
    }

    private void showAddDialog() {
        // flipping inflating the member layout
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








    private void showEditMemberDialog(Member m) {
        // Inflate your same add_member.xml (fields: etUsername, etFirstname, etc.)
        View dialogView = getLayoutInflater().inflate(R.layout.add_member, null);

        // Find all EditTexts:
        EditText inputUsername = dialogView.findViewById(R.id.inputUsername);
        EditText inputFirst = dialogView.findViewById(R.id.inputFirstName);
        EditText inputLast  = dialogView.findViewById(R.id.inputLastName);
        EditText inputEmail = dialogView.findViewById(R.id.inputEmail);
        EditText inputContact = dialogView.findViewById(R.id.inputContact);
        EditText inputEndDate = dialogView.findViewById(R.id.input_membership_end_date);

        // Setting existing values to start with
        inputUsername.setText(m.getUsername());
        inputUsername.setEnabled(false);        // username is the primary (unique) key, so no editing
        inputFirst.setText(m.getFirstname());
        inputLast.setText(m.getLastname());
        inputEmail.setText(m.getEmail());
        inputContact.setText(m.getContact());
        inputEndDate.setText(m.getMembershipEndDate());

        new android.app.AlertDialog.Builder(this)
                .setTitle("Edit Member")
                .setView(dialogView)
                .setPositiveButton("Save", (dlg, which) -> {
                    // Taking in the updated text
                    String username = inputUsername.getText().toString().trim();
                    String first = inputFirst.getText().toString().trim();
                    String last = inputLast.getText().toString().trim();
                    String email = inputEmail.getText(). toString().trim();
                    String contact = inputContact.getText().toString().trim();
                    String endDate= inputEndDate.getText().toString().trim();

                    // actually calling for the update
                    updateMember(m.getUsername(), first, last, email, contact, endDate);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }







    private void updateMember(String username, String first, String last,String email,String contact,String endDate) {


        UpdateMemberRequest dto = new UpdateMemberRequest(first, last, email, contact, endDate);

        // using PUT to update the API
        APIClient.getApi()
                .updateMember(username, dto)
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> c, Response<MessageResponse> r) {
                        if (r.isSuccessful()) {
                            // if its a  success: update Room with member and refresh

                            Member  UpdatedMember = new Member(username, first, last, email, contact, endDate);

                            new Thread(() -> {

                                memberDao.update(UpdatedMember);
                                runOnUiThread(() -> fetchMembersFromAPI());

                            }).start();
                        }
                        else { // If it fails...
                            runOnUiThread(() ->
                                    Toast.makeText(StaffManageMembersActivity.this, "The update has failed: " + r.code(), Toast.LENGTH_SHORT).show());
                        }
                    }

                    @Override // more if it fails stuff...
                    public void onFailure(Call<MessageResponse> c, Throwable t) {
                        runOnUiThread(() ->

                                Toast.makeText(StaffManageMembersActivity.this,
                                        "A network error has occured: " + t.getMessage(),
                                        Toast.LENGTH_SHORT).show());
                    }
                });
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


                            // Error messaging!
                        }
                        else {
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

        // adding a divider between members  in the container!
        DividerItemDecoration lilDivider= new DividerItemDecoration(rv.getContext(), LinearLayoutManager.VERTICAL);
        rv.addItemDecoration(lilDivider);

        fetchMembersFromAPI();

        // finds the addNewMemberButton! xo
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


        // gets  the search bar by ID
        SearchView search = findViewById(R.id.searchBar);
        // expanding the search view when clicked
        search.setOnClickListener(v -> {
            search.setIconified(false);  // opening the search input
            search.requestFocus();
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit (String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Member> filteredList;
                if (newText.isEmpty()) {
                    filteredList = allMembers; // if the user has nothing typed, show all members
                }
                else {
                    filteredList = new java.util.ArrayList<>();
                    for (Member x : allMembers) {
                        if (x.username.toLowerCase().contains(newText.toLowerCase())   ||    x.firstname.toLowerCase().contains(newText.toLowerCase())  ||    x.lastname.toLowerCase().contains(newText.toLowerCase()))  {
                            filteredList.add(x);
                        }
                    }
                }

                // stops crashing if filtering happens before the data is loaded (or if it loads wrong)
                if (rv.getAdapter() != null) {
                    // telling the adapter to use the new list (if no issues)
                    ((MemberAdapter) rv.getAdapter()).filter(filteredList);

                    // puts the user to the top of the results when  search results change (nifty eh)
                    rv.scrollToPosition(0);
                }

                return true;


            }

        });


    }

    private void fetchMembersFromAPI() {

        LibraryAPI api = APIClient.getApi();
        api.getAllMembers().enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Member> members = response.body();

                    for (Member m : members) {
                        m.normalizeEndDate();
                    }

                    runOnUiThread(() -> {
                        MemberAdapter adapter = new MemberAdapter(members, member -> {
                            // item click stuff...

                            Intent intent = new Intent(StaffManageMembersActivity.this, StaffManageIndividualActivity.class);
                            intent.putExtra("username", member.getUsername());
                            startActivity(intent);
                        });
                        rv.setAdapter(adapter);
                    });

                // ## TODO - Handle member click - OPEN EDIT PAGE FOR Individual MEMBER


                } else
                {
                    runOnUiThread(() -> {
                        // error message and code here - with Toast (yummy)
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