package com.example.planificatumenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CambiarMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_menu);
    }

//    Botones
//    Botón Aceptar

    public void Aceptar (View view){
        Intent aceptar = new Intent(this, ComprobarMenu.class);
        startActivity(aceptar);
    }

//    Botón CAMBIAR
    public void Cambiar (){
//        PENDIENTE CREAR CAMBIAR DE PLATO
    }

    public void Ver(){
//        PENDIENTE CREAR VER RECETA
    }

}