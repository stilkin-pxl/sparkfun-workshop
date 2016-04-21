package be.pxl.smartict.sparkfun;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by stilkin on 20/04/16.
 */
public class SparkUtility {
    public static final String BOARD_URL = "http://192.168.4.1/";
    public static final String MSG_URL = "msg?msg=%s";
    public static final String RGB_URL = "rgb?rgb=%03d/%03d/%03d";
    public static final String PIX_URL = "pix?pix=%02d";
    public static final String CLEAR_URL = "clear?clear";

    public static int LAST_COLOR = Color.RED;

    private static RequestQueue requestQueue;

    private static RequestQueue getRequestQueue(final Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
            requestQueue.getCache().initialize();
        }
        return requestQueue;
    }

    public static void performWebRequest(final Context context, final String requestUrl) {
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                requestUrl,
                new VolleyResponseListener(context),
                new DefaultErrorListener(context));
        getRequestQueue(context).add(stringRequest);
    }

    private static class VolleyResponseListener implements Response.Listener<String> {
        private final Context context;

        public VolleyResponseListener(final Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(final String response) {
            Toast.makeText(context, "Sent successfully. \n" + response, Toast.LENGTH_SHORT).show();
            Log.d("onResponse", response);
        }
    }


    public static class DefaultErrorListener implements Response.ErrorListener {
        private final Context context;

        public DefaultErrorListener(Context context) {
            this.context = context;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(context, "Could not get fresh data. \nAre you online?", Toast.LENGTH_SHORT).show();
            Log.e("ErrorResponse", "" + error);
        }
    }

    /**
     * Encode a string in URL encoding (e.g. with %20 for spaces).
     * @param text
     * @return Will return encoded string on success, or unchanged string on failure.
     */
    public static String encodeAsUrl (final String text) {
        String encodedText = text;
        try {
            encodedText = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        return encodedText;
    }
}
