package com.example.planificatumenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private String DB_PATH, DB_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Comprobamos si existe el archivo de la BD
        File fileBD = new File("/data/data/com.example.planificatumenu/databases/recetas.sqlite");
        //Si no existe, lanza el método de copia
        if (!fileBD.exists()) {
            copyAsset("recetas.sqlite");
        }

    }

// ----------- ZONA DE INSTALACIÓN DE LA BASE DE DATOS ----------------

    //Metodo que copia el archivo BD al directorio de almacenamiento interno
    //FUNDAMENTAL
    public void copyAsset(String fileName) {
        String dirPath = "/data/data/com.example.planificatumenu/databases/";
        File dir = new File(dirPath);
        //Primero comprueba si existe el directorio y si no, lo crea
        if (!dir.exists()) {
            dir.mkdirs();
        }
        AssetManager assetManager = getAssets();
        //Proceso de copiado, con un outputstream y un inputstream
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fileName);
            File outFile = new File(dirPath, fileName);
            out = new FileOutputStream(outFile);
            copyFile(in, out);
            Toast.makeText(this, "BD actualizada con éxito", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Fallo al crear la BD. Reinstale la APP", Toast.LENGTH_LONG).show();
        } finally {
            //Por último cerramos los streams
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //Método que realiza la copia física de los streams
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

// --------------------------- ZONA DE BOTONES ----------------------------------


//    Botón HISTORIAL

    public void Historial(View view) {
        Intent historial = new Intent(this, HistorialMenu.class);
        startActivity(historial);
    }

//    Botón NUEVO MENU

    public void Nuevo(View view) {
        Intent nuevo = new Intent(this, NuevoMenu.class);
        startActivity(nuevo);
    }

    //    Botón SALIR

    public void Salir(View view) {
        finishAffinity();
        System.exit(0);
    }

// -------------------------------------------------------------


}