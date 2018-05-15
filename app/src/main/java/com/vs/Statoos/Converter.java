package com.vs.Statoos;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * Created by Android-Dev2 on 2/20/2018.
 */

public class Converter {

    int height;
    float heightHalf;

    public Bitmap textAsBitmap(String text, float textSize, float stroke,
                               int color, Typeface typeface) {

        TextPaint paint = new TextPaint();
        paint.setColor(color);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(stroke);
        paint.setTypeface(typeface);

        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);


        int textWidth = (int) paint.measureText(text);
        float baseline = (int) (-paint.ascent() + 2f); // ascent() is negative
        int width = 300;
        int padding = 30;
        float spacingMultiplier = 1;
        float spacingAddition = 0;
        boolean includePadding = false;


        StaticLayout staticLayout = new StaticLayout(text, 0, text.length(),
                paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f,
                1.0f, false);

        //StaticLayout staticLayout = new StaticLayout(text, paint, width, alignment, spacingMultiplier, spacingAddition, includePadding);

        int linecount = staticLayout.getLineCount();
        height = staticLayout.getHeight();
        if(height>400){
            height = (int) (baseline + paint.descent() + 5) * linecount + 7;
        }else{
            height = 400;
        }
        if(linecount<=3){
            heightHalf = linecount/2 + 130;
        }else if(linecount>3 && linecount<=5){
            heightHalf = linecount/2 + 120;
        }
        else if(linecount>5 && linecount<9){
            heightHalf = linecount/2 + 70;
        }
        else{
            heightHalf = linecount/2 + 45;
        }

       /* if(linecount < 5){
            heightHalf = height/2;
        }else{
            heightHalf = linecount/2 +60;
        }*/


        Bitmap image = Bitmap
                .createBitmap(width+padding, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawARGB(0xFF, 0xFF, 0xFF, 0xFF);
        canvas.translate(19.0f, heightHalf);
        staticLayout.draw(canvas);

        return image;

    }

    // Adding Border to bitmap
    public Bitmap addBorder(Bitmap bmp, int borderSize, int borderColor) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize
                * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(borderColor);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

}