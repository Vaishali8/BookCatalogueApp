package com.vaishali.admin.barcodescan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Add extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button add;
    EditText bname,aname;
    Spinner genre;
    RatingBar rate;
    CheckBox read;
    Float Rate;
    Integer checked;
    String genre1;



    DatabaseAdapter d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        add=(Button)findViewById(R.id.button2);

        bname=(EditText)findViewById(R.id.editText);
        aname=(EditText)findViewById(R.id.editText2);
        genre=(Spinner)findViewById(R.id.spinner);
        rate=(RatingBar)findViewById(R.id.ratingBar);
        read=(CheckBox)findViewById(R.id.checkBox2);
        d=new DatabaseAdapter(this);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.main_title2);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<String> gen=new ArrayList<>();
        gen.add("None");
        gen.add("Fiction");
        gen.add("Non-Fiction");
        gen.add("Horror");
        gen.add("Mystery/Thriller");
        gen.add("Romance");
        gen.add("Fantasy");
        gen.add("Young-adult");
        gen.add("Graphic/Comics");
        ArrayAdapter<String> adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,gen);
        genre.setAdapter(adapter);




        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                Rate=rating;
            }
        });

        read.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked)
                {
                    checked=1;
                }
                else
                    checked=0;

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookname = bname.getText().toString();
                String author = aname.getText().toString();

                if(v.getId()==R.id.button2)
                {
                    long id = d.insertData(bookname,author,genre1,Rate,checked);


                    if (id < 0) {
                        Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();

                    }


                }

            }
        });


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selecteditem=parent.getItemAtPosition(position).toString();
        genre1=selecteditem;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
