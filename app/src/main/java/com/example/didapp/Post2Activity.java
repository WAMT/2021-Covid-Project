package com.example.didapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Post2Activity extends AppCompatActivity {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private TextView mTitleText, mContentsText1, mContentsText2, mContentsText3
            , mContentsText4, mContentsText5, mContentsText6
            , mContentsText7, mContentsText8, mContentsText9, mContentsText10;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);

        mTitleText = findViewById(R.id.post2_title);
        mContentsText1 = findViewById(R.id.post2_contents1);
        mContentsText2 = findViewById(R.id.post2_contents2);
        mContentsText3 = findViewById(R.id.post2_contents3);
        mContentsText4 = findViewById(R.id.post2_contents4);
        mContentsText5 = findViewById(R.id.post2_contents5);
        mContentsText6 = findViewById(R.id.post2_contents6);
        mContentsText7 = findViewById(R.id.post2_contents7);
        mContentsText8 = findViewById(R.id.post2_contents8);
        mContentsText9 = findViewById(R.id.post2_contents9);
        mContentsText10 = findViewById(R.id.post2_contents10);

        Intent getIntent = getIntent();
        id = getIntent.getStringExtra(FirebaseID.documentId);
        Log.e("ITEM DOCUMENT ID:", id);

        mStore.collection(FirebaseID.post).document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                                Map<String, Object> snap = task.getResult().getData();
                                String title = String.valueOf(snap.get(FirebaseID.title));
                                String contents1 = String.valueOf(snap.get(FirebaseID.contents1));
                                String contents2 = String.valueOf(snap.get(FirebaseID.contents2));
                                String contents3 = String.valueOf(snap.get(FirebaseID.contents3));
                                String contents4 = String.valueOf(snap.get(FirebaseID.contents4));
                                String contents5 = String.valueOf(snap.get(FirebaseID.contents5));
                                String contents6 = String.valueOf(snap.get(FirebaseID.contents6));
                                String contents7 = String.valueOf(snap.get(FirebaseID.contents7));
                                String contents8 = String.valueOf(snap.get(FirebaseID.contents8));
                                String contents9 = String.valueOf(snap.get(FirebaseID.contents9));
                                String contents10 = String.valueOf(snap.get(FirebaseID.contents10));

                                mTitleText.setText(title);
                                mContentsText1.setText(contents1);
                                mContentsText2.setText(contents2);
                                mContentsText3.setText(contents3);
                                mContentsText4.setText(contents4);
                                mContentsText5.setText(contents5);
                                mContentsText6.setText(contents6);
                                mContentsText7.setText(contents7);
                                mContentsText8.setText(contents8);
                                mContentsText9.setText(contents9);
                                mContentsText10.setText(contents10);
                            }
                        }
                    }
                });
    }
}