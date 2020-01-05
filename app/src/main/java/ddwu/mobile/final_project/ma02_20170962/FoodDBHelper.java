package ddwu.mobile.final_project.ma02_20170962;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "myfood_db";
    public final static String TABLE_NAME = "myfood_table";
    public final static String COL_ID = "_id";
    public final static String COL_NAME = "name";
    public final static String COL_PRICE = "price";
    public final static String COL_PLACE = "place";
    public final static String COL_EXTRA = "extra";
    public final static String COL_RATE = "rate";
    public final static String COL_ADD = "address";
    public final static String COL_IMAGE = "image";



    public FoodDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COL_ID + " integer primary key autoincrement,"
                + COL_NAME + " TEXT, " + COL_PRICE + " TEXT, " + COL_PLACE + " TEXT, " + COL_EXTRA + " TEXT, " + COL_RATE + " TEXT, " + COL_ADD + " TEXT, " + COL_IMAGE + " BLOB);");



//		샘플 데이터
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '허니버터칩포르마쥬블랑', '2380', '이마트24 백마역점', '맛있긴하지만 기본이 더 맛있다', '3', '경기도 고양시 일산동구 마두1동', null);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}



