package com.example.wallpaper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;  // Request code for gallery image selection

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set OnClickListener for images from image1 to image50 dynamically
        for (int i = 1; i <= 50; i++) {  // Now loop until 50 to include image26 to image50
            String imageViewId = "image" + i;
            int resID = getResources().getIdentifier(imageViewId, "id", getPackageName());
            ImageView imageView = findViewById(resID);

            if (imageView != null) {
                final int finalI = i;
                imageView.setOnClickListener(view -> {
                    Uri imageUri = Uri.parse("android.resource://" + getPackageName() + "/" + getResources().getIdentifier("image" + finalI, "drawable", getPackageName()));
                    openWallpaperActivity(imageUri);
                });
            }
        }

        // Set OnClickListener for the gallery button
        Button chooseFromGalleryButton = findViewById(R.id.uploadButton);
        chooseFromGalleryButton.setOnClickListener(v -> {
            // Open gallery to pick an image
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE);
        });
    }

    private void openWallpaperActivity(Uri imageUri) {
        Intent intent = new Intent(MainActivity.this, WallpaperActivity.class);
        intent.putExtra("imageUri", imageUri.toString());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();  // Get the selected image URI
            openWallpaperActivity(imageUri);
        }
    }
}
