package ddwu.mobile.final_project.ma02_20170962;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.CommerceDetailObject;
import com.kakao.message.template.CommerceTemplate;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.TextTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.network.storage.ImageUploadResponse;
import com.kakao.util.KakaoParameterException;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodHomeActivity extends Activity {

    final int REQ_CODE = 100;
    final int UPDATE_CODE = 200;

    ArrayList<FoodDTO> list;

    ArrayList<FoodDTO> foodList = null;

    GridView gridView;
    FoodDBHelper helper;
    Cursor cursor;
    MyFoodCursorAdapter adapter;
    //Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myhomefood_layout);
        gridView = (GridView)findViewById(R.id.gridview);

        //list = new ArrayList<FoodDTO>();

        //foodList = new ArrayList<FoodDTO>();
        //list.addAll(foodList);


        helper = new FoodDBHelper(this);

        adapter = new MyFoodCursorAdapter(FoodHomeActivity.this, R.layout.myhomefood_layout, null);
        gridView.setAdapter(adapter);

//		리스트 뷰 클릭 처리
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(FoodHomeActivity.this, UpdateFoodActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

//		리스트 뷰 롱클릭 처리
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final long targetId = id;
                TextView tvName = view.findViewById(R.id.fnameview);

                String dialogMessage = "'" + tvName.getText().toString() + "' 음식 삭제?";

                new AlertDialog.Builder(FoodHomeActivity.this).setTitle("삭제 확인")
                        .setMessage(dialogMessage)
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase db = helper.getWritableDatabase();

                                String whereClause = FoodDBHelper.COL_ID + "=?";
                                String[] whereArgs = new String[] { String.valueOf(targetId) };

                                db.delete(FoodDBHelper.TABLE_NAME, whereClause, whereArgs);
                                //helper.close();
                                readAllContacts();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();


                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        readAllContacts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        cursor 사용 종료
        if (cursor != null) cursor.close();
    }


    private void readAllContacts() {
//        DB에서 데이터를 읽어와 Adapter에 설정
        SQLiteDatabase db = helper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + FoodDBHelper.TABLE_NAME, null);

        adapter.changeCursor(cursor);
        //helper.close();
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.shareKaKao:
                shareKakao();
                break;
        }
    }

    public void shareKakao(){
        try {
            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(this);
            final KakaoTalkLinkMessageBuilder kakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

            String url = "https://image.fmkorea.com/files/attach/new/20141220/486616/44244808/89578747/751ed31ba63d386bb7026377cf0b342e.jpg";
            kakaoBuilder.addImage(url, 200, 100);
            kakaoBuilder.addAppButton("앱 실행");

            kakaoLink.sendMessage(kakaoBuilder, this);
        }catch (KakaoParameterException e){
            e.printStackTrace();
        }
    }
}
