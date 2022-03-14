package com.example.fukuirecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolBar);
        setSupportActionBar(toolbar);
        setTitle(R.string.toolBar_name);
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
                Toast.makeText(this, PSD.CANTUSE, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_star:
                Intent intent1 = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent1);
                return true;
            case R.id.menu_setting:
                Toast.makeText(this, PSD.CANTUSE, Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() { }

    public void choseCSV(View view) {
        // どのボタンが押されたか
        int csvId = 1;
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();
        for(int i = 0; i < PSD.CSVNAME.length; i++) {
            if(PSD.CSVNAME[i].equals((btnText))) {
                csvId = i+1;
                break;
            }
        }
        Intent intent = new Intent(MainActivity.this, RandOrChoseActivity.class);
        intent.putExtra(PSD.INTENT_CSVID, csvId);
        startActivity(intent);
    }
}