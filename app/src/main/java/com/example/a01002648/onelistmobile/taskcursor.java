package com.example.a01002648.onelistmobile;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by A01002648 on 2/24/2017.
 */

public class taskcursor extends CursorAdapter {
    @SuppressWarnings("deprecation")
    public taskcursor(Context context, Cursor c) {
        super(context, c);
    }
    private int ID_POSITION=0;
    private int NAME_POSITION=1;
    private int DESCRIPTION_POSITION=2;
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // When the view will be created for first time,
        // we need to tell the adapters, how each item will look.
        // Uses an UI Fragment to inflate the View.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View retView = inflater.inflate(R.layout.single_row_item, parent, false);

        return retView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        /* Here we are setting our data by taking it from the cursor and
         * putting it in textViews defined in the fragment single_row_item.xml
         */


        TextView textViewID = (TextView) view.findViewById(R.id.TaskID);
        textViewID.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(ID_POSITION))));

        TextView textViewName = (TextView) view.findViewById(R.id.taskName);
        textViewName.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(NAME_POSITION))));

        TextView textViewDescription = (TextView) view.findViewById(R.id.taskDescription);
        textViewDescription.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(DESCRIPTION_POSITION))));
    }
}
