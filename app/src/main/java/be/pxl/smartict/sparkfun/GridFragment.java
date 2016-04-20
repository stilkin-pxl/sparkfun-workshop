package be.pxl.smartict.sparkfun;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by stilkin on 20/04/16.
 */
public class GridFragment extends Fragment {
    private String urlFormat = SparkUtility.BOARD_URL + SparkUtility.PIX_URL;
    private GridView gv_pixel_grid;
    private int MAX_DIM = 8;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gv_pixel_grid = (GridView) view.findViewById(R.id.gv_pixel_grid);
        gv_pixel_grid.setAdapter(new ImageAdapter(getActivity()));

        gv_pixel_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                final String url = String.format(urlFormat, id);
                SparkUtility.performWebRequest(getActivity(), url);
                // TODO: toggle color

            //    Toast.makeText(getActivity(), id + " " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context ctx) {
            context = ctx;
        }

        public int getCount() {
            return (MAX_DIM * MAX_DIM);
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            // convert to XY coords
            final int y = position / MAX_DIM;
            final int x = position - (MAX_DIM * y);

            return (10 * x + y);
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(60, 60));
                imageView.setPadding(10, 10, 10, 10);
                imageView.setBackgroundColor(Color.BLACK);
            } else {
                imageView = (ImageView) convertView;
            }
            return imageView;
        }
    }
}