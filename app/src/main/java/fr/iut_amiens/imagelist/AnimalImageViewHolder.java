package fr.iut_amiens.imagelist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

class AnimalImageViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleView;

    private final ImageView imageView;

    private final ProgressBar loading;

    private final ImageLoader imageLoader;

    private ImageLoadTask asyncLoader;

    public AnimalImageViewHolder(View itemView, ImageLoader imageLoader) {
        super(itemView);
        titleView = itemView.findViewById(R.id.imageTitle);
        imageView = itemView.findViewById(R.id.imageView);
        loading = itemView.findViewById(R.id.loading);
        this.imageLoader = imageLoader;
    }


    public void bind(AnimalImage image) {
        titleView.setText(image.getTitle());
        asyncLoader = new ImageLoadTask(imageLoader, image, imageView, loading);
        asyncLoader.execute();
    }

    public void recycle() {
        Log.d(AnimalImageAdapter.class.getSimpleName(), "recycle image");
        if (asyncLoader != null) {
            asyncLoader.cancel(true);
            asyncLoader = null;
        }
    }
}
