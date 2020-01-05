package ddwu.mobile.final_project.ma02_20170962;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddGroupActivity extends Activity {

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
    long id;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        setContentView(R.layout.addgroup_layout);

        etGname = findViewById(R.id.addGroupName);
        etGprice = findViewById(R.id.addGroupPrice);
        etGFood1 = findViewById(R.id.addGroupFood1);
        etGFood2 = findViewById(R.id.addGroupFood2);
        etGFood3 = findViewById(R.id.addGroupFood3);
        etGextra = findViewById(R.id.addGroupExtra);
        rbGRate = findViewById(R.id.rbgroupRate);
        imageView = findViewById(R.id.addGroupImageView);


        id = getIntent().getLongExtra("id", 0);


        helper = new GroupDBHelper(this);

//        Intent intent2 = getIntent();
//        etPlace.setText(intent2.getStringExtra("name"));
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddGroup:
//			DB 데이터 삽입 작업 수행
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues row = new ContentValues();
                row.put("gname", etGname.getText().toString());
                row.put("gprice", etGprice.getText().toString());
                row.put("food1", etGFood1.getText().toString());
                row.put("food2", etGFood2.getText().toString());
                row.put("food3", etGFood3.getText().toString());
                row.put("gextra", etGextra.getText().toString());
                row.put("grate", rbGRate.getRating());
                row.put("gimage", imageViewToByte(imageView));

                db.insert("mygroup_table", null, row);
                helper.close();
                finish();
                break;

            case R.id.btnAddGroupCancel:
//			DB 데이터 삽입 취소 수행
                finish();
                break;
            //case R.id.btnSetFoodLoc:
//            case R.id.btnSetFoodPlace:
//                Paramenters.addOrUpdaate = "add";
//                Intent intent = new Intent(this, ShowPlaceListActivity.class);
//                intent.putExtra("id", id);
//                startActivityForResult(intent, 1112);
//                break;

            case R.id.btnSetGroupPic:
                Intent intent4 = new Intent(Intent.ACTION_PICK);
                intent4.setType("image/*");
                startActivityForResult(intent4, REQUEST_CODE_GALLERY);
                break;



        }
    }
}
