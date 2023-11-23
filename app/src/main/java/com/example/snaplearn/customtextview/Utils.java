package com.example.snaplearn.customtextview;


import android.content.Context;
import android.graphics.Typeface;

public class Utils
{
    private static Typeface RobotoBlackTypeface;
    private static Typeface RobotoBlackItalicTypeface;
    private static Typeface RobotoBoldTypeface;
    private static Typeface RobotoBoldCondensedTypeface;
    private static Typeface RobotoBoldCondensedItalicTypeface;
    private static Typeface RobotoBoldItalicTypeface;
    private static Typeface RobotoCondensedTypeface;
    private static Typeface RobotoCondensedItalicTypeface;
    private static Typeface RobotoLightTypeface;
    private static Typeface RobotoLightItalicTypeface;
    private static Typeface RobotoMediumTypeface;
    private static Typeface RobotoMediumItalicTypeface;
    private static Typeface RobotoRegularTypeface;
    private static Typeface RobotoThinTypeface;
    private static Typeface RobotoThinItalicTypeface;
    private static Typeface RobotoItalicTypeface;

    public static Typeface getRobotoItalicTypeface(Context context) {
        if(RobotoItalicTypeface==null)
        {
            RobotoItalicTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoItalic.ttf.ttf");
        }
        return RobotoItalicTypeface;
    }

    public static Typeface getRobotoBlackTypeface(Context context) {
        if(RobotoBlackTypeface==null)
        {
            RobotoBlackTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoBlack.ttf");
        }
        return RobotoBlackTypeface;
    }

    public static Typeface getRobotoBlackItalicTypeface(Context context) {
        if(RobotoBlackItalicTypeface==null)
        {
            RobotoBlackItalicTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoBlackItalic.ttf");
        }
        return RobotoBlackItalicTypeface;
    }

    public static Typeface getRobotoBoldTypeface(Context context) {
        if(RobotoBoldTypeface==null)
        {
            RobotoBoldTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoBold.ttf");
        }
        return RobotoBoldTypeface;
    }

    public static Typeface getRobotoBoldCondensedTypeface(Context context) {
        if(RobotoBoldCondensedTypeface==null)
        {
            RobotoBoldCondensedTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoBoldCondensed.ttf");
        }
        return RobotoBoldCondensedTypeface;
    }

    public static Typeface getRobotoBoldCondensedItalicTypeface(Context context) {
        if(RobotoBoldCondensedItalicTypeface==null)
        {
            RobotoBoldCondensedItalicTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoBoldCondensedItalic.ttf");
        }
        return RobotoBoldCondensedItalicTypeface;
    }

    public static Typeface getRobotoBoldItalicTypeface(Context context) {
        if(RobotoBoldItalicTypeface==null)
        {
            RobotoBoldItalicTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoBoldItalic.ttf");
        }
        return RobotoBoldItalicTypeface;
    }

    public static Typeface getRobotoCondensedTypeface(Context context) {
        if(RobotoCondensedTypeface==null)
        {
            RobotoCondensedTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoCondensed.ttf");
        }
        return RobotoCondensedTypeface;
    }

    public static Typeface getRobotoCondensedItalicTypeface(Context context) {
        if(RobotoCondensedItalicTypeface==null)
        {
            RobotoCondensedItalicTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoCondensedItalic.ttf");
        }
        return RobotoCondensedItalicTypeface;
    }

    public static Typeface getRobotoLightTypeface(Context context) {
        if(RobotoLightTypeface==null)
        {
            RobotoLightTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoLight.ttf");
        }
        return RobotoLightTypeface;
    }

    public static Typeface getRobotoLightItalicTypeface(Context context) {
        if(RobotoLightItalicTypeface==null)
        {
            RobotoLightItalicTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoLightItalic.ttf");
        }
        return RobotoLightItalicTypeface;
    }

    public static Typeface getRobotoMediumTypeface(Context context) {
        if(RobotoMediumTypeface==null)
        {
            RobotoMediumTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoMedium.ttf");
        }
        return RobotoMediumTypeface;
    }

    public static Typeface getRobotoMediumItalicTypeface(Context context) {
        if(RobotoMediumItalicTypeface==null)
        {
            RobotoMediumItalicTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoMediumItalic.ttf");
        }
        return RobotoMediumItalicTypeface;
    }

    public static Typeface getRobotoRegularTypeface(Context context) {
        if(RobotoRegularTypeface==null)
        {
            RobotoRegularTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoRegular.ttf");
        }
        return RobotoRegularTypeface;
    }

    public static Typeface getRobotoThinTypeface(Context context) {
        if(RobotoThinTypeface==null)
        {
            RobotoThinTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoThin.ttf");
        }
        return RobotoThinTypeface;
    }

    public static Typeface getRobotoThinItalicTypeface(Context context) {
        if(RobotoThinItalicTypeface==null)
        {
            RobotoThinItalicTypeface=Typeface.createFromAsset(context.getAssets(),"fonts/RobotoThinItalic.ttf");
        }
        return RobotoThinItalicTypeface;
    }
}
