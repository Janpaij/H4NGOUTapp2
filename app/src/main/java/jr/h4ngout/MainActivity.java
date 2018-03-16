package jr.h4ngout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    /** Called when the user taps the Sign UP button */
    public void signUp(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    /** Called when the user taps the Log In button */
    public void logIn(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, LogIn.class);
        intent.putExtra("value", "login");
        startActivity(intent);
    }
}
