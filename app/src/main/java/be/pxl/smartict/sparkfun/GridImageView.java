package be.pxl.smartict.sparkfun;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by stilkin on 22/04/16.
 */
public class GridImageView extends ImageView {
    public static final int MAX_FIELDS = 8;

    public GridImageView(Context context) {
        super(context);
    }

    public GridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        final int width = canvas.getWidth();
        final int height = canvas.getHeight();
        final int xPart = width / MAX_FIELDS;
        final int yPart = height / MAX_FIELDS;
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);

        for (int i = 0; i < MAX_FIELDS; i++) {
            canvas.drawLine(i * xPart, 0, i * xPart, height, paint);
            canvas.drawLine(0, i * yPart, width, i * yPart, paint);
        }
    }
}
