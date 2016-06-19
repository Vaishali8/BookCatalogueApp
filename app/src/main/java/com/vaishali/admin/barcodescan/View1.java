package com.vaishali.admin.barcodescan;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ListView;

public class View1 extends AppCompatActivity {

    TextView t;
    DatabaseAdapter d;

    private SimpleCursorAdapter _adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        t = (TextView) findViewById(R.id.textView7);
        d = new DatabaseAdapter(this);
        listView=(ListView)findViewById(R.id.listView);

        populateList();

    }
    public void populateList()
    {
        Cursor cursor = d.getAllData();


        String[] columns = new String[]{
                d.NAME,
                d.ANAME,
                d.GENRE,
                d.RATING
        };
        int[] to = new int[]{
                R.id.book,
                R.id.aname,
                R.id.genre,
                R.id.rating,
        };
        _adapter= new SimpleCursorAdapter(this,R.layout.listview,cursor,columns ,to,0);
        listView.setAdapter(_adapter);
    }


}