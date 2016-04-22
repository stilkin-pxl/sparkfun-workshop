package be.pxl.smartict.sparkfun;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by stilkin on 22/04/16.
 */
public class PaintFragment extends Fragment {
    private Button btn_go_to_paint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_paint, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_go_to_paint = (Button) view.findViewById(R.id.btn_go_to_paint);
        btn_go_to_paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent navInt = new Intent(getActivity(), PaintActivity.class);
                getActivity().startActivity(navInt);
            }
        });
    }
}
