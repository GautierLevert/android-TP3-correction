package fr.iut_amiens.imagelist;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;

public class AsyncDownloader extends AsyncTask<Void, Void, Void> {

    private ImageCache imageCache;

    private AnimalImage image;

    private ImageView imageView;

    private ProgressBar loading;

    public AsyncDownloader(ImageCache imageCache, AnimalImage image, ImageView imageView, ProgressBar loading) {
        this.imageCache = imageCache;
        this.image = image;
        this.imageView = imageView;
        this.loading = loading;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            imageCache.download(image);
        } catch (IOException | InterruptedException e) {
            Log.e("DOWNLOADER", "download error", e);
            cancel(true);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void o) {
        imageView.setImageURI(imageCache.getLocalData(image));
        loading.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCancelled(Void o) {
        imageView.setImageResource(R.drawable.error);
        loading.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }
}
