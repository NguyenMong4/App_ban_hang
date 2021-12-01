package nguyen.com.shoplaptopmobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nguyen.com.shoplaptopmobile.Adapter.LoaispAdapter;
import nguyen.com.shoplaptopmobile.Adapter.SanphamAdapter;
import nguyen.com.shoplaptopmobile.R;
import nguyen.com.shoplaptopmobile.model.Giohang;
import nguyen.com.shoplaptopmobile.model.Loaisp;
import nguyen.com.shoplaptopmobile.model.Sanpham;
import nguyen.com.shoplaptopmobile.utilities.CheckConnection;
import nguyen.com.shoplaptopmobile.utilities.Server;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    ArrayList<Loaisp> loaispArrayList;
    LoaispAdapter loaispAdapter;

    ArrayList<Sanpham> sanphamArrayList;
    SanphamAdapter sanphamAdapter;

    public static ArrayList<Giohang> arrayListgiohang;

    public int id=0;
    public String tenloaisp ="";
    public String hinhanhloaisp = "";
    int idsp;
    String tensp;
    String hinhsp;
    int giamgia;
    String mota;
    int giasp;
    int idloaisp=0;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            actionbar();
            actionviewfliper();
            Getdulieuloaisp();
            Getsanphammoi();
            CatchonItem();
        }else {
            CheckConnection.ShowToast_short(getApplicationContext(),"Kiem tra lai ket noi");
            finish();
        }

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

    private void CatchonItem() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(),"Kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);//đóng thanh menu
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent1 = new Intent(MainActivity.this,DienthoaiActivity.class);
                            intent1.putExtra("idloaisp1",loaispArrayList.get(i).getId());//truyền dũ liệu sang màn hình khác
                            startActivity(intent1);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(),"Kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);//đóng thanh menu
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent2 = new Intent(MainActivity.this,LaptopActivity.class);
                            intent2.putExtra("idloaisp2",loaispArrayList.get(i).getId());//truyền dũ liệu sang màn hình khác
                            //Log.d("bbb", String.valueOf(loaispArrayList.get(i).getId()));
                            startActivity(intent2);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(),"Kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);//đóng thanh menu
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent3 = new Intent(MainActivity.this,LienheActivity.class);

                            startActivity(intent3);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(),"Kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);//đóng thanh menu
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent4 = new Intent(MainActivity.this,DienthoaiActivity.class);
                            startActivity(intent4);
                        }else {
                            CheckConnection.ShowToast_short(getApplicationContext(),"Kiem tra lai ket noi");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);//đóng thanh menu
                        break;
                }
            }
        });
    }

    private void Getsanphammoi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://phamvannguyena4.000webhostapp.com/Server/banhang/getsanphammoi.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i = 0;i<response.length();i++){
                        try{
                            JSONObject jsonObject = response.getJSONObject(i);
                            idsp=jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensp").trim();
                            hinhsp = jsonObject.getString("hinhanhsp");
                            giasp = jsonObject.getInt("giasp");
                            mota = jsonObject.getString("mota");
                            idloaisp = jsonObject.getInt("idsanpham");
                            sanphamArrayList.add(new Sanpham(idsp,tensp,giasp,hinhsp,mota,idloaisp));
                            sanphamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionviewfliper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cdn.tgdd.vn/Products/Images/44/231244/macbook-air-m1-2020-gray-600x600.jpg");
        mangquangcao.add("https://cdn.tgdd.vn/Products/Images/44/238139/lg-gram-17-i7-17z90pgah78a5-3-600x600.jpg");
        mangquangcao.add("https://znews-photo.zadn.vn/w660/Uploaded/yqdlcqrwq/2021_07_07/19113072021.jpg");
        for (int i=0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);

        viewFlipper.setInAnimation(animation_slide_out);
        viewFlipper.setInAnimation(animation_slide_in);
        }
    private void Getdulieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://phamvannguyena4.000webhostapp.com/Server/banhang/getloaisp.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp").trim();
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            loaispArrayList.add(new Loaisp(id,tenloaisp,hinhanhloaisp));

                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                    loaispArrayList.add(3,new Loaisp(0,"Thông tin","https://debat.su.ust.hk/images/info.png"));
                    loaispArrayList.add(4,new Loaisp(0,"Liên hệ","https://voip24h.vn/wp-content/uploads/2019/03/phone-png-mb-phone-icon-256.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //checkconectwifi.returnmessage(getApplicationContext(),error.toString());

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
    }

    private void Anhxa() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawblemanhinhchinh);
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper =(ViewFlipper) findViewById(R.id.viewflippermanhinhchinh);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewds);
        listView = (ListView) findViewById(R.id.listviewmanhinhchinh);
        loaispArrayList = new ArrayList<>();
        loaispArrayList.add(0,new Loaisp(0,"Trang Chính","https://bikersaigon.net/wp-content/uploads/2019/02/ngon_la_ca_mau_icon_home-300x300.png"));
        loaispAdapter = new LoaispAdapter(loaispArrayList,getApplicationContext());
        listView.setAdapter(loaispAdapter);

        sanphamArrayList = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),sanphamArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        //recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL,true));
        recyclerView.setAdapter(sanphamAdapter);

        if (arrayListgiohang!=null){

        }else {
            arrayListgiohang = new ArrayList<>();
        }

    }
}