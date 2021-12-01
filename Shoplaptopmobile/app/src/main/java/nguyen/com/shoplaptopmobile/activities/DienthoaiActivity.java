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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nguyen.com.shoplaptopmobile.Adapter.Dien_thoai_adapter;
import nguyen.com.shoplaptopmobile.R;
import nguyen.com.shoplaptopmobile.model.Sanpham;
import nguyen.com.shoplaptopmobile.utilities.CheckConnection;
import nguyen.com.shoplaptopmobile.utilities.Server;

public class DienthoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    Dien_thoai_adapter dien_thoai_adapter;
    ArrayList<Sanpham> arrayList;

    boolean isLoading = false;
    boolean limitdata =false;
    View footerview;
    myHandler myHandler;
    int iddt=0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);
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
                intent.putExtra("thongtinsp",arrayList.get(i));
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
                "https://phamvannguyena4.000webhostapp.com/Server/banhang/getsp.php?page=" + String.valueOf(Page), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0 ;
                String tendt;
                int gia;
                String hinhanhdt;
                String mota;
                int idspdt ;
                if (response!=null && response.length()!=2  ){
                    listView.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tendt=jsonObject.getString("tensp");
                            gia = jsonObject.getInt("giasp");
                            hinhanhdt=jsonObject.getString("hinhanhsp");
                            mota=jsonObject.getString("mota");
                            idspdt = jsonObject.getInt("idsanpham");

                            arrayList.add(new Sanpham(id,tendt,gia,hinhanhdt,mota,idspdt));
                            dien_thoai_adapter.notifyDataSetChanged();

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
                HashMap<String,String> hashMap = new HashMap<String,String>();
                hashMap.put("idloaisanpham",String.valueOf(iddt));
                return hashMap;
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
        iddt = getIntent().getIntExtra("idloaisp1",-1);
        Log.d("iddt:", String.valueOf(iddt));
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbardienthoai);
        listView = findViewById(R.id.listviewdienthoai);
        arrayList = new ArrayList<>();
        dien_thoai_adapter = new Dien_thoai_adapter(getApplicationContext(),arrayList);
        listView.setAdapter(dien_thoai_adapter);// truyền vào bản vẽ

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