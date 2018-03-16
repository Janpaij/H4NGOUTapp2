package jr.h4ngout;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by jrfif on 05/02/2018.
 */

public class BackgroundTask extends AsyncTask<String, Void, String> {

    Context ctx;

    BackgroundTask(Context ctx)
    {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String instances_url = "http://well-prepared-socie.000webhostapp.com/instances.php";
        String getJSON_url = "http://well-prepared-socie.000webhostapp.com/json_get_events.php";
        String method = params[0];
        if(method.equals("instances"))
        {
            String eventTitle = params[1];
            String startDay = params[2];
            String startMinute = params[3];
            String endDay = params[4];
            String endMinute = params[5];
            String token = params[6];


            try {

                URL url = new URL(instances_url);
                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                OutputStream OS = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter (new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("eventTitle", "UTF-8") +"="+URLEncoder.encode(eventTitle, "UTF-8")+"&"+
                        URLEncoder.encode("startDay", "UTF-8") +"="+URLEncoder.encode(startDay, "UTF-8")+"&"+
                        URLEncoder.encode("startMinute", "UTF-8") +"="+URLEncoder.encode(startMinute, "UTF-8")+"&"+
                        URLEncoder.encode("endDay", "UTF-8") +"="+URLEncoder.encode(endDay, "UTF-8")+"&"+
                        URLEncoder.encode("endMinute", "UTF-8") +"="+URLEncoder.encode(endMinute, "UTF-8")+"&"+
                        URLEncoder.encode("IDToken", "UTF-8") +"="+URLEncoder.encode(token, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpsURLConnection.getInputStream();
                IS.close();

                return "Calendar sync success...";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(method.equals("getJSON"))
        {
            String chosenDayJulian = params[1];

            try {

                URL url = new URL(getJSON_url);
                HttpURLConnection httpsURLConnection = (HttpURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                OutputStream OS = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter (new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("chosenDayJulian", "UTF-8") +"="+URLEncoder.encode(chosenDayJulian, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpsURLConnection.getInputStream();
                IS.close();

                return "Julian success...";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
    }
}


