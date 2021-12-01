package nguyen.com.shoplaptopmobile.Adapter;

import android.Manifest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import nguyen.com.shoplaptopmobile.R;
import nguyen.com.shoplaptopmobile.activities.GiohangActivity;
import nguyen.com.shoplaptopmobile.activities.MainActivity;
import nguyen.com.shoplaptopmobile.model.Giohang;

public class GiohangAdapter extends BaseAdapter {
    Context context;

    public GiohangAdapter(Context context, ArrayList<Giohang> giohangArrayList) {
        this.context = context;
        this.giohangArrayList = giohangArrayList;
    }

    ArrayList<Giohang> giohangArrayList;
    @Override
    public int getCount() {
        return giohangArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return giohangArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Viewholder {
        public TextView txttengiohang, txtgia;
        public ImageView imageView;
        public Button btntru,btncong,btnvalue;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder viewholder ;
        if (view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_giohang,null);
            viewholder.txttengiohang = view.findViewById(R.id.textviewtengiohang);
            viewholder.txtgia = view.findViewById(R.id.textviewgiagiohang);
            viewholder.imageView = view.findViewById(R.id.imgvgiohang);
            viewholder.btntru = view.findViewById(R.id.btntru);
            viewholder.btnvalue = view.findViewById(R.id.btnvalue);
            viewholder.btncong = view.findViewById(R.id.btncong);
            view.setTag(viewholder);

        }else {
            viewholder= (Viewholder) view.getTag();
        }
        Giohang giohang = (Giohang) getItem(i);
        viewholder.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
        viewholder.txtgia.setText("Giá :" + decimalFormat.format(giohang.getGia())+ "VNĐ");

        if (giohang.getHinhanh().isEmpty()) {
            Picasso.with(context).load(R.drawable.iconerror).placeholder(R.drawable.noimage).into(viewholder.imageView);
        } else {        Picasso.with(context).load(giohang.getHinhanh()).placeholder(R.drawable.noimage).into(viewholder.imageView);
        }
        viewholder.btnvalue.setText(giohang.getSoluongsp()+"");
        int sl = Integer.parseInt(viewholder.btnvalue.getText().toString());
        if (sl>=10){
            viewholder.btncong.setVisibility(View.INVISIBLE);
            viewholder.btntru.setVisibility(View.VISIBLE);
        }else if (sl<=1){
            viewholder.btncong.setVisibility(View.VISIBLE);
            viewholder.btntru.setVisibility(View.INVISIBLE);

        }else if (sl>=1){
            viewholder.btncong.setVisibility(View.VISIBLE);
            viewholder.btntru.setVisibility(View.VISIBLE);
        }

        Viewholder finalViewholder = viewholder;

        viewholder.btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(viewholder.btnvalue.getText().toString())+1;
                int slht = MainActivity.arrayListgiohang.get(i).getSoluongsp();
                long giaht = MainActivity.arrayListgiohang.get(i).getGia();
                MainActivity.arrayListgiohang.get(i).setSoluongsp(slmoi);
                long giamoi = (giaht*slmoi)/slht;
                MainActivity.arrayListgiohang.get(i).setGia(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
                viewholder.txtgia.setText("Giá :" + decimalFormat.format(giamoi)+ "VNĐ");
                GiohangActivity.Eventutil();

                if (slmoi > 9){
                    viewholder.btncong.setVisibility(View.INVISIBLE);
                    viewholder.btntru.setVisibility(View.VISIBLE);
                    viewholder.btnvalue.setText(String.valueOf(slmoi));
                }else {
                    viewholder.btncong.setVisibility(View.VISIBLE);
                    viewholder.btntru.setVisibility(View.VISIBLE);
                    viewholder.btnvalue.setText(String.valueOf(slmoi));
                }

            }
        });

        viewholder.btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(finalViewholder.btnvalue.getText().toString())-1;
                int slht = MainActivity.arrayListgiohang.get(i).getSoluongsp();
                long giaht = MainActivity.arrayListgiohang.get(i).getGia();
                MainActivity.arrayListgiohang.get(i).setSoluongsp(slmoi);
                long giamoi = (giaht*slmoi)/slht;
                MainActivity.arrayListgiohang.get(i).setGia(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
                finalViewholder.txtgia.setText("Giá :" + decimalFormat.format(giamoi)+ "VNĐ");
                GiohangActivity.Eventutil();

                if (slmoi <2){
                    finalViewholder.btncong.setVisibility(View.VISIBLE);
                    finalViewholder.btntru.setVisibility(View.INVISIBLE);
                    finalViewholder.btnvalue.setText(String.valueOf(slmoi));
                }else {
                    finalViewholder.btncong.setVisibility(View.VISIBLE);
                    finalViewholder.btntru.setVisibility(View.VISIBLE);
                    viewholder.btnvalue.setText(String.valueOf(slmoi));
                }
            }
        });
        return view;
    }
}
