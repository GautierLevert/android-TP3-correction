package fr.iut_amiens.imagelist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnimalImageAdapter extends BaseAdapter {

    private ArrayList<AnimalImage> images = new ArrayList<>();

    private LayoutInflater layoutInflater;

    private ImageCache imageCache;

    private List<AsyncDownloader> downloaders = new ArrayList<>();

    public AnimalImageAdapter(LayoutInflater layoutInflater, ImageCache imageCache) {
        this.layoutInflater = layoutInflater;
        this.imageCache = imageCache;
    }

    public void add(AnimalImage image) {
        images.add(image);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public AnimalImage getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("ADAPTER", "getView(" + position + ")");

        View view;
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.list_animals, parent, false);
        } else {
            view = convertView;
        }

        AnimalImage image = getItem(position);

        TextView titleView = (TextView) view.findViewById(R.id.imageTitle);
        titleView.setText(image.getTitle());

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        ProgressBar loading = (ProgressBar) view.findViewById(R.id.loading);

        if (imageCache.isImageDownloaded(image)) {
            imageView.setImageURI(imageCache.getLocalData(image));
            loading.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        } else {
            AsyncDownloader imageDownloader = new AsyncDownloader(imageCache, image, imageView, loading);
            imageDownloader.execute();
            downloaders.add(imageDownloader);
        }
        return view;
    }

    public void clear() {
        images.clear();
        notifyDataSetChanged();
    }

    public List<AnimalImage> getAll() {
        return images;
    }

    public void addAll(Collection<AnimalImage> collection) {
        images.addAll(collection);
    }

    public void cancelTasks() {
        for (AsyncDownloader downloader : downloaders) {
            downloader.cancel(true);
        }
    }
}
