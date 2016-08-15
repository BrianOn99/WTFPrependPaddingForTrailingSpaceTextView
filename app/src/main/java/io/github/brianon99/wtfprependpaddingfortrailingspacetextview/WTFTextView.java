package io.github.brianon99.wtfprependpaddingfortrailingspacetextview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by brianchiu on 15/8/2016.
 */
public class WTFTextView extends TextView {
    public WTFTextView(Context context) {
        this(context, null);
    }

    public WTFTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WTFTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
