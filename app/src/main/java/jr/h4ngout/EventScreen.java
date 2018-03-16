package jr.h4ngout;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class EventScreen extends AppCompatActivity {

    private TextView theDate;
    //String JSON_STRING;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String json_string2;
    JSONObject jsonObject2;
    JSONArray jsonArray2;
    AttendeesAdapter eventsAdapter;
    ListView listView;


    String eventID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_screen);
        // Get the Intent that started this activity
        Intent intent = getIntent();
        eventID = intent.getStringExtra("EventID");
        if (android.os.Build.VERSION.SDK_INT > 15) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listView = (ListView)findViewById(R.id.listview);

        eventsAdapter = new AttendeesAdapter(this, R.layout.row_layout_get_attendees);
        listView.setAdapter(eventsAdapter);

        new EventScreenBackground().execute();
        new AttendeesBackground().execute();
    }

    class EventScreenBackground extends AsyncTask<Void, Void, String>
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
            json_url = "http://well-prepared-socie.000webhostapp.com/EventsScreen.php";
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
                String data = URLEncoder.encode("EventID", "UTF-8")+"="+URLEncoder.encode(eventID, "UTF-8");
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
                    Toast.makeText(getApplicationContext(), json_string, Toast.LENGTH_SHORT).show();
                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("server_response");

                    int count = 0;
                    String title="", creatorName="", description="", date="", startTime="", endTime="", coordinates="", locationName="", locationAddress="";
                    String displayName = "";

                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        title = JO.getString("Title");
                        creatorName = JO.getString("DisplayName");
                        description = JO.getString("Description");
                        date = JO.getString("Date");
                        startTime = JO.getString("startTime");
                        endTime = JO.getString("endTime");
                        coordinates = JO.getString("Coordinates");
                        locationName = JO.getString("locationName");
                        locationAddress = JO.getString("locationAddress");

                        count++;
                    }
                    setUI(title, creatorName, description, date, startTime, endTime, coordinates, locationName, locationAddress);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class AttendeesBackground extends AsyncTask<Void, Void, String>
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
            json_url = "http://well-prepared-socie.000webhostapp.com/GetAttendees.php";
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
                String data = URLEncoder.encode("EventID", "UTF-8")+"="+URLEncoder.encode(eventID, "UTF-8");
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
                    Toast.makeText(getApplicationContext(), json_string2, Toast.LENGTH_SHORT).show();
                    jsonObject2 = new JSONObject(json_string2);
                    jsonArray2 = jsonObject2.getJSONArray("server_response");

                    int count = 0;

                    String displayName = "";

                    while (count < jsonArray2.length()) {
                        JSONObject JO = jsonArray2.getJSONObject(count);
                        displayName = JO.getString("displayName");

                        Attendees events = new Attendees(displayName);
                        eventsAdapter.add(events);

                        count++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    /** Called when the user taps on Group Chat button */
    public void groupChat(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, GroupChat.class);
        startActivity(intent);

    }

    public void setUI(String title, String creatorName, String description, String date, String startTime, String endTime, String coordinates, String locationName, String locationAddress)
    {
        TextView titletx;
        TextView creator;
        TextView descriptiontx;
        TextView datetx;
        TextView starttx;
        TextView endtx;
        TextView locationNametx;
        TextView locationAddresstx;


        titletx = (TextView)findViewById(R.id.Title);
        creator = (TextView)findViewById(R.id.Creator);
        descriptiontx = (TextView)findViewById(R.id.Description);
        datetx = (TextView)findViewById(R.id.date);
        starttx = (TextView)findViewById(R.id.tx_start);
        endtx = (TextView)findViewById(R.id.tx_end);
        locationNametx = (TextView)findViewById(R.id.locationName);
        locationAddresstx = (TextView)findViewById(R.id.locationAddress);

        titletx.setText(title);
        creator.setText(creatorName);
        descriptiontx.setText(description);
        datetx.setText(date);
        starttx.setText(startTime);
        endtx.setText(endTime);
        locationNametx.setText(locationName);
        locationAddresstx.setText(locationAddress);

        //map stuff using coordinates

    }


}
