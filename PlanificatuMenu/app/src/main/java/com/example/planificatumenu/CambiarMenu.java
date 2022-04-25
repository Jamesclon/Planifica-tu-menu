package com.example.planificatumenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CambiarMenu extends AppCompatActivity {

    private TextView tvPrimero, tvSegundo;
    private String getPrimero, getSegundo,  idHistorial, primerPlato, segundoPlato, getDiaPrimero, getDiaSegundo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_menu);

        tvPrimero = (TextView) findViewById(R.id.textViewCambiarPrimerPlato);
        tvSegundo = (TextView) findViewById(R.id.textViewCambiarSegundoPlato);
        //Datos que envía la anterior activity, ComprobarMenu
        getPrimero = getIntent().getStringExtra("primero");
        getSegundo = getIntent().getStringExtra("segundo");
        getDiaPrimero = getIntent().getStringExtra("getDiaPrimero");
        getDiaSegundo = getIntent().getStringExtra("getDiaSegundo");
        idHistorial = getIntent().getStringExtra("idHistorial");


        //Escribimos los platos en los textview
        tvPrimero.setText(getPrimero);
        tvSegundo.setText(getSegundo);
    }

//    Botones
//    Botón Aceptar

    public void Aceptar (View view){
        //Creamos la conexión a la BD en modo escritura para guardar los cambios
        AccesoBD accesoBD = new AccesoBD(this,"recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD.getWritableDatabase();

        BaseDeDatos.execSQL("UPDATE historial SET "+getDiaPrimero+" = '"+tvPrimero.getText()
                +"', "+getDiaSegundo+" = '"+tvSegundo.getText()+"' WHERE id = '"+idHistorial+"'");

        System.out.println("Producido update en id: "+idHistorial+", en columnas: "+(getDiaPrimero+getDiaSegundo)+
                ", los platos: "+((String) tvPrimero.getText() + tvSegundo.getText()));

        Intent aceptar = new Intent(this, ComprobarMenu.class);
        aceptar.putExtra("historialID", idHistorial);
        startActivity(aceptar);
    }

//    Botón CAMBIAR

    public void CambiarPrimeros (View view){

        //Creamos la conexión a la BD
        AccesoBD accesoBD = new AccesoBD(this,"recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD.getReadableDatabase();

        //Comprobamos el tipo de plato
        Cursor tipoPlato = BaseDeDatos.rawQuery
                ("SELECT orden_plato FROM platos p WHERE p.nombre_plato = '"+tvPrimero.getText()+"' ", null);
        int numTipoPlato = 0;

        while (tipoPlato.moveToNext()){
            numTipoPlato = tipoPlato.getInt(0);
        }

        //Creamos un switch que cambie el plato por otro del mismo tipo: primer plato normal o vegano.
        switch (numTipoPlato){
            case 1:
                Cursor platoCambio1 = BaseDeDatos.rawQuery
                        ("SELECT nombre_plato FROM platos p WHERE p.orden_plato = 1 ORDER BY RANDOM() LIMIT 1" , null);
                while(platoCambio1.moveToNext()){
                    primerPlato = platoCambio1.getString(0);
                    tvPrimero.setText(primerPlato);
                    BaseDeDatos.close();
                }

                break;
            case 2:
                Cursor platoCambio2 = BaseDeDatos.rawQuery
                        ("SELECT nombre_plato FROM platos p WHERE p.orden_plato = 2 ORDER BY RANDOM() LIMIT 1" , null);
                while (platoCambio2.moveToNext()){
                    primerPlato = platoCambio2.getString(0);
                    tvPrimero.setText(primerPlato);
                    BaseDeDatos.close();
                }

                System.out.println("llega hasta el fin del case 2");
                break;
        }

    }

    public void CambiarSegundos (View view){

        //Creamos la conexión a la BD
        AccesoBD accesoBD3 = new AccesoBD(this,"recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD3.getReadableDatabase();
        //Comprobamos el tipo de plato
        Cursor tipoPlato = BaseDeDatos.rawQuery
                ("SELECT orden_plato FROM platos p WHERE nombre_plato = '"+tvSegundo.getText()+"' ", null);
        int numTipoPlato = 0;
        while (tipoPlato.moveToNext()){
            numTipoPlato = tipoPlato.getInt(0);
        }

        //Creamos un switch que cambie el plato por otro del mismo tipo: primer plato normal o vegano.
        switch (numTipoPlato){
            case 3:
                Cursor platoCambioPescado = BaseDeDatos.rawQuery
                        ("SELECT nombre_plato FROM platos p WHERE p.orden_plato = 3 ORDER BY RANDOM() LIMIT 1" , null);
                while (platoCambioPescado.moveToNext()){
                    segundoPlato = platoCambioPescado.getString(0);
                    tvSegundo.setText(segundoPlato);
                    BaseDeDatos.close();
                }

                break;
            case 4:
                Cursor platoCambioCarne = BaseDeDatos.rawQuery
                        ("SELECT nombre_plato FROM platos p WHERE p.orden_plato = 4 ORDER BY RANDOM() LIMIT 1" , null);
                while (platoCambioCarne.moveToNext()){
                    segundoPlato = platoCambioCarne.getString(0);
                    tvSegundo.setText(segundoPlato);
                    BaseDeDatos.close();
                }

                break;
            case 5:
                Cursor platoCambioVegano = BaseDeDatos.rawQuery
                        ("SELECT nombre_plato FROM platos p WHERE p.orden_plato = 5 ORDER BY RANDOM() LIMIT 1" , null);
                while (platoCambioVegano.moveToNext()){
                    segundoPlato = platoCambioVegano.getString(0);
                    tvSegundo.setText(segundoPlato);
                    BaseDeDatos.close();
                }
                break;
        }

    }


    public void VerPrimeros (View view){

        //Creamos la conexión a la BD
        AccesoBD accesoBD2 = new AccesoBD(this,"recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD2.getReadableDatabase();

        //Recuperar los ingredientes del plato deseado
        Cursor ingredientes = BaseDeDatos.rawQuery
                ("SELECT nombre_ingrediente  FROM receta_ingredientes ri WHERE ri.nombre_plato = '"+tvPrimero.getText()+"'", null);

        List<String> arrayIngredientes = new ArrayList<>();

        //Mostramos los ingredientes en un Alertdialog
        AlertDialog.Builder ADingredientes = new AlertDialog.Builder(this);
        ADingredientes.setTitle("Ingredientes");
        while ((ingredientes.moveToNext())){
            arrayIngredientes.add(ingredientes.getString(0)+"\n");
            BaseDeDatos.close();
        }
        ADingredientes.setMessage(arrayIngredientes.toString().replace(',',' ').replace('[',' ').replace(']',' '));
        ADingredientes.show();

    }


    public void VerSegundos (View view){

        //Creamos la conexión a la BD
        AccesoBD accesoBD2 = new AccesoBD(this,"recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD2.getReadableDatabase();

        //Recuperar los ingredientes del plato deseado
        Cursor ingredientes = BaseDeDatos.rawQuery
                ("SELECT nombre_ingrediente  FROM receta_ingredientes ri WHERE ri.nombre_plato = '"+tvSegundo.getText()+"'", null);

        List<String> arrayIngredientes = new ArrayList<>();

        //Mostramos los ingredientes en un Alertdialog
        AlertDialog.Builder ADingredientes = new AlertDialog.Builder(this);
        ADingredientes.setTitle("Ingredientes");
        while ((ingredientes.moveToNext())){
            arrayIngredientes.add(ingredientes.getString(0)+"\n");
            BaseDeDatos.close();
        }
        ADingredientes.setMessage(arrayIngredientes.toString().replace(',',' ').replace('[',' ').replace(']',' '));
        ADingredientes.show();

    }

}