package jr.h4ngout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateEvent2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);
        // Get the Intent that started this activity
        Intent intent = getIntent();
    }

    /** Called when the user taps the Next button */
    public void creatEvent3(View view) {
        // Do something in response to button
        EditText Title = (EditText) findViewById(R.id.Title);
        String title = Title.getText().toString();

        EditText Description = (EditText) findViewById(R.id.Description);
        String description = Description.getText().toString();

        EditText Date = (EditText) findViewById(R.id.Date);
        String date = Date.getText().toString();

        EditText StartTime = (EditText) findViewById(R.id.StartTime);
        String startTime = StartTime.getText().toString();

        EditText EndTime = (EditText) findViewById(R.id.EndTime);
        String endTime = EndTime.getText().toString();

        Intent intent = new Intent(this, CreateEvent4.class);
        intent.putExtra("Title", title);
        intent.putExtra("Description", description);
        intent.putExtra("Date", date);
        intent.putExtra("StartTime", startTime);
        intent.putExtra("EndTime", endTime);
        startActivity(intent);

    }
}
