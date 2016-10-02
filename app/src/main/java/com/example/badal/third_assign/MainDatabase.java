package com.example.badal.third_assign;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MainDatabase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="students.db";
    public static final String TABLE_STUDENTS="students";
    public static final String COLUMN_ROLLNO="Roll_Number";
    public static final String COLUMN_STUNAME="Student_Name";
    public static final String COLUMN_SEMESTER="Semester";
    private static final String TAG="MDatabase";

    public MainDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "Inside OnCreate");
        String query= "CREATE TABLE " + TABLE_STUDENTS + " (Roll_Number TEXT PRIMARY KEY ,Student_Name TEXT,Semester TEXT) ";
        sqLiteDatabase.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "Inside OnUpgrade");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(sqLiteDatabase);


    }

    public boolean addstudent(String r_num,String stu_name,String seme){
        Log.d(TAG, "Inside AddStudent");
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        if((r_num.equals(""))||(stu_name.equals(""))||(seme.equals("")))
            return false;
         contentValues.put(COLUMN_ROLLNO,r_num);
        contentValues.put(COLUMN_STUNAME,stu_name);
        contentValues.put(COLUMN_SEMESTER,seme);
        long res=sqLiteDatabase.insert(TABLE_STUDENTS,null,contentValues);
        if (res==-1)
        {
            return false;
        }
        else {
            return true;
        }

    }

    public int deletestudent(String roll_number){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_STUDENTS," Roll_Number = ? ",new String[]{roll_number});

    }



    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_STUDENTS,null);
        return res;

    }

    public boolean updateData(String r_num,String stu_name,String seme){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_ROLLNO,r_num);
        contentValues.put(COLUMN_STUNAME,stu_name);
        contentValues.put(COLUMN_SEMESTER,seme);
        sqLiteDatabase.update(TABLE_STUDENTS, contentValues, " Roll_Number = ? ", new String[]{r_num});
        return  true;
    }
}
