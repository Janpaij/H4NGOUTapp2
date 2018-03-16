package jr.h4ngout;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EventsList extends AppCompatActivity {

    private TextView theDate;
    //String JSON_STRING;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    EventsAdapter eventsAdapter;
    ListView listView;
    String dateInJulian;
    String selectedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        if (android.os.Build.VERSION.SDK_INT > 15) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listView = (ListView)findViewById(R.id.listview);

        eventsAdapter = new EventsAdapter(this, R.layout.row_layout_events_availability);
        listView.setAdapter(eventsAdapter);



        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Events eventsel = (Events) eventsAdapter.getItem(i);
                String eventTitle = eventsel.getTitle();
                String eventStart = eventsel.getStart();
                String eventEnd = eventsel.getEnd();
                Toast.makeText(getApplicationContext(), eventTitle+" "+eventStart+" "+eventEnd, Toast.LENGTH_SHORT).show();

            }
        }); */

        theDate = (TextView)findViewById(R.id.date);

        Intent incomingIntent = getIntent();
        //String date = incomingIntent.getStringExtra("date");
        Integer day = incomingIntent.getIntExtra("day", 1);
        Integer month = incomingIntent.getIntExtra("month", 1);
        Integer year = incomingIntent.getIntExtra("year", 1);
        selectedUsers = incomingIntent.getStringExtra("selectedUsers");
        String date = day + "." + month + "." + year;
        theDate.setText(date);
        long x = Math.round(toJulian( new int[] {year, month, day } ));//
        dateInJulian = String.valueOf(x);//
        //String dateInJulian = String.valueOf(toJulian( new int[] {year, month, day } ));

        //Post to PHP
        //String method = "variables";
        //VariablesPostBackground variablesPostBackground = new VariablesPostBackground(this);
        //variablesPostBackground.execute(method, dateInJulian);

        //this one
        new EventsListBackground().execute();

        /*if(json_string==null)
        {
            Toast.makeText(getApplicationContext(), "First get JSON", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                jsonObject = new JSONObject(json_string);
                jsonArray = jsonObject.getJSONArray("server_response");

                int count = 0;
                String title, start, end;

                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    title = JO.getString("title");
                    start = JO.getString("startMinute");
                    end = JO.getString("endMinute");

                    Events events = new Events(title, start, end);
                    eventsAdapter.add(events);

                    count++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } */
    }

    /*public void getJSON()
    {
        new EventsListBackground().execute();
        //json_string = ...

    }*/
    /*public void parseJSON(View view)
    {
        if(json_string==null)
        {
            Toast.makeText(getApplicationContext(), "First get JSON", Toast.LENGTH_SHORT).show();
        }
        else
        {

        }
    }*/

    public static double toJulian(int[] ymd) {

        // Gregorian Calendar adopted Oct. 15, 1582 (2299161)
        int JGREG= 15 + 31*(10+12*1582);
        double HALFSECOND = 0.5;

        int year=ymd[0];
        int month=ymd[1]; // jan=1, feb=2,...
        int day=ymd[2];
        int julianYear = year;
        if (year < 0) julianYear++;
        int julianMonth = month;
        if (month > 2) {
            julianMonth++;
        }
        else {
            julianYear--;
            julianMonth += 13;
        }

        double julian = (java.lang.Math.floor(365.25 * julianYear)
                + java.lang.Math.floor(30.6001*julianMonth) + day + 1720995.0);
        if (day + 31 * (month + 12 * year) >= JGREG) {
            // change over to Gregorian calendar
            int ja = (int)(0.01 * julianYear);
            julian += 2 - ja + (0.25 * ja);
        }
        return java.lang.Math.floor(julian);
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
        //String json_string;


        @Override
        protected void onPreExecute() {
            json_url = "http://well-prepared-socie.000webhostapp.com/json_get_events.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            //from here
            /*try {

                URL url = new URL(json_url);
                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                OutputStream OS = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter (new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("chosenDayJulian", "UTF-8")+"="+URLEncoder.encode(dateInJulian, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpsURLConnection.getInputStream();
                IS.close();
                httpsURLConnection.disconnect();//
                //return "Variable post success";
                //return chosenDayJulian;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } //to here


            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } */
            //Pasted from notepad++
            try {

                URL url = new URL(json_url);
                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                OutputStream OS = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter (new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("chosenDayJulian", "UTF-8")+"="+URLEncoder.encode(dateInJulian, "UTF-8")+"&"+URLEncoder.encode("favouriteCategories", "UTF-8")+"="+URLEncoder.encode(selectedUsers, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpsURLConnection.getInputStream();
                //IS.close();
                //httpsURLConnection.disconnect();//
                //return "Variable post success";
                //return chosenDayJulian;

                //URL url = new URL(json_url);
                //HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                //InputStream inputStream = httpURLConnection.getInputStream();
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
                    String title, start, end;

                    while (count < jsonArray.length()) {
                        JSONObject JO = jsonArray.getJSONObject(count);
                        title = JO.getString("title");
                        start = JO.getString("startMinute");
                        end = JO.getString("endMinute");

                        Events events = new Events(title, start, end);
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
