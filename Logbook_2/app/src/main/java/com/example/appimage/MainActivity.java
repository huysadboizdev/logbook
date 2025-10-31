package com.example.appimage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    int[] images = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
    int index = 0;
    ImageView imageView;
    Button btnNext, btnPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);

        imageView.setImageResource(images[index]);

        btnNext.setOnClickListener(v -> {
            index = (index + 1) % images.length;
            imageView.setImageResource(images[index]);
        });

        btnPrev.setOnClickListener(v -> {
            index = (index - 1 + images.length) % images.length;
            imageView.setImageResource(images[index]);
        });
    }
}