package com.vaishali.admin.MyBookApp;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b,v;
    String[] content={"  Scan using barcode scanner","  Add manually"};
    ListView l=null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.main_title);
        getSupportActionBar().setIcon(R.drawable.ic_action_name);

        b=(Button)findViewById(R.id.button);
        v=(Button)findViewById(R.id.button4);


        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.dialog_box,R.id.option,content);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.button)
                {
                    AlertDialog dialog;
                    AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("CHOOSE AN OPTION");

                    l=new ListView(MainActivity.this);
                    l.setAdapter(adapter);
                    l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0)
                            {
                                Intent i=new Intent(MainActivity.this,Scan.class);
                                startActivity(i);
                            }
                            else if(position==1)
                            {
                                Intent i=new Intent(MainActivity.this,Add.class);
                                startActivity(i);
                            }
                        }
                    });

                    builder.setView(l);
                    dialog=builder.create();
                    dialog.show();

                }
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   try{
                       Intent i=new Intent(MainActivity.this,View1.class);
                       startActivity(i);
                   }
                   catch (ActivityNotFoundException e)
                   {
                       e.printStackTrace();
                       Toast.makeText(getApplicationContext(),"error"+e,Toast.LENGTH_SHORT).show();
                   }


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_help: {
                AlertDialog dialog;
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("HELP");
                builder.setMessage(" Hi! Welcome to MyBooks App. Add your book information either by" +
                        " scanning the book using barcode scanner or manually. You'll recieve a prompt" +
                        " message to download the barcode scanner if you have installed this App for the first time." +
                 "Check more info about the scanned book using the 'More Info' button. It will take you directly to corresponding Google Books Webpage." +
                "Happy Reading !");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog=builder.create();
                dialog.show();
                break;
            }
            case R.id.menu_about: {
                AlertDialog dialog;
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("ABOUT");


                builder.setMessage(" Hi! This App is made by Vaishali Yadav." + "\n" + "EmailId:vaish.yadav8@gmail.com" +
                        "\n"+"Code available on https://github.com/Vaishali8/BookCatalogueApp");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog=builder.create();
                dialog.show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}