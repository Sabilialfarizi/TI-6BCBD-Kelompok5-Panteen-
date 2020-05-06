package com.example.panteen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class pesanan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
    public void btn_back(View view) {
        Intent intent= new Intent(pesanan.this, Home.class);
        startActivity(intent);
    }

    public void proses(View view) {
        Intent intent= new Intent(pesanan.this, riwayat.class);
        startActivity(intent);
    }
}


