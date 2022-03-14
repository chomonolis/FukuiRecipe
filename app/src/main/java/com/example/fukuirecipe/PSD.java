package com.example.fukuirecipe;

public class PSD {
    public static final String APPNAME = "FukuiRecipe";
    public static final String[] CSVNAME = {
            "「福井ふるさとの味」のレシピ",
            "「ふく囲鍋」のレシピ",
            "「ふくい健幸美食」のレシピ",
            "ふくい朝ごはんレシピ",
            "南越米っ子コンクール応援作品",
            "地場農作物を使ったレシピ",
            "米料理レシピ",
            "さといも料理レシピ",
            "ジビエと奥越産食材を使ったレシピ",
            "米粉レシピ"
    };
    public static final int FILE_SIZE = 128, FAVO_SIZE = 10;
    public static final String FILE_END = "FIN";
    public static final String INTENT_CSVID = "CSVID";
    public static final String INTENT_RECIPEID = "RECIPEID";
    public static final String INTENT_CONDITION = "CONDITION";
    public static final String NOCON = "指定なし";
    public static final String[] CSV2CON ={"指定なし", "春", "夏", "秋", "冬"};
    public static final String[] CSV4CON ={"指定なし", "ご飯（炭水化物）", "汁もの", "おかず（たんぱく質）", "おかず（ミネラル）", };
    public static final String[] CSV8CON ={"指定なし", "2人分", "3人分", "4人分", "6人分"};
    public static final String[] CSV10CON ={"指定なし", "2人分", "3人分", "4人分", "6人分"};
    public static final String[] CONKEYS = {"季節", "一汁三菜の位置づけ", "分量", "分量"};
    public static final String FAVOF = "FAVOF", FAVOS = "FAVOS";
    public static final String CANTUSE = "この画面では使用できません";
}
