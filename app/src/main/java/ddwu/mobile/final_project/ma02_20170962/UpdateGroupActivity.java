package ddwu.mobile.final_project.ma02_20170962;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class UpdateGroupActivity extends AppCompatActivity {

    final int REQUEST_CODE_GALLERY = 999;

    EditText etGname;
    EditText etGprice;
    EditText etGFood1;
    EditText etGFood2;
    EditText etGFood3;
    EditText etGextra;
    RatingBar rbGRate;
    ImageView imageView;

    GroupDBHelper helper;
    GroupDTO groupDTO;
    long id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updategroup_layout);

        helper = new GroupDBHelper(this);

        etGname = findViewById(R.id.updateGroupName);
        etGprice = findViewById(R.id.updateGroupPrice);
        etGFood1 = findViewById(R.id.updateGroupFood1);
        etGFood2 = findViewById(R.id.updateGroupFood2);
        etGFood3 = findViewById(R.id.updateGroupFood3);
        etGextra = findViewById(R.id.updateGroupExtra);
        rbGRate = findViewById(R.id.updateGroupRate);
        imageView = findViewById(R.id.imageViewUpateGroup);


        id = getIntent().getLongExtra("id", 0);

    }

    @Override
    protected void onResume() {
        super.onResume();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery( "select * from " + GroupDBHelper.TABLE_NAME + " where " + GroupDBHelper.COL_GID + "=?", new String[] { String.valueOf(id) });
        while (cursor.moveToNext()) {
            etGname.setText( cursor.getString( cursor.getColumnIndex(GroupDBHelper.COL_GNAME) ) );
            etGprice.setText( cursor.getString( cursor.getColumnIndex(GroupDBHelper.COL_GPRICE) ) );
            etGFood1.setText( cursor.getString( cursor.getColumnIndex(GroupDBHelper.COL_FOOD1) ) );
            etGFood2.setText( cursor.getString( cursor.getColumnIndex(GroupDBHelper.COL_FOOD2) ) );
            etGFood3.setText( cursor.getString( cursor.getColumnIndex(GroupDBHelper.COL_FOOD3) ) );
            etGextra.setText( cursor.getString( cursor.getColumnIndex(GroupDBHelper.COL_GEXTRA) ) );
            rbGRate.setRating(Float.parseFloat(cursor.getString( cursor.getColumnIndex(GroupDBHelper.COL_GRATE))));

            if(cursor.getBlob( cursor.getColumnIndex(GroupDBHelper.COL_GIMAGE)) == null){

            } else{
                byte[] foodImage = cursor.getBlob( cursor.getColumnIndex(GroupDBHelper.COL_GIMAGE));
                Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
                imageView.setImageBitmap(bitmap);
            }

        }

        cursor.close();
        helper.close();

    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnUpdateGroup:
                if(etGname.getText().toString().equals("")){
                    Toast.makeText(this, "수정 실패! 조합이름은 필수 사항입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    SQLiteDatabase db = helper.getWritableDatabase();

                    ContentValues row = new ContentValues();

                    row.put(GroupDBHelper.COL_GNAME, etGname.getText().toString());
                    row.put(GroupDBHelper.COL_GPRICE, etGprice.getText().toString());
                    row.put(GroupDBHelper.COL_FOOD1, etGFood1.getText().toString());
                    row.put(GroupDBHelper.COL_FOOD2, etGFood2.getText().toString());
                    row.put(GroupDBHelper.COL_FOOD3, etGFood3.getText().toString());
                    row.put(GroupDBHelper.COL_GRATE, rbGRate.getRating());
                    row.put(GroupDBHelper.COL_GEXTRA, etGextra.getText().toString());
                    row.put(GroupDBHelper.COL_GIMAGE, imageViewToByte(imageView));

                    String whereClause = FoodDBHelper.COL_ID + "=?";

                    String[] whereArgs = new String[] { String.valueOf(id) };

                    //db.update(FoodDBHelper.TABLE_NAME, row, whereClause, whereArgs);

                    db.update ("mygroup_table", row, whereClause, whereArgs);
                    helper.close();
                    finish();
                }

                break;
            case R.id.btnUpdateCancel:
                setResult(RESULT_CANCELED);
                finish();
                break;

            case R.id.btnUpdateFoodPlace:
                Paramenters.addOrUpdaate = "update";
                Intent intent = new Intent(this, ShowPlaceListActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1111);
                break;

//            case R.id.updateGroupImage:
//                Intent intent4 = new Intent(Intent.ACTION_PICK);
//                intent4.setType("image/*");
//                startActivityForResult(intent4, REQUEST_CODE_GALLERY);
//                break;



        }


    }
}
