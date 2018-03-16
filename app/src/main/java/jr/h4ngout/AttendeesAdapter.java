package jr.h4ngout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrfif on 23/02/2018.
 */

public class AttendeesAdapter extends ArrayAdapter {

    @NonNull
    @Override
    public Context getContext() {
        return super.getContext();
    }

    List list = new ArrayList();

    public AttendeesAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }


    public void add(@Nullable Attendees object) {
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
            row = layoutInflater.inflate(R.layout.row_layout_get_attendees, parent, false);
            eventHolder = new EventHolder();
            eventHolder.displayName = (TextView) row.findViewById(R.id.displayName);

            row.setTag(eventHolder);

        }

        else
        {
            eventHolder = (EventHolder)row.getTag();
        }

        final Attendees myEvents = (Attendees) this.getItem(position);
        eventHolder.displayName.setText(myEvents.getDisplayName());
        return row;
    }

    static class EventHolder
    {
        TextView displayName;
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

