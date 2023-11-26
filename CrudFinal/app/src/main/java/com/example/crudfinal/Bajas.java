package com.example.crudfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Bajas extends AppCompatActivity implements View.OnClickListener {
    EditText eClave, nomb, liga, equipo, edad, starter;
    Button bBuscar, bRegr;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        bRegr = findViewById(R.id.bregresar);
        bRegr.setOnClickListener(this);
        nomb = findViewById(R.id.eDesc);
        liga = findViewById(R.id.Liga);
        liga.setEnabled(false);
        equipo = findViewById(R.id.equipo);
        equipo.setEnabled(false);
        edad = findViewById(R.id.edad);
        edad.setEnabled(false);
        starter = findViewById(R.id.starter);
        starter.setEnabled(false);
        nomb.setEnabled(false);
        bBuscar = findViewById(R.id.bbuscar);
        bBuscar.setOnClickListener(this);
        imagen = findViewById(R.id.fotilla);

    }

    @SuppressLint("Range")
    @Override
    public void onClick(View view) {
        String cadenita = ((Button) view).getText().toString();

        if (cadenita.equals("Regresar")) {
            Intent intentito = new Intent(this, MainActivity.class);
            startActivity(intentito);
        } else if (cadenita.equals("Buscar")) {
            eClave = findViewById(R.id.eclave);
            String clave = eClave.getText().toString();


            Base admin = new Base(this, "adminsillo", null, 1);
            SQLiteDatabase basededatos = admin.getWritableDatabase();


            Cursor cursor = basededatos.rawQuery("SELECT * FROM jugadores WHERE codigo = ?", new String[]{clave});


            if (cursor.moveToFirst()) {

                nomb.setText(cursor.getString(cursor.getColumnIndex("descripcion")));
                liga.setText(cursor.getString(cursor.getColumnIndex("liga")));
                equipo.setText(cursor.getString(cursor.getColumnIndex("equipo")));
                starter.setText(cursor.getString(cursor.getColumnIndex("posi")));
                edad.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex("edad"))));

                mostrarImagen(cursor.getString(cursor.getColumnIndex("equipo")));
            } else {

                nomb.setText("");
                liga.setText("");
                equipo.setText("");
                starter.setText("");
                edad.setText("");
                imagen.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
            }


            cursor.close();
            basededatos.close();
            bBuscar.setText("Eliminar");
        } else if (cadenita.equals("Eliminar")) {
            String clave = eClave.getText().toString();


            Base admin = new Base(this, "administracion", null, 1);
            SQLiteDatabase basededatos = admin.getWritableDatabase();


            int filasAfectadas = basededatos.delete("jugadores", "codigo=?", new String[]{clave});


            if (filasAfectadas > 0) {
                imagen.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Elemento eliminado correctamente", Toast.LENGTH_SHORT).show();


                nomb.setText("");
                liga.setText("");
                equipo.setText("");
                starter.setText("");
                edad.setText("");
                bBuscar.setText("Buscar");

            } else {
                Toast.makeText(this, "Error al intentar eliminar el elemento", Toast.LENGTH_SHORT).show();
            }

            basededatos.close();
        }
    }

    private void mostrarImagen(String artista)
    {
        String nombreImg = artista.toLowerCase().replace(" ", "");
        int recImagen = getResources().getIdentifier(nombreImg, "drawable", getPackageName());

        if(recImagen != 0 )
        {
            imagen.setImageResource(recImagen);
            imagen.setVisibility(View.VISIBLE);
        } else
        {
            imagen.setVisibility(View.INVISIBLE);
        }
    }


}