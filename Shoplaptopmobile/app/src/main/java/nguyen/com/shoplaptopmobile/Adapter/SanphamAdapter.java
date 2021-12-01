package nguyen.com.shoplaptopmobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import nguyen.com.shoplaptopmobile.R;
import nguyen.com.shoplaptopmobile.activities.ChitietspActivity;
import nguyen.com.shoplaptopmobile.model.Sanpham;
import nguyen.com.shoplaptopmobile.utilities.CheckConnection;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.spholder> {
    Context context;

    public SanphamAdapter(Context context, ArrayList<Sanpham> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<Sanpham> list;


    @Override
    public spholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoi,null);
        spholder sp = new spholder(v);
        return sp;
    }

    @Override
    public void onBindViewHolder(spholder holder, int position) {
        Sanpham sp = list.get(position);
        Picasso.with(context).load(sp.getHinhanhsp()).placeholder(R.drawable.noimage).error(R.drawable.iconerror).into(holder.imghinh);
        holder.txtensp.setText(sp.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
        holder.txtgiaps.setText("Giá :" + decimalFormat.format(sp.getGiasp())+ "VNĐ");



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class spholder extends RecyclerView.ViewHolder{
        public ImageView imghinh;
        public TextView txtensp,txtgiaps;

        public spholder(View itemView) {
            super(itemView);
            imghinh = (ImageView) itemView.findViewById(R.id.imagehinhsp);
            txtensp = (TextView) itemView.findViewById(R.id.txttensp);
            txtgiaps = (TextView) itemView.findViewById(R.id.txtgia);

            imghinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChitietspActivity.class);
                    intent.putExtra("thongtinsp",list.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });


        }
    }
}
