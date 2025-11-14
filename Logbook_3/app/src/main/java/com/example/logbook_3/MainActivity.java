package com.example.logbook_3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    EditText etName, etDob, etEmail;
    Spinner spinnerAvatar;
    ImageView ivSelectedImage;
    Button btnSelectImage;

    AppDatabase db;
    int[] avatars = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3};
    String[] avatarNames = {"Avatar 1", "Avatar 2", "Avatar 3"};


    private Uri selectedImageUri = null;


    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {

                    selectedImageUri = uri;
                    ivSelectedImage.setImageURI(uri);
                    spinnerAvatar.setSelection(0);
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etName = findViewById(R.id.etName);
        etDob = findViewById(R.id.etDob);
        etEmail = findViewById(R.id.etEmail);
        spinnerAvatar = findViewById(R.id.spinnerAvatar);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnView = findViewById(R.id.btnView);


        ivSelectedImage = findViewById(R.id.ivSelectedImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);



        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "contact_db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, avatarNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAvatar.setAdapter(adapter);


        btnSelectImage.setOnClickListener(v -> {

            imagePickerLauncher.launch("image/*");
        });


        btnSave.setOnClickListener(v -> {

            Contact c = new Contact();
            c.name = etName.getText().toString().trim();
            c.dob = etDob.getText().toString().trim();
            c.email = etEmail.getText().toString().trim();

            if (selectedImageUri != null) {

                Toast.makeText(this, "Vui lòng cập nhật Contact Entity để lưu Uri!", Toast.LENGTH_LONG).show();

                c.avatarResId = avatars[spinnerAvatar.getSelectedItemPosition()];

            } else {

                c.avatarResId = avatars[spinnerAvatar.getSelectedItemPosition()];
            }


            db.contactDao().insert(c);
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();

            // reset input
            etName.setText("");
            etDob.setText("");
            etEmail.setText("");
            spinnerAvatar.setSelection(0);
            ivSelectedImage.setImageResource(R.drawable.ic_default_avatar); // Đặt lại ảnh mặc định
            selectedImageUri = null;
        });


        btnView.setOnClickListener(v -> startActivity(new Intent(this, ListActivity.class)));
    }
}