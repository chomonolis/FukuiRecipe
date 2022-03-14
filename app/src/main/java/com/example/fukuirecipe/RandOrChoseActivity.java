package com.example.fukuirecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class RandOrChoseActivity extends AppCompatActivity {
    
    private Switch rocSwitch;
    private Spinner spinner;
    private String conditions = PSD.NOCON;
    private int csvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rand_or_chose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.rocToolBar);
        setSupportActionBar(toolbar);
        setTitle(R.string.toolBar_name);

        this.csvId = getIntent().getIntExtra(PSD.INTENT_CSVID, 1);

        this.rocSwitch = findViewById(R.id.rocRandSwitch);
        this.spinner = findViewById(R.id.spinner);
        this.spinner.setVisibility(View.VISIBLE);
        String[] spinnerSetString = new String[0];
        switch (csvId){
            case 2:
                spinnerSetString = PSD.CSV2CON;
                break;
            case 4:
                spinnerSetString = PSD.CSV4CON;
                break;
            case 8:
                spinnerSetString = PSD.CSV8CON;
                break;
            case 10:
                spinnerSetString = PSD.CSV10CON;
                break;
            default:
                this.spinner.setVisibility(View.GONE);
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerSetString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //　アイテムが選択された時
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
            }
            //　アイテムが選択されなかった
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
                Intent intent = new Intent(RandOrChoseActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_star:
                Intent intent1 = new Intent(RandOrChoseActivity.this, FavoriteActivity.class);
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

    public void clickExecutionButton(View view) {
        if(this.spinner.getVisibility() == View.VISIBLE) this.conditions = (String) this.spinner.getSelectedItem();
        else this.conditions = PSD.NOCON;
        if(!this.rocSwitch.isChecked()) {
            Intent intent = new Intent(RandOrChoseActivity.this, ListActivity.class);
            intent.putExtra(PSD.INTENT_CSVID, this.csvId);
            intent.putExtra(PSD.INTENT_CONDITION, this.conditions);
            startActivity(intent);
    } else if(conditions.equals(PSD.NOCON)){
            Intent intent = new Intent(RandOrChoseActivity.this, DetailActivity.class);
            intent.putExtra(PSD.INTENT_CSVID, this.csvId);
            intent.putExtra(PSD.INTENT_CONDITION, this.conditions);
            String[][] data = FileIO.fileReadWrapper(this, csvId);
            int size = 1;
            for(int i = 1; i < data.length; i++) {
                if(data[i][0].equals(PSD.FILE_END)){
                    size = i-1;
                    break;
                }
            }
            int r = (int)(Math.random()*size);
            int recipeId = r+1;
            intent.putExtra(PSD.INTENT_RECIPEID, recipeId);
            startActivity(intent);
        } else {
            Intent intent = new Intent(RandOrChoseActivity.this, DetailActivity.class);
            intent.putExtra(PSD.INTENT_CSVID, this.csvId);
            intent.putExtra(PSD.INTENT_CONDITION, this.conditions);
            String[][] data = FileIO.fileReadWrapper(this, csvId);
            int recipeId = Util.randFromCondition(this.csvId, this.conditions, data);
            intent.putExtra(PSD.INTENT_RECIPEID, recipeId);
            startActivity(intent);
        }
    }
}