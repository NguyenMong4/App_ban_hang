package nguyen.com.shoplaptopmobile.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import nguyen.com.shoplaptopmobile.R;
import nguyen.com.shoplaptopmobile.model.Sanpham;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraylaptop;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arraylaptop) {
        this.context = context;
        this.arraylaptop = arraylaptop;
    }

    @Override
    public int getCount() {
        return arraylaptop.size();
    }

    @Override
    public Object getItem(int i) {//get ra nhuwng thuoc tinh ben trong cua mang

        return arraylaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttenlaptop,txtmotalaptop, txtgialaptop;
        public ImageView imgvlaptop;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);//gán layout
            view = inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.txttenlaptop = view.findViewById(R.id.txttenlaptop);
            viewHolder.txtgialaptop = view.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop = view.findViewById(R.id.textviewmotalaptop);
            viewHolder.imgvlaptop = view.findViewById(R.id.imgviewlaptop);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttenlaptop.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
        viewHolder.txtgialaptop.setText("Giá :" + decimalFormat.format(sanpham.getGiasp())+ "VNĐ");
        viewHolder.txtmotalaptop.setMaxLines(2);
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotalaptop.setText(sanpham.getMota());
        Picasso.with(context).load(sanpham.getHinhanhsp()).placeholder(R.drawable.noimage).error(R.drawable.iconerror).into(viewHolder.imgvlaptop);
        return view;
    }
}
