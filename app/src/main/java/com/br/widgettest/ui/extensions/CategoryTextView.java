package com.br.widgettest.ui.extensions;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import com.br.widgettest.core.Category;

/**
 * Created by Breno on 1/23/2016.
 */
public class CategoryTextView extends TextView {
    private Category category;

    public CategoryTextView(Context context) {
        super(context);
    }

    public CategoryTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (category != null) {
            setText(category.getName());
            setBackgroundColor(category.getColor());
        }
        super.onDraw(canvas);
    }
}
