package com.foxconn.matthew.materialdesigndemo;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AnimalActivity extends AppCompatActivity {

    public static final String ANIMAL_NAME = "animal_name";
    public static final String ANIMAL_IMAGE_ID = "animal_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        Intent intent = getIntent();
        String animal_name = intent.getStringExtra(ANIMAL_NAME);
        int animal_image_id = intent.getIntExtra(ANIMAL_IMAGE_ID, 0);
        Toolbar toolbar = findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout =findViewById(R.id.collapsingToolbarLayout);
        ImageView animal_image = findViewById(R.id.animal_image_view);
        TextView animal_content_text = findViewById(R.id.animal_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(animal_name);
        Glide.with(this).load(animal_image_id).into(animal_image);
        String animal_content = getAnimalContentByName(animal_name);
        animal_content_text.setText(animal_content);
    }

    private String getAnimalContentByName(String animal_name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append(animal_name);
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
