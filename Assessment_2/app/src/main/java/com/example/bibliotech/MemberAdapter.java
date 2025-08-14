package com.example.bibliotech;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bibliotech.data.Member;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<Member> members;
    private OnItemClickListener listener;

    // adding an interface for any member click events again! :)
    public interface OnItemClickListener {
        void onItemClick(Member  member);
    }


    // Adapter gobbling in members and click listeners
    public MemberAdapter(List<Member> members, OnItemClickListener listener) {
        this.members = members;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // turning item_member.xml into a viewObject
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Member member= members.get(position);

        // binding the member  data to each  textView
        holder.usernameText.setText(member.username);
        holder.firstNameText.setText(member.firstname);
        holder.lastNameText.setText(member.lastname);
        holder.emailText.setText(member.email);
        holder.contactText.setText(member.contact);
        holder.membership_end_dateText.setText(member.membership_end_date);

        // calling listener when this row's clicked
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(member);
            }
        });
    }
    // ## LATER ADD TO THIS - so users can search by author too!

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void updateList(List<Member> newMembers) {
        this.members=  newMembers;
        notifyDataSetChanged();
    }


    // keeps references to the views for each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Initialising the title and author
        TextView usernameText;
        TextView firstNameText;
        TextView lastNameText;
        TextView emailText;
        TextView contactText;
        TextView membership_end_dateText;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.textUsername);
            firstNameText = itemView.findViewById(R.id.firstName);
            lastNameText = itemView.findViewById(R.id.lastName);
            emailText = itemView.findViewById(R.id.email);
            contactText = itemView.findViewById(R.id.contact);
            membership_end_dateText = itemView.findViewById(R.id.membership_end_date);
        }
    }
}