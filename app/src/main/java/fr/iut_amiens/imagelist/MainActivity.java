package fr.iut_amiens.imagelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private AnimalImageAdapter adapter;

    private ImageCache imageCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.listView);

        File imageCacheDirectory = new File(getCacheDir(), "images");

        if (!imageCacheDirectory.exists()) {
            imageCacheDirectory.mkdirs();
        }

        imageCache = new ImageCache(imageCacheDirectory);

        adapter = new AnimalImageAdapter(getLayoutInflater(), imageCache);

        listView.setAdapter(adapter);

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
                imageCache.deleteCache();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, ViewActivity.class).putExtra(ViewActivity.EXTRA_IMAGE, adapter.getItem(position)));
            }
        });
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

    @Override
    protected void onDestroy() {
        adapter.cancelTasks();
        super.onDestroy();
    }
}
