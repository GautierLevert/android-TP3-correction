package fr.iut_amiens.imagelist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private AnimalImageAdapter adapter;

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File imageCacheDirectory = new File(getCacheDir(), "images");

        if (!imageCacheDirectory.exists()) {
            imageCacheDirectory.mkdirs();
        }

        imageLoader = new ImageLoader(imageCacheDirectory);

        adapter = new AnimalImageAdapter(getLayoutInflater(), imageLoader);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            private int insert = 0;

            @Override
            public void onClick(View v) {
                if (insert >= AnimalImage.ANIMAL_IMAGE_LIST.size()) {
                    insert = 0;
                }
                adapter.add(AnimalImage.ANIMAL_IMAGE_LIST.get(insert));
                insert++;
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                imageLoader.clearCache();
            }
        });

        /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, ViewActivity.class).putExtra(ViewActivity.EXTRA_IMAGE, adapter.getItem(position)));
            }
        }); */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<AnimalImage> list = new ArrayList<>(adapter.getAll());
        outState.putSerializable("adapter", list);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.addAll((ArrayList<AnimalImage>) savedInstanceState.getSerializable("adapter"));
    }
}
