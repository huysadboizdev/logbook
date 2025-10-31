package com.example.logbook_3;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "contacts")
public class Contact {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String dob;
    public String email;
    public int avatarResId; // ID hình trong drawable
}
