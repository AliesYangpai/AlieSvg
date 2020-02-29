package org.alie.aliesvg.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Alie on 2020/2/29.
 * 类描述
 * 版本
 */
public class Province {
    private String name;
    private Path path;
    private int color = Color.BLACK;

    public Province() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void drawItem(Canvas canvas, Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        canvas.drawPath(path,paint);

        paint.reset();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path,paint);
    }
}
