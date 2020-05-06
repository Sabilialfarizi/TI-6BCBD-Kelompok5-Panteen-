package com.example.panteen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void pesan(View view) {
        Intent intent= new Intent(Home.this, menu.class);
        startActivity(intent);
    }

    public void profil(View view) {
        Intent intent= new Intent(Home.this, profile.class);
        startActivity(intent);
    }

    public void pesanan(View view) {
        Intent intent= new Intent(Home.this, pesanan.class);
        startActivity(intent);
    }
}
