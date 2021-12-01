package nguyen.com.shoplaptopmobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import nguyen.com.shoplaptopmobile.R;
import nguyen.com.shoplaptopmobile.model.Giohang;
import nguyen.com.shoplaptopmobile.model.Sanpham;

public class ChitietspActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;
    TextView txtten, txtgia,txtmota;
    Spinner spinner;
    Button button;

    int id=0;
    String tensp="";
    int gia=0;
    String mota="";
    String hinhanh="";
    int idsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsp);
        Anhxa();
        Actiontoolbar();
        Getdata();
        CatchEventSpiner();
        Eventbutton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void Eventbutton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.arrayListgiohang.size()>0){

                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exit=false;
                    for (int i=0;i<MainActivity.arrayListgiohang.size();i++){
                        if (MainActivity.arrayListgiohang.get(i).getId()==id){
                            MainActivity.arrayListgiohang.get(i).setSoluongsp(MainActivity.arrayListgiohang.get(i).getSoluongsp()+sl);
                            if (MainActivity.arrayListgiohang.get(i).getSoluongsp()>=10){
                                MainActivity.arrayListgiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.arrayListgiohang.get(i).setGia(gia * MainActivity.arrayListgiohang.get(i).getSoluongsp());
                            exit = true;
                        }
                    }
                    if (exit==false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong*gia;
                        MainActivity.arrayListgiohang.add(new Giohang(id,tensp,giamoi,hinhanh,soluong));
                    }
                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi = soluong*gia;
                    MainActivity.arrayListgiohang.add(new Giohang(id,tensp,giamoi,hinhanh,soluong));
                }
                Intent intent = new Intent(getApplicationContext(),GiohangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpiner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void Getdata() {
        Sanpham sanpham= (Sanpham) getIntent().getSerializableExtra("thongtinsp");
        id = sanpham.getId();
        tensp = sanpham.getTensp();
        gia= sanpham.getGiasp();
        hinhanh= sanpham.getHinhanhsp();
        mota= sanpham.getMota();
        idsp= sanpham.getIdsanpham();

        txtten.setText(tensp);
        DecimalFormat decimalFormat = new DecimalFormat("@@@,@@@,@@@");
        txtgia.setText("Giá :" + decimalFormat.format(gia)+ "VNĐ");
        txtmota.setText(mota);
        Picasso.with(getApplicationContext()).load(hinhanh).placeholder(R.drawable.noimage).error(R.drawable.iconerror).into(imageView);

    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarchitietsp);
        imageView= findViewById(R.id.imageviewchitietsp);
        txtten= findViewById(R.id.textviewtensp);
        txtgia= findViewById(R.id.textviewgia);
        txtmota= findViewById(R.id.textviewmota);
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.btndatmua);
    }
}