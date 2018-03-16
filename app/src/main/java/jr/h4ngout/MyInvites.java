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

public class MyInvites extends AppCompatActivity {

    private TextView theDate;
    //String JSON_STRING;
    String json_string1;
    String json_string2;
    String json_string3;
    JSONObject jsonObject1;
    JSONObject jsonObject2;
    JSONObject jsonObject3;
    JSONArray jsonArray1;
    JSONArray jsonArray2;
    JSONArray jsonArray3;
    PendingAdapter eventsAdapter1;
    AttendingAdapter eventsAdapter2;
    NotAttendingAdapter eventsAdapter3;
    ListView listView1;
    ListView listView2;
    ListView listView3;
    String dateInJulian;
    String selectedUsers;
    String idToken;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue1;
    // Creating Progress dialog.
    ProgressDialog progressDialog1;

    // Storing server url into String variable.
    String HttpUrl1 = "http://well-prepared-socie.000webhostapp.com/going.php";

    // Creating Volley RequestQueue.
    RequestQueue requestQueue2;
    // Creating Progress dialog.
    ProgressDialog progressDialog2;

    // Storing server url into String variable.
    String HttpUrl2 = "http://well-prepared-socie.000webhostapp.com/notgoing.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invites);
        // Get the Intent that started this activity
        Intent intent = getIntent();

        if (android.os.Build.VERSION.SDK_INT > 15) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listView1 = (ListView)findViewById(R.id.listviewpending);
        listView2 = (ListView)findViewById(R.id.listviewattending);
        listView3 = (ListView)findViewById(R.id.listviewnotattending);

        eventsAdapter1 = new PendingAdapter(this, R.layout.row_layout_pending_invites);
        listView1.setAdapter(eventsAdapter1);

        eventsAdapter2 = new AttendingAdapter(this, R.layout.row_layout_attending_invites);
        listView2.setAdapter(eventsAdapter2);

        eventsAdapter3 = new NotAttendingAdapter(this, R.layout.row_layout_not_attending_invites);
        listView3.setAdapter(eventsAdapter3);

        // Creating Volley newRequestQueue .
        requestQueue1 = Volley.newRequestQueue(MyInvites.this);

        // Assigning Activity this to progress dialog.
        progressDialog1 = new ProgressDialog(MyInvites.this);

        // Creating Volley newRequestQueue .
        requestQueue2 = Volley.newRequestQueue(MyInvites.this);

        // Assigning Activity this to progress dialog.
        progressDialog2 = new ProgressDialog(MyInvites.this);

        theDate = (TextView)findViewById(R.id.date);

        Intent incomingIntent = getIntent();

        //Get user IDtoken to pass to PHP script

        GoogleSignInAccount accountSignedIn = GoogleSignIn.getLastSignedInAccount(this);
        if(accountSignedIn != null)
        {
            idToken = accountSignedIn.getIdToken();
        }

        new MyInvites.PendingBackground().execute();
        new MyInvites.AttendingBackground().execute();
        new MyInvites.NotAttendingBackground().execute();
    }

    public void going(final String EventID1)
    {
        // Showing progress dialog at user registration time.
        progressDialog1.setMessage("Please Wait, We are Deleting Your Event");
        progressDialog1.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog1.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(getApplicationContext(), ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog1.dismiss();

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
                params.put("EventID", EventID1);
                params.put("IDToken", idToken);



                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MyInvites.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    public void notgoing(final String EventID2)
    {
        // Showing progress dialog at user registration time.
        progressDialog2.setMessage("Please Wait, We are Deleting Your Event");
        progressDialog2.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog2.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(getApplicationContext(), ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog2.dismiss();

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
                params.put("EventID", EventID2);
                params.put("IDToken", idToken);



                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(MyInvites.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    class PendingBackground extends AsyncTask<Void, Void, String>
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
            json_url = "http://well-prepared-socie.000webhostapp.com/pending.php";
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
            json_string1 = result;

            if(json_string1==null)
            {
                Toast.makeText(getApplicationContext(), "First get JSON", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    Toast.makeText(getApplicationContext(), json_string1, Toast.LENGTH_SHORT).show();
                    jsonObject1 = new JSONObject(json_string1);
                    jsonArray1 = jsonObject1.getJSONArray("server_response");

                    int count = 0;
                    String title, date, location, eventID;

                    while (count < jsonArray1.length()) {
                        JSONObject JO = jsonArray1.getJSONObject(count);
                        eventID = JO.getString("eventID");
                        title = JO.getString("Title");
                        //date = JO.getString("Date");
                        //location = JO.getString("LocationName");
                        date = "date";
                        location = "location";

                        PendingGetSet events = new PendingGetSet(title, date, location, eventID);
                        eventsAdapter1.add(events);

                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class AttendingBackground extends AsyncTask<Void, Void, String>
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
            json_url = "http://well-prepared-socie.000webhostapp.com/attending.php";
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
            json_string2 = result;

            if(json_string2==null)
            {
                Toast.makeText(getApplicationContext(), "First get JSON", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    jsonObject2 = new JSONObject(json_string2);
                    jsonArray2 = jsonObject2.getJSONArray("server_response");

                    int count = 0;
                    String title, date, location, eventID;

                    while (count < jsonArray2.length()) {
                        JSONObject JO = jsonArray2.getJSONObject(count);
                        eventID = JO.getString("eventID");
                        title = JO.getString("Title");
                        //date = JO.getString("Date");
                        //location = JO.getString("LocationName");
                        date = "date";
                        location = "location";

                        AttendingGetSet events = new AttendingGetSet(title, date, location, eventID);
                        eventsAdapter2.add(events);

                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class NotAttendingBackground extends AsyncTask<Void, Void, String>
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
            json_url = "http://well-prepared-socie.000webhostapp.com/notattending.php";
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
            json_string3 = result;

            if(json_string3==null)
            {
                Toast.makeText(getApplicationContext(), "First get JSON", Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    jsonObject3 = new JSONObject(json_string3);
                    jsonArray3 = jsonObject3.getJSONArray("server_response");

                    int count = 0;
                    String title, date, location, eventID;

                    while (count < jsonArray3.length()) {
                        JSONObject JO = jsonArray3.getJSONObject(count);
                        eventID = JO.getString("eventID");
                        title = JO.getString("Title");
                        //date = JO.getString("Date");
                        //location = JO.getString("LocationName");
                        date = "date";
                        location = "location";

                        NotAttendingGetSet events = new NotAttendingGetSet(title, date, location, eventID);
                        eventsAdapter3.add(events);

                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
