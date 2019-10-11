package com.vs.Statoos;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.io.ByteArrayOutputStream;

/**
 * Created by Android-Dev2 on 2/20/2018.
 */

public class Converter {

    int height;
    float heightHalf;
    Bitmap mBackground;
    Context ctx;
    Canvas canvas;
    Bitmap image, returnedBitmap;
    Bitmap b2 = null;
    int AdditionHeightWidth = 0;

    public Converter(Context context){
        ctx = context;
    }

    public Bitmap textAsBitmap(String text, float textSize, float stroke,
                               int color, int btmColor, Typeface typeface, int backgroundImage, String picturePath) {

        TextPaint paint = new TextPaint();
        paint.setColor(color);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(stroke);
        paint.setTypeface(typeface);

        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);


        int textWidth = (int) paint.measureText(text);
        float baseline = (int) (-paint.ascent() + 2f); // ascent() is negative
        int width = 330;
        int padding = 100;
        float spacingMultiplier = 1;
        float spacingAddition = 0;
        boolean includePadding = false;


        StaticLayout staticLayout = new StaticLayout(text, 0, text.length(),
                paint, width, Layout.Alignment.ALIGN_CENTER, 1.0f,
                1.0f, false);

        //StaticLayout staticLayout = new StaticLayout(text, paint, width, alignment, spacingMultiplier, spacingAddition, includePadding);

        int linecount = staticLayout.getLineCount();
        height = staticLayout.getHeight();

        if(height > 400){
            height = (int) (baseline + paint.descent() + 5) * linecount + 7;
        } else{
            height = 400;
        }

        if(linecount<=3){
            heightHalf = linecount/2 + 130;
        }
        else if(linecount>3 && linecount<=5){
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


       image = Bitmap
                .createBitmap(width+padding, height, Bitmap.Config.ARGB_8888);


        //canvas = new Canvas(image);
        /*Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(btmColor, 1);
        p.setColorFilter(filter);
        canvas.drawARGB(0xFF, 0xFF, 0xFF, 0xFF);
        canvas.drawBitmap(image, 0, 0, p);*/
        Paint p = new Paint();

        if(picturePath != null){
            mBackground = BitmapFactory.decodeFile(picturePath).copy(Bitmap.Config.ARGB_8888, true);
            // original measurements
            int origWidth = mBackground.getWidth();
            int origHeight = mBackground.getHeight();
            final int destWidth = 400;
            if(origWidth > destWidth){
                if(height > 400){
                    AdditionHeightWidth = 100;
                }
                //origHeight/( origWidth / destWidth ) + AdditionHeightWidth
                int destHeight = height;
                b2 = Bitmap.createScaledBitmap(mBackground, destWidth + AdditionHeightWidth, destHeight, false);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                b2.compress(Bitmap.CompressFormat.JPEG,80, outStream);
            }
            canvas = new Canvas(b2);
            canvas.drawBitmap(image, 0, 0, p);
            //canvas.drawBitmap(image, 0, 0, p);
            returnedBitmap = b2;

        } else {
            if(backgroundImage != 0){

                mBackground = BitmapFactory.decodeResource(ctx.getResources(), backgroundImage).copy(Bitmap.Config.ARGB_8888, true);
                // original measurements
                int origWidth = mBackground.getWidth();
                int origHeight = mBackground.getHeight();
                final int destWidth = 400;
                if(origWidth > destWidth){
                    if(height > 400){
                        AdditionHeightWidth = 100;
                    }
                    //origHeight/( origWidth / destWidth ) + AdditionHeightWidth
                    int destHeight = height;
                    b2 = Bitmap.createScaledBitmap(mBackground, destWidth + AdditionHeightWidth, destHeight, false);
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    b2.compress(Bitmap.CompressFormat.JPEG,80, outStream);
                }
                canvas = new Canvas(b2);
                canvas.drawBitmap(image, 0, 0, p);
                //canvas.drawBitmap(image, 0, 0, p);
                returnedBitmap = b2;

            } else {
                canvas = new Canvas(image);
                ColorFilter filter = new LightingColorFilter(btmColor, 1);
                p.setColorFilter(filter);
                canvas.drawARGB(0xFF, 0xFF, 0xFF, 0xFF);
                canvas.drawBitmap(image, 0, 0, p);
                returnedBitmap = image;

            }

        }

        canvas.translate(19.0f, heightHalf);
        staticLayout.draw(canvas);

        return returnedBitmap;
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
