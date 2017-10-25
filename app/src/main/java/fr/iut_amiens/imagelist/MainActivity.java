package fr.iut_amiens.imagelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private AnimalImageAdapter adapter;

    private int insert = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new AnimalImageAdapter(getLayoutInflater());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
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
            }
        });

        adapter.setOnAnimalImageClickListener(new AnimalImageAdapter.OnAnimalImageClickListener() {
            @Override
            public void onClick(AnimalImage image) {
                startActivity(new Intent(MainActivity.this, ViewActivity.class).putExtra(ViewActivity.EXTRA_IMAGE, image));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ArrayList<AnimalImage> list = new ArrayList<>(adapter.getAll());
        outState.putSerializable("adapter", list);
        outState.putInt("insert", insert);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.addAll((ArrayList<AnimalImage>) savedInstanceState.getSerializable("adapter"));
        insert = savedInstanceState.getInt("insert");
    }
}
