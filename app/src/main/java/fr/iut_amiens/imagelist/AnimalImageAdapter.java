package fr.iut_amiens.imagelist;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnimalImageAdapter extends RecyclerView.Adapter<AnimalImageViewHolder> {

    public interface OnAnimalImageClickListener {
        void onClick(AnimalImage image);
    }

    private ArrayList<AnimalImage> images = new ArrayList<>();

    private LayoutInflater layoutInflater;

    @Nullable
    private OnAnimalImageClickListener onAnimalImageClickListener;

    public AnimalImageAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Nullable
    public OnAnimalImageClickListener getOnAnimalImageClickListener() {
        return onAnimalImageClickListener;
    }

    public void setOnAnimalImageClickListener(@Nullable OnAnimalImageClickListener onAnimalImageClickListener) {
        this.onAnimalImageClickListener = onAnimalImageClickListener;
        notifyItemRangeChanged(0, images.size());
    }

    public void add(AnimalImage image) {
        final int position = images.size();
        images.add(image);
        notifyItemInserted(position);
        Log.d(AnimalImageAdapter.class.getSimpleName(), "add image at position " + position);
    }

    @Override
    public AnimalImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(AnimalImageAdapter.class.getSimpleName(), "create viewholder");
        return new AnimalImageViewHolder(layoutInflater.inflate(R.layout.list_animals, parent, false));
    }

    @Override
    public void onBindViewHolder(AnimalImageViewHolder holder, int position) {
        Log.d(AnimalImageAdapter.class.getSimpleName(), "bind position " + position);
        AnimalImage image = getItem(position);
        holder.bind(image);
        holder.setOnAnimalImageClickListener(onAnimalImageClickListener);
    }

    @Override
    public void onViewRecycled(AnimalImageViewHolder holder) {
        holder.recycle();
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public AnimalImage getItem(int position) {
        return images.get(position);
    }

    public void clear() {
        notifyItemRangeRemoved(0, images.size());
        images.clear();
    }

    public List<AnimalImage> getAll() {
        return images;
    }

    public void addAll(Collection<AnimalImage> collection) {
        images.addAll(collection);
    }
}
