package fr.iut_amiens.imagelist;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

class AnimalImageViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleView;

    private final ImageView imageView;

    private final ProgressBar loading;

    @Nullable
    private AnimalImage image = null;

    @Nullable
    private AnimalImageAdapter.OnAnimalImageClickListener onAnimalImageClickListener;

    public AnimalImageViewHolder(View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.imageTitle);
        imageView = itemView.findViewById(R.id.imageView);
        loading = itemView.findViewById(R.id.loading);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image != null && onAnimalImageClickListener != null) {
                    onAnimalImageClickListener.onClick(image);
                }
            }
        });
    }

    @Nullable
    public AnimalImageAdapter.OnAnimalImageClickListener getOnAnimalImageClickListener() {
        return onAnimalImageClickListener;
    }

    public void setOnAnimalImageClickListener(@Nullable AnimalImageAdapter.OnAnimalImageClickListener onAnimalImageClickListener) {
        this.onAnimalImageClickListener = onAnimalImageClickListener;
    }

    public void bind(AnimalImage image) {
        titleView.setText(image.getTitle());

        Picasso.with(imageView.getContext())
                .load(image.getImageUrl().toString())
                .error(R.drawable.error)
                .fit()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        loading.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        loading.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                });

        this.image = image;
    }

    public void recycle() {
        image = null;
    }
}
