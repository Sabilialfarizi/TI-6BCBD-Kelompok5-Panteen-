package com.example.panteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegister extends AppCompatActivity {
    EditText username,password, email,phone,cpass;
     Button signIN;
     ProgressBar loading;
    private static  String URL_REGIST = "http://192.168.0.11/Panteen/android/register.php";
    String success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.loading);
        username = (EditText) findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText)findViewById(R.id.phone);
        cpass = (EditText)findViewById(R.id.cpass);

        signIN = (Button) findViewById(R.id.signIN);

        signIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                benar();
            }
        });
        cek_form(username);
        cek_form(password);
        cek_form(email);
        cek_form(phone);
        cek_form(cpass);
    }
    void benar() {
        if (username.getText().length() < 1) {
            username.setBackgroundResource(R.drawable.form_error);
        }
        if (password.getText().length() < 1) {
            password.setBackgroundResource(R.drawable.form_error);
        }
        if (email.getText().length() < 1) {
            email.setBackgroundResource(R.drawable.form_error);
        }
        if (phone.getText().length() < 1) {
            phone.setBackgroundResource(R.drawable.form_error);
        }
        if (cpass.getText().length() < 1) {
            cpass.setBackgroundResource(R.drawable.form_error);
        }else {
            Regist();

        }
    }
    private void Regist(){
        loading.setVisibility(View.VISIBLE);
        signIN.setVisibility(View.GONE);
        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final  String email = this.email.getText().toString().trim();
        final  String phone = this.phone.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            success = jsonObject.optString("success");

                            if (success.equals("1")) {
                                Toast.makeText(ActivityRegister.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ActivityRegister.this, sukses.class));
                                loading.setVisibility(View.GONE);
                                signIN.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ActivityRegister.this, "Register Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            signIN.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityRegister.this, "Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        signIN.setVisibility(View.VISIBLE);

                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);
                params.put("phone", phone);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
    public void SignIn(View view) {
        Intent intent= new Intent(ActivityRegister.this, ActivityLogin.class);
        startActivity(intent);
    }



}
