package com.vaishali.admin.barcodescan;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button b,v,s;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.main_title);
        getSupportActionBar().setIcon(R.drawable.ic_action_name);

        b=(Button)findViewById(R.id.button);
        v=(Button)findViewById(R.id.button4);
        s=(Button)findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.button)
                {
                    Intent i=new Intent(MainActivity.this,Add.class);
                    startActivity(i);
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
    public void scanfunc(View v)
    {
        Intent i2=new Intent(MainActivity.this,Scan.class);
        startActivity(i2);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


}