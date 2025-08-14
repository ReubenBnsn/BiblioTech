package com.example.bibliotech;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bibliotech.api.APIClient;
import com.example.bibliotech.api.LibraryAPI;
import com.example.bibliotech.api.MessageResponse;
import com.example.bibliotech.api.UpdateMemberRequest;
import com.example.bibliotech.data.AppDatabase;
import com.example.bibliotech.data.Member;
import com.example.bibliotech.data.MemberDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffManageIndividualActivity extends AppCompatActivity {


    private MemberDao memberDao;
    private String username;
    private LibraryAPI api;

    private Button manageSaveChangesButton;
    private Button manageDeleteMemberButton;
    private Button manageBackButton;


    private EditText manageUsername;
    private EditText manageFirstName;
    private EditText manageLastName;
    private EditText manageEmail;
    private EditText manageContact;
    private EditText manageMembershipEndDate;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // loads file to start off the program
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_manage_individual);

        // linking the views to the variables
        manageUsername = findViewById(R.id.manageUsername);
        manageFirstName    = findViewById(R.id.manageFirstName);
        manageLastName     = findViewById(R.id.manageLastName);
        manageEmail    = findViewById(R.id.manageEmail);
        manageContact  = findViewById(R.id.manageContact);
        manageMembershipEndDate  = findViewById(R.id.manageMembershipEndDate);

        manageSaveChangesButton   = findViewById(R.id.manageSaveChangesButton);
        manageDeleteMemberButton = findViewById(R.id.manageDeleteMemberButton);
        manageBackButton   = findViewById(R.id.manageBackButton);



        memberDao = AppDatabase.getInstance(this).memberDao();
        api = APIClient.getApi();

        // getting the clicked members' username (to find the memeber)
        // loading that member via API and populating fields from the database
        username = getIntent().getStringExtra("username");

        new Thread(() -> {
            Member m = memberDao.getByUsername(username);

            runOnUiThread(() -> {

                manageUsername.setText(m.getUsername());
                manageUsername.setEnabled(false);
                manageFirstName.setText(m.getFirstname());
                manageLastName.setText(m.getLastname());
                manageEmail.setText(m.getEmail());
                manageContact.setText(m.getContact());
                manageMembershipEndDate.setText(m.getMembershipEndDate());
            });
        }).start();




        // When save button is pressed it calls updateMember()
        manageSaveChangesButton.setOnClickListener(v -> {
            String first   = manageFirstName.getText().toString().trim();
            String last    = manageLastName.getText().toString().trim();
            String email   = manageEmail.getText().toString().trim();
            String contact = manageContact.getText().toString().trim();
            String endDate = manageMembershipEndDate.getText().toString().trim();
            updateMember(username, first, last, email, contact, endDate);
        });


       // When delete button is pressed it checks to confirm the deleteMember(...)
        manageDeleteMemberButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete Member")
                    .setMessage("Delete " + username + "?")
                    .setPositiveButton("Delete", (dlg, w) -> {
                        deleteMember(username);
                        setResult(RESULT_OK);
                        Toast.makeText(StaffManageIndividualActivity.this, "Deleted", Toast.LENGTH_SHORT).show();


                        finish();
                    })


                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // back button
        // Sets an onClickListener (when x is clicked, do y)
        manageBackButton.setOnClickListener(v -> {
            // Start up 'StaffManageMembersActivity.java' (activity for 'staff_Manage_Members_Activity.xml')
            Intent intent = new Intent(StaffManageIndividualActivity.this, StaffManageMembersActivity.class);
            startActivity(intent);
        });

    }



    private void updateMember(String username  ,  String first ,  String last , String email , String contact , String endDate) {
        UpdateMemberRequest request = new UpdateMemberRequest(first, last, email, contact, endDate);

        api.updateMember(username, request).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> c, Response<MessageResponse> r) {
                if (r.isSuccessful()) {
                    new Thread(() -> {
                        Member updatedMember = new Member(username, first, last, email, contact, endDate);
                        memberDao.update(updatedMember);
                        runOnUiThread(() -> {
                            Toast.makeText(StaffManageIndividualActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }).start();
                } else {
                    runOnUiThread(() -> Toast.makeText(StaffManageIndividualActivity.this, "Error Given: " + r.code(), Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> c, Throwable t) {
                runOnUiThread(() -> Toast.makeText(StaffManageIndividualActivity.this, "Network error given :(", Toast.LENGTH_SHORT).show());
            }
        });
    }




    private void deleteMember(String username)
    {
        api.deleteMember(username).enqueue(new Callback<MessageResponse>()
        {
            @Override public void onResponse(Call<MessageResponse> c , Response<MessageResponse> r)
            {
                if (r.isSuccessful())
                {
                    new Thread(() ->
                    {
                        memberDao.delete(new Member(username, null,null,null,null,null));
                        runOnUiThread(() ->
                        {
                            Toast.makeText(StaffManageIndividualActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            finish();   // done! back to manage members list
                        });
                    }).start();
                }
                else {
                    runOnUiThread(() -> Toast.makeText(StaffManageIndividualActivity.this, "Error: "+r.code(), Toast.LENGTH_SHORT).show());
                }
            }
            @Override public void onFailure(Call<MessageResponse> c, Throwable  t)
            {
                runOnUiThread(() -> Toast.makeText(StaffManageIndividualActivity.this, "Network error", Toast.LENGTH_SHORT).show());
            }
        });
    }



}
