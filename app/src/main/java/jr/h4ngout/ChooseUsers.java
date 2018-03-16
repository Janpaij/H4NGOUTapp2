package jr.h4ngout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//import com.codingtrickshub.checkboxlistview.adapter.CategoryAdapter;
//import com.codingtrickshub.checkboxlistview.model.Category;
//import com.codingtrickshub.checkboxlistview.serverCalls.FavouriteCategoriesJsonParser;
//import com.codingtrickshub.checkboxlistview.serverCalls.InsertUpdateFavouriteCategories;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class ChooseUsers extends AppCompatActivity {

    Context context;
    ArrayList<User> array_list;
    UsersJsonParser categoryJsonParser;
    String categoriesCsv;
    String idToken;
    String groupName;
    String function;
    String title;
    String description;
    String date;
    String startTime;
    String endTime;
    String name;
    String address;
    String phone;
    String website;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = "http://well-prepared-socie.000webhostapp.com/newEvent.php";

    // Creating Volley RequestQueue.
    RequestQueue requestQueue2;
    // Creating Progress dialog.
    ProgressDialog progressDialog2;

    // Storing server url into String variable.
    String HttpUrl2 = "http://well-prepared-socie.000webhostapp.com/newEventUsers.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_users);
        // Get the Intent that started this activity
        Intent intent = getIntent();
        groupName = intent.getStringExtra("GroupName");
        function = intent.getStringExtra("function");
        title = intent.getStringExtra("Title");
        description = intent.getStringExtra("Description");
        date = intent.getStringExtra("Date");
        startTime = intent.getStringExtra("StartTime");
        endTime = intent.getStringExtra("EndTime");
        name = intent.getStringExtra("Name");
        address = intent.getStringExtra("Address");
        phone = intent.getStringExtra("Phone");
        website = intent.getStringExtra("Website");
        context = this;

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(ChooseUsers.this);

        // Assigning Activity this to progress dialog.
        progressDialog = new ProgressDialog(ChooseUsers.this);

        // Creating Volley newRequestQueue .
        requestQueue2 = Volley.newRequestQueue(ChooseUsers.this);

        // Assigning Activity this to progress dialog.
        progressDialog2 = new ProgressDialog(ChooseUsers.this);

        GoogleSignInAccount accountSignedIn = GoogleSignIn.getLastSignedInAccount(this);
        if(accountSignedIn != null)
        {
            idToken = accountSignedIn.getIdToken();
        }

        new asyncTask_getCategories().execute();
    }



    public class asyncTask_getCategories extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait...");
            dialog.setMessage("Loading Users!");
            dialog.show();
            array_list = new ArrayList<>();
            categoryJsonParser = new UsersJsonParser();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            array_list = categoryJsonParser.getParsedCategories();




            return null;
        }

        @Override
        protected void onPostExecute(Void s) {

            ListView mListViewBooks = (ListView) findViewById(R.id.category_listView);
            final UserAdapter categoryAdapter = new UserAdapter(context, R.layout.row_user, array_list);
            mListViewBooks.setAdapter(categoryAdapter);
            Button button = (Button) findViewById(R.id.selectCategoryButton);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    categoriesCsv = UsersJsonParser.selectedCategories.toString();
                    categoriesCsv = categoriesCsv.substring(1, categoriesCsv.length() - 1);

                    if (categoriesCsv.length() > 0) {
                        if(function.equals("NewGroup")) {
                            new asyncTask_insertUpdatefavouriteCategories().execute();
                            Intent intent = new Intent(context, MyGroups.class);
                            startActivity(intent);
                        }
                        else if(function.equals("Availability")){
                            Intent intent = new Intent(context, DatePicker.class);
                            intent.putExtra("selectedUsers", categoriesCsv);
                            startActivity(intent);
                        }
                        else if(function.equals("NewEvent")){
                            insertEvent(categoriesCsv);
                            //insertEventUsers(categoriesCsv);
                            Intent intent = new Intent(context, Home.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(context, "Please Select Atleast One User", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            super.onPostExecute(s);
            dialog.dismiss();
        }

        public class asyncTask_insertUpdatefavouriteCategories extends AsyncTask<Void, Void, Void> {

            String response;

            @Override
            protected Void doInBackground(Void... params) {
                response = SelectedUsers.insertUpdateCall(categoriesCsv, function, idToken, groupName);
                return null;
            }

            @Override
            protected void onPostExecute(Void s) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                super.onPostExecute(s);
            }
        }
    }

    // *************************************************
    // -------- Volley functions
    // *************************************************


    public void insertEvent(final String categoriesCsv){

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
                        Toast.makeText(context, ServerResponse, Toast.LENGTH_LONG).show();


                        insertEventUsers(categoriesCsv);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("IDToken", idToken);
                params.put("favouriteCategories", categoriesCsv);
                params.put("title", title);
                params.put("description", description);
                params.put("date", date);
                params.put("startTime", startTime);
                params.put("endTime", endTime);
                params.put("name", name);
                params.put("address", address);
                params.put("phone", phone);
                params.put("website", website);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ChooseUsers.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    public void insertEventUsers(final String categoriesCsv){

        // Showing progress dialog at user registration time.
        progressDialog2.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog2.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog2.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(context, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog2.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("IDToken", idToken);
                params.put("favouriteCategories", categoriesCsv);
                params.put("title", title);
                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ChooseUsers.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


}
