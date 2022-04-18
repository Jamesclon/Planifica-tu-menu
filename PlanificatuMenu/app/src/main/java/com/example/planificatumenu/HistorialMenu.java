package com.example.planificatumenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class HistorialMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_menu);
    }

//    Botones
//    Botón VOLVER

    public void Volver(View view){
        Intent volver = new Intent(this, MainActivity.class);
        startActivity(volver);
    }

}