package com.example.logbook_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button; // Cần import Button
import android.widget.Toast;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ContactAdapter adapter;
    AppDatabase db;
    Button btnBack; // Khai báo Button mới

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Ánh xạ View
        recyclerView = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btnBack); // Ánh xạ nút Quay lại

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Xử lý sự kiện click cho nút Quay lại
        btnBack.setOnClickListener(v -> {
            finish(); // Kết thúc Activity hiện tại và quay về màn hình trước đó (MainActivity)
        });

        // Tải dữ liệu từ database
        try {
            db = Room.databaseBuilder(getApplicationContext(),
                            AppDatabase.class, "contact_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();

            List<Contact> contacts = db.contactDao().getAllContacts();

            if (contacts == null || contacts.isEmpty()) {
                Toast.makeText(this, "No contacts found!", Toast.LENGTH_SHORT).show();
            } else {
                adapter = new ContactAdapter(contacts);
                recyclerView.setAdapter(adapter);
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("ListActivity", "Crash error", e);
        }
    }
}