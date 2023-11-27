package com.example.crudcito;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
public class Base extends SQLiteOpenHelper{
    public Base(@Nullable Context context, @Nullable String name,
                @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public Base(@Nullable Context context, @Nullable String name,
                @Nullable SQLiteDatabase.CursorFactory factory, int version,
                @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    public Base(@Nullable Context context,
                @Nullable String name, int version,
                @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }
    @Override
    public void onCreate(SQLiteDatabase basesita) {
        basesita.execSQL("CREATE TABLE jugadores (" +
                "clave INTEGER PRIMARY KEY," + /*ID*/
                "nombre TEXT," +
                " lesion TEXT," + /*Genero = lesion*/
                "conf TEXT," + /* Conferencia = Pais*/
                " equipo TEXT," + /* Equipo = ciudad*/
                " trade INTEGER," + /* Elegible trade/extension = ida y vuelta */
                " ext INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
