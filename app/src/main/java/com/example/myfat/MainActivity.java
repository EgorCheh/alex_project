package com.example.myfat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
     private final String[] spinnerDataActivity = {"","Не активный", "Умеренный", "Активный"};
    private final String[] spinnerDataSex = {"","Женский", "Мужской"};
    private FirebaseAuth mAuth;
    private Map<String, Object> user = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button btnReg =findViewById(R.id.btnReg);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser userAuth = mAuth.getCurrentUser();

        EditText edAge=findViewById(R.id.edAge);
        EditText edName=findViewById(R.id.edName);
        EditText edWeght=findViewById(R.id.edWeght);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerDataActivity);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerActivity);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                user.put("activity", spinnerDataActivity[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerDataSex);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerSex);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                user.put("sex", spinnerDataSex[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.put("weight", edWeght.getText().toString());
                user.put("name", edName.getText().toString());
                user.put("age", edAge.getText().toString());

                Log.d("mydeb", "button work");
                db.collection("users")
                        .document(userAuth.getUid().toString()).set(user).addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("mydeb", "signInFull:success");
                            Toast.makeText(getApplicationContext(),"Регистрация прошла успешна",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                            intent.putExtra("name", edName.getText().toString());
                            startActivity(intent);
                        }else {
                            Log.d("mydeb", "signInWitFull:success");
                        }
                    }
                });
            }
        });
    }

}