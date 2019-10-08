package com.example.chatdroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.chatdroid.fragments.ChatFragment;
import com.example.chatdroid.fragments.UserFragment;
import com.example.chatdroid.model.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class chat extends AppCompatActivity {
CircleImageView profile;
TextView username;
FirebaseUser firebaseUser;
DatabaseReference databaseReference;
MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
      profile=findViewById(R.id.profile);
        username=findViewById(R.id.username);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setSubtitle("");




        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                username.setText(user.getUsername());
                //profile.setImageResource(R.mipmap.ic_launcher);
                if (null==(user.getImageURL())) {
                    profile.setImageResource(R.mipmap.ic_launcher);
                    // Glide.with(chat.this).load(user.getImageURL()).into(profile);

                } else {
                   Glide.with(chat.this).load(user.getImageURL()).into(profile);
                    //profile.setImageResource(R.mipmap.ic_launcher);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TabLayout tabLayout=findViewById(R.id.tab_layout);
        ViewPager viewPager=findViewById(R.id.view_pager);

        Viewpageadapter viewpageadapter=new Viewpageadapter(getSupportFragmentManager());

   viewpageadapter.addfragment(new ChatFragment(),"Chats");
   viewpageadapter.addfragment(new UserFragment(),"Users");

   viewPager.setAdapter(viewpageadapter);
  tabLayout.setupWithViewPager(viewPager);
  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this,MainActivity.class));
                finish();
                return true;
        }
        return false;
    }

    class  Viewpageadapter extends FragmentPagerAdapter{

        private ArrayList<Fragment > fragments;
        private  ArrayList<String> titles;

        Viewpageadapter(FragmentManager fragmentManager)
        {
            super(fragmentManager);
            this.fragments=new ArrayList<>();
            this.titles= new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public  void addfragment(Fragment fragment,String title)
        {
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
