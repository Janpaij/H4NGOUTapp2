package jr.h4ngout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        // Get the Intent that started this activity
        Intent intent = getIntent();
    }

    /** Called when the user taps on the New Group button */
    public void newGroupNext(View view) {
        // Do something in response to button
        EditText GroupName = (EditText) findViewById(R.id.GroupName);
        String groupName = GroupName.getText().toString();
        Intent intent = new Intent(this, ChooseUsers.class);
        intent.putExtra("GroupName", groupName);
        intent.putExtra("function", "NewGroup");
        startActivity(intent);

    }
}
