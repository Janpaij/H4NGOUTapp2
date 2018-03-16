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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrfif on 26/02/2018.
 */

public class MyGroupsAdapter extends ArrayAdapter {

    @NonNull
    @Override
    public Context getContext() {
        return super.getContext();
    }

    List list = new ArrayList();

    public MyGroupsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(@Nullable MyGroupsGetSet object) {
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
            row = layoutInflater.inflate(R.layout.row_layout_my_groups, parent, false);
            eventHolder = new EventHolder();
            eventHolder.GroupName = (TextView) row.findViewById(R.id.GroupName);
            eventHolder.members = (TextView) row.findViewById(R.id.members);
            eventHolder.search = (ImageButton) row.findViewById(R.id.search);
            eventHolder.delete = (ImageButton) row.findViewById(R.id.delete);
            row.setTag(eventHolder);

        }

        else
        {
            eventHolder = (EventHolder)row.getTag();
        }

        final MyGroupsGetSet myEvents = (MyGroupsGetSet) this.getItem(position);
        eventHolder.GroupName.setText(myEvents.getTitle());
        eventHolder.members.setText(myEvents.getMembers());
        final String EventID = myEvents.getGroupID();


        /*
        eventHolder.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DatePicker.class);
                intent.putExtra("Users", categoriescsv );
                getContext().startActivity(intent);
            }
        });  */

        eventHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new MyGroups().deleteGroup(EventID);
                ((MyGroups)getContext()).deleteGroup(EventID);
                Intent intent = new Intent(getContext(), Home.class);
                getContext().startActivity(intent);
            }
        });

        eventHolder.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MyGroups)getContext()).groupSearch(EventID);
            }
        });





        return row;
    }

    static class EventHolder
    {
        TextView GroupName, members;
        ImageButton search, delete;

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

