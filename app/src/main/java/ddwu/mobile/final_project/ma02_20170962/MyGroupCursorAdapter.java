package ddwu.mobile.final_project.ma02_20170962;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGroupCursorAdapter extends CursorAdapter{

    LayoutInflater inflater;
    Cursor cursor;

    public MyGroupCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cursor = c;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView foodNameView = (TextView)view.findViewById(R.id.groupNameView);
        TextView frate = (TextView)view.findViewById(R.id.groupRateView);
        ImageView imageView2 = view.findViewById(R.id.imageView3);

        foodNameView.setText(cursor.getString(cursor.getColumnIndex(GroupDBHelper.COL_GNAME)));
        frate.setText(cursor.getString(cursor.getColumnIndex(GroupDBHelper.COL_GRATE)));
        if(cursor.getBlob(cursor.getColumnIndex(GroupDBHelper.COL_GIMAGE)) == null){
            imageView2.setImageResource(R.mipmap.yang);
        }
        else{
            byte[] foodImage = cursor.getBlob(cursor.getColumnIndex(GroupDBHelper.COL_GIMAGE));
            Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, foodImage.length);
            imageView2.setImageBitmap(bitmap);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View listItemLayout = inflater.inflate(R.layout. groupview_layout, parent, false);
        return listItemLayout;
    }
}
