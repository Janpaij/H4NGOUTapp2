package jr.h4ngout;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "SIGNIN_EXERCISE";
    private static final int RES_CODE_SIGN_IN = 1001;

    private GoogleApiClient mGoogleApiClient;

    private TextView m_tvStatus;
    private TextView m_tvDispName;
    private TextView m_tvEmail;

    private String value;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = "http://well-prepared-socie.000webhostapp.com/signIn.php";

    //Button button;
    Cursor cursor;
    int MY_CAL_REQ = 1409;
    long beginTime = System.currentTimeMillis();
    Calendar mycal = new GregorianCalendar(2018,2, 1);
    //manually set end time
    //long endTime = mycal.getTimeInMillis();
    long year = 31556952000L;
    long endTime = beginTime + year;



    private void startSignIn() {
        // TODO: Create sign-in intent and begin auth flow
        //To invoke the account picker that lets the user choose the account they want to sign in with, we need to create a SignIn intent and start it.
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RES_CODE_SIGN_IN);
    }

    public void signOut() {
        // TODO: Sign the user out and update the UI
        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        m_tvStatus.setText(R.string.status_notsignedin);
                        m_tvEmail.setText("");
                        m_tvDispName.setText("");
                    }
                });
    }

    private void disconnect() {
        // TODO: Disconnect this account completely and update UI
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        m_tvStatus.setText(R.string.status_notconnected);
                        m_tvEmail.setText("");
                        m_tvDispName.setText("");
                    }
                });
    }

    private void signInResultHandler(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            m_tvStatus.setText(R.string.status_signedin);
            try {
                String displayName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                String idToken = acct.getIdToken();
                m_tvDispName.setText(displayName);
                m_tvEmail.setText(acct.getEmail());
                //Execute call to background task here (to upload tokenID, display name, email address etc, to the server)
                //Update UI accordingly

                UserRegistration(idToken);
                getInstances(idToken);
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);

            }
            catch (NullPointerException e) {
                Log.d(TAG, "Error retrieving some account information");
            }
        }
        else {
            Status status = result.getStatus();
            int statusCode = status.getStatusCode();
            if (statusCode == GoogleSignInStatusCodes.SIGN_IN_CANCELLED) {
                m_tvStatus.setText(R.string.status_signincancelled);
            }
            else if (statusCode == GoogleSignInStatusCodes.SIGN_IN_FAILED) {
                m_tvStatus.setText(R.string.status_signinfail);
            }
            else {
                m_tvStatus.setText(R.string.status_nullresult);
            }
        }
    }

    // *************************************************
    // -------- ANDROID ACTIVITY LIFECYCLE METHODS
    // *************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        // Get the Intent that started this activity
        Intent intent = getIntent();
        value = intent.getStringExtra("value");


        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(LogIn.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(LogIn.this);

        m_tvStatus = (TextView)findViewById(R.id.tvStatus);
        m_tvDispName = (TextView)findViewById(R.id.tvDispName);
        m_tvEmail = (TextView)findViewById(R.id.tvEmail);

        findViewById(R.id.btnSignIn).setOnClickListener(this);
        findViewById(R.id.btnSignOut).setOnClickListener(this);
        findViewById(R.id.btnDisconnect).setOnClickListener(this);


        // TODO: Create a sign-in options object
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("647954692974-fa2f4qich34pc4k23hi8mjgqj2eo0js2.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //After building the Google SignIn Options builder, pass that to the Google API client so
        // we can use the Google SignIn API.
        // Build the GoogleApiClient object
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addConnectionCallbacks(this)
                .build();




        // TODO: Customize the sign in button
        SignInButton signInButton = (SignInButton)findViewById(R.id.btnSignIn);
        signInButton.setSize(signInButton.SIZE_WIDE);
        signInButton.setColorScheme(SignInButton.COLOR_DARK);
    }

    @Override
    protected void onStart() {

        super.onStart();
        if(value.equals("login"))
        {
            GoogleSignInAccount accountSignedIn = GoogleSignIn.getLastSignedInAccount(this);
            if(accountSignedIn != null)
            {
                Intent intent = new Intent(this, Home.class);
                startActivity(intent);
            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        //extract google sign in result, and pass that to signInResulotHandler
        if (requestCode == RES_CODE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInResultHandler(result);
        }
    }

    // *************************************************
    // -------- GOOGLE PLAY SERVICES METHODS
    // *************************************************
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Could not connect to Google Play Services");
    }

    @Override
    public void onConnected(Bundle bundle) {

        if(value.equals("logout"))
        {
            signOut();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Applications should disable UI components that require the service,
        // and wait for a call to onConnected(Bundle) to re-enable them.
    }

    // *************************************************
    // -------- CLICK LISTENER FOR THE ACTIVITY
    // *************************************************
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                startSignIn();
                break;
            case R.id.btnSignOut:
                signOut();
                break;
            case R.id.btnDisconnect:
                disconnect();
                break;

        }
    }



    // *************************************************
    // -------- Non-SignIn functions
    // *************************************************



        /** Called when the user taps the Log in button */
    public void home(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }


    public void getInstances(String token)
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            //return;
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_CALENDAR}, MY_CAL_REQ);
        }
        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(eventsUriBuilder, beginTime);
        ContentUris.appendId(eventsUriBuilder, endTime);
        Uri eventUri = eventsUriBuilder.build();
        cursor = getContentResolver().query(eventUri, null, null, null,CalendarContract.Instances.BEGIN + " ASC");
        while(cursor.moveToNext()){
            if(cursor != null){
                int id_1 = cursor.getColumnIndex(CalendarContract.Instances.START_DAY);
                int id_2 = cursor.getColumnIndex(CalendarContract.Instances.START_MINUTE);
                int id_3 = cursor.getColumnIndex(CalendarContract.Instances.END_DAY);
                int id_4 = cursor.getColumnIndex(CalendarContract.Instances.END_MINUTE);
                int id_5 = cursor.getColumnIndex(CalendarContract.Events.TITLE);


                String startDay = cursor.getString(id_1);
                String startMinute = cursor.getString(id_2);
                String endDay = cursor.getString(id_3);
                String endMinute = cursor.getString(id_4);
                String eventTitle = cursor.getString(id_5);


                //Post to database
                String method = "instances";
                BackgroundTask backgroundTask = new BackgroundTask(this);
                backgroundTask.execute(method, eventTitle, startDay, startMinute, endDay, endMinute, token);

                //Toast.makeText(this, "Title: "+eventTitle+" , StartDay: "+startDay+" , StartMinute: "+startMinute+" , EndDay: "+endDay+" , EndMinute: "+endMinute, Toast.LENGTH_SHORT).show();


            }
            else{
                Toast.makeText(this, "Event is not present.", Toast.LENGTH_SHORT).show();

            }
        }

        //Toast.makeText(this, "Done.", Toast.LENGTH_SHORT).show();

    }




    // *************************************************
    // -------- Volley functions
    // *************************************************







    public void UserRegistration(final String token){

        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(LogIn.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(LogIn.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("IDToken", token);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(LogIn.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

}
