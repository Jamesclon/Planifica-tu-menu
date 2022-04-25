package com.example.planificatumenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HistorialMenu extends AppCompatActivity {

    private TextView tvTitulo, tvLunesPrimero, tvLunesSegundo, tvMartesPrimero, tvMartesSegundo;
    private TextView tvMiercolesPrimero, tvMiercolesSegundo, tvJuevesPrimero, tvJuevesSegundo;
    private TextView tvViernesPrimero, tvViernesSegundo, tvSabadoPrimero, tvSabadoSegundo;
    private TextView tvDomingoPrimero, tvDomingoSegundo;
    private List<String> arrayListHistorial = new ArrayList<>();
    private List<String> arrayListHistorialID = new ArrayList<>();
    private String id, getId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_menu);

        tvTitulo = (TextView) findViewById(R.id.textViewHistorialTituloMenu);
        tvLunesPrimero = (TextView) findViewById(R.id.textViewHistorialLunesPrimero);
        tvLunesSegundo = (TextView) findViewById(R.id.textViewHistorialLunesSegundo);
        tvMartesPrimero = (TextView) findViewById(R.id.textViewHistorialMartesPrimero);
        tvMartesSegundo = (TextView) findViewById(R.id.textViewHistorialMartesSegundo);
        tvMiercolesPrimero = (TextView) findViewById(R.id.textViewHistorialMiercolesPrimero);
        tvMiercolesSegundo = (TextView) findViewById(R.id.textViewHistorialMiercolesSegundo);
        tvJuevesPrimero = (TextView) findViewById(R.id.textViewHistorialJuevesPrimero);
        tvJuevesSegundo = (TextView) findViewById(R.id.textViewHistorialJuevesSegundo);
        tvViernesPrimero = (TextView) findViewById(R.id.textViewHistorialViernesPrimero);
        tvViernesSegundo = (TextView) findViewById(R.id.textViewHistorialViernesSegundo);
        tvSabadoPrimero = (TextView) findViewById(R.id.textViewHistorialSabadoPrimero);
        tvSabadoSegundo = (TextView) findViewById(R.id.textViewHistorialSabadoSegundo);
        tvDomingoPrimero = (TextView) findViewById(R.id.textViewHistorialDomingoPrimero);
        tvDomingoSegundo = (TextView) findViewById(R.id.textViewHistorialDomingoSegundo);


        leerTablaHistorial();

        //Si venimos del Activity ComprobarMenu, recuperamos ese menú
        getId = getIntent().getStringExtra("id");
        if(!(getId == null)){
            escribirMenusSemana(getId);
        }

    }


//    Botones
//    Botón VOLVER

    public void Volver(View view){
        Intent volver = new Intent(this, MainActivity.class);
        startActivity(volver);
    }

// Botón Seleccionar del historial
    public void dialogHistorial(View view){

        //Convertimos el ArrayList en un Array convencional
        String[] historialID = arrayListHistorialID.toArray(new String[0]);

        //Creamos la lista donde el usuario debe elegir el menú que quiere visualizar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar del Histórico").setItems(historialID, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                id = historialID[i];
                escribirMenusSemana(id);
            }
        });
        builder.create().show();

    }

//    Métodos a utilizar
//    Leer menús almacenados en el historial
    public void leerTablaHistorial(){

        //Creamos la conexión a la BD en modo lectura
        AccesoBD accesoBD = new AccesoBD(this,"recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD.getReadableDatabase();

        //Creamos la sentencia Query

        Cursor leerID = BaseDeDatos.rawQuery
                ("SELECT DISTINCT id  FROM historial h ORDER BY id ", null);


        //Metemos todos los datos en el ArrayList
        while (leerID.moveToNext()){
            arrayListHistorialID.add(leerID.getString(0));
        }
        BaseDeDatos.close();

    }

    // Este método va a escribir todos los platos del historial elegido
    public void escribirMenusSemana(String historialID){

        //Creamos la conexión a la BD en modo lectura
        AccesoBD accesoBD = new AccesoBD(this,"recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD.getReadableDatabase();

        //Creamos las sentencias Query
        Cursor leerTabla = BaseDeDatos.rawQuery
                ("SELECT * FROM historial h WHERE id = '"+historialID+"'", null);

        //Escribimos los datos en los textview
        while (leerTabla.moveToNext()){
            tvTitulo.setText("Creado: "+leerTabla.getString(0));
            tvLunesPrimero.setText(leerTabla.getString(1));
            tvLunesSegundo.setText(leerTabla.getString(2));
            tvMartesPrimero.setText(leerTabla.getString(3));
            tvMartesSegundo.setText(leerTabla.getString(4));
            tvMiercolesPrimero.setText(leerTabla.getString(5));
            tvMiercolesSegundo.setText(leerTabla.getString(6));
            tvJuevesPrimero.setText(leerTabla.getString(7));
            tvJuevesSegundo.setText(leerTabla.getString(8));
            tvViernesPrimero.setText(leerTabla.getString(9));
            tvViernesSegundo.setText(leerTabla.getString(10));
            tvSabadoPrimero.setText(leerTabla.getString(11));
            tvSabadoSegundo.setText(leerTabla.getString(12));
            tvDomingoPrimero.setText(leerTabla.getString(13));
            tvDomingoSegundo.setText(leerTabla.getString(14));
        }

    }

}