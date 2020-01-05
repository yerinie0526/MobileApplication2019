package ddwu.mobile.final_project.ma02_20170962;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GroupDBHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "mygroup_db";
    public final static String TABLE_NAME = "mygroup_table";
    public final static String COL_GID = "_id";
    public final static String COL_GNAME = "gname";
    public final static String COL_GPRICE = "gprice";
    public final static String COL_FOOD1 = "food1";
    public final static String COL_FOOD2 = "food2";
    public final static String COL_FOOD3 = "food3";
    public final static String COL_GEXTRA = "gextra";
    public final static String COL_GRATE = "grate";
    public final static String COL_GIMAGE = "gimage";


    public GroupDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ( " + COL_GID + " integer primary key autoincrement,"
                + COL_GNAME + " TEXT, " + COL_GPRICE + " TEXT, " + COL_FOOD1 + " TEXT, " + COL_FOOD2 + " TEXT, " + COL_FOOD3 + " TEXT, " + COL_GEXTRA + " TEXT, " + COL_GRATE + " TEXT, " + COL_GIMAGE  + " BLOB);");

//		샘플 데이터
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES (null, '양꼬치세트', '40000', '양꼬치 2인분', '꿔바로우', '칭따오맥주', '양꼬치에는 칭따오', '5', null);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + TABLE_NAME);
        onCreate(db);
    }
}
