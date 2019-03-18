package com.foxconn.matthew.materialdesigndemo;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    private Animal[] animals = new Animal[]{new Animal("Cattle", R.drawable.image_cattle), new Animal("Cattle", R.drawable.image_cattle)
            , new Animal("Chicken", R.drawable.image_chicken), new Animal("Cock", R.drawable.image_cock)
            , new Animal("Cow", R.drawable.image_cow), new Animal("Crab", R.drawable.image_crab)
            , new Animal("Deer", R.drawable.image_deer), new Animal("Elephant", R.drawable.image_elephant)
            , new Animal("Fox", R.drawable.image_fox), new Animal("Hedgehog", R.drawable.image_hedgehog)
            , new Animal("Hippo", R.drawable.image_hippo), new Animal("Koala", R.drawable.image_koala)
            , new Animal("Lion", R.drawable.image_lion), new Animal("Monkey", R.drawable.image_monkey)
            , new Animal("Owl", R.drawable.image_owl), new Animal("Panda", R.drawable.image_panda)
            , new Animal("Pig", R.drawable.image_pig), new Animal("Tiger", R.drawable.image_tiger)
            , new Animal("Whale", R.drawable.image_whale), new Animal("Wolf", R.drawable.image_wolf)
            , new Animal("Zebra", R.drawable.image_zebra)
    };

    private List<Animal> animalList = new ArrayList<>();
    private AnimalAdapter animalAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.wolf);
        }
        navigationView.setCheckedItem(R.id.chicken);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Morning~", Toast.LENGTH_SHORT).show();
                Snackbar.make(view, "Data delete~", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Data restored!", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        initAnimals();
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        animalAdapter = new AnimalAdapter(animalList);
        recyclerView.setAdapter(animalAdapter);
        swipeRefreshLayout= findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshAnimals();
            }
        });
    }

    private void refreshAnimals() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initAnimals();
                        animalAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initAnimals() {
        animalList.clear();
        for (int i = 0; i < 50; i++) {
            Random ramdom = new Random();
            animalList.add(animals[ramdom.nextInt(animals.length)]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.elephant:
                Toast.makeText(this, "You click the elephant~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.owl:
                Toast.makeText(this, "You click the owl~", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wolf:
                Toast.makeText(this, "You click the wolf ~", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
}
