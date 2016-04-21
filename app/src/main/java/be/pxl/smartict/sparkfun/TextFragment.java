package be.pxl.smartict.sparkfun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by stilkin on 21/04/16.
 */
public class TextFragment extends Fragment {
    private EditText et_text_input;
    private Button btn_send_text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_text_input = (EditText) view.findViewById(R.id.et_text_input);
        btn_send_text = (Button) view.findViewById(R.id.btn_send_text);
        btn_send_text.setOnClickListener(new SendTextHandler());
    }

    private class SendTextHandler implements View.OnClickListener {
        private final String urlFormat = SparkUtility.BOARD_URL + SparkUtility.MSG_URL;

        @Override
        public void onClick(View v) {
            String message = et_text_input.getText().toString();
            message = SparkUtility.encodeAsUrl(message);
            final String requestUrl = String.format(urlFormat, message);
            SparkUtility.performWebRequest(getActivity(), requestUrl);
        }
    }
}
