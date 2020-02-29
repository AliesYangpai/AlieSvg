package org.alie.aliesvg.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;

import org.alie.aliesvg.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Alie on 2020/2/29.
 * 类描述
 * 版本
 */
public class AlieMapView extends View {
    private Context mContext;
    private List<Province> provinces;
    private Object object = new Object();
    private Paint paint;
    private RectF mRectF;
    private float scale;

    public AlieMapView(Context context) {
        super(context);
    }

    public AlieMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public AlieMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        loadThread.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (provinces != null && provinces.size() > 0) {
            if (mRectF != null) {
                double mapWidth = mRectF.width();
                scale= (float) (getMeasuredWidth() / mapWidth);
            }
            canvas.scale(scale, scale);
            for (Province province : provinces) {
                province.drawItem(canvas, paint);
            }
        }

    }

    private Thread loadThread = new Thread() {
        @Override
        public void run() {
            InputStream inputStream = mContext.getResources().openRawResource(R.raw.china);
            provinces = new ArrayList<>();
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = null;
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(inputStream);
                Element rootElement = doc.getDocumentElement();
                NodeList items = rootElement.getElementsByTagName("path");
                float left = -1;
                float right = -1;
                float top = -1;
                float bottom = -1;
                synchronized (provinces) {
                    for (int i = 0; i < items.getLength(); i++) {
                        Element element = (Element) items.item(i);
                        String pathData = element.getAttribute("android:pathData"); // 得到目标pathData数据（当然了这里是String）
                        Path path = PathParser.createPathFromPathData(pathData);
                        RectF rect = new RectF();
                        path.computeBounds(rect, true); // 计算边界
                        left = left == -1 ? rect.left : Math.min(left, rect.left);
                        right = right == -1 ? rect.right : Math.max(right, rect.right);
                        top = top == -1 ? rect.top : Math.min(top, rect.top);
                        bottom = bottom == -1 ? rect.bottom : Math.max(bottom, rect.bottom);

                        Province province = new Province();
                        province.setPath(path);
                        provinces.add(province);
                    }
                    mRectF = new RectF(left, top, right, bottom);
                    postInvalidate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };



}
