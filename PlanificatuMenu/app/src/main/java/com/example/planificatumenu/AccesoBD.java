package com.example.planificatumenu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;


public class AccesoBD extends SQLiteOpenHelper{


    public AccesoBD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
//        context.openOrCreateDatabase(name, context.MODE_PRIVATE, null);
    }

    //    no necesario al utilizar bd externa
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }


    //    no necesario al utilizar bd externa
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
