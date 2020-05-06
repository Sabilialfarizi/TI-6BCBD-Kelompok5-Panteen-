package com.example.panteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {
TextView username,email,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final SharedPreferences sharedPreferences=getSharedPreferences("sesion_login",MODE_PRIVATE);
        Button logout=findViewById(R.id.logout);
        profile();

        username = (TextView) findViewById(R.id.username);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().clear().apply();
                Intent intent=new Intent(getApplicationContext(),ActivityLogin.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Anda Telah Logout", Toast.LENGTH_SHORT).show();

            }
        });
    }
    void profile(){

        final SharedPreferences.Editor session=getSharedPreferences("session_login",MODE_PRIVATE).edit();
        String url="http://192.168.0.11/Panteen/android/user_control.php";
        StringRequest respon = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.optString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");
                            if(success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                   String strusername = object.getString("username").trim();
                                    String strphone = object.getString("phone").trim();
                                    String stremail = object.getString("email").trim();

                                    username.setText(strusername);
                                    phone.setText(strphone);
                                    email.setText(stremail);
                                }
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(profile.this, "Error Reading Detail" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            protected Map<String, String> getParams(){
                Map<String, String>kirm_form=new HashMap<String, String>();
                kirm_form.put("username",username.getText().toString());
                kirm_form.put("username",username.getText().toString());
                kirm_form.put("email",email.getText().toString());
                kirm_form.put("phone",phone.getText().toString());
                return kirm_form;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(profile.this);
        requestQueue.add(respon);
    }

    public void btn_back(View view) {
        Intent intent= new Intent(profile.this, Home.class);
        startActivity(intent);
    }

    public void logout(View view) {
    }
}
