package com.example.afinal.custom_textView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class RobotoBlackTextView extends AppCompatTextView {
    public RobotoBlackTextView(@NonNull Context context) {
        super(context);
        setFontTextView();
    }

    public RobotoBlackTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);        setFontTextView();

    }

    public RobotoBlackTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);        setFontTextView();

    }
    private void setFontTextView(){
        Typeface typeface = fonts.getRoboto_Black_Typeface(getContext());
        setTypeface(typeface);
    }
}
