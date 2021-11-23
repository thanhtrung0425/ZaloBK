package com.example.zalobk.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.zalobk.databinding.ActivitySetProfileBinding;
import com.example.zalobk.models.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class setProfile extends AppCompatActivity {

    private ActivitySetProfileBinding profileBinding;

    private static int PICK_IMAGE = 44;
    private Uri imagepath;

    private FirebaseAuth firebaseAuth;
    private String name;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private String ImageUriAcessToken;
    private FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding = ActivitySetProfileBinding.inflate(getLayoutInflater());
        setContentView(profileBinding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        profileBinding.mAvata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        profileBinding.mSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = profileBinding.mGetUserName.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Name is Empty", Toast.LENGTH_SHORT).show();
                }
//                else if(imagepath == null){
//                    Toast.makeText(getApplicationContext(), "Image is Empty", Toast.LENGTH_SHORT).show();
//                }
                else{
                    profileBinding.mProgressbarofsetProfile.setVisibility(View.VISIBLE);
                    sendDataForNewUser();
                    profileBinding.mProgressbarofsetProfile.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(setProfile.this, ChatActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    private void sendDataForNewUser() {
        sendDataToRealTimeDatabase();
    }

    private void sendDataToRealTimeDatabase() {

        name = profileBinding.mGetUserName.getText().toString().trim();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        UserProfile newUser = new UserProfile(name, firebaseAuth.getUid());
        databaseReference.setValue(newUser);
        Toast.makeText(getApplicationContext(), "User Profile Added Success", Toast.LENGTH_SHORT).show();
        sendImageToStorage();

    }

    private void sendImageToStorage() {

        StorageReference imageref = storageReference.child("Images").child(firebaseAuth.getUid()).child("Profile Pic");

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        //Putting image to storage

        UploadTask uploadTask = imageref.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageUriAcessToken = uri.toString();
                        Toast.makeText(getApplicationContext(), "URI get success", Toast.LENGTH_SHORT).show();
                        sendDataToCloudFireStore();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "URI get Failed", Toast.LENGTH_SHORT).show();

                    }
                });

                Toast.makeText(getApplicationContext(), "Image is UpLoaded", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Image Not UpLoaded", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void sendDataToCloudFireStore() {

        DocumentReference documentReference = firebaseFirestore.collection("Users").document(firebaseAuth.getUid());
        Map<String, Object> UserData = new HashMap<>();
        UserData.put("name", name);
        UserData.put("image", ImageUriAcessToken);
        UserData.put("uid", firebaseAuth.getUid());
        UserData.put("status", "Online");

        documentReference.set(UserData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Send data to cloud Firestore success", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imagepath = data.getData();
            profileBinding.mAvata.setImageURI(imagepath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}