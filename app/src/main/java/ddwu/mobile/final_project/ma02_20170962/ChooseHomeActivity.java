package ddwu.mobile.final_project.ma02_20170962;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

public class ChooseHomeActivity extends Activity {

    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosehome_layout);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChooseFood:
                intent = new Intent(ChooseHomeActivity.this, FoodHomeActivity.class);
                startActivity(intent);
                break;
            case R.id.btnChooseGroup:
                intent = new Intent(ChooseHomeActivity.this, GroupHomeActivity.class);
                startActivity(intent);
                break;

        }
    }

}
