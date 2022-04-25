package com.example.planificatumenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComprobarMenu extends AppCompatActivity implements Serializable{


    private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13, tv14;
    private TextView tvTitulo;
    private String id;
    private List<String> listaPlatos = new ArrayList<>();
    private List<String> listaSegundos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprobar_menu);

        tvTitulo = (TextView) findViewById(R.id.textViewComprobarTitulo);
        tv1 = (TextView) findViewById(R.id.textViewComprobarLunesPlatos);
        tv2 = (TextView) findViewById(R.id.textViewComprobarLunesPlatos2);
        tv3 = (TextView) findViewById(R.id.textViewComprobarMartesPlatos);
        tv4 = (TextView) findViewById(R.id.textViewComprobarMartesPlatos2);
        tv5 = (TextView) findViewById(R.id.textViewComprobarMiercolesPlatos);
        tv6 = (TextView) findViewById(R.id.textViewComprobarMiercolesPlatos2);
        tv7 = (TextView) findViewById(R.id.textViewComprobarJuevesPlatos);
        tv8 = (TextView) findViewById(R.id.textViewComprobarJuevesPlatos2);
        tv9 = (TextView) findViewById(R.id.textViewComprobarViernesPlatos);
        tv10 = (TextView) findViewById(R.id.textViewComprobarViernesPlatos2);
        tv11 = (TextView) findViewById(R.id.textViewComprobarSabadoPlatos);
        tv12 = (TextView) findViewById(R.id.textViewComprobarSabadoPlatos2);
        tv13 = (TextView) findViewById(R.id.textViewComprobarDomingoPlatos);
        tv14 = (TextView) findViewById(R.id.textViewComprobarDomingoPlatos2);

        id = getIntent().getStringExtra("historialID");
        System.out.println("ComprobarMenuID correcto: "+id);

        //Creamos la conexión a la BD en modo lectura
        AccesoBD accesoBD = new AccesoBD(this,"recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD.getReadableDatabase();

        Cursor leerPlatos = BaseDeDatos.rawQuery
                ("SELECT * FROM historial h WHERE id = '"+ id +"' ", null);
        System.out.println(1);
        int i = 1;
//            while (leerPlatos.moveToNext()){
//                listaPlatos.add(leerPlatos.getString(i));
//                i++;
//            }
        while (leerPlatos.moveToNext()){

            tvTitulo.setText("Menú "+leerPlatos.getString(0));
            tv1.setText(leerPlatos.getString(1));
            tv2.setText(leerPlatos.getString(2));
            tv3.setText(leerPlatos.getString(3));
            tv4.setText(leerPlatos.getString(4));
            tv5.setText(leerPlatos.getString(5));
            tv6.setText(leerPlatos.getString(6));
            tv7.setText(leerPlatos.getString(7));
            tv8.setText(leerPlatos.getString(8));
            tv9.setText(leerPlatos.getString(9));
            tv10.setText(leerPlatos.getString(10));
            tv11.setText(leerPlatos.getString(11));
            tv12.setText(leerPlatos.getString(12));
            tv13.setText(leerPlatos.getString(13));
            tv14.setText(leerPlatos.getString(14));

        }

        BaseDeDatos.close();


    }

//    Botones
//    Botón VOLVER

    public void Volver(View view){
        Intent volver = new Intent(this, NuevoMenu.class);
        startActivity(volver);
    }

//    Botón ACEPTAR

    public void Aceptar(View view){
        Intent aceptar = new Intent(this, HistorialMenu.class);
        aceptar.putExtra("id", id);
        startActivity(aceptar);
    }

//    Botón LUNES

    public void Lunes(View view){
        Intent lunes = new Intent(this, CambiarMenu.class);
        lunes.putExtra("primero", tv1.getText());
        lunes.putExtra("segundo", tv2.getText());
        lunes.putExtra("getDiaPrimero", "lp");
        lunes.putExtra("getDiaSegundo", "ls");
        lunes.putExtra("idHistorial", id);
        startActivity(lunes);
    }

    //    Botón MARTES

    public void Martes(View view){
        Intent martes = new Intent(this, CambiarMenu.class);
        martes.putExtra("primero", tv3.getText());
        martes.putExtra("segundo", tv4.getText());
        martes.putExtra("getDiaPrimero", "mp");
        martes.putExtra("getDiaSegundo", "ms");
        martes.putExtra("idHistorial", id);
        startActivity(martes);
    }

    //    Botón MIERCOLES

    public void Miercoles(View view){
        Intent miercoles = new Intent(this, CambiarMenu.class);
        miercoles.putExtra("primero", tv5.getText());
        miercoles.putExtra("segundo", tv6.getText());
        miercoles.putExtra("getDiaPrimero", "xp");
        miercoles.putExtra("getDiaSegundo", "xs");
        miercoles.putExtra("idHistorial", id);
        startActivity(miercoles);
    }

    //    Botón JUEVES

    public void Jueves(View view){
        Intent jueves = new Intent(this, CambiarMenu.class);
        jueves.putExtra("primero", tv7.getText());
        jueves.putExtra("segundo", tv8.getText());
        jueves.putExtra("getDiaPrimero", "jp");
        jueves.putExtra("getDiaSegundo", "js");
        jueves.putExtra("idHistorial", id);
        startActivity(jueves);
    }

    //    Botón VIERNES

    public void Viernes(View view){
        Intent viernes = new Intent(this, CambiarMenu.class);
        viernes.putExtra("primero", tv9.getText());
        viernes.putExtra("segundo", tv10.getText());
        viernes.putExtra("getDiaPrimero", "vp");
        viernes.putExtra("getDiaSegundo", "vs");
        viernes.putExtra("idHistorial", id);
        startActivity(viernes);
    }

    //    Botón SABADO

    public void Sabado(View view){
        Intent sabado = new Intent(this, CambiarMenu.class);
        sabado.putExtra("primero", tv11.getText());
        sabado.putExtra("segundo", tv12.getText());
        sabado.putExtra("getDiaPrimero", "sp");
        sabado.putExtra("getDiaSegundo", "ss");
        sabado.putExtra("idHistorial", id);
        startActivity(sabado);
    }

    //    Botón DOMINGO

    public void Domingo(View view){
        Intent domingo = new Intent(this, CambiarMenu.class);
        domingo.putExtra("primero", tv13.getText());
        domingo.putExtra("segundo", tv14.getText());
        domingo.putExtra("getDiaPrimero", "dp");
        domingo.putExtra("getDiaSegundo", "ds");
        domingo.putExtra("idHistorial", id);
        startActivity(domingo);
    }



}