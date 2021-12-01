package nguyen.com.shoplaptopmobile.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

import nguyen.com.shoplaptopmobile.Adapter.GiohangAdapter;
import nguyen.com.shoplaptopmobile.R;
import nguyen.com.shoplaptopmobile.model.Giohang;
import nguyen.com.shoplaptopmobile.utilities.CheckConnection;

public class GiohangActivity extends AppCompatActivity {

    ListView listView;
    TextView textViewthongbao;
    static TextView txttongtien;
    Button btnthanhtoan,btnnext;
    GiohangAdapter giohangAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        Actiontoolbar();
        checkdata();
        Eventutil();
        Catchonitem();
        EventButton();
    }

    private void EventButton() {
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.arrayListgiohang.size()>0){
                    Intent intent = new Intent(getApplicationContext(),ThongtinKHActivity.class);
                    startActivity(intent);
                }
                else
                    CheckConnection.ShowToast_short(getApplicationContext(),"Giỏ hàng của bạn chưa có sản phẩm");
            }
        });

    }

    private void Catchonitem() {
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("xác nhận xóa sản phẩm");
                builder.setMessage("bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (MainActivity.arrayListgiohang.size()<=0){
                            textViewthongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.arrayListgiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            Eventutil();
                            if (MainActivity.arrayListgiohang.size()<=0){
                                textViewthongbao.setVisibility(View.VISIBLE);
                            }else {
                                textViewthongbao.setVisibility(View.INVISIBLE);//an di
                                giohangAdapter.notifyDataSetChanged();
                                Eventutil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        Eventutil();
                    }
                });
                builder.show();
            }
        });*/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("xác nhận xóa sản phẩm");
                builder.setMessage("bạn có chắc muốn xóa sản phẩm này");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (MainActivity.arrayListgiohang.size()<=0){
                            textViewthongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.arrayListgiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            Eventutil();
                            if (MainActivity.arrayListgiohang.size()<=0){
                                textViewthongbao.setVisibility(View.VISIBLE);
                            }else {
                                textViewthongbao.setVisibility(View.INVISIBLE);//an di
                                giohangAdapter.notifyDataSetChanged();
                                Eventutil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        Eventutil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void Eventutil() {
        long tongtien = 0;
        for (int i=0;i<MainActivity.arrayListgiohang.size();i++){
            tongtien +=MainActivity.arrayListgiohang.get(i).getGia();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien)+ "đ");
    }

    private void checkdata() {//kiem tra xem cos sp hay khong
        if (MainActivity.arrayListgiohang.size()<=0){
            giohangAdapter.notifyDataSetChanged();
            textViewthongbao.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        }else {
            giohangAdapter.notifyDataSetChanged();
            textViewthongbao.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }

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
        listView = findViewById(R.id.listviewgiohang);
        textViewthongbao = findViewById(R.id.textviewgiohangnull);
        txttongtien=findViewById(R.id.textviewbill);
        btnthanhtoan=findViewById(R.id.btnthanhtoan);
        btnnext=findViewById(R.id.btntnext);
        toolbar=findViewById(R.id.toolbargiohang);
        giohangAdapter = new GiohangAdapter(GiohangActivity.this,MainActivity.arrayListgiohang);
        listView.setAdapter(giohangAdapter);


    }
}