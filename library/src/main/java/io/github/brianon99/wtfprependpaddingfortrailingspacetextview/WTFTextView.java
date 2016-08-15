package io.github.brianon99.wtfprependpaddingfortrailingspacetextview;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

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

    /* This approach will make the cursor not matching text
    protected void onDraw(Canvas canvas) {
        if (!shouldPrependPadding()) {
            super.onDraw(canvas);
            return;
        }
        canvas.save();
        CharSequence text = getText();
        int spaceCount = trailingSpaceCount(text);
        int spaceWidth = (int)Layout.getDesiredWidth(text, text.length()-spaceCount, text.length(),getPaint());
        canvas.translate(spaceWidth/2, 0);
        super.onDraw(canvas);
        canvas.restore();
    }
    */

    /*
     * Offset content from the left boundary, a hook method called in TextView#onDraw and
     * onMeasure
     */
    @Override
    public int getCompoundPaddingLeft() {
        int base_padding = super.getCompoundPaddingLeft();
        if (!shouldPrependPadding()) {
            return base_padding;
        } else {
            return base_padding + (int) (getTrailingSpaceWidth() / 2);
        }
    }

    /*
     * This could be negative, to make some trailing space not shown up
     */
    @Override
    public int getCompoundPaddingRight() {
        int base_padding = super.getCompoundPaddingRight();
        if (!shouldPrependPadding()) {
            return base_padding;
        } else {
            return base_padding - (int) (getTrailingSpaceWidth() / 2);
        }
    }

    private float getTrailingSpaceWidth() {
        CharSequence text = getText();
        int spaceCount = trailingSpaceCount(text);
        /* TextView#onMeasure use this method to calculate width */
        return Layout.getDesiredWidth(text, text.length() - spaceCount, text.length(), getPaint());
    }

    private static int trailingSpaceCount(CharSequence text) {
        int len = text.length();
        int i;
        for (i=len-1; i>=0; i--) {
            if (text.charAt(i) != ' ')
                break;
        }
        return (len-1) - i;
    }

    private boolean shouldPrependPadding() {
        if ((getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK) != Gravity.CENTER_HORIZONTAL)
            return false;

        if (getLayoutParams().width != ViewGroup.LayoutParams.WRAP_CONTENT)
            return false;

        CharSequence text = getText();
        return (text.length() == 0) || (text.charAt(text.length()-1) == ' ');
    }
}
