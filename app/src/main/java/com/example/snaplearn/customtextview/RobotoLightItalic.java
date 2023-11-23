package com.example.snaplearn.customtextview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class RobotoLightItalic extends AppCompatTextView {
    public RobotoLightItalic(@NonNull Context context) {
        super(context);
        setFont();
    }

    public RobotoLightItalic(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RobotoLightItalic(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }
    private void setFont()
    {
        Typeface typeface=Utils.getRobotoLightItalicTypeface(getContext());
        setTypeface(typeface);
    }
}
