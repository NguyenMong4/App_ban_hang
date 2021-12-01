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

public class Dien_thoai_adapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraydienthoai;

    public Dien_thoai_adapter(Context context, ArrayList<Sanpham> arraydienthoai) {
        this.context = context;
        this.arraydienthoai = arraydienthoai;
    }

    @Override
    public int getCount() {
        return arraydienthoai.size();
    }

    @Override
    public Object getItem(int i) {//get ra nhuwng thuoc tinh ben trong cua mang

        return arraydienthoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttendt,txtmotadt, txtgiadt;
        public ImageView imgdienthoai;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);//gán layout
            view = inflater.inflate(R.layout.dong_dienthoai,null);
            viewHolder.txttendt = view.findViewById(R.id.txttendt);
            viewHolder.txtgiadt = view.findViewById(R.id.textviewgiadienthoai);
            viewHolder.txtmotadt = view.findViewById(R.id.textviewmotadt);
            viewHolder.imgdienthoai = view.findViewById(R.id.imgviewdienthoai);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttendt.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
        viewHolder.txtgiadt.setText("Giá :" + decimalFormat.format(sanpham.getGiasp())+ "VNĐ");
        viewHolder.txtmotadt.setMaxLines(2);
        viewHolder.txtmotadt.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotadt.setText(sanpham.getMota());
        Picasso.with(context).load(sanpham.getHinhanhsp()).placeholder(R.drawable.noimage).error(R.drawable.iconerror).into(viewHolder.imgdienthoai);
        return view;
    }
}
