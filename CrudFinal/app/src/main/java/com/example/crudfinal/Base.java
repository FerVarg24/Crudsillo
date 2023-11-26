package com.example.crudfinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Base extends SQLiteOpenHelper {


    public Base(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase base) {
    base.execSQL("create table jugadores(codigo int primary key," +
            "descripcion text," +/* Descripcion = Nombre del jugador */
            "liga text," + /*Genero = Liga*/
            "equipo text," + /* Artistas = Equipo*/
            "posi text," + /* Horario = Titular o Banca*/
            "edad real)" /* Precio = Edad */ );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
