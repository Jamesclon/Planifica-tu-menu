package com.example.planificatumenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class NuevoMenu extends AppCompatActivity {


    private Spinner spinner_carne, spinner_pescado, spinner_comensales;
    private TextView tvNombreIngrediente, tvTipoComida;
    private EditText etNombreIngrediente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_menu);

        tvNombreIngrediente = (TextView) findViewById(R.id.textViewNombrePlato);
        tvTipoComida = (TextView) findViewById(R.id.textViewOrdenPlato);
        etNombreIngrediente = (EditText) findViewById(R.id.editTextTextIngrediente);

        spinner_carne = (Spinner) findViewById(R.id.spinner_carne);
        spinner_pescado = (Spinner) findViewById(R.id.spinner_pescado);
        spinner_comensales = (Spinner) findViewById(R.id.spinner_comensales);

        Integer[] dias_semana = {0,1,2,3,4,5,6,7};
        Integer[] comensales = {1,2,3,4,5,6,7,8,9,10,11,12};

        ArrayAdapter <Integer> adapter1 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, dias_semana);
        ArrayAdapter <Integer> adapter2 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, comensales);

        spinner_carne.setAdapter(adapter1);
        spinner_pescado.setAdapter(adapter1);
        spinner_comensales.setAdapter(adapter2);

        //Creamos un SelectedListener que al cambiar spinnerCarne a >0 desmarque el checkVegano
        spinner_carne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox checkVegano = (CheckBox) findViewById(R.id.checkBox_vegano);
                int pescado = (int) spinner_pescado.getSelectedItem();
                if (i>0){
                    checkVegano.setChecked(false);
                    //Aquí calculamos que entre los dos spinner no pase de 7
                    if(pescado + i > 7){
                        spinner_carne.setSelection(7 - pescado);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Creamos un SelectedListener que al cambiar spinnerPescado a >0 desmarque el checkVegano
        spinner_pescado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox checkVegano = (CheckBox) findViewById(R.id.checkBox_vegano);
                int carne = (int) spinner_carne.getSelectedItem();
                if (i>0){
                    checkVegano.setChecked(false);
                    //Aquí calculamos que entre los dos spinner no pase de 7
                    if (carne + i > 7){
                        spinner_pescado.setSelection(7 -carne);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


//    Botones
//    Botón CREAR

    public void Crear(View view){

        //Leemos la selección marcada por el usuario, días carne, días pescado y nº comensales
        int diasCarne = (int) spinner_carne.getSelectedItem();
        int diasPescado = (int) spinner_pescado.getSelectedItem();
        int numComensales = (int) spinner_comensales.getSelectedItem();
        int diasVegano = 7-(diasCarne+diasPescado);


        //Creamos la conexión a la BD
        AccesoBD accesoBD = new AccesoBD(this,"recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD.getReadableDatabase();


        Editable nombreIngrediente = etNombreIngrediente.getText();

        Cursor fila = BaseDeDatos.rawQuery
                ("SELECT * FROM ingredientes WHERE nombre_ingrediente = '"+nombreIngrediente+"'" , null);
        if (fila.moveToFirst()){
            tvNombreIngrediente.setText(fila.getString(0));
            tvTipoComida.setText(fila.getString(1));
            BaseDeDatos.close();
        } else {
            Toast.makeText(this, "Ingrediente no existe", Toast.LENGTH_LONG).show();
            BaseDeDatos.close();
        }

//        Intent comprobar = new Intent(this, ComprobarMenu.class);
//        startActivity(comprobar);
    }

//    Botón VOLVER

    public void Volver(View view){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    //    Botón VEGANO

    public void Vegano(View view){
        boolean checked = ((CheckBox) view).isChecked();

        //Si marcamos el checkVegano, pone los spinner carne y pescado a 0
        switch (view.getId()){
            case R.id.checkBox_vegano:
                if (checked)
                    spinner_carne.setSelection(0);
                    spinner_pescado.setSelection(0);
                break;
        }
    }



//    Botón seleccionar ingredientes
//<<<<< ojo falta implementar los datos de la BD >>>>>>>>>>>

    public void Seleccionar(View view){



        AlertDialog.Builder dialogoSeleccionar = new AlertDialog.Builder(this);
        dialogoSeleccionar.setTitle("Selección de Ingredientes");


        dialogoSeleccionar.setMessage("Actualmente no existen datos. Disculpe las molestias.");
        dialogoSeleccionar.show();

    }


}