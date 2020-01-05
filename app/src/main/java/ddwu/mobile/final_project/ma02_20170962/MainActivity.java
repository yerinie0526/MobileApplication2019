package ddwu.mobile.final_project.ma02_20170962;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.addFoodMenu:
                intent = new Intent(this, AddFoodActivity.class);
                break;
            case R.id.addGroupMenu:
                intent = new Intent(this, AddGroupActivity.class);
                break;
            case R.id.myHomeMenu:
                intent = new Intent(this, ChooseHomeActivity.class);
                break;
            case R.id.btninformation:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle("개발자 정보 및 사진 출처");
                builder2.setMessage("이예린 yerinie@naver.com\n사진출처:instargram @moreparsley");
                builder2.show();
                break;

        }

        if (intent != null) startActivity(intent);
    }
}
