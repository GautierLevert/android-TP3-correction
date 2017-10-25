package fr.iut_amiens.imagelist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class ViewActivity extends Activity {

    public static final String EXTRA_IMAGE = "fr.iut_amiens.imagelist.EXTRA_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        AnimalImage image = (AnimalImage) getIntent().getSerializableExtra(EXTRA_IMAGE);
        setTitle(image.getTitle());
        ImageView imageView = findViewById(R.id.imageView);

        File imageCacheDirectory = new File(getCacheDir(), "images");

        if (!imageCacheDirectory.exists()) {
            imageCacheDirectory.mkdirs();
        }

        ImageLoader cache = new ImageLoader(imageCacheDirectory);
        try {
            imageView.setImageBitmap(cache.getLocalData(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
