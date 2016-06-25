package com.vaishali.admin.barcodescan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.BufferedInputStream;
;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Scan extends AppCompatActivity implements View.OnClickListener{


    private Button scanBtn, linkBtn,add;
    private TextView authorText, titleText, descriptionText, dateText, ratingCountText;
    private LinearLayout starLayout;
    private ImageView thumbView;
    private ImageView[] starViews;

    private Bitmap thumbImg;

    DatabaseAdapter d;

    String bookname;
    String authorname;
    String genre=null;
    String rating;
    String read=null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scanBtn = (Button) findViewById(R.id.scan_button);
        add = (Button) findViewById(R.id.button3);
        add.setVisibility(View.GONE);

        linkBtn = (Button) findViewById(R.id.link_btn);
        linkBtn.setVisibility(View.GONE);

        authorText = (TextView) findViewById(R.id.book_author);
        titleText = (TextView) findViewById(R.id.book_title);
        descriptionText = (TextView) findViewById(R.id.book_description);
        dateText = (TextView) findViewById(R.id.book_date);
        starLayout = (LinearLayout) findViewById(R.id.star_layout);
        ratingCountText = (TextView) findViewById(R.id.book_rating_count);
        thumbView = (ImageView) findViewById(R.id.thumb);

        d= new DatabaseAdapter(this);

        starViews = new ImageView[5];
        for (int s = 0; s < starViews.length; s++) {
            starViews[s] = new ImageView(this);
        }

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.main_title3);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scanBtn.setOnClickListener(this);
        linkBtn.setOnClickListener(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                d.insertData1(bookname,authorname,genre,rating,read);

            }
        });

    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            if (scanContent != null && scanFormat != null && scanFormat.equalsIgnoreCase("EAN_13")) {

                String bookSearchString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + scanContent + "&key=AIzaSyDkyN_HLNcwXNQT7bIh2WMQryVvFDfGP9g";
                new GetBookInfo().execute(bookSearchString);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Not a valid scan!", Toast.LENGTH_SHORT);
                toast.show();
            }

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }




    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button)
        {
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }

        else if(v.getId()==R.id.link_btn){
            //get the url tag
            String tag = (String)v.getTag();
            //launch the url
            Intent webIntent = new Intent(Intent.ACTION_VIEW);
            webIntent.setData(Uri.parse(tag));
            startActivity(webIntent);
        }

    }


    private class GetBookInfo extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... bookURLs) {
            StringBuilder responseOutput = new StringBuilder();
            for (String bookSearchURL : bookURLs) {

                try {
                    URL url = new URL(bookSearchURL);

                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();

                    final StringBuilder output = new StringBuilder("Request URL " + url);
                    output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                    output.append(System.getProperty("line.separator") + "Type " + "GET");





                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while((line = br.readLine()) != null ) {
                        responseOutput.append(line);
                    }
                    br.close();

                }
                catch(Exception e){ e.printStackTrace(); }
            }

            return responseOutput.toString();
        }
        protected void onPostExecute(String result) {
            try{

                JSONObject resultObject = new JSONObject(result);
                JSONArray bookArray = resultObject.getJSONArray("items");
                JSONObject bookObject = bookArray.getJSONObject(0);
                JSONObject volumeObject = bookObject.getJSONObject("volumeInfo");
                try{ titleText.setText("TITLE: "+volumeObject.getString("title"));
                     bookname=titleText.getText().toString();}
                catch(JSONException jse){
                    titleText.setText("");
                    jse.printStackTrace();
                }
                StringBuilder authorBuild = new StringBuilder("");
                try{
                    JSONArray authorArray = volumeObject.getJSONArray("authors");
                    for(int a=0; a<authorArray.length(); a++){
                        if(a>0) authorBuild.append(", ");
                        authorBuild.append(authorArray.getString(a));
                    }
                    authorText.setText("AUTHOR(S): "+authorBuild.toString());
                    authorname=authorText.getText().toString();
                }
                catch(JSONException jse){
                    authorText.setText("");
                    jse.printStackTrace();
                }
                try{ dateText.setText("PUBLISHED: "+volumeObject.getString("publishedDate")); }
                catch(JSONException jse){
                    dateText.setText("");
                    jse.printStackTrace();
                }
                try{ descriptionText.setText("DESCRIPTION: "+volumeObject.getString("description")); }
                catch(JSONException jse){
                    descriptionText.setText("");
                    jse.printStackTrace();
                }
                try{

                    double decNumStars = Double.parseDouble(volumeObject.getString("averageRating"));
                    int numStars = (int)decNumStars;
                    starLayout.setTag(numStars);
                    starLayout.removeAllViews();
                    for(int s=0; s<numStars; s++){
                        starViews[s].setImageResource(R.drawable.star);
                        starLayout.addView(starViews[s]);
                    }

                }
                catch(JSONException jse){
                    starLayout.removeAllViews();
                    jse.printStackTrace();
                }
                try{ ratingCountText.setText(" - "+volumeObject.getString("ratingsCount")+" ratings");
                    rating=ratingCountText.getText().toString();}
                catch(JSONException jse){
                    ratingCountText.setText("");
                    jse.printStackTrace();
                }

                try{
                    linkBtn.setTag(volumeObject.getString("infoLink"));
                    linkBtn.setVisibility(View.VISIBLE);
                    add.setVisibility(View.VISIBLE);

                }
                catch(JSONException jse){
                    linkBtn.setVisibility(View.GONE);
                    jse.printStackTrace();
                }
                try{
                    JSONObject imageInfo = volumeObject.getJSONObject("imageLinks");
                    new GetBookThumb().execute(imageInfo.getString("smallThumbnail"));
                }
                catch(JSONException jse){
                    thumbView.setImageBitmap(null);
                    jse.printStackTrace();
                }





            }
            catch (Exception e) {
                e.printStackTrace();
                titleText.setText("NOT FOUND");
                authorText.setText("");
                descriptionText.setText("");
                dateText.setText("");
                starLayout.removeAllViews();
                ratingCountText.setText("");
                thumbView.setImageBitmap(null);

            }

        }

    }
    private class GetBookThumb extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... thumbURLs) {
            try{
                URL thumbURL = new URL(thumbURLs[0]);
                URLConnection thumbConn = thumbURL.openConnection();
                thumbConn.connect();
                InputStream thumbIn = thumbConn.getInputStream();
                BufferedInputStream thumbBuff = new BufferedInputStream(thumbIn);
                thumbImg = BitmapFactory.decodeStream(thumbBuff);
                thumbBuff.close();
                thumbIn.close();

            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(String result)
        {
            thumbView.setImageBitmap(thumbImg);
        }

    }
}

