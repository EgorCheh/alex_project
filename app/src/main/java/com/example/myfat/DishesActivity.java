package com.example.myfat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DishesActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);
        db = FirebaseFirestore.getInstance();
        List<ItemDishes> mlist = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rvDishes);
        AdapterDishes adapterDishes = new AdapterDishes(this,mlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        db.collection("dishes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("mydeb", document.getId() + " => " + document.getData());
                                //mlist.add(new ItemDishes(R.drawable.hf,"100","100","100","100","100"));

                                  mlist.add(new ItemDishes(R.drawable.hf,document.getId(),document.get("белок").toString(),document.get("жир").toString(),document.get("углеводы").toString(),document.get("калории").toString()));
                            }
                        } else {
                            Log.d("mydeb", "Error getting documents: ", task.getException());
                        }recyclerView.setAdapter(adapterDishes);
                    }
                });





    }
}