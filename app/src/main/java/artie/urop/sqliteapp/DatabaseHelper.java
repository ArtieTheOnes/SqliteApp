package artie.urop.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by artie on 7/10/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String  DATABASE_NAME = "test.db";
    public static final String  TABLE_NAME = "test_table";
    //public static final String COL_1 = "ID";
    public static final String COL_2 = "ID";
    public static final String COL_3 = "CONTENT";
    public static final String COL_4 = "MOMENT";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER,CONTENT TEXT,MOMENT DATETIME DEFAULT CURRENT_TIMESTAMP)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String id, String content)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,id);
        contentValues.put(COL_3, content);
        contentValues.put(COL_4, getDateTime());
        long result = db.insert(TABLE_NAME,null,contentValues);

        return !(result == -1);

    }

    public Cursor getAllData ()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id,String content)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_1,id);
        contentValues.put(COL_2,id);
        contentValues.put(COL_3, content);
        contentValues.put(COL_4,getDateTime());
        db.update(TABLE_NAME,contentValues,"ID = ?", new String[] {id});
        return true;

    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }




}
