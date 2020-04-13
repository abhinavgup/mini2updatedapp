package com.example.whatsapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    //AppBarLayout appBarLayout;
    ViewPager viewPager ;
//    tabacesoradapter tabs;
    FirebaseUser user ;
    FirebaseAuth mAuth;
    DatabaseReference reference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        mAuth = FirebaseAuth.getInstance();

        user=mAuth.getCurrentUser();

        toolbar= (Toolbar)findViewById(R.id.main1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DriverApp");

//        viewPager = (ViewPager)findViewById(R.id.view);
//
//        tabLayout =(TabLayout)findViewById(R.id.tab);
//
//        tabs = new tabacesoradapter(getSupportFragmentManager());
//
//        viewPager.setAdapter(tabs);

//        tabLayout.setupWithViewPager(viewPager);
        reference= FirebaseDatabase.getInstance().getReference();

    }


    @Override
    protected void onStart() {
        super.onStart();

        if(user==null){

            gotologin();

        }

        else{
            verifyuserexistence();
        }
    }

    private void gotologin() {

        Intent intent = new Intent(MainActivity.this,loginactivity1.class);
        startActivity(intent);
    }


    void verifyuserexistence(){


        String curid= mAuth.getCurrentUser().getUid();
        reference.child("users").child(curid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.child("name").exists()){
//
//                    Toast.makeText(MainActivity.this,"welcome",Toast.LENGTH_SHORT).show();
//                }
//                else{
//
//                    settings();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//
//   void  settings(){
//       Intent intent = new Intent(MainActivity.this,settingsactivity.class);
//       startActivity(intent);
//   }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);


         getMenuInflater().inflate(R.menu.menu1,menu);

         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);



//         if(item.getItemId() == R.id.settings){
//             settings();
//
//         }

        if(item.getItemId()==R.id.logout){

            mAuth.signOut();
            gotologin();
        }

        return true;
    }
}
