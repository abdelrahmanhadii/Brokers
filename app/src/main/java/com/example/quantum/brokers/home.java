package com.example.quantum.brokers;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    Button signOutBtn;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference brokerDb;

    ArrayList<realStateClass> dbList;

    RecyclerView recyclerview;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

        dbList = new ArrayList<>();
        brokerDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot realStateSnapShot: dataSnapshot.getChildren())
                {
                    realStateClass newClass = realStateSnapShot.getValue(realStateClass.class);
                    dbList.add(newClass);
                }
                cardViewRecyclerAdapter mAdapter = new cardViewRecyclerAdapter(dbList, home.this);
                recyclerview.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        recyclerview = (RecyclerView) findViewById(R.id.rv);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        signOutBtn = (Button)findViewById(R.id.googleLogOut);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null)
                {
                    startActivity(new Intent(home.this, loginWithGoogle.class));
                }
            }
        };

        mAuth = FirebaseAuth.getInstance();
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });

        brokerDb = FirebaseDatabase.getInstance().getReference().child("broker");

    }

    public void newItem(View v)
    {
        startActivity(new Intent(home.this, newItem.class));
    }

}
