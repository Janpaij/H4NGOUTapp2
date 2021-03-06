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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrfif on 26/02/2018.
 */

public class AttendingAdapter extends ArrayAdapter {

    @NonNull
    @Override
    public Context getContext() {
        return super.getContext();
    }

    List list = new ArrayList();

    public AttendingAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    public void add(@Nullable AttendingGetSet object) {
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
            row = layoutInflater.inflate(R.layout.row_layout_attending_invites, parent, false);
            eventHolder = new EventHolder();
            eventHolder.event_name = (Button) row.findViewById(R.id.event_name_attending);
            eventHolder.notgoing = (Button) row.findViewById(R.id.NotGoing);
            row.setTag(eventHolder);

        }

        else
        {
            eventHolder = (EventHolder)row.getTag();
        }

        final AttendingGetSet myEvents = (AttendingGetSet) this.getItem(position);
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

        eventHolder.notgoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new MyInvites().notgoing(EventID);
                ((MyInvites)getContext()).notgoing(EventID);
                Intent intent = new Intent(getContext(), Home.class);
                getContext().startActivity(intent);
            }
        });
        return row;
    }

    static class EventHolder
    {
        Button event_name, notgoing;
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


