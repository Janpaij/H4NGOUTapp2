package jr.h4ngout;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrfif on 22/02/2018.
 */

public class MyEventsAdapter extends ArrayAdapter {

    @NonNull
    @Override
    public Context getContext() {
        return super.getContext();
    }

    List list = new ArrayList();

    public MyEventsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    public void add(@Nullable MyEventsGetSet object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row = convertView;
        EventHolder eventHolder;
        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout_my_events, parent, false);
            eventHolder = new EventHolder();
            eventHolder.event_name = (Button) row.findViewById(R.id.event_name);
            eventHolder.edit = (ImageButton) row.findViewById(R.id.edit);
            eventHolder.delete = (ImageButton) row.findViewById(R.id.delete);
            row.setTag(eventHolder);

        }

        else
        {
            eventHolder = (EventHolder)row.getTag();
        }

        final MyEventsGetSet myEvents = (MyEventsGetSet) this.getItem(position);
        eventHolder.event_name.setText(myEvents.getTitle());
        final String EventID = myEvents.getEventID();



        eventHolder.event_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EventScreen.class);
                intent.putExtra("EventID", EventID );
                getContext().startActivity(intent);
            }
        });

        eventHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateEvent2.class);
                intent.putExtra("method", "Edit");
                intent.putExtra("EventID", EventID);
                getContext().startActivity(intent);
            }
        });

        eventHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MyEventsList eventsList = new MyEventsList();
                //eventsList.deleteEvent(EventID);

                ((MyEventsList)getContext()).deleteEvent(EventID);

                Intent intent = new Intent(getContext(), Home.class);
                getContext().startActivity(intent);
            }
        });



        return row;
    }

    static class EventHolder
    {
        Button event_name;
        ImageButton edit, delete;

    }

    private String getTime(String t)
    {
        int timeInt = Integer.parseInt(t);
        int hours = timeInt / 60; //since both are ints, you get an int
        int minutes = timeInt % 60;

        String time = hours+":"+minutes;
        return time;
    }



    /*public void deleteEvent(final String EventID)
    {

        // Creating Volley RequestQueue.
        RequestQueue requestQueue;
        // Creating Progress dialog.
        final ProgressDialog progressDialog;

        // Storing server url into String variable.
        String HttpUrl = "http://well-prepared-socie.000webhostapp.com/myEventsDelete.php";

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getContext());

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(getContext());




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
                        Toast.makeText(getContext(), ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(getContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    } */
}

