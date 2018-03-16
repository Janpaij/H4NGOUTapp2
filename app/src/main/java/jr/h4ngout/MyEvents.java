package jr.h4ngout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        // Get the Intent that started this activity
        Intent intent = getIntent();
    }

    /** Called when the user taps on an event button */
    public void eventScreen(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, EventScreen.class);
        startActivity(intent);

    }
}
