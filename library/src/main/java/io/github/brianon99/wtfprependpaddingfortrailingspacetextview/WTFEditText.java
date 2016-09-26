package io.github.brianon99.wtfprependpaddingfortrailingspacetextview;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * An almost copy-and-paste of android TextView source code
 */
public class WTFEditText extends EditText {
    public WTFEditText(Context context) {
        super(context);
    }

    public WTFEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
       //this(context, attrs, R.attr.editTextStyle);
    }

    public WTFEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

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
