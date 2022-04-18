package com.example.planificatumenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VerMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_menu);
    }

//    Botones
//    Botón ACEPTAR
    public void Aceptar(View view){
        Intent aceptar = new Intent(this, HistorialMenu.class);
        startActivity(aceptar);
    }

//    Botón RECETA
    public void Receta(){
//        PENDIENTE CREAR VISOR RECETA
    }

}