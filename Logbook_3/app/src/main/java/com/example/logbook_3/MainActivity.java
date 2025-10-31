package com.example.logbook_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    EditText etName, etDob, etEmail;
    Spinner spinnerAvatar;
    AppDatabase db;
    int[] avatars = {R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3};
    String[] avatarNames = {"Avatar 1", "Avatar 2", "Avatar 3"};

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


        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "contact_db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, avatarNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAvatar.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            Contact c = new Contact();
            c.name = etName.getText().toString().trim();
            c.dob = etDob.getText().toString().trim();
            c.email = etEmail.getText().toString().trim();
            c.avatarResId = avatars[spinnerAvatar.getSelectedItemPosition()];

            db.contactDao().insert(c);
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();

            // reset input
            etName.setText("");
            etDob.setText("");
            etEmail.setText("");
            spinnerAvatar.setSelection(0);
        });

        btnView.setOnClickListener(v -> startActivity(new Intent(this, ListActivity.class)));
    }
}
