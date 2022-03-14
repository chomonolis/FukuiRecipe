package com.example.fukuirecipe;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileIO {

    private static String[][] fileRead(Context context, String s) {
        String[][] res = new String[PSD.FILE_SIZE][PSD.FILE_SIZE];
        try {
            AssetManager asm = context.getAssets();
            InputStream is = asm.open(s);//指定  asset の場合、左項のFileを消して、右項をgetAssets().open(FILE_NAME)に
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int readi = 0;
            String temp;
            temp = br.readLine();
            while(temp != null){
                res[readi] = temp.split(",");
                temp = br.readLine();
                readi++;
            }
            res[readi][0] = PSD.FILE_END;
            br.close();
            System.out.println("success_read");
        }catch (IOException e) {
            e.printStackTrace();
        }
        return  res;
    }

    public static String[][] fileReadWrapper(Context context, int id) {
        return FileIO.fileRead(context, id + ".csv");
    }

    public static int[][] readFavorite(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PSD.APPNAME, Context.MODE_PRIVATE);
        int[][] res = new int[PSD.FAVO_SIZE][2];
        for(int i = 0; i < PSD.FAVO_SIZE; i++) {
            int adfirst = prefs.getInt(PSD.FAVOF + i, 0);
            int adsecond = prefs.getInt(PSD.FAVOS + i, 0);
            res[i][0] = adfirst;
            res[i][1] = adsecond;
        }
        return res;
    }

    public static void writeFavorite(Context context, int[][] data) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PSD.APPNAME, Context.MODE_PRIVATE).edit();
        for(int i = 0; i < PSD.FAVO_SIZE; i++) {
            editor.putInt(PSD.FAVOF + i, data[i][0]);
            editor.putInt(PSD.FAVOS + i, data[i][1]);
        }
        editor.apply();
    }

    public static boolean isFavorite(Context context, int csvId, int recipeId) {
        int[][] data = FileIO.readFavorite(context);
        for(int i = 0; i < data.length; i++) {
            if(data[i][0] == csvId && data[i][1] == recipeId) {
                return true;
            }
        }
        return false;
    }

    public static void eraseFavorite(Context context, int csvId, int recipeId) {
        int[][] data = FileIO.readFavorite(context);
        boolean flg = false;
        for(int i = 0; i < data.length; i++) {
            if(data[i][0] == csvId && data[i][1] == recipeId) {
                flg = true;
            }else if(flg) {
                data[i-1][0] = data[i][0];
                data[i-1][1] = data[i][1];
            }
        }
        data[data.length-1][0] = 0; data[data.length-1][1] = 0;
        writeFavorite(context, data);
    }

    public static boolean addFavorite(Context context, int csvId, int recipeId) {
        int[][] data = FileIO.readFavorite(context);
        for(int i = 0; i < data.length; i++) {
            if(data[i][0] == csvId && data[i][1] == recipeId) {
                return true;
            }else if(data[i][0] == 0 && data[i][1] == 0) {
                data[i][0] = csvId; data[i][1] = recipeId;
                writeFavorite(context, data);
                return true;
            }
        }
        return false;
    }

    public static void __EEEF(Context context) {
        int[][] data = new int[PSD.FAVO_SIZE][2];
        for(int i = 0; i < data.length; i++) {
            data[i][0] = 0; data[i][0] = 0;
        }
        FileIO.writeFavorite(context, data);
        System.out.println("Erased!!!");
    }
}
