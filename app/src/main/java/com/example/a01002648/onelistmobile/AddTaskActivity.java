package com.example.a01002648.onelistmobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText txtName;
    private EditText txtDescription;
    private EditText txtEndDate;
    private EditText txtCost;
    private Button btnSave;
    private SharedPreferences savedValues;
    private Menu mainMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        txtName=(EditText) findViewById(R.id.txtTaskName);
        txtDescription=(EditText) findViewById(R.id.txtTaskDescription);
        txtEndDate=(EditText) findViewById(R.id.txtTaskDate);
        txtCost=(EditText) findViewById(R.id.txtTaskCost);
        btnSave=(Button) findViewById(R.id.btnSave);
        txtCost.setText("0");//initial value for cost
        btnSave.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnSave:
                saveInfo();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                this.finish();
                break;
        }
    }
    private void saveInfo() {
        DBAdapter db = new DBAdapter(this);
        db.open();
        long id = db.insertTask(txtName.getText().toString(),txtDescription.getText().toString(),txtEndDate.getText().toString(),0,Double.parseDouble(txtCost.getText().toString()));
        if(id!=0)
        {
            Toast.makeText(getBaseContext(),
                    "Task saved successfully!",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getBaseContext(),
                    "There was an error while creating your task. Please check your information and try again",
                    Toast.LENGTH_SHORT).show();

        }
    }

}
