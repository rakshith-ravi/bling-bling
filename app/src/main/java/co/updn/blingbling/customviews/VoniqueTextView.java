package co.updn.blingbling.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import co.updn.blingbling.R;

public class VoniqueTextView extends TextView {
    public VoniqueTextView(Context context) {
        super(context);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/vonique_regular.ttf");
        setTypeface(typeface);
    }

    public VoniqueTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/vonique_regular.ttf");
        setTypeface(typeface);
    }

    public VoniqueTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/vonique_regular.ttf");
        setTypeface(typeface);
    }
}
