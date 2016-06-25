package com.vaishali.admin.barcodescan;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ListView;

public class View1 extends AppCompatActivity {

    TextView t;
    DatabaseAdapter d;
    EditText search;

    private SimpleCursorAdapter _adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        t = (TextView) findViewById(R.id.textView7);
        search= (EditText) findViewById(R.id.editText3);
        d = new DatabaseAdapter(this);
        listView=(ListView)findViewById(R.id.listView);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.main_title1);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


        String query;
        Intent searchIntent=getIntent();
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction()))
        {
             query=searchIntent.getStringExtra(SearchManager.QUERY);


        }
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             _adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        _adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {

                return d.fetchInfoBookName(constraint.toString());
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.view_menu,menu);

        SearchView searchview= (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager= (SearchManager) getSystemService(SEARCH_SERVICE);
        searchview.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_add: {
                Intent i=new Intent(this,Add.class);
                startActivity(i);
                break;
            }
            case R.id.menu_scan: {
                Intent i=new Intent(this,Scan.class);
                startActivity(i);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}