package com.example.a01002648.onelistmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Lists extends AppCompatActivity {
    private EditText txtComplete;
    private SharedPreferences savedValues;
    private Menu mainMenu;
    private int ID_POSITION=0;
    private int NAME_POSITION=1;
    private int DESCRIPTION_POSITION=2;
    private int ENDDATE_POSITION=3;
    private int DONE_POSITION=4;
    private int COST_POSITION=5;
    private ListView listView;
    private taskcursor customAdapter;
    private DBAdapter databaseHelper;
    private static final String TAG = Lists.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        listView = (ListView) findViewById(R.id.list_data);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView TaskID =(TextView) view.findViewById(R.id.TaskID);
                TextView name =(TextView) view.findViewById(R.id.taskName);
                Log.d(TAG, "clicked on item: " + name.getText().toString());

                Intent listInfo = new Intent(getApplicationContext(),TaskDetails.class);
                listInfo.putExtra("Task_ID", TaskID.getText().toString());
                startActivity(listInfo);
            }
        });
        databaseHelper = new DBAdapter(this);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                databaseHelper.open();
                customAdapter = new taskcursor(Lists.this, databaseHelper.getAllTasks());
                listView.setAdapter(customAdapter);
                databaseHelper.close();
            }
        });


    }
    private String getInfo()
    {
        String Result ="";
        txtComplete.setText("");
        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllTasks();
        if (c.moveToFirst()) {
            do {
                Result = Result + "id: " + c.getString(ID_POSITION) + System.getProperty("line.separator") +
                        "Task Name: " + c.getString(NAME_POSITION) + "\n" +
                        "Description:  " + c.getString(DESCRIPTION_POSITION) + System.getProperty("line.separator") +
                        "Due Date: " + c.getString(ENDDATE_POSITION) + System.getProperty("line.separator") +
                        "Done?: " + c.getString(DONE_POSITION) + System.getProperty("line.separator") +
                        "Cost: " + c.getString(COST_POSITION) +System.getProperty("line.separator")+System.getProperty("line.separator");

            } while (c.moveToNext());
        }
        db.close();
     return Result;
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.task_menu,menu);
        return true;

    }
    @Override
    public void onPause() {
        // save the instance variables
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onPause();
        databaseHelper.open();
        customAdapter = new taskcursor(Lists.this, databaseHelper.getAllTasks());
        listView.setAdapter(customAdapter);
        databaseHelper.close();
    }
    @Override
    public void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        databaseHelper.open();
        customAdapter = new taskcursor(Lists.this, databaseHelper.getAllTasks());
        listView.setAdapter(customAdapter);
        databaseHelper.close();
    }
    @Override
    public void onRestart(){
        super.onRestart();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        databaseHelper.open();
        customAdapter = new taskcursor(Lists.this, databaseHelper.getAllTasks());
        listView.setAdapter(customAdapter);
        databaseHelper.close();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.addTask:
                Intent listInfo = new Intent(getApplicationContext(),AddTaskActivity.class);
                startActivity(listInfo);
                return true;

        }
        return true;
    }
}
