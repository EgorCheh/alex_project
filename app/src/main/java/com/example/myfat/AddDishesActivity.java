package com.example.myfat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddDishesActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private ImageView imageView;
    private UUID id;
    private FirebaseFirestore db;
    private Map<String, Object> dish = new HashMap<>();
    private EditText etBelok,etKkl,etJir,etUglevod,etLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dishes);
        etBelok = findViewById(R.id.etBelokAdd);
        etJir = findViewById(R.id.etJirAdd);
        etKkl = findViewById(R.id.etKklAdd);
        etUglevod = findViewById(R.id.etUglrvodAdd);
        etLabel=findViewById(R.id.etLabelAdd);

         db = FirebaseFirestore.getInstance();
        Button btnAddImage = findViewById(R.id.btnAddImage);
        Button btnAddDishes = findViewById(R.id.btnAddDishesAdd);
        imageView = findViewById(R.id.ivAdd);
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnAddDishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            uploadFile();
            }
        });



    }

    private void uploadFile() {
        id= UUID.randomUUID();
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStorageRef.child(id+".jpg");
        riversRef.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        dish.put("калории",etKkl.getText().toString());
                        dish.put("жир",etJir.getText().toString());
                        dish.put("углеводы",etUglevod.getText().toString());
                        dish.put("белок",etBelok.getText().toString());
                        dish.put("image",id.toString());
                        uploadData();
                    }
                });

    }
    private  void uploadData(){
        db.collection("dishes")
                .document(etLabel.getText().toString()).set(dish).addOnCompleteListener(AddDishesActivity.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("mydeb", "signInFull:success");
                    Toast.makeText(getApplicationContext(),"Блюдо добавленно ",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                    startActivity(intent);
                }else {
                    Log.d("mydeb", "signInWitFull: не success");
                }
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}