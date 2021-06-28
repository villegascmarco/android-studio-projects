package com.example.vcimppiechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class PieChart extends View {
    private float[] values;
    private float[] valuesD;
    private String[] headers;
    private Paint pincel;
    private RectF rectf;
    private int[] COLORS = {Color.BLACK, Color.RED, Color.MAGENTA};

    public PieChart(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        initialize();
        drawValues(canvas);
        printText(canvas);
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    private void initialize() {
        pincel = new Paint();
        valuesD = new float[values.length];
        rectf = new RectF(100, 100, 500, 500);

        convertToDegrees();
    }

    private void drawValues(Canvas canvas) {
        float accum = 0;
        for (int i = 0; i < valuesD.length; i++) {
            if (i != 0) {
                accum += valuesD[i - 1];
            }
            pincel.setColor(COLORS[i]);
            canvas.drawArc(rectf, accum, valuesD[i], true, pincel);

        }
    }

    private void convertToDegrees() {
        float total = 0;

        for (float value : values) {
            total += value;
        }

        for (int i = 0; i < values.length; i++) {
            valuesD[i] = 360 * (values[i] / total);
            values[i] *= 100 / total;
        }
    }

    private void printText(Canvas canvas) {
        float height = canvas.getHeight() - 800;
        float spacing = 0;
        pincel.setARGB(255, 255, 0, 0);
        pincel.setTextSize(30);

        for (int i = 0; i < headers.length; i++) {
            pincel.setColor(COLORS[i]);
            canvas.drawText(headers[i] + " " + values[i] + "%", spacing, height, pincel);
            spacing += 340;
        }
    }
}
