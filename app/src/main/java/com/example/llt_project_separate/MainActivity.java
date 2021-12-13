package com.example.llt_project_separate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView sectionsRecyclerView;
    private SectionsRecyclerViewAdapter sectionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        sectionsRecyclerView.setAdapter(sectionsAdapter);
        sectionsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Section> sections = new ArrayList<>();
        sections.add(new Section(10001, "Secţiune Video", R.drawable.ic_video_section, R.drawable.video_section));
        sections.add(new Section(10002, "Secţiune Text to Sign", R.drawable.ic_text_to_sign_section, R.drawable.text_to_sign_section));
        sections.add(new Section(10003, "Secţiune Voice to Sign", R.drawable.ic_voice_to_sign_section, R.drawable.voice_to_sign_section));
        sections.add(new Section(10004, "Secţiune Expresii", R.drawable.ic_expressions_section, R.drawable.expressions_section));
        sectionsAdapter.setSections(sections);
    }

    private void initializeViews() {
        sectionsRecyclerView = findViewById(R.id.sectionsRecyclerView);
        sectionsAdapter = new SectionsRecyclerViewAdapter(this);
    }
}