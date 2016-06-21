package com.example.bharath.bt_check;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Bharath on 6/18/2016.
 */

public class data extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "profile";
    private static final int DATABASE_VERSION = 1;
    private static final String CALL = "call";
    private static final String MESSAGE = "message";

    private static final String cid = "id";

    //For calling
    private static final String cn1 = "cn1";
    private static final String cp1 = "cp1";
    private static final String cn2 = "cn2";
    private static final String cp2 = "cp2";
    private static final String cn3 = "cn3";
    private static final String cp3 = "cp3";
    private static final String cn4 = "cn4";
    private static final String cp4 = "cp4";


    //For messaging..

    private static final String mid = "id";
    private static final String mn1 = "mn1";

    //For table..

    private static final String CREATE_CALL="CREATE TABLE "+CALL+"("+cid+" INTEGER PRIMARY KEY AUTOINCREMENT, "+cn1+" TEXT, "+cp1+" TEXT, "+cn2+" TEXT, "+cp2+" TEXT, "+cn3+" TEXT, "+cp3+" TEXT, "+cn4+" TEXT, "+cp4+" TEXT "+")";



    public data(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_CALL);
        Log.d("DB", "DB created..");

    }

    public void addCall(){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(cn1,"Bharath");
        cv.put(cp1,"+919524899989");
        cv.put(cn2,"Robert");
        cv.put(cp2,"+919629025806");
        cv.put(cn3,"Barbieee");
        cv.put(cp3,"+918056182319");
        cv.put(cn4,"Pangali");
        cv.put(cp4,"+919487256689");
        db.insert(CALL, null, cv);
        Log.d("Inserted:","DB values inserted..");
        db.close();
    }

    public void getConatct(){

        String selectQuery = "SELECT  * FROM " + CALL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id=Integer.parseInt(cursor.getString(0));
                String n1=cursor.getString(1);
                String p1=cursor.getString(2);
                String n2=cursor.getString(3);
                String p2=cursor.getString(4);
                String n3=cursor.getString(5);
                String p3=cursor.getString(6);
                String n4=cursor.getString(7);
                String p4=cursor.getString(8);
                Log.d("Reading..",id+":"+n1+":"+p1+"\n"+id+":"+n2+":"+p2+"\n"+id+":"+n3+":"+p3+"\n"+id+":"+n4+":"+p4);

            } while (cursor.moveToNext());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CALL);

        // Create tables again
        onCreate(db);

    }
}

