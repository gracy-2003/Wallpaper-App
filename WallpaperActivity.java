package com.example.wallpaper;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {

    private ImageView selectedImageView;
    private Button setHomeScreenButton, setLockScreenButton;
    private Bitmap selectedImageBitmap;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        // Initialize views
        selectedImageView = findViewById(R.id.selectedImageView);
        setHomeScreenButton = findViewById(R.id.setHomeScreenButton);
        setLockScreenButton = findViewById(R.id.setLockScreenButton);
        backButton = findViewById(R.id.backButton);

        // Get the image URI passed from MainActivity
        String imageUriString = getIntent().getStringExtra("imageUri");

        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);

            try {
                // Convert the URI to Bitmap
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                // Display the image in the ImageView
                selectedImageView.setImageBitmap(selectedImageBitmap);
                selectedImageView.setVisibility(View.VISIBLE);

                // Show the buttons to set wallpaper
                setHomeScreenButton.setVisibility(View.VISIBLE);
                setLockScreenButton.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No image URI received", Toast.LENGTH_SHORT).show();
        }

        // Set the image as home screen wallpaper
        setHomeScreenButton.setOnClickListener(v -> setWallpaper(WallpaperManager.FLAG_SYSTEM));

        // Set the image as lock screen wallpaper
        setLockScreenButton.setOnClickListener(v -> setWallpaper(WallpaperManager.FLAG_LOCK));

        // Back button functionality
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void setWallpaper(int flag) {
        if (selectedImageBitmap != null) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            try {
                // Set the wallpaper
                wallpaperManager.setBitmap(selectedImageBitmap, null, true, flag);
                String message = (flag == WallpaperManager.FLAG_SYSTEM) ? "Home screen wallpaper set!" : "Lock screen wallpaper set!";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to set wallpaper", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
}
