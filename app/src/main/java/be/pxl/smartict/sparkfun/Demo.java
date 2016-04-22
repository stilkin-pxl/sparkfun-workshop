package be.pxl.smartict.sparkfun;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by stilkin on 21/04/16.
 */
public class Demo {


    private Context getContext() {
        return null;
    }

    private void performRequest() {

        final RequestQueue queue = Volley.newRequestQueue(getContext());

        final String url = "http://www.google.com";

        final StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        // TODO: do something with the response
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        // TODO: notify user of error
                    }
                });

        queue.add(stringRequest); // GO!

    }


}
