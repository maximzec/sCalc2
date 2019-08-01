package com.app.drink.scalc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class RightAlignedEditText extends android.support.v7.widget.AppCompatEditText {

    public RightAlignedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * This is a Fix of buggy behavior of android, where after setting text using setText(...), selection remain on
     * first character and not at last character.
     */
    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (text != null && ! (text.toString().trim().length() == 0 )) {
            final int textLength = text.length();
            if (getSelectionEnd() != textLength) {
                setSelection(textLength);
            }
        }
    }
}
