package jr.h4ngout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class Home extends AppCompatActivity {

    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Get the Intent that started this activity
        Intent intent = getIntent();

        userName = (TextView)findViewById(R.id.UserName);
        GoogleSignInAccount accountSignedIn = GoogleSignIn.getLastSignedInAccount(this);
        if(accountSignedIn != null)
        {
            String name = accountSignedIn.getDisplayName();
            String email = accountSignedIn.getEmail();
            String message = "Signed in as: "+email+" ("+name+")";
            userName.setText(message);
        }

    }

    /** Called when the user taps the Log in button */
    public void createEvent1(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, CreateEvent2.class);
        startActivity(intent);

    }

    /** Called when the user taps the My Events button */
    public void myEvents(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MyEventsList.class);
        startActivity(intent);

    }

    /** Called when the user taps on the My Invites button */
    public void myInvites(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MyInvites.class);
        startActivity(intent);

    }

    /** Called when the user taps on the Search Availability button */
    public void availability(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ChooseUsers.class);
        intent.putExtra("function", "Availability");
        startActivity(intent);

    }

    /** Called when the user taps on the My Groups button */
    public void myGroups(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MyGroups.class);
        startActivity(intent);

    }

    /** Called when the user taps on the Past Events button */
    public void pastEvents(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, PastEvents.class);
        startActivity(intent);

    }

    /** Called when the user taps on the Settings button */
    public void notifications(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Notifications.class);
        startActivity(intent);

    }

    /** Called when the user taps on the Settings button */
    public void settings(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);

    }

    /** Called when the user taps on the Settings button */
    public void logout(View view) {
        // Do something in response to button

        Intent intent = new Intent(this, LogIn.class);
        intent.putExtra("value", "logout");
        startActivity(intent);
        //LogIn logIn = new LogIn();
        //logIn.signOut();



    }



}
