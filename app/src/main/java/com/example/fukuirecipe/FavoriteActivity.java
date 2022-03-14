package com.example.fukuirecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class FavoriteActivity extends AppCompatActivity {
    private String[] favoriteData = new String[PSD.FAVO_SIZE];
    private int[][] favorites = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.favoriteToolBar);
        setSupportActionBar(toolbar);
        setTitle(R.string.toolBar_name);
        LinearLayoutCompat layout = findViewById(R.id.favorite_linerLayout);
        this.favorites = FileIO.readFavorite(this);
        for(int i = 0; i < PSD.FAVO_SIZE; i++) {
            if(favorites[i][0] == 0) break;
            String[][] data = FileIO.fileReadWrapper(this, favorites[i][0]);
            favoriteData[i] = data[favorites[i][1]][1];
            MaterialButton addButton = new MaterialButton(this, null, R.attr.materialButtonOutlinedStyle);
            addButton.setText(favoriteData[i]);
            int finalI = i;
            addButton.setOnClickListener(v -> {
                Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
                intent.putExtra(PSD.INTENT_CSVID, favorites[finalI][0]);
                intent.putExtra(PSD.INTENT_RECIPEID, favorites[finalI][1]);
                startActivity(intent);
            });
            layout.addView(addButton);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.munu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_setting:
                Toast.makeText(this, PSD.CANTUSE, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_star:
                Toast.makeText(this, PSD.CANTUSE, Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}