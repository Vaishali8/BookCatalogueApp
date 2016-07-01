package com.vaishali.admin.MyBookApp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SearchActivity extends AppCompatActivity {
    DatabaseAdapter d;

    ListView l=(ListView)findViewById(R.id.listView2);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        d=new DatabaseAdapter(this);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Bundle b=getIntent().getExtras();
        String query=b.getString("Query");
        getSupportActionBar().setTitle(query);

        Cursor cursor = d.search((query != null ? query.toString():"@@@@"));
        if(cursor==null)
        {

        }
        else
        {
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
            SimpleCursorAdapter book=new SimpleCursorAdapter(this,R.layout.listview,cursor,columns,to,0);
            l.setAdapter(book);
        }
    }
}
