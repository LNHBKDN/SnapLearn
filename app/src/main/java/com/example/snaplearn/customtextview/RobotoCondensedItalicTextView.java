package com.example.snaplearn.customtextview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class RobotoCondensedItalicTextView extends AppCompatTextView {
    public RobotoCondensedItalicTextView(@NonNull Context context) {
        super(context);
        setFont();
    }

    public RobotoCondensedItalicTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RobotoCondensedItalicTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }
    private void setFont()
    {
        Typeface typeface=Utils.getRobotoCondensedItalicTypeface(getContext());
        setTypeface(typeface);
    }
}
