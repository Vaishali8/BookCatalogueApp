package com.vaishali.admin.barcodescan;

/**
 * Created by Admin on 6/16/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by hi on 4/29/2016.
 */
public class DatabaseAdapter {

    public static final String NAME = "Name";

    public static final String GENRE = "Genre";
    public static final String RATING = "Rating";
    public static final String ANAME = "Aname";

    public static final String READ = "Read";

    DbHelper helper;

    public DatabaseAdapter(Context context) {
        helper = new DbHelper(context);
    }

    public long insertData(String bname, String aname, String genr, Float rate1, Integer read1) {
        SQLiteDatabase object = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,bname);
        contentValues.put(ANAME,aname);
        contentValues.put(GENRE,genr);
        contentValues.put(RATING,rate1);
        contentValues.put(READ,read1);

        long id = object.insert(DbHelper.TABLE_NAME,null,contentValues);
        return id;
    }

    public Cursor getAllData()
    {

        SQLiteDatabase db=helper.getReadableDatabase();


        Cursor cursor=db.query(true,DbHelper.TABLE_NAME,null,null,null,null,null,null,null);
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext())
        {


            Integer bname1=cursor.getColumnIndex(NAME);
            Integer bname2=cursor.getColumnIndex(ANAME);
            Integer bname3=cursor.getColumnIndex(GENRE);
            Integer bname4=cursor.getColumnIndex(RATING);
            Integer bname5=cursor.getColumnIndex(READ);
            String bname=cursor.getColumnName(bname1);
            String aname=cursor.getString(bname2);
            String genre=cursor.getString(bname3);
            Integer rating=cursor.getInt(bname4);
            Integer read=cursor.getInt(bname5);
            buffer.append(" "+bname+" "+aname+" "+genre+" "+rating+" "+read+"\n");
        }
        return cursor;
    }
    public String getAllData1()
    {

        SQLiteDatabase db=helper.getReadableDatabase();


        Cursor cursor=db.query(true,DbHelper.TABLE_NAME,null,null,null,null,null,null,null);
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext())
        {


            Integer bname1=cursor.getColumnIndex(NAME);
            Integer bname2=cursor.getColumnIndex(ANAME);
            Integer bname3=cursor.getColumnIndex(GENRE);
            Integer bname4=cursor.getColumnIndex(RATING);
            Integer bname5=cursor.getColumnIndex(READ);
            String bname=cursor.getColumnName(bname1);
            String aname=cursor.getString(bname2);
            String genre=cursor.getString(bname3);
            Integer rating=cursor.getInt(bname4);
            Integer read=cursor.getInt(bname5);
            buffer.append(" "+bname+" "+aname+" "+genre+" "+rating+" "+read+"\n");
        }
        return buffer.toString();
    }





    static class DbHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "bookdatabase.db";
        private static final String TABLE_NAME = "BOOKTABLE";
        private static final int DATABASE_VERSION = 130;
        private static final String UID = "_id";

        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY," + NAME + " VARCHAR(255)," + ANAME + " VARCHAR(255) ," + GENRE + " VARCHAR(255), " + RATING+ " INTEGER, " + READ + " INTEGER);";
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;



        private Context c;

        public DbHelper(Context c) {

            super(c,DATABASE_NAME,null,DATABASE_VERSION);
            this.c = c;

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            try {
                db.execSQL(CREATE_TABLE);

            } catch (SQLException e) {
                Toast.makeText(c, "Unsuccessful" + e,Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            try {


                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                Toast.makeText(c, "Unsuccessful" + e,Toast.LENGTH_SHORT).show();
            }
        }



    }
}


