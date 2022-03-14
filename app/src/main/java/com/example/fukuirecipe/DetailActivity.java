package com.example.fukuirecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private int csvId, recipeId, recipeSize;
    private boolean isFavo;
    private String condition = PSD.NOCON;
    private String[][] csvData = new String[PSD.FILE_SIZE][PSD.FILE_SIZE];
    private String[] recipe = new String[PSD.FILE_SIZE], baseString = new String[PSD.FILE_SIZE];
    private TextView title, other;
    private ImageView addFavoStar;
    private Button bNext, bPrev;
    private LinearLayout materialAndQuantityLayout, procedureLayout, othersLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detailToolBar);
        setSupportActionBar(toolbar);
        setTitle(R.string.toolBar_name);

        this.recipeId = getIntent().getIntExtra(PSD.INTENT_RECIPEID, 1);
        this.csvId = getIntent().getIntExtra(PSD.INTENT_CSVID, 1);
        this.condition = getIntent().getStringExtra(PSD.INTENT_CONDITION);

        this.materialAndQuantityLayout = findViewById(R.id.detailMaterialAndQuantityLayout);
        this.procedureLayout = findViewById(R.id.detailProcedureLayout);
        this.othersLayout = findViewById(R.id.detailOtherLayout);
        this.bNext = findViewById(R.id.detailNext);
        this.bPrev = findViewById(R.id.detailPrev);
        this.title = findViewById(R.id.detailTitle);
        this.other = findViewById(R.id.detailOther);
        this.other.setVisibility(View.GONE);

        if(this.condition == null) this.condition = PSD.NOCON;
        this.csvData = FileIO.fileReadWrapper(this, csvId);
        this.baseString = this.csvData[0];
        int count = 0;
        for(String[] v : this.csvData) {
            if(v[0].equals(""+recipeId)) {
                this.recipe = v;
            }else if(v[0].equals(PSD.FILE_END)){
                this.recipeSize = count-1;
                break;
            }
            count++;
        }
        if(this.recipe[0] == null) return;

        this.setRecipeContent();
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
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_setting:
                Intent inte = new Intent(DetailActivity.this, RandOrChoseActivity.class);
                inte.putExtra(PSD.INTENT_CSVID, csvId);
                startActivity(inte);
                return true;
            case R.id.menu_star:
                Intent intent1 = new Intent(DetailActivity.this, FavoriteActivity.class);
                startActivity(intent1);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() { }

    private void setRecipeContent() {
        this.bPrev.setEnabled(true);
        this.bNext.setEnabled(true);
        this.other.setVisibility(View.GONE);
        this.addFavoStar = findViewById(R.id.addFavoStar);
        if(this.isFavo = FileIO.isFavorite(this, this.csvId, this.recipeId)){
            this.addFavoStar.setImageResource(R.drawable.baseline_star_black_48);
        }else{
            this.addFavoStar.setImageResource(R.drawable.baseline_star_border_black_48);
        }
        int color = Color.parseColor("#FFAB00");
        this.addFavoStar.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        this.othersLayout.removeViews(0, this.othersLayout.getChildCount());
        this.materialAndQuantityLayout.removeViews(0, this.materialAndQuantityLayout.getChildCount());
        this.procedureLayout.removeViews(0, this.procedureLayout.getChildCount());
        if(this.recipeId == 1) {
            this.bPrev.setEnabled(false);
        }
        if(this.recipeId == this.recipeSize) {
            this.bNext.setEnabled(false);
        }
        if(this.recipe[0] == null) return;

        int tezidx = 1;
        for(int i = 1; i < this.baseString.length; i++) {
            if(this.baseString[i].equals("FIN")) break;
            if(this.recipe[i].equals("")) continue;
            String nbs = this.baseString[i];

            if(nbs.equals("レシピ名")){
                this.title.setText(getString(R.string.detail_title, this.recipe[i]));
                continue;
            }
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            String addText = "";
            boolean zairyou = nbs.length() > 2 && nbs.charAt(0) == '材' && nbs.charAt(1) == '料';
            boolean tezyun = nbs.length() > 2 && nbs.charAt(0) == '手' && nbs.charAt(1) == '順';
            if(zairyou){
                addText = recipe[i];
                i++;
                addText += " : " + recipe[i];
            }else if(tezyun){
                addText = "手順" + tezidx + "  " + recipe[i];
                tezidx++;
            }else{
                addText = baseString[i] + " : " + recipe[i];
            }
            textView.setText(addText);

            if(zairyou){
                this.materialAndQuantityLayout.addView(textView);
            }else if(tezyun){
                this.procedureLayout.addView(textView);
            }else{
                if(this.othersLayout.getChildCount() == 0) {
                    this.other.setVisibility(View.VISIBLE);
                }
                this.othersLayout.addView(textView);
            }

        }
    }

    private void setRecipeData() {
        for(String[] v : this.csvData) {
            if(v[0].equals(""+recipeId)) {
                this.recipe = v;
            }else if(v[0].equals(PSD.FILE_END)){
                break;
            }
        }
    }

    public void clickPrev(View view) {
        this.recipeId--;
        this.setRecipeData();
        this.setRecipeContent();
    }

    public void clickNext(View view) {
        this.recipeId++;
        this.setRecipeData();
        this.setRecipeContent();
    }

    public void clickList(View view) {
        Intent intent = new Intent(DetailActivity.this, ListActivity.class);
        intent.putExtra(PSD.INTENT_CSVID, this.csvId);
        intent.putExtra(PSD.INTENT_CONDITION, this.condition);
        startActivity(intent);
    }

    public void clickRand(View view) {
        int r = (int)(Math.random()*this.recipeSize);
        if(r == this.recipeId-1) {
            if(r == this.recipeSize-1) r = 0;
            else r++;
        }
        this.recipeId = r+1;
        if(!this.condition.equals(PSD.NOCON)) {
            this.recipeId = Util.randFromCondition(this.csvId, this.condition, this.csvData);
        }
        this.setRecipeData();
        this.setRecipeContent();
    }

    public void clickStar(View view) {
        if(this.isFavo) {
            this.addFavoStar.setImageResource(R.drawable.baseline_star_border_black_48);
            int color = Color.parseColor("#FFAB00");
            this.addFavoStar.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            FileIO.eraseFavorite(this, this.csvId, this.recipeId);
            this.isFavo = false;
        } else {
            boolean r = FileIO.addFavorite(this, this.csvId, this.recipeId);
            if(r == false){
                Toast.makeText(this, "お気に入り件数が最大値です", Toast.LENGTH_SHORT).show();
                return;
            }
            this.addFavoStar.setImageResource(R.drawable.baseline_star_black_48);
            int color = Color.parseColor("#FFAB00");
            this.addFavoStar.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            this.isFavo = true;
        }
    }

}