package com.example.vcimpandroidcharts;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Canvas extends View {
    private float height;
    private float width;
    private float marginTop;
    private float marginSide;
    private String headerX[];
    private double values[];
    private Paint pincel;
    double maxValue;

    public Canvas(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        initialize(canvas);

        drawMargins(canvas);
        setTextX(canvas);
        setTextY(canvas);
        drawValues(canvas);

    }

    private void initialize(android.graphics.Canvas canvas) {
        marginSide = 150;
        marginTop = 300;
        height = canvas.getHeight() - marginTop;
        width = canvas.getWidth() - marginSide;
        pincel = new Paint();
        maxValue = Arrays.stream(values).max().getAsDouble();
    }

    public void setHeaderX(String[] headerX) {
        this.headerX = headerX;
    }


    public void setValues(double[] values) {
        this.values = values;
    }

    private void drawMargins(android.graphics.Canvas canvas) {
        pincel.setStrokeWidth(4);
        canvas.drawLine(marginSide, marginTop, marginSide, height, pincel);
        canvas.drawLine(marginSide, height, 1000, height, pincel);
    }

    private void setTextX(android.graphics.Canvas canvas) {
        if (headerX == null) {
            return;
        }

        float spacing = width / headerX.length;
        float accum = spacing;

        pincel.setARGB(255, 255, 0, 0);
        pincel.setTextSize(30);
        pincel.setTypeface(Typeface.SERIF);

        canvas.save();
        canvas.rotate(-90f, 970, 870);
        for (String item : headerX) {
            canvas.drawText(item, marginSide, accum, pincel);
            accum += spacing - 3;
        }
        canvas.restore();
    }

    private void setTextY(android.graphics.Canvas canvas) {
        float spacing = height / values.length;
        float accum = spacing;
        float difference = height - marginTop;

        pincel.setARGB(255, 255, 0, 0);
        pincel.setTextSize(30);

        for (double value : values) {
            canvas.drawText("" + value, 0, (float) (height - (value * difference / maxValue)), pincel);
            accum += spacing;
        }
    }

    private void drawValues(android.graphics.Canvas canvas) {
        float spacing = width / values.length;
        float lastRectangleLocation = marginSide;
        float difference = height - marginTop;

        for (double value : values) {
            if (value == maxValue) {
                canvas.drawRect(lastRectangleLocation, height, lastRectangleLocation + spacing - 20, marginTop, pincel);
            } else if (value <= 0) {
                canvas.drawRect(lastRectangleLocation, height, lastRectangleLocation + spacing - 20, height, pincel);
            } else {

                canvas.drawRect(lastRectangleLocation, height, lastRectangleLocation + spacing - 20, (float) (height - (value * difference / maxValue)), pincel);
            }
            lastRectangleLocation += marginSide - 75;//El 75 no sé porqué pero funciona
        }
    }
}
