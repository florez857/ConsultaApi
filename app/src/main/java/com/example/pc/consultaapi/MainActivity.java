package com.example.pc.consultaapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.pc.consultaapi.R.id.imageView;

public class MainActivity extends AppCompatActivity {


    public RequestQueue requestQueue;
    public TextView results;
    public ImageView imagen;

    //String JsonURL = "https://raw.githubusercontent.com/ianbar20/JSON-Volley-Tutorial/master/Example-JSON-Files/Example-Object.JSON";
    String JsonURL = "http://gateway.marvel.com:80/v1/public/comics/3?ts=1&apikey=0edf0dea162612b738bd9da490aa4a18&hash=8df4c36e0ab8a326763ee2fecd804b39";
     String imagenurl= "http://i.annihil.us/u/prod/marvel/i/mg/1/30/56538fd257915.jpg?ts=1&apikey=0edf0dea162612b738bd9da490aa4a18&hash=8df4c36e0ab8a326763ee2fecd804b39";
    static String KEY="ts=1&apikey=0edf0dea162612b738bd9da490aa4a18&hash=8df4c36e0ab8a326763ee2fecd804b39";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         imagen=(ImageView)findViewById(imageView);



        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        results = (TextView) findViewById(R.id.texto);



        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, JsonURL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        String data="",id="";
                        try {

                             JSONObject obj = response.getJSONObject("data");

                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            //JSONArray   array=  obj.getJSONArray("results");
                            //array.get(1).toString();

                            JSONArray array=obj.getJSONArray("results");

                            for(int i=0;i<array.length();i++){

                                      JSONObject obj1= (JSONObject)array.get(i);
                                      id=obj1.getString("id");
                                      Log.d("datos de objeto",obj1.toString());
                                      JSONArray img =obj1.getJSONArray("images");
                                      JSONObject thum=(JSONObject) img.get(0);
                                       String path= thum.getString("path");
                                       String extension= thum.getString("extension");
                                       String url=path+"."+extension+"?"+KEY;
                                       cargarImagen(url);

                            }



                            Log.d("datos de jsno", obj.toString());

                            String color = obj.getString("limit");
                            String desc = obj.getString("total");
                            String count=obj.getString("offset");



                            // Adds strings from object to the "data" string
                            data += "id Name: " + color +
                                    "title : " + desc+ "offset "+count+"id "+id + "tamaño de array"+array.length() ;  ; //"tamaño de array"+array.length()+"datos"+array.get(1).toString();;

                            // Adds the data string to the TextView "results"
                            results.setText(data);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }

     public void cargarImagen(String imagenurl){

         Glide.with(this).load(imagenurl)
                 .animate(android.R.anim.fade_in)

                 .into(imagen);
     }
}


