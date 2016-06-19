package com.vaishali.admin.barcodescan;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    Button b,v,s;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    public void Scanfunc(View v)
    {
        Intent i2=new Intent(MainActivity.this,Scan.class);
        startActivity(i2);
    }

}