package be.pxl.smartict.sparkfun;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stilkin on 20/04/16.
 */
public class GridFragment extends Fragment {
    private String urlFormat = SparkUtility.BOARD_URL + SparkUtility.PIX_URL;
    private GridView gv_pixel_grid;
    private GridAdapter ga_pixel_grid;
    private Button btn_clear_grid;
    private int MAX_DIM = 8;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_clear_grid = (Button) view.findViewById(R.id.btn_clear_grid);
        gv_pixel_grid = (GridView) view.findViewById(R.id.gv_pixel_grid);

        ga_pixel_grid = new GridAdapter(getActivity());
        gv_pixel_grid.setAdapter(ga_pixel_grid);
        gv_pixel_grid.setOnItemClickListener(new GridViewHandler());

        btn_clear_grid.setOnClickListener(new ClearButtonHandler());
    }

    private class GridViewHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final String url = String.format(urlFormat, id);
            SparkUtility.performWebRequest(getActivity(), url);
            view.setBackgroundColor(SparkUtility.LAST_COLOR);
        }
    }

    private class ClearButtonHandler implements View.OnClickListener {
        private String clearUrl = SparkUtility.BOARD_URL + SparkUtility.CLEAR_URL;

        @Override
        public void onClick(View v) {
            SparkUtility.performWebRequest(getActivity(), clearUrl);
            ImageView imageView;
            for(int iv = 0; iv < ga_pixel_grid.getCount(); iv++) {
                imageView = (ImageView) ga_pixel_grid.getItem(iv);
                imageView.setBackgroundColor(Color.BLACK);
            }
        }
    }

    private class GridAdapter extends BaseAdapter {
        private List<ImageView> imageViews;
        private Context context;

        public GridAdapter(Context ctx) {
            context = ctx;
            imageViews = new ArrayList<ImageView>();
            // add 64 views
            for (int i = 0; i < getCount(); i++) {
                imageViews.add(getNewView());
            }
        }

        private ImageView getNewView() {
            final ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(50, 50));
            imageView.setPadding(10, 10, 10, 10);
            imageView.setBackgroundColor(Color.BLACK);
            return imageView;
        }

        public int getCount() {
            return (MAX_DIM * MAX_DIM);
        }

        public Object getItem(int position) {
            return imageViews.get(position);
        }

        public int getXYfromPosition(int position) {
            // convert to XY coords
            final int y = position / MAX_DIM;
            final int x = position - (MAX_DIM * y);

            return (10 * x + y);
        }

        public long getItemId(int position) {
            return getXYfromPosition(position);
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = imageViews.get(position);
            if (imageView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(50, 50));
                imageView.setPadding(10, 10, 10, 10);
                imageView.setBackgroundColor(Color.RED);
            }
            return imageView;
        }
    }
}