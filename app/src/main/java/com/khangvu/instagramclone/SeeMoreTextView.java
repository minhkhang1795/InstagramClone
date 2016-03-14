package com.khangvu.instagramclone;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by duyvu on 3/14/16.
 */
public class SeeMoreTextView extends TextView {
    public SeeMoreTextView(Context context) {
        super(context);
    }

    public SeeMoreTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeeMoreTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        CharSequence text = getText();
//        onPreDraw();
//
//        while(getLineCount() > getMaxLines()) {
//            text = text.subSequence(0, text.length()-1);
//            super.setText(text + "more");
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            onPreDraw();
//        }
//    }
}

