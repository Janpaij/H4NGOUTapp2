package jr.h4ngout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MyEventsList extends AppCompatActivity {

    private TextView theDate;
    //String JSON_STRING;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    MyEventsAdapter eventsAdapter;
    ListView listView;
    String dateInJulian;
    String selectedUsers;
    String idToken;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = "http://well-prepared-socie.000webhostapp.com/myEventsDelete.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events_list);
        if (android.os.Build.VERSION.SDK_INT > 15) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listView = (ListView)findViewById(R.id.listview);

        eventsAdapter = new MyEventsAdapter(this, R.layout.row_layout_my_events);
        listView.setAdapter(eventsAdapter);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(MyEventsList.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(MyEventsList.this);

        theDate = (TextView)findViewById(R.id.date);

        Intent incomingIntent = getIntent();

        //Get user IDtoken to pass to PHP script

        GoogleSignInAccount accountSignedIn = GoogleSignIn.getLastSignedInAccount(this);
        if(accountSignedIn != null)
        {
            idToken = accountSignedIn.getIdToken();
        }

        new EventsListBackground().execute();



    }
    
    public void deleteEvent(final String EventID)
    {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Deleting Your Event");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(getApplicationContext(), ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getApplicationContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("EventID", EventID);



                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MyEventsList.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    class EventsListBackground extends AsyncTask<Void, Void, String>
    {
        Context ctx;

        //To pass parameters to this class
        //EventsListBackground(Context ctx)
        //{
        //    this.ctx = ctx;
        //}
        String json_url;
        String JSON_STRING;


        @Override
        protected void onPreExecute() {
            json_url = "http://well-prepared-socie.000webhostapp.com/myEvents.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {

                URL url = new URL(json_url);
                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                OutputStream OS = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter (new OutputStreamWriter(OS, "UTF-8"));
                //String data = URLEncoder.encode("IDToken", "UTF-8")+"="+URLEncoder.encode(idToken, "UTF-8")+"&"+URLEncoder.encode("favouriteCategories", "UTF-8")+"="+URLEncoder.encode(selectedUsers, "UTF-8");
                String data = URLEncoder.encode("IDToken", "UTF-8")+"="+URLEncoder.encode(idToken, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpsURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                IS.close();
                httpsURLConnection.disconnect();
                return stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } //to here

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json_string = result;

            if(json_string==null)
            {
                Toast.makeText(getApplicationContext(), "First get JSON", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("server_response");

                    int count = 0;
                    String title, date, location, eventID;

                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        eventID = JO.getString("eventID");
                        title = JO.getString("Title");
                        date = JO.getString("Date");
                        location = JO.getString("LocationName");

                        MyEventsGetSet events = new MyEventsGetSet(title, date, location, eventID);
                        eventsAdapter.add(events);

                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

