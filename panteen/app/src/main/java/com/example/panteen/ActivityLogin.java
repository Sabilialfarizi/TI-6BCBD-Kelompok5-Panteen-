package com.example.panteen;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ActivityLogin extends AppCompatActivity {
    EditText username, password;
    Button login;
    ProgressBar loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
                SharedPreferences session=getSharedPreferences("session_login",MODE_PRIVATE);
        if(session.contains("username"))
        {
            finish();
            Intent intent=new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
       }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loading = findViewById(R.id.loading);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                benar();
            }
        });
        cek_form(username);
        cek_form(password);

    }


    void benar() {
        loading.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        if (username.getText().length()<1) {
            username.setBackgroundResource(R.drawable.form_error);
            Toast.makeText(getApplicationContext(), "Username Belum Di isi", Toast.LENGTH_SHORT).show();
        }
        if (password.getText().length()<1) {
            password.setBackgroundResource(R.drawable.form_error);
            Toast.makeText(getApplicationContext(), "Password Belum Di isi", Toast.LENGTH_SHORT).show();
        } else {
            Login();

        }

    }
    void Login(){

final SharedPreferences.Editor session=getSharedPreferences("session_login",MODE_PRIVATE).edit();
        String url="http://192.168.0.11/Panteen/android/login.php";
        StringRequest respon = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.optString("success");
                            if(success.equals("1")) {
                                session.putString("username",username.getText().toString());
                               session.putString("password",password.getText().toString());
                                Toast.makeText(ActivityLogin.this, "Selamat Datang!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ActivityLogin.this, Home.class));
                                loading.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                                }
                           else if(success.equals("0")) {
                                Toast.makeText(getApplicationContext(), "Login Gagal" + " username atau password tidak benar", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                           }

                        } catch (JSONException e){
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);
                    }
                }
        ){
            protected Map<String, String>getParams(){
                Map<String, String>kirm_form=new HashMap<String, String>();
                kirm_form.put("username",username.getText().toString());
                kirm_form.put("password",password.getText().toString());
                return kirm_form;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(ActivityLogin.this);
        requestQueue.add(respon);
    }

    void cek_form(final EditText editText)
    {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(count < 1)
                {
                    editText.setBackgroundResource(R.drawable.form_error);



                }
                else
                {
                    editText.setBackgroundResource(R.drawable.edit_round);


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void RegisterInHere(View view) {
        Intent intent= new Intent(ActivityLogin.this, ActivityRegister.class);
        startActivity(intent);

    }
}

