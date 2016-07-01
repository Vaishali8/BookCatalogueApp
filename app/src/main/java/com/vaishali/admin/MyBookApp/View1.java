package com.vaishali.admin.MyBookApp;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.ListView;

public class View1 extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener {

    TextView t;
    DatabaseAdapter d;

    SearchView searchview;

    private SimpleCursorAdapter _adapter;
    ListView listView,l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        t = (TextView) findViewById(R.id.textView7);

        d = new DatabaseAdapter(this);
        listView=(ListView)findViewById(R.id.listView);



        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.main_title1);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent searchIntent=getIntent();
        String query1;
        if(Intent.ACTION_SEARCH.equals(searchIntent.getAction()))
        {
            query1=searchIntent.getStringExtra(SearchManager.QUERY);

        }

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




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.view_menu,menu);

        searchview= (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager= (SearchManager) getSystemService(SEARCH_SERVICE);
        searchview.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchview.setOnQueryTextListener(this);
        searchview.setOnCloseListener(this);

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

    @Override
    public boolean onClose() {
        ShowResults("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ShowResults(query + "*");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ShowResults(newText + "*");
        return false;
    }
    public void ShowResults(String query)
    {
         l=(ListView)findViewById(R.id.listView);
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