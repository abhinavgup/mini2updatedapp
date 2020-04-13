package com.example.whatsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registeractivity1 extends AppCompatActivity implements View.OnClickListener
{
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    EditText edittextEmail, edittextPassword;
    DatabaseReference rootreference;
    Toolbar toolbar;
    TabLayout tabLayout;
    //AppBarLayout appBarLayout;
    ViewPager viewPager ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_registeractivity1);

        mAuth = FirebaseAuth.getInstance ();
        findViewById (R.id.textViewLogin).setOnClickListener (this);
        edittextEmail = (EditText) findViewById (R.id.editTextEmail);
        edittextPassword = (EditText) findViewById (R.id.editTextPassword);
        findViewById (R.id.buttonSignUp).setOnClickListener (this);
        progressBar =  (ProgressBar) findViewById (R.id.progressbar);
        toolbar= (Toolbar)findViewById(R.id.main1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DriverApp");

        rootreference= FirebaseDatabase.getInstance().getReference();
    }
    private void registerUser()
    {
        String email = edittextEmail.getText ().toString ().trim ();
        String PassWord = edittextPassword.getText ().toString ().trim ();

        if(email.isEmpty ())
        {
            edittextEmail.setError ("Email is required");
            edittextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher (email).matches ())
        {
            edittextEmail.setError ("Please enter a valid email address");
            edittextEmail.requestFocus ();
            return;
        }




        if(PassWord.isEmpty ())
        {
            edittextPassword.setError ("Password is required");
            edittextPassword.requestFocus ();
            return;
        }
        if(PassWord.length ()<6)
        {
            edittextPassword.setError ("Minimum length of password required is 6");
            edittextPassword.requestFocus ();
            return;
        }
        progressBar.setVisibility (View.VISIBLE);



        //this is to create new users with email address and password
        mAuth.createUserWithEmailAndPassword (email , PassWord).addOnCompleteListener (new OnCompleteListener<AuthResult> ( ) {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility (View.GONE);
                if(task.isSuccessful ())

                {    finish();

                String currentuserid= mAuth.getCurrentUser().getUid();
                rootreference.child("users").child(currentuserid).setValue(" ");
                sendusermain();
                    // Intent intent= new Intent(getApplicationContext(),profileactivity.class);
                    //startActivity(new Intent(getAppli cationContext(),profileactivity.class));
                       Toast.makeText (getApplicationContext (),"You registered successfully",Toast.LENGTH_SHORT).show ();
                }
                else {
                    if (task.getException ( ) instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText (getApplicationContext ( ), "You are already registered", Toast.LENGTH_SHORT).show ( );
                    } else
                    {
                        Toast.makeText (getApplicationContext (),task.getException ().getMessage (),Toast.LENGTH_SHORT).show ();
                    }
                }
            }

        });
    }

    public void  sendusermain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId ())
        {
            case R.id.buttonSignUp:
                registerUser();
                break;
            case R.id.textViewLogin:

                startActivity (new Intent (this, loginactivity1 .class));

                break;
        }
    }


}