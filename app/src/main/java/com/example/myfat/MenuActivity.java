package com.example.myfat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MenuActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = FirebaseFirestore.getInstance();
        TextView tvUglevod,tvJir,tvBelok;
        tvBelok=findViewById(R.id.tvBel);
        tvJir=findViewById(R.id.tvJir);
        tvUglevod=findViewById(R.id.tvUgl);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("mydeb", "DocumentSnapshot data: " + document.getData());
                        String name = document.getString("name");
                        TextView tvname= findViewById(R.id.tvName);
                        tvname.setText("Привет "+ name);
                        long belok = (long) document.get("weight");
                        tvBelok.setText(""+belok*2);
                    } else {
                        Log.d("mydeb", "No such document");
                    }
                } else {
                    Log.d("mydeb", "get failed with ", task.getException());
                }
            }
        });
        Button bntExit = findViewById(R.id.btnExit);





        bntExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
    }
}