package fr.iut_amiens.imagelist;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private ImageLoader imageLoader;

    private AnimalImage image;

    private WeakReference<ImageView> imageViewReference;

    private WeakReference<ProgressBar> loadingReference;

    public ImageLoadTask(ImageLoader imageLoader, AnimalImage image, ImageView imageView, ProgressBar loading) {
        this.imageLoader = imageLoader;
        this.image = image;
        this.imageViewReference = new WeakReference<>(imageView);
        this.loadingReference = new WeakReference<>(loading);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        final ProgressBar loading = loadingReference.get();
        if (loading != null) {
            loading.setVisibility(View.VISIBLE);
        }
        final ImageView imageView = imageViewReference.get();
        if (imageView != null) {
            imageView.setVisibility(View.GONE);
        }
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            return imageLoader.load(image);
        } catch (IOException | InterruptedException e) {
            Log.e("DOWNLOADER", "load error", e);
            cancel(true);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (!isCancelled() && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            }

            final ProgressBar progressBar = loadingReference.get();
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onCancelled(Bitmap bitmap) {
        final ImageView imageView = imageViewReference.get();
        if (imageView != null) {
            imageView.setImageResource(R.drawable.error);
            imageView.setVisibility(View.VISIBLE);
        }

        final ProgressBar loading = loadingReference.get();
        if (loading != null) {
            loading.setVisibility(View.GONE);
        }
    }
}
