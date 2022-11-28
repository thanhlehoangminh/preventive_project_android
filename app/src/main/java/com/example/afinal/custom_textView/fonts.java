package com.example.afinal.custom_textView;


import android.graphics.Typeface;
import android.content.Context;

public class fonts {
    private static Typeface Roboto_Black_Typeface;
    private static Typeface Roboto_BlackItalic_Typeface;
    private static Typeface Roboto_Bold_Typeface;
    private static Typeface Roboto_BoldItalic_Typeface;
    private static Typeface Roboto_Italic_Typeface;
    private static Typeface Roboto_Light_Typeface;
    private static Typeface Roboto_LightItalic_Typeface;
    private static Typeface Roboto_Medium_Typeface;
    private static Typeface Roboto_MediumItalic_Typeface;
    private static Typeface Roboto_Regular_Typeface;
    private static Typeface Roboto_Thin_Typeface;
    private static Typeface Roboto_ThinItalic_Typeface;

    public static Typeface getRoboto_Black_Typeface(Context context) {
        if (Roboto_Black_Typeface == null){
            Roboto_Black_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-Black.ttf");
        }
        return Roboto_Black_Typeface;
    }

    public static Typeface getRoboto_BlackItalic_Typeface(Context context) {
        if (Roboto_BlackItalic_Typeface == null){
            Roboto_BlackItalic_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-BlackItalic.ttf");
        }
        return Roboto_BlackItalic_Typeface;
    }

    public static Typeface getRoboto_Bold_Typeface(Context context) {
        if (Roboto_Bold_Typeface == null){
            Roboto_Bold_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-Bold.ttf");
        }return Roboto_Bold_Typeface;
    }

    public static Typeface getRoboto_BoldItalic_Typeface(Context context) {
        if (Roboto_BoldItalic_Typeface == null){
            Roboto_BoldItalic_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-BoldItalic.ttf");
        }
        return Roboto_BoldItalic_Typeface;
    }

    public static Typeface getRoboto_Italic_Typeface(Context context) {
        if (Roboto_Italic_Typeface == null){
            Roboto_Italic_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-Italic.ttf");
        }
        return Roboto_Italic_Typeface;
    }

    public static Typeface getRoboto_Light_Typeface(Context context) {
        if (Roboto_Light_Typeface == null){
            Roboto_Light_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-Light.ttf");
        }
        return Roboto_Light_Typeface;
    }

    public static Typeface getRoboto_LightItalic_Typeface(Context context) {
        if (Roboto_LightItalic_Typeface == null){
            Roboto_LightItalic_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-LightItalic.ttf");
        }
        return Roboto_LightItalic_Typeface;
    }

    public static Typeface getRoboto_Medium_Typeface(Context context) {
        if (Roboto_Medium_Typeface == null){
            Roboto_Medium_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-Medium.ttf");
        }
        return Roboto_Medium_Typeface;
    }

    public static Typeface getRoboto_MediumItalic_Typeface(Context context) {
        if (Roboto_MediumItalic_Typeface == null){
            Roboto_MediumItalic_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-MediumItalic.ttf");
        }
        return Roboto_MediumItalic_Typeface;
    }

    public static Typeface getRoboto_Regular_Typeface(Context context) {
        if (Roboto_Regular_Typeface == null){
            Roboto_Regular_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-Regular.ttf");
        }
        return Roboto_Regular_Typeface;
    }

    public static Typeface getRoboto_Thin_Typeface(Context context) {
        if (Roboto_Thin_Typeface == null){
            Roboto_Thin_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-Thin.ttf");
        }
        return Roboto_Thin_Typeface;
    }

    public static Typeface getRoboto_ThinItalic_Typeface(Context context) {
        if (Roboto_ThinItalic_Typeface == null){
            Roboto_ThinItalic_Typeface = Typeface.createFromAsset(context.getAssets(),"font/Roboto-ThinItalic.ttf");
        }
        return Roboto_ThinItalic_Typeface;
    }
}

