package com.example.snaplearn.customtextview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class RobotoBoldCondensedItalicTextView extends AppCompatTextView {
    public RobotoBoldCondensedItalicTextView(@NonNull Context context) {
        super(context);
        setFont();
    }

    public RobotoBoldCondensedItalicTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public RobotoBoldCondensedItalicTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }
    private void setFont()
    {
        Typeface typeface=Utils.getRobotoBoldCondensedItalicTypeface(getContext());
        setTypeface(typeface);
    }
}
