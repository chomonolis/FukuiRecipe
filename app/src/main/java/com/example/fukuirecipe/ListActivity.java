package com.example.fukuirecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private int csvId, keyIndex = 1;
    private String condition;
    private String[][] data = new String[PSD.FILE_SIZE][PSD.FILE_SIZE];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.listToolBar);
        setSupportActionBar(toolbar);
        setTitle(R.string.toolBar_name);


        LinearLayoutCompat layout = findViewById(R.id.list_linerLayout);
        this.csvId = getIntent().getIntExtra(PSD.INTENT_CSVID, 1);
        this.condition = getIntent().getStringExtra(PSD.INTENT_CONDITION);
        if(this.condition == null) this.condition = PSD.NOCON;
        this.data = FileIO.fileReadWrapper(this, this.csvId);
        if(!this.condition.equals(PSD.NOCON)) {
            this.keyIndex = Util.findKeyIndex(this.csvId, this.condition, this.data);
        }
        for(int i = 1; i < this.data.length; i++) {
            String[] ss = this.data[i];
            if(ss[0].equals("FIN")) break;
            if(keyIndex != 1) {
                if(!ss[keyIndex].equals(this.condition)) continue;
            }
            MaterialButton addButton = new MaterialButton(this, null, R.attr.materialButtonOutlinedStyle);
            addButton.setText(ss[1]);
            int finalI = i;
            addButton.setOnClickListener(v -> {
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra(PSD.INTENT_CSVID, csvId);
                intent.putExtra(PSD.INTENT_RECIPEID, finalI);
                intent.putExtra(PSD.INTENT_CONDITION, this.condition);
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
                Intent intent = new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_setting:
                Intent inte = new Intent(ListActivity.this, RandOrChoseActivity.class);
                inte.putExtra(PSD.INTENT_CSVID, this.csvId);
                startActivity(inte);
                return true;
            case R.id.menu_star:
                Intent intent1 = new Intent(ListActivity.this, FavoriteActivity.class);
                startActivity(intent1);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() { }
}