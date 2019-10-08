package com.example.chatdroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.chatdroid.Adapter.MessagesAdapter;
import com.example.chatdroid.model.Chat2;
import com.example.chatdroid.model.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile;
    TextView username;
    FirebaseUser fuser;
    DatabaseReference reference;
    MaterialToolbar toolbar;
ImageButton send_button;
EditText send_text;

MessagesAdapter messagesAdapter;
List<Chat2> uchat;

RecyclerView recyclerView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);


      toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setSubtitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        profile=findViewById(R.id.profile);
        username=findViewById(R.id.username);
        send_button=findViewById(R.id.send_button);
        send_text=findViewById(R.id.send_text);
        intent=getIntent();

        final String userid=intent.getStringExtra("userid");

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=send_text.getText().toString();
                if(!message.equals(""))
                {
                    sendmeaasage(fuser.getUid(),userid,message);
                }
                else {
                    Toast.makeText(MessageActivity.this,"No message to send",Toast.LENGTH_SHORT).show();
                }
                send_text.setText("");
            }
        });
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if(user.getImageURL()==null)
                {
                    profile.setImageResource(R.mipmap.ic_launcher);
                }

                else
                {
                    Glide.with(MessageActivity.this).load(user.getImageURL()).into(profile);
                }
                readmessage(fuser.getUid(),userid,user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendmeaasage(String sender,String reciever,String message)
    {
        HashMap<String,Object> hashMap=new HashMap<>();
         hashMap.put("sender",sender);
        hashMap.put("reciever",reciever);
        hashMap.put("message",message);


        reference.child("Chats").push().setValue(hashMap);
    }

    private void readmessage(final String myid , final String userid, final String imageurl)
    {
        reference=FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             //   uchat.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Chat2 chat2=snapshot.getValue(Chat2.class);
                    System.out.println(chat2.getReciever());
                     System.out.println(chat2.getSender());
                   /* if(chat2.getReciever().equals(myid)&& chat2.getSender().equals(userid)||
                    chat2.getReciever().equals(userid)&& chat2.getSender().equals(myid))
                    {
                        uchat.add(chat2);

                    }*/
                    messagesAdapter=new MessagesAdapter(MessageActivity.this,uchat,imageurl);
                    recyclerView.setAdapter(messagesAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
