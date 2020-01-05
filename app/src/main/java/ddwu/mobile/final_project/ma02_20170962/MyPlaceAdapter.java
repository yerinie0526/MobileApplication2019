package ddwu.mobile.final_project.ma02_20170962;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MyPlaceAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int layout;
    private ArrayList<NaverPlaceDTO> list;


    public MyPlaceAdapter(Context context, int resource, ArrayList<NaverPlaceDTO> list) {
        this.context = context;
        this.layout = resource;
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public NaverPlaceDTO getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int position) {
        return list.get(position).get_id();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d(TAG, "getView with position : " + position);
        View view = convertView;
        ViewHolder viewHolder = null;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvTitle = view.findViewById(R.id.placeNameView);
            viewHolder.tvDes = view.findViewById(R.id.placeDesView);
            viewHolder.tvAdd = view.findViewById(R.id.placeAddView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        NaverPlaceDTO dto = list.get(position);

        viewHolder.tvTitle.setText(dto.getName());
        viewHolder.tvDes.setText(dto.getDescription());
        viewHolder.tvAdd.setText(dto.getAddress());

        return view;
    }


    public void setList(ArrayList<NaverPlaceDTO> list) {
        this.list = list;
    }

    static class ViewHolder {
        public TextView tvTitle = null;
        public TextView tvAdd = null;
        public TextView tvDes = null;
    }
}
