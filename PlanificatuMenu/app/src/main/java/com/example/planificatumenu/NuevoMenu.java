package com.example.planificatumenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class NuevoMenu extends AppCompatActivity {


    private Spinner spinner_carne, spinner_pescado, spinner_comensales;
    private List<String> segundosPescado = new ArrayList<>();
    private List<String> segundosCarne = new ArrayList<>();
    private List<String> segundosVegano = new ArrayList<>();
    private List<String> listaPrimeros = new ArrayList<>();
    private List<String> segundosCollection = new ArrayList<>();
    private List<String> listaIngredientes = new ArrayList<>();
    private List<String> ingredientesSeleccionados = new ArrayList<>();
    private String historialID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_menu);

        spinner_carne = (Spinner) findViewById(R.id.spinner_carne);
        spinner_pescado = (Spinner) findViewById(R.id.spinner_pescado);
        spinner_comensales = (Spinner) findViewById(R.id.spinner_comensales);

        //
        leerIngredientes();

        Integer[] dias_semana = {0, 1, 2, 3, 4, 5, 6, 7};
        Integer[] comensales = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        ArrayAdapter<Integer> adapter1 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, dias_semana);
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, comensales);

        spinner_carne.setAdapter(adapter1);
        spinner_pescado.setAdapter(adapter1);
        spinner_comensales.setAdapter(adapter2);

        historialID = obtenerHistorialId();

        //Creamos un SelectedListener que al cambiar spinnerCarne a >0 desmarque el checkVegano
        spinner_carne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox checkVegano = (CheckBox) findViewById(R.id.checkBox_vegano);
                int pescado = (int) spinner_pescado.getSelectedItem();
                if (i > 0) {
                    checkVegano.setChecked(false);
                    //Aquí calculamos que entre los dos spinner no pase de 7
                    if (pescado + i > 7) {
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
                if (i > 0) {
                    checkVegano.setChecked(false);
                    //Aquí calculamos que entre los dos spinner no pase de 7
                    if (carne + i > 7) {
                        spinner_pescado.setSelection(7 - carne);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


// ----------------------- ZONA DE BOTONES ---------------------------------

    //    Boton INFO
    public void Info(View view) {
        AlertDialog.Builder dialogoSeleccionar = new AlertDialog.Builder(this);
        dialogoSeleccionar.setTitle("INFO");
        dialogoSeleccionar.setMessage("Seleccionamos cuantos días de la semana deseamos cada tipo de plato.\n" +
                "Si seleccionamos menos de 7 días, se completará con platos veganos.\n" +
                "Al marcar el icono Menú Vegano, se desmarcan el resto de selecciones.\n" +
                "");
        dialogoSeleccionar.show();
    }

    //    Leer ingredientes almacenados a evitar
    public void leerIngredientes() {

        //Creamos la conexión a la BD en modo lectura
        AccesoBD accesoBD = new AccesoBD(this, "recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD.getReadableDatabase();

        //Creamos la sentencia Query

        Cursor leerID = BaseDeDatos.rawQuery
                ("SELECT i.nombre_ingrediente  FROM ingredientes i WHERE tipo_comida = 'azúcares' OR tipo_comida = 'fondos'", null);


        //Metemos todos los datos en el ArrayList
        while (leerID.moveToNext()) {
            listaIngredientes.add(leerID.getString(0));
        }
        BaseDeDatos.close();

    }

    // Botón Seleccionar de los ingredientes
    public void dialogIngredientes(View view) {

        //Convertimos el ArrayList en un Array convencional
        String[] arrayIngredientes = listaIngredientes.toArray(new String[0]);
        //Creamos la lista donde el usuario debe elegir el menú que quiere visualizar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selección de Ingredientes").setMultiChoiceItems(arrayIngredientes, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if (isChecked) {
                    ingredientesSeleccionados.add(arrayIngredientes[position]);
                } else {
                    ingredientesSeleccionados.remove((position));
                }
            }
        });
        builder.setCancelable(false);
//      Creamos los botones de acción del Dialog
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int j) {
//                for (int i=0; i<ingredientesSeleccionados.size(); i++){
//                    System.out.println(ingredientesSeleccionados.get(i));
//                }
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();

    }


    //    Botón CREAR

    public void Crear(View view) {

        //Leemos la selección marcada por el usuario, días carne, días pescado y nº comensales
        int diasCarne = (int) spinner_carne.getSelectedItem();
        int diasPescado = (int) spinner_pescado.getSelectedItem();
        int numComensales = (int) spinner_comensales.getSelectedItem();
        int diasVegano = 7 - (diasCarne + diasPescado);

        //Añadimos un comprobador que añada días para cumplir la semana
        if ((diasCarne + diasPescado + diasVegano) < 7) {
            Toast.makeText(this, "Debe haber al menos 7 menús seleccionados", Toast.LENGTH_LONG).show();
        } else {
            Intent comprobar = new Intent(this, ComprobarMenu.class);

            //Enviamos la variable historialID
            comprobar.putExtra("historialID", historialID);
//          Lanzamos el método Historial para que genere y guarde el menú
            Historial(diasPescado, diasCarne, diasVegano);
//          Lanzamos la activity ComprobarMenu
            startActivity(comprobar);

        }


    }

    //    Botón VOLVER

    public void Volver(View view) {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    //    Botón VEGANO

    public void Vegano(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        //Si marcamos el checkVegano, pone los spinner carne y pescado a 0
        switch (view.getId()) {
            case R.id.checkBox_vegano:
                if (checked)
                    spinner_carne.setSelection(0);
                spinner_pescado.setSelection(0);
                break;
        }
    }

    //  Botón que reinstala la BD eliminando todos los datos del historial

    // Actualmente la funcionalidad está eliminada, dejo el método por si se quiere recuperar

    public void limpiarBD(View view){
        //Primero lanzamos el aviso al usuario y le pedimos que confirme la acción
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Borrar Historial?")
                .setMessage("Este proceso elimina todos los datos almacenados en el historial.\n" +
                        "No se puede revertir. Usar solo en caso de mal funcionamiento de la aplicación")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    //Si el usuario confirma la acción reinstalamos la BD
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity ma = new MainActivity();
                        ma.copyAsset("recetas.sqlite");
                    }
                })
                //Si el usuario cancela desechamos el alerdialog
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();

    }

