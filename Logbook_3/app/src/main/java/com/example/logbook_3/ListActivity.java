package com.example.logbook_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ContactAdapter adapter;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
