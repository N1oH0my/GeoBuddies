package com.surf2024.geobuddies.presentation.feature;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.annotation.Nullable;

import com.surf2024.geobuddies.R;

public class GradientTextView extends androidx.appcompat.widget.AppCompatTextView {
    private String startColor = String.valueOf(getCurrentTextColor());
    private String endColor = String.valueOf(getCurrentTextColor());
    private String centerColor= String.valueOf(getCurrentTextColor());
    private int angle=0;

    public GradientTextView(Context context) {
        super(context);
        init(context, null);

    }

    public GradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GradientTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.GradientTextView);
            startColor =ta.getString(R.styleable.GradientTextView_startColor);
            centerColor =ta.getString(R.styleable.GradientTextView_centerColor);
            endColor =ta.getString(R.styleable.GradientTextView_endColor);
            angle =Integer.valueOf(ta.getString(R.styleable.GradientTextView_angle));
            ta.recycle();
        }
    }

    @SuppressLint("GradientTextView")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed)
        {
            TextPaint paint = getPaint();
            float width = paint.measureText(getText().toString());
            double endX= Math.sin(angle) * width;
            double endY= Math.cos(angle) * width;
            Shader textShader = new LinearGradient(0, 0, (int)endX, (int)endY,
                    new int[]{
                            Color.parseColor(String.valueOf(startColor)),
                            Color.parseColor(String.valueOf(centerColor)),
                            Color.parseColor(String.valueOf(endColor))
                    }, null, Shader.TileMode.CLAMP);
            getPaint().setShader(textShader);
        }
    }
}
