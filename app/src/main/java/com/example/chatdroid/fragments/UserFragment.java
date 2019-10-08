package com.example.chatdroid.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatdroid.Adapter.Useradapter;
import com.example.chatdroid.R;
import com.example.chatdroid.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {

    private RecyclerView recyclerView;
    private Useradapter useradapter;
    private List<User> nusers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_user,container,false);
        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        nusers=new ArrayList<>();

        readUsers();

        return view;
    }

private void  readUsers()
{
    final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Users");
   databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            nusers.clear();
            for (DataSnapshot datasnapshot  :dataSnapshot.getChildren())
            {
                User user=datasnapshot.getValue(User.class);

               assert user != null;
                assert firebaseUser != null;
                if(!firebaseUser.getUid().equals(user.getId()))
                {
                    nusers.add(user);
                }

            }

            useradapter=new Useradapter(getContext(),nusers);
            recyclerView.setAdapter(useradapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
   });
}

}
