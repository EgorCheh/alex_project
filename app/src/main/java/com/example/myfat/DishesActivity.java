package com.example.myfat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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


                                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                                mStorageRef.child(document.getData().get("image").toString()+".jpg").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if(task.isSuccessful())
                                        {
                                            mlist.add(new ItemDishes(task.getResult(),document.getId(),document.get("белок").toString(),document.get("жир").toString(),document.get("углеводы").toString(),document.get("калории").toString()));

                                        }
                                        else
                                        {
                                            Uri uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/myfat-418e4.appspot.com/o/hf.jpg?alt=media&token=9d4ec8fb-bd82-4fe1-9ef4-a6bdd4825f8b");
                                            mlist.add(new ItemDishes(uri,document.getId(),document.get("белок").toString(),document.get("жир").toString(),document.get("углеводы").toString(),document.get("калории").toString()));

                                        }recyclerView.setAdapter(adapterDishes);
                                    }
                                });



                             }
                        } else {
                            Log.d("mydeb", "Error getting documents: ", task.getException());
                        }
                    }
                });





    }



}