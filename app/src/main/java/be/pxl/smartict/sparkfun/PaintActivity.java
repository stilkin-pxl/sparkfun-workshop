package be.pxl.smartict.sparkfun;

import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Byte2;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by stilkin on 22/04/16.
 */
public class PaintActivity extends AppCompatActivity {
    private final String urlFormat = SparkUtility.BOARD_URL + SparkUtility.PIX_URL;
    private GridImageView iv_paint_canvas;
    private Button btn_clear_canvas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        iv_paint_canvas = (GridImageView) findViewById(R.id.iv_paint_canvas);
        iv_paint_canvas.setOnTouchListener(new TouchHandler());

        btn_clear_canvas = (Button) findViewById(R.id.btn_clear_canvas);
        btn_clear_canvas.setOnClickListener(new ClearButtonHandler());
    }

    private class TouchHandler implements View.OnTouchListener {
        private final int MAX_DIM = 8;
        private boolean[][] touched = new boolean[MAX_DIM][MAX_DIM];

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int width = v.getWidth();
            final int height = v.getHeight();
            final int xPos = (int) event.getX();
            final int yPos = (int) event.getY();

            final int x = 8 * xPos / width;
            final int y = 8 * yPos / height;


            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (touched[x][y] == false) {
                    Log.d("ACTION_MOVE", "" + x + " " + y);
                    final int pos = 10 * x + y;
                    final String url = String.format(urlFormat, pos);
                    SparkUtility.performWebRequest(PaintActivity.this, url);
                    touched[x][y] = true;
                }
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Log.d("ACTION_DOWN", "Cells reset");
                for (int i = 0; i < MAX_DIM; i++) {
                    for (int j = 0; j < MAX_DIM; j++) {
                        touched[i][j] = false;
                    }
                }
            }
            return true;
        }
    }

    private class ClearButtonHandler implements View.OnClickListener {
        private final String clearUrl = SparkUtility.BOARD_URL + SparkUtility.CLEAR_URL;

        @Override
        public void onClick(View v) {
            SparkUtility.performWebRequest(PaintActivity.this, clearUrl);
        }
    }
}
