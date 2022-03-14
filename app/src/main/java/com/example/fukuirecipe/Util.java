package com.example.fukuirecipe;

import android.view.View;

public class Util {
    public static int randFromCondition(int csvId, String condition, String[][] data) {
        int res = 1, keyIndex = Util.findKeyIndex(csvId, condition, data);
        if(keyIndex == 1) return 1;
        int sz = 0;
        for(int i = 0; i < data.length; i++) {
            if(data[i][0].equals(PSD.FILE_END)) break;
            if(data[i][keyIndex].equals(condition)) sz++;
        }
        int r = (int)(Math.random()*sz);
        for(int i = 0; i < data.length; i++) {
            if(data[i][0].equals(PSD.FILE_END)) break;
            if(data[i][keyIndex].equals(condition)){
                if(r == 0){
                    res = i;
                    break;
                }
                r--;
            }
        }

        return  res;
    }

    public static int findKeyIndex(int csvId, String condition, String[][] data) {
        String key = "";
        switch (csvId){
            case 2:
                key = PSD.CONKEYS[0];
                break;
            case 4:
                key = PSD.CONKEYS[1];
                break;
            case 8:
                key = PSD.CONKEYS[2];
                break;
            case 10:
                key = PSD.CONKEYS[3];
                break;
            default:
                return 1;
        }
        int keyIndex = 1;
        for(int i = 0; i < data[0].length; i++) {
            if(data[0][i].equals(key)){
                keyIndex = i;
                break;
            }
        }
        return keyIndex;
    }
}
