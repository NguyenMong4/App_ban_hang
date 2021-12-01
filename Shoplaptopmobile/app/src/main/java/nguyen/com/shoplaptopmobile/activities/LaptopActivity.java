package nguyen.com.shoplaptopmobile.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nguyen.com.shoplaptopmobile.Adapter.Dien_thoai_adapter;
import nguyen.com.shoplaptopmobile.Adapter.LaptopAdapter;
import nguyen.com.shoplaptopmobile.R;
import nguyen.com.shoplaptopmobile.model.Sanpham;
import nguyen.com.shoplaptopmobile.utilities.CheckConnection;
import nguyen.com.shoplaptopmobile.utilities.Server;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    LaptopAdapter laptopAdapter ;
    ArrayList<Sanpham> sanphamArrayList;
    int idlaptop=0;
    int page = 1;
    boolean isLoading = false;
    boolean limitdata =false;
    View footerview;
    myHandler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdloaisp();
            actiontoolbar();//tro ve
            Getdata(page);
            Loadmore_data();

        }else {
            CheckConnection.ShowToast_short(getApplicationContext(),"kiem tra lai ket noi");
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

    private void Loadmore_data() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChitietspActivity.class);
                intent.putExtra("thongtinsp",sanphamArrayList.get(i));
                startActivity(intent);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotelItem) {
                if (FirstItem + VisibleItem == TotelItem && TotelItem !=0 && isLoading==false && limitdata==false){
                    isLoading=true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();

                }

            }
        });
    }
    private void Getdata(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://phamvannguyena4.000webhostapp.com/Server/banhang/getsp2.php?page=" + String.valueOf(Page), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int idlt = 0;
                String tenlt;
                int gialt;
                String hinhanhlt;
                String mota;
                int idsplt;
                if (response != null && response.length() != 2) {
                    //listView.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idlt = jsonObject.getInt("id");
                            tenlt = jsonObject.getString("tensp");
                            gialt = jsonObject.getInt("giasp");
                            hinhanhlt = jsonObject.getString("hinhanhsp");
                            mota = jsonObject.getString("mota");
                            idsplt = jsonObject.getInt("idsanpham");

                            sanphamArrayList.add(new Sanpham(idlt, tenlt, gialt, hinhanhlt, mota, idsplt));
                            Log.d("tensp:",sanphamArrayList.get(0).getTensp());
                            laptopAdapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    limitdata=true;
                    listView.removeFooterView(footerview);
                    CheckConnection.ShowToast_short(getApplicationContext(),"da het du lieu");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idloaisanpham",String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void GetIdloaisp() {
        idlaptop = getIntent().getIntExtra("idloaisp2",-1);
        Log.d("id:", String.valueOf(idlaptop));

    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbarlaptop);
        listView = findViewById(R.id.listviewlaptop);
        sanphamArrayList = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(getApplicationContext(),sanphamArrayList);
        listView.setAdapter(laptopAdapter);// truyền vào bản vẽ

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progessbar,null);
        myHandler = new myHandler();
    }
    public  class myHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listView.addFooterView(footerview);
                    break;
                case 1:
                    Getdata(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message= myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
}