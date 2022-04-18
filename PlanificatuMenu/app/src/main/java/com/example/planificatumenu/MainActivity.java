package com.example.planificatumenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    Botones
//    Botón HISTORIAL

    public void Historial(View view){
        Intent historial = new Intent(this, HistorialMenu.class);
        startActivity(historial);
    }

//    Botón NUEVO MENU

    public void Nuevo(View view){
        Intent nuevo = new Intent(this, NuevoMenu.class);
        startActivity(nuevo);
    }

    //    Botón SALIR

    public void Salir(View view){
       finishAffinity();
       System.exit(0);
    }
}