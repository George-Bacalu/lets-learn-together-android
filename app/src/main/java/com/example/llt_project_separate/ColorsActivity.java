package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ColorsActivity extends AppCompatActivity {
    private RecyclerView colorsRecyclerView;
    private ColorsRecyclerViewAdapter colorsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(ColorsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        colorsRecyclerView.setAdapter(colorsAdapter);
        colorsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> colors = new ArrayList<>();
        colors.add(new Category(42, "ROŞU", R.drawable.rosu));
        colors.add(new Category(43, "PORTOCALIU", R.drawable.portocaliu));
        colors.add(new Category(44, "GALBEN", R.drawable.galben));
        colors.add(new Category(45, "VERDE", R.drawable.verde));
        colors.add(new Category(46, "ALBASTRU", R.drawable.albastru));
        colors.add(new Category(47, "INDIGO", R.drawable.indigo));
        colors.add(new Category(48, "VIOLET", R.drawable.violet));
        colors.add(new Category(49, "MARO", R.drawable.maro));
        colors.add(new Category(50, "ROZ", R.drawable.roz));
        colors.add(new Category(51, "TURCOAZ", R.drawable.turcoaz));
        colorsAdapter.setColors(colors);
    }

    private void initializeViews() {
        colorsRecyclerView = findViewById(R.id.colorsRecyclerView);
        colorsAdapter = new ColorsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}