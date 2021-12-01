package nguyen.com.shoplaptopmobile.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

import nguyen.com.shoplaptopmobile.R;
import nguyen.com.shoplaptopmobile.utilities.CheckConnection;

public class ThongtinKHActivity extends AppCompatActivity {

    EditText edittxttenkh,edittxtsdt,edittxtemail;
    Button btnxacnhan, btntrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_khactivity);
        Anhxa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            Actionbutton();
        }else {
            CheckConnection.ShowToast_short(getApplicationContext(),"kiem tra lai ket noi");
        }

    }

    private void Actionbutton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String tenkh= edittxttenkh.getText().toString().trim();
                final String sodt = edittxtsdt.getText().toString().trim();
                final String email = edittxtemail.getText().toString().trim();

                if(tenkh.length()>0&&sodt.length()>0&&email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://phamvannguyena4.000webhostapp.com/Server/banhang/thongtinkh.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response1) {
                            if(Integer.parseInt(response1)>0){
                                RequestQueue request = Volley.newRequestQueue(getApplicationContext());
                                StringRequest strequest = new StringRequest(Request.Method.POST, "https://phamvannguyena4.000webhostapp.com/Server/banhang/donhang.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            MainActivity.arrayListgiohang.clear();
                                            Toast.makeText(getApplicationContext(),"Đã thanh toán thành công",Toast.LENGTH_LONG).show();
                                            Intent in= new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(in);
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"Thanh toán thất bại",Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray js = new JSONArray();
                                        for(int i=0;i<MainActivity.arrayListgiohang.size();i++){
                                            JSONObject object = new JSONObject();
                                            try {
                                                object.put("madonhang",Integer.parseInt(response1));
                                                object.put("masp",MainActivity.arrayListgiohang.get(i).getId());
                                                object.put("tensp",MainActivity.arrayListgiohang.get(i).getTensp());
                                                object.put("giasp",MainActivity.arrayListgiohang.get(i).getGia());
                                                object.put("soluongsp",MainActivity.arrayListgiohang.get(i).getSoluongsp());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            js.put(object);

                                        }
                                        HashMap<String ,String> has = new HashMap<String, String>();
                                        has.put("json",js.toString());
                                        return has;
                                    }
                                };
                                request.add(strequest);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hs = new HashMap<String, String>();
                            hs.put("tenkh",tenkh);
                            hs.put("sdt",sodt);
                            hs.put("email",email);

                            return hs;
                        }
                    };
                    requestQueue.add(stringRequest);

                }
                else {
                    Toast.makeText(getApplicationContext(),"Hãy kiểm tra lại dữ liệu",Toast.LENGTH_LONG).show();
                }


            }
        });

    }



    private void Anhxa() {
        edittxttenkh = findViewById(R.id.edittxttenkh);
        edittxtsdt = findViewById(R.id.edittxtsdt);
        edittxtemail = findViewById(R.id.edittxtemail);
        btntrove = findViewById(R.id.btntrove);
        btnxacnhan  = findViewById(R.id.btnxacnhan);
    }
}