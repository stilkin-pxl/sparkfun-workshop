package be.pxl.smartict.sparkfun;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;

/**
 * Created by stilkin in april 2016.
 * Uses com.android.volley library -> add!
 */
public class SparkUtility extends Application{
    public static final int TIMEOUT_MS = 15000;
    public static final int RETRIES = 0;
    public static final String BOARD_URL = "http://192.168.4.1/";
    public static final String MSG_URL = "msg?msg=%s";
    public static final String RGB_URL = "rgb?rgb=%03d/%03d/%03d";
    public static final String PIX_URL = "pix?pix=%02d";
    public static final String CLEAR_URL = "clear?clear";

    private static ArrayBlockingQueue<String> requestUrls = new ArrayBlockingQueue<String>(64);
    private static boolean ready = true;
    private static RequestQueue requestQueue; // Volley request queue
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        initialize(this);
    }

    private static void initialize(final Context context) {
        SparkUtility.context = context;
        if (requestQueue == null) { // lazy initialization
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
            requestQueue.getCache().initialize();
        }
        final Thread queue = new Thread(new VolleyTask());
        queue.start();
    }

    /**
     * Make a web call to the SparkFun board
     *
     * @param context    pass a context, e.g. the activity or application object
     * @param requestUrl pass a valid url, e.g. BOARD_URL + CLEAR_URL
     *                   For some of the URLs you will need to fill in some fields
     */
    public static void performWebRequest(final Context context, final String requestUrl) {
        requestUrls.add(requestUrl);
    }

    private static class VolleyTask implements Runnable {
        @Override
        public void run() {
            while(true) {
                if (requestUrls.size() > 0 && ready == true){
                    final String url = requestUrls.poll();
                    executeRequest(url);
                    ready = false;
                }
            }
        }
    }

    private static void executeRequest(final String requestUrl) {
        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                requestUrl,
                new VolleyResponseListener(context),
                new DefaultErrorListener(context));
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        TIMEOUT_MS,
                        RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    /**
     * This class is called after a web request is completed.
     * It provides you with a pop-up indicating the result
     */
    private static class VolleyResponseListener implements Response.Listener<String> {
        private final Context context;

        public VolleyResponseListener(final Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(final String response) {
            Toast.makeText(context, "Sent successfully. \n" + response, Toast.LENGTH_SHORT).show();
            Log.d("onResponse", response);
            ready = true;
        }
    }

    /**
     * This class is called if the web request fails.
     * It provides you with a pop-up indicating a problem
     */
    public static class DefaultErrorListener implements Response.ErrorListener {
        private final Context context;

        public DefaultErrorListener(Context context) {
            this.context = context;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            final String msg = "Could not get a response. \nAre you connected to the access point?";
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Log.e("ErrorResponse", "" + error);
            ready = true;
        }
    }

    /**
     * Encode a string in URL encoding (e.g. with %20 for spaces).
     *
     * @param text
     * @return Will return encoded string on success, or unchanged string on failure.
     */
    public static String encodeAsUrl(final String text) {
        String encodedText = text;
        try {
            encodedText = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
        return encodedText;
    }
}