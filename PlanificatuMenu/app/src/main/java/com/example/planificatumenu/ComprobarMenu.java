package com.example.planificatumenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ComprobarMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprobar_menu);
    }

//    Botones
//    Botón VOLVER

    public void Volver(View view){
        Intent volver = new Intent(this, NuevoMenu.class);
        startActivity(volver);
    }

//    Botón ACEPTAR

    public void Aceptar(View view){
        Intent aceptar = new Intent(this, VerMenu.class);
        startActivity(aceptar);
    }

//    Botón LUNES

    public void Lunes(View view){
        Intent lunes = new Intent(this, CambiarMenu.class);
        startActivity(lunes);
    }

    //    Botón MARTES

    public void Martes(View view){
        Intent martes = new Intent(this, CambiarMenu.class);
        startActivity(martes);
    }

    //    Botón MIERCOLES

    public void Miercoles(View view){
        Intent miercoles = new Intent(this, CambiarMenu.class);
        startActivity(miercoles);
    }

    //    Botón JUEVES

    public void Jueves(View view){
        Intent jueves = new Intent(this, CambiarMenu.class);
        startActivity(jueves);
    }

    //    Botón VIERNES

    public void Viernes(View view){
        Intent viernes = new Intent(this, CambiarMenu.class);
        startActivity(viernes);
    }

    //    Botón SABADO

    public void Sabado(View view){
        Intent sabado = new Intent(this, CambiarMenu.class);
        startActivity(sabado);
    }

    //    Botón DOMINGO

    public void Domingo(View view){
        Intent domingo = new Intent(this, CambiarMenu.class);
        startActivity(domingo);
    }
}