// --------------------------------- ZONA DE MÉTODOS -------------------------------------------

    // Método encargado de generar el menú completo de la semana y llamar al método insertartablahistorial()

    public void Historial(int diasPescado, int diasCarne, int diasVegano) {
        //Creamos la conexión a la BD
        AccesoBD accesoBD = new AccesoBD(this, "recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD.getReadableDatabase();

        //Seleccionar 7 primeros

        Cursor filaPrimeros = BaseDeDatos.rawQuery
                ("SELECT nombre_plato FROM platos p WHERE p.orden_plato = 1 ORDER BY RANDOM() LIMIT 7", null);


        while (filaPrimeros.moveToNext()) {
            listaPrimeros.add(filaPrimeros.getString(0));
        }


        //Seleccionar 7 segundos según preferencias usuario
        //Pescados

        Cursor filaSegundosPescado = BaseDeDatos.rawQuery
                ("SELECT nombre_plato  FROM platos p WHERE p.orden_plato = 3 ORDER BY RANDOM() LIMIT " + diasPescado, null);
        while (filaSegundosPescado.moveToNext()) {
            segundosPescado.add(filaSegundosPescado.getString(0));
        }


        //Carnes

        Cursor filaSegundosCarne = BaseDeDatos.rawQuery
                ("SELECT nombre_plato  FROM platos p WHERE p.orden_plato = 4 ORDER BY RANDOM() LIMIT " + diasCarne, null);
        while (filaSegundosCarne.moveToNext()) {
            segundosCarne.add(filaSegundosCarne.getString(0));
        }

        //Veganos

        Cursor filaSegundosVegano = BaseDeDatos.rawQuery
                ("SELECT nombre_plato  FROM platos p WHERE orden_plato = 5 ORDER BY RANDOM() LIMIT " + diasVegano, null);
        while (filaSegundosVegano.moveToNext()) {
            segundosVegano.add(filaSegundosVegano.getString(0));
        }

        BaseDeDatos.close();

//        Unimos las listas de segundos en una única para consumir

        segundosCollection.addAll(segundosCarne);
        segundosCollection.addAll(segundosVegano);
        segundosCollection.addAll(segundosPescado);

        //Alteramos el orden de los platos

        Collections.shuffle(segundosCollection);


//        Guardamos los datos en la tabla Historial

        insertarTablaHistorial(historialID, listaPrimeros.get(0), segundosCollection.get(0),
                listaPrimeros.get(1), segundosCollection.get(1), listaPrimeros.get(2),
                segundosCollection.get(2), listaPrimeros.get(3), segundosCollection.get(3),
                listaPrimeros.get(4), segundosCollection.get(4), listaPrimeros.get(5),
                segundosCollection.get(5), listaPrimeros.get(6), segundosCollection.get(6));

    }



    //Método que comprueba si el ingrediente ha sido descartado por el usuario

    //Actualmente la funcionalidad está eliminada, dejo el método para cuando haya suficientes platos
    //ya que ahora no hay suficiente variedad como para eliminarlos

    public boolean comprobarIngredientes(String plato) {
        //Creamos la conexión a la BD
        AccesoBD accesoBD = new AccesoBD(this, "recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD.getReadableDatabase();
        //Query para obtener la lista de ingredientes de un plato
        Cursor ingredientes = BaseDeDatos.rawQuery
                ("SELECT ri.nombre_ingrediente FROM platos p, receta_ingredientes ri WHERE '" + plato + "' = ri.nombre_plato ", null);

        int j = 0;
        while (ingredientes.moveToNext()) {
            if (!ingredientesSeleccionados.isEmpty()) {
                for (int i = 0; i < ingredientesSeleccionados.size(); i++) {
                    if (ingredientes.getString(j) == ingredientesSeleccionados.get(i)) {
                        System.out.println(ingredientes.getString(j) + " coincide con " + ingredientesSeleccionados.get(i));
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
        return true;
    }


    //Método que genera el historial_ID a partir de la fecha de creación del menú

    public String obtenerHistorialId() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy MMM dd, HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    //Método que inserta los datos del menú en la tabla historial

    public void insertarTablaHistorial(String id, String lp, String ls, String mp, String ms, String xp, String xs,
                                       String jp, String js, String vp, String vs, String sp, String ss, String dp, String ds) {

        //Creamos la conexión a la BD en modo escritura

        AccesoBD accesoBD = new AccesoBD(this, "recetas.sqlite", null, 1);
        SQLiteDatabase BaseDeDatos = accesoBD.getWritableDatabase();

        //Insertamos los datos
        ContentValues registro = new ContentValues();
        registro.put("id", id);
        registro.put("lp", lp);
        registro.put("ls", ls);
        registro.put("mp", mp);
        registro.put("ms", ms);
        registro.put("xp", xp);
        registro.put("xs", xs);
        registro.put("jp", jp);
        registro.put("js", js);
        registro.put("vp", vp);
        registro.put("vs", vs);
        registro.put("sp", sp);
        registro.put("ss", ss);
        registro.put("dp", dp);
        registro.put("ds", ds);

        BaseDeDatos.insert("historial", null, registro);
        BaseDeDatos.close();

    }


}