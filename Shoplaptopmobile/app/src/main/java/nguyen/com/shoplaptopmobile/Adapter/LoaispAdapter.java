package nguyen.com.shoplaptopmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nguyen.com.shoplaptopmobile.R;
import nguyen.com.shoplaptopmobile.model.Loaisp;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<Loaisp> loaispArrayList ;
    Context context;

    public LoaispAdapter(ArrayList<Loaisp> loaispArrayList, Context context) {
        this.loaispArrayList = loaispArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return loaispArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaispArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public  class viewHolder{
        TextView textView;
        ImageView imageView;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder viewHolder = null;
        if (view == null){
            viewHolder = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewHolder.textView = view.findViewById(R.id.textviewloaisp);
            viewHolder.imageView= view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewHolder);

        }else {
            viewHolder= (LoaispAdapter.viewHolder) view.getTag();
        }
        Loaisp loaisp = (Loaisp) getItem(i);
        viewHolder.textView.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhanhloaisp()).placeholder(R.drawable.noimage)
                .error(R.drawable.iconerror).into(viewHolder.imageView);
        return view;
    }
}
