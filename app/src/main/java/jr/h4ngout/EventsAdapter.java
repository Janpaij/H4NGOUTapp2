package jr.h4ngout;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrfif on 05/02/2018.
 */

public class EventsAdapter extends ArrayAdapter {

    @NonNull
    @Override
    public Context getContext() {
        return super.getContext();
    }

    List list = new ArrayList();

    public EventsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    public void add(@Nullable Events object) {
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
            row = layoutInflater.inflate(R.layout.row_layout_events_availability, parent, false);
            eventHolder = new EventHolder();
            eventHolder.tx_title = (TextView)row.findViewById(R.id.tx_title);
            eventHolder.tx_start = (TextView)row.findViewById(R.id.tx_start);
            eventHolder.tx_end = (TextView)row.findViewById(R.id.tx_end);
            eventHolder.hello = (ImageButton) row.findViewById(R.id.hello);//
            row.setTag(eventHolder);

        }

        else
        {
            eventHolder = (EventHolder)row.getTag();
        }

        final Events events = (Events) this.getItem(position);
        eventHolder.tx_title.setText(events.getTitle());
        //eventHolder.tx_start.setText(events.getStart());
        //eventHolder.tx_end.setText(events.getEnd());
        eventHolder.tx_start.setText(getTime(events.getStart()));
        eventHolder.tx_end.setText(getTime(events.getEnd()));

        eventHolder.tx_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), events.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), DatePicker.class);
                getContext().startActivity(intent);
            }
        });

        eventHolder.hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Home.class);
                getContext().startActivity(intent);
            }
        });



        return row;
    }

    static class EventHolder
    {
        TextView tx_title, tx_start, tx_end;
        ImageButton hello;//
    }

    private String getTime(String t)
    {
        int timeInt = Integer.parseInt(t);
        int hours = timeInt / 60; //since both are ints, you get an int
        int minutes = timeInt % 60;

        String time = hours+":"+minutes;
        return time;
    }
}
