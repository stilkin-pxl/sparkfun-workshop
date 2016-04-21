package be.pxl.smartict.sparkfun;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Created by stilkin on 20/04/16.
 */
public class ColorFragment extends Fragment {
    // last color used by demo application
    // (note that this is not a proper way to share data between objects)
    public static int LAST_COLOR = Color.RED;

    private ImageView iv_color_preview;
    private SeekBar sb_red, sb_green, sb_blue;
    private Button btn_set_color;
    private int color = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_color, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // UI elements
        sb_red = (SeekBar) view.findViewById(R.id.sb_red);
        sb_green = (SeekBar) view.findViewById(R.id.sb_green);
        sb_blue = (SeekBar) view.findViewById(R.id.sb_blue);
        iv_color_preview = (ImageView) view.findViewById(R.id.iv_color_preview);
        btn_set_color = (Button) view.findViewById(R.id.btn_set_color);

        // handlers
        final SeekBarChangeHandler sbHandler = new SeekBarChangeHandler();
        sb_red.setOnSeekBarChangeListener(sbHandler);
        sb_green.setOnSeekBarChangeListener(sbHandler);
        sb_blue.setOnSeekBarChangeListener(sbHandler);

        btn_set_color.setOnClickListener(new ButtonHandler());
    }

    private class ButtonHandler implements View.OnClickListener {
        private final String urlFormat = SparkUtility.BOARD_URL + SparkUtility.RGB_URL;;

        @Override
        public void onClick(View v) {
            final int red = sb_red.getProgress();
            final int green = sb_green.getProgress();
            final int blue = sb_blue.getProgress();
            color = Color.argb(255, red, green, blue);

            LAST_COLOR = color;
            final String url = String.format(urlFormat, red, green, blue);
            SparkUtility.performWebRequest(getActivity(), url);
        }
    }

    private class SeekBarChangeHandler implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            final int red = sb_red.getProgress();
            final int green = sb_green.getProgress();
            final int blue = sb_blue.getProgress();
            color = Color.argb(255, red, green, blue);
            iv_color_preview.setBackgroundColor(color);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    }
}
