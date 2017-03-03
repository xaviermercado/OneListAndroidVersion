package com.example.a01002648.onelistmobile;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TaskDetails extends AppCompatActivity {
    private int ID_POSITION=0;
    private int NAME_POSITION=1;
    private int DESCRIPTION_POSITION=2;
    private int ENDDATE_POSITION=3;
    private int DONE_POSITION=4;
    private int COST_POSITION=5;
    private EditText txtName;
    private EditText txtDescription;
    private EditText txtEndDate;
    private EditText txtCost;
    private Button btnSave;
    private SharedPreferences savedValues;
    private Menu mainMenu;
    private String textID;
    private static final String TAG = TaskDetails.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        txtName=(EditText) this.findViewById(R.id.txtTaskName);
        txtDescription=(EditText) this.findViewById(R.id.txtTaskDescription);
        txtEndDate=(EditText) this.findViewById(R.id.txtTaskDate);
        txtCost=(EditText) this.findViewById(R.id.txtTaskCost);
        textID= this.getIntent().getExtras().getString("Task_ID");
        Log.d(TAG, "bringing information of following value: " + textID);
        getInfo(textID);
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.details_menu,menu);
        return true;

    }
    private void getInfo(String textID)
    {
        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getTask(Long.parseLong(textID));
        if (c.moveToFirst())
        {
            txtName.setText( c.getString(NAME_POSITION));
            txtDescription.setText( c.getString(DESCRIPTION_POSITION));
            txtEndDate.setText( c.getString(ENDDATE_POSITION)) ;
           // txtDone = c.getString(DONE_POSITION);
            txtCost.setText( c.getString(COST_POSITION));
        }
        db.close();
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.updateTask:
               updateTask();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                Toast.makeText(getBaseContext(),
                        "Task update successfully!",
                        Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            case R.id.deleteTask:
                deleteTask();
                Toast.makeText(getBaseContext(),
                        "Task deleted successfully!",
                        Toast.LENGTH_SHORT).show();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                this.finish();
                break;
        }
        return true;
    }
    private void updateTask()
    {
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.updateTask(Long.parseLong(textID),txtName.getText().toString(),txtDescription.getText().toString(),txtEndDate.getText().toString(),0,Double.parseDouble(txtCost.getText().toString()));
        db.close();


    }
    private void deleteTask()
    {
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.deleteTask(Long.parseLong(textID));
        db.close();

    }
}
