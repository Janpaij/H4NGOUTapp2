package jr.h4ngout;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

public class CreateEvent4 extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private final String TAG = "PLACEPICKER_EXERCISE";

    private final int RESOLVE_CONNECTION_REQUEST_CODE = 1000;
    private final int PLACE_PICKER_REQUEST = 1001;

    protected GoogleApiClient mGoogleApiClient;
    private boolean mHaveLocPerm = false;

    // Invoked when the user clicks on the "Trigger the PlacePicker"
    // button in the main activity
    private void pickAPlace() {
        if (mHaveLocPerm) {
            try {
                // TODO: set a default viewport
                // get the bounding area for the Pike Place market

                // TODO: Trigger the PlacePicker, which will then pass the result
                // to the onActivityResult function below
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent = builder.build(this);
                //Fire off intent, uaing start activity for result, this will show the picker
                startActivityForResult(intent, PLACE_PICKER_REQUEST);
            }
            catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }

    // Called from onActivityResult to update the UI with the data
    // for the Place that was selected by the user
    String name;
    String address;
    String phone;
    String website;
    String coordinates;
    private void updateUI(Place chosenPlace) {
        TextView placeData = (TextView)findViewById(R.id.tvPlaceData);
        name = chosenPlace.getName().toString();
        address = chosenPlace.getAddress().toString();
        phone = chosenPlace.getPhoneNumber().toString();
        website = chosenPlace.getWebsiteUri().toString();
        coordinates = chosenPlace.getLatLng().toString();
        String str = name + "\n"
                + address + "\n"
                + chosenPlace.getLocale() + "\n"
                + phone + "\n"
                + chosenPlace.getPriceLevel() + "\n"
                + chosenPlace.getRating() + "\n"
                + website;

        placeData.setText(str);
    }

    /**
     * Standard Android Activity lifecycle methods
     */

    String Title;
    String Description;
    String Date;
    String StartTime;
    String EndTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event4);
        // Get the Intent that started this activity
        Intent intent = getIntent();
        Title = intent.getStringExtra("Title");
        Description = intent.getStringExtra("Description");
        Date = intent.getStringExtra("Date");
        StartTime = intent.getStringExtra("StartTime");
        EndTime = intent.getStringExtra("EndTime");


        // Build the GoogleApiClient and connect to the Places API
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        findViewById(R.id.btnTriggerPlacePick).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickAPlace();
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
            // This code is passed when the user has resolved whatever connection
            // problem there was with the Google Play Services library
            case RESOLVE_CONNECTION_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    mGoogleApiClient.connect();
                }
                break;
            //check to see if place was picked
            case PLACE_PICKER_REQUEST:
                if (resultCode == RESULT_OK) {
                    Place chosenPlace = PlacePicker.getPlace(this, data);
                    updateUI(chosenPlace);
                }
                break;
        }
    }

    /**
     * Called when the user has been prompted at runtime to grant permissions
     */
    @Override
    public void onRequestPermissionsResult(int reqCode, String[] perms, int[] results){
        if (reqCode == 1) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                mHaveLocPerm = true;
            }
        }
    }

    /**
     * Standard Google Play Services lifecycle callbacks
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "Connected to Places API");

        // If we're running on API 23 or above, we need to ask permissions at runtime
        // In this case, we need Fine Location access to use Place Detection
        int permCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            mHaveLocPerm = true;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.d(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
        if (result.hasResolution()) {
            try {
                result.startResolutionForResult(this, RESOLVE_CONNECTION_REQUEST_CODE);
            }
            catch (IntentSender.SendIntentException e) {
                // Unable to resolve, message user appropriately
            }
        }
        else {
            GoogleApiAvailability gAPI = GoogleApiAvailability.getInstance();
            gAPI.getErrorDialog(this, result.getErrorCode(), 0).show();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.d(TAG, "Connection was suspended for some reason");
        mGoogleApiClient.connect();
    }



    /** Called when the user taps the Next button */
    public void chooseUsers(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, ChooseUsers.class);
        intent.putExtra("Title", Title );
        intent.putExtra("Description", Description);
        intent.putExtra("Date", Date);
        intent.putExtra("StartTime", StartTime);
        intent.putExtra("EndTime", EndTime);
        intent.putExtra("Name", name);
        intent.putExtra("Address", address);
        intent.putExtra("Phone", phone);
        intent.putExtra("Website", website);
        intent.putExtra("function", "NewEvent");
        startActivity(intent);

    }


}
