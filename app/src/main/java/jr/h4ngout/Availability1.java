package jr.h4ngout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Availability1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability1);
        // Get the Intent that started this activity
        Intent intent = getIntent();
    }

    /** Called when the user taps on the Next button */
    public void availability2(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DatePicker.class);
        startActivity(intent);

    }
}
