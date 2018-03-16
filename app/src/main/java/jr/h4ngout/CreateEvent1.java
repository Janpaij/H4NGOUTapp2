package jr.h4ngout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateEvent1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event1);
        // Get the Intent that started this activity
        Intent intent = getIntent();
    }

    /** Called when the user taps the Next button */
    public void creatEvent2(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CreateEvent2.class);
        startActivity(intent);

    }
}
