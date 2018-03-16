package jr.h4ngout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class DatePicker extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        // Get the Intent that started this activity
        Intent intent = getIntent();
        final String selectedUsers = intent.getStringExtra("selectedUsers");
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i2 + "/" + (i1+1) + "/" + i;

                Intent intent = new Intent(DatePicker.this, EventsList.class);
                intent.putExtra("date", date);
                intent.putExtra("day", i2);
                intent.putExtra("month", (i1+1));
                intent.putExtra("year", i);
                intent.putExtra("selectedUsers", selectedUsers);
                startActivity(intent);
            }
        });
    }
}
