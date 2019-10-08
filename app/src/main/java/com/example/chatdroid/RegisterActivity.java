package com.example.chatdroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    EditText username, email, password,compassword;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Signup");


        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password1);
        compassword=findViewById(R.id.password2);


        firebaseAuth=FirebaseAuth.getInstance();



    }
    public void signup2(View view)
    {
        String user_username=username.getText().toString();
        String user_email=email.getText().toString();
        String user_password=password.getText().toString();
        String user_conpassword=compassword.getText().toString();
        if(TextUtils.isEmpty(user_username) ||  TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_password) || TextUtils.isEmpty(user_conpassword))

        {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
        else if(user_password.length() <6)
        {
            Toast.makeText(this,"password must have 6 characters",Toast.LENGTH_SHORT).show();
        }
        else if(!user_password.equals(user_conpassword))
        {
            Toast.makeText(this,"passwords must match ",Toast.LENGTH_SHORT).show();
        }
        else
            register(user_username,user_email,user_password);
    }
    private void register(final String username, String email, String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                            String userid=firebaseUser.getUid();
                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);
                            hashMap.put("imageurl","default");
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Intent intent= new Intent(RegisterActivity.this,chat.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                            });
                        }
                        /*else
                        {
                            Toast.makeText(RegisterActivity.this,"You can't register with this email and password",Toast.LENGTH_SHORT).show();
                        }*/
                    }

                });

    }


}

