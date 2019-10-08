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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;



public class Login extends AppCompatActivity {
    EditText email,password;
    Button login;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        firebaseAuth=FirebaseAuth.getInstance();



        email=findViewById(R.id.mail1);
        password= findViewById(R.id.password_1);
        login=findViewById(R.id.login_1);

    }
    public  void login1(View view)
    {
        String user_email=email.getText().toString();
        String user_password=password.getText().toString();

        if(TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_password))

        {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            firebaseAuth.signInWithEmailAndPassword(user_email,user_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(Login.this, chat.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
        }
    }
}
