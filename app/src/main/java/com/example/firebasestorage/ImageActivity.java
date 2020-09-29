package com.example.firebasestorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ImageAdapter myAdapter;
    private List<Upload> uploadList;
    private ProgressBar progressBar;
    DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        recyclerView = findViewById(R.id._imageRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = findViewById(R.id.progressBarId);

        uploadList = new ArrayList<>();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Upload");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                uploadList.clear();

                for (DataSnapshot dataSnapshot1 : snapshot.getChildren())
                {
                    Upload upload =dataSnapshot1.getValue(Upload.class);
                    upload.setKey(dataSnapshot1.getKey());
                    uploadList.add(upload);
                }
                myAdapter = new ImageAdapter(ImageActivity.this, uploadList);
                recyclerView.setAdapter(myAdapter);

                myAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String text = uploadList.get(position).getImageName();
                        Toast.makeText(getApplicationContext(),text+" is selected "+position,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDoAnyTask(int position) {

                    }

                    @Override
                    public void onDelete(int position) {

                        Upload selectedItem = uploadList.get(position);
                        final String key = selectedItem.getKey();

                        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(selectedItem.getImageUrl());
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                databaseReference.child(key).removeValue();
                            }
                        });

                    }
                });
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(),"Error :"+ error.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }
}























