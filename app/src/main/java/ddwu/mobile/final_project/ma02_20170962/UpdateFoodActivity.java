package ddwu.mobile.final_project.ma02_20170962;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateFoodActivity extends AppCompatActivity {

    final int REQUEST_CODE_GALLERY = 999;

    EditText etFname;
    EditText etFprice;
    EditText etPlace;
    EditText etExtra;
    RatingBar rbRate;
    String address;
    ImageView imageView;

    FoodDBHelper helper;
    FoodDTO foodDTO;
    long id;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1235){
            id = data.getLongExtra("id2", 0);
            etPlace.setText(data.getStringExtra("name"));
            address = data.getStringExtra("add");
        }

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatefood_layout);

        helper = new FoodDBHelper(this);

        etFname = findViewById(R.id.updateFoodName);
        etFprice = findViewById(R.id.updateFoodPrice);
        etPlace = findViewById(R.id.updateFoodPlace);
        etExtra = findViewById(R.id.updateFoodExtra);
        rbRate = findViewById(R.id.updateFoodRate);
        imageView = findViewById(R.id.imageViewUpdate);

;

        id = getIntent().getLongExtra("id", 0);

//        Intent intent2 = getIntent();
//        etPlace.setText(intent2.getStringExtra("name"));




    }

    @Override
    protected void onResume() {
        super.onResume();

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery( "select * from " + FoodDBHelper.TABLE_NAME + " where " + FoodDBHelper.COL_ID + "=?", new String[] { String.valueOf(id) });
        while (cursor.moveToNext()) {
            etFname.setText( cursor.getString( cursor.getColumnIndex(FoodDBHelper.COL_NAME) ) );
            etFprice.setText( cursor.getString( cursor.getColumnIndex(FoodDBHelper.COL_PRICE) ) );
            etPlace.setText( cursor.getString( cursor.getColumnIndex(FoodDBHelper.COL_PLACE) ) );
            etExtra.setText( cursor.getString( cursor.getColumnIndex(FoodDBHelper.COL_EXTRA) ) );
            rbRate.setRating(Float.parseFloat(cursor.getString( cursor.getColumnIndex(FoodDBHelper.COL_RATE))));
            address = cursor.getString((cursor.getColumnIndex(FoodDBHelper.COL_ADD)));

            if(cursor.getBlob( cursor.getColumnIndex(FoodDBHelper.COL_IMAGE)) == null){
                imageView.setImageResource(R.mipmap.honeychip);
            } else{
                byte[] foodImage = cursor.getBlob( cursor.getColumnIndex(FoodDBHelper.COL_IMAGE));
                Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
                imageView.setImageBitmap(bitmap);
            }



        };



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
            case R.id.btnUpdateFood:
                if(etFname.getText().toString().equals("") || etExtra.getText().toString().equals("")){
                    Toast.makeText(this, "수정 실패! 메뉴 이름과 후기는 필수 사항입니다.", Toast.LENGTH_SHORT).show();
                }
                else{
                    SQLiteDatabase db = helper.getWritableDatabase();

                    ContentValues row = new ContentValues();

                    row.put(FoodDBHelper.COL_NAME, etFname.getText().toString());
                    row.put(FoodDBHelper.COL_PRICE, etFprice.getText().toString());
                    row.put(FoodDBHelper.COL_PLACE, etPlace.getText().toString());
                    row.put(FoodDBHelper.COL_EXTRA, etExtra.getText().toString());
                    row.put(FoodDBHelper.COL_RATE, rbRate.getRating());
                    row.put(FoodDBHelper.COL_ADD, address);
                    row.put(FoodDBHelper.COL_IMAGE, imageViewToByte(imageView));

                    String whereClause = FoodDBHelper.COL_ID + "=?";

                    String[] whereArgs = new String[] { String.valueOf(id) };

                    db.update(FoodDBHelper.TABLE_NAME, row, whereClause, whereArgs);

                    //db.update ("myfood_table", row, whereClause, whereArgs);
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
            case R.id.btnShowMap:
                if(address == null){
                    Toast.makeText(UpdateFoodActivity.this, "지점검색을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent3 = new Intent(this, MapActivity.class);
                    intent3.putExtra("add", address);
                    intent3.putExtra("placename", etPlace.getText().toString());
                    intent3.putExtra("foodname", etFname.getText().toString());
                    startActivity(intent3);
                }
                break;
//            case R.id.updateFoodImage:
//                Intent intent4 = new Intent(Intent.ACTION_PICK);
//                intent4.setType("image/*");
//                startActivityForResult(intent4, REQUEST_CODE_GALLERY);
//                break;

        }


    }
}
