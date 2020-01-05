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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddFoodActivity extends Activity {

    final int REQUEST_CODE_GALLERY = 999;

    EditText etFname;
    EditText etFprice;
    EditText etPlace;
    EditText etExtra;
    RatingBar rbRate;
    String address = null;
    ImageView imageView;

    FoodDBHelper helper;
    long id;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1234){
            etPlace.setText(data.getStringExtra("name"));
            address =  data.getStringExtra("add");
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
        setContentView(R.layout.addfood_layout);

        etFname = findViewById(R.id.fname);
        etFprice = findViewById(R.id.fcost);
        etPlace = findViewById(R.id.floc);
        etExtra = findViewById(R.id.freview);
        rbRate = findViewById(R.id.frate);
        imageView = findViewById(R.id.mImageView);


        id = getIntent().getLongExtra("id", 0);


        helper = new FoodDBHelper(this);

        Intent intent2 = getIntent();
        etPlace.setText(intent2.getStringExtra("name"));

    }




//    private void cropImageFromAlbum(Uri inputUri){
//        Uri outputUri = Uri.fromFile(profileIconFile);
//
//        Intent intent = getCropIntent(inputUri, outputUri);
//        startActivityForResult(intent, 3);
//    }
//
//    private Intent getCropIntent(Uri inputUri, Uri outuputUri) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(inputUri, "image/*");
//
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 200);
//        intent.putExtra("outputY", 200);
//        intent.putExtra("scale", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, outuputUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
//
//        return intent;
//
//    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInsertFood:
//			DB 데이터 삽입 작업 수행
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues row = new ContentValues();
                row.put("name", etFname.getText().toString());
                row.put("price", etFprice.getText().toString());
                row.put("place", etPlace.getText().toString());
                row.put("extra", etExtra.getText().toString());
                row.put("rate", rbRate.getRating());
                row.put("address", address);
                row.put("image", imageViewToByte(imageView));

                db.insert("myfood_table", null, row);
                //helper.close();
                finish();
                break;

            case R.id.btnCancelInsertFood:
//			DB 데이터 삽입 취소 수행
                finish();
                break;
            case R.id.btnSetFoodLoc:
                if(address == null){
                    Toast.makeText(AddFoodActivity.this, "지점검색을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent3 = new Intent(this, MapActivity.class);
                    intent3.putExtra("add", address);
                    intent3.putExtra("placename", etPlace.getText().toString());
                    intent3.putExtra("foodname", etFname.getText().toString());
                    startActivity(intent3);
                }
                break;
            case R.id.btnSetFoodPlace:
                Paramenters.addOrUpdaate = "add";
                Intent intent = new Intent(this, ShowPlaceListActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, 1112);
                break;

            case R.id.btnSetPic:
                Intent intent4 = new Intent(Intent.ACTION_PICK);
                intent4.setType("image/*");
                startActivityForResult(intent4, REQUEST_CODE_GALLERY);
                break;



        }
    }
}
