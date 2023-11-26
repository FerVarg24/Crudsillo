package com.example.crudfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Cambios extends AppCompatActivity implements View.OnClickListener {
    EditText eClave, eDesc, liga, equipo, edad, posi;
    Button bBuscar, bRegr;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        bRegr = findViewById(R.id.bregresar);
        bRegr.setOnClickListener(this);
        eDesc = findViewById(R.id.eDesc);
        liga = findViewById(R.id.Liga);
        equipo = findViewById(R.id.equipo);
        edad = findViewById(R.id.edad);
        posi = findViewById(R.id.starter);
        equipo.setEnabled(false);
        edad.setEnabled(false);
        liga.setEnabled(false);
        posi.setEnabled(false);
        eDesc.setEnabled(false);
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


            Base admin = new Base(this, "administracion", null, 1);
            SQLiteDatabase basededatos = admin.getWritableDatabase();

            Cursor cursor = basededatos.rawQuery("SELECT * FROM jugadores WHERE codigo = ?", new String[]{clave});


            if (cursor.moveToFirst()) {
                // Mostrar los resultados en los EditText correspondientes
                eDesc.setText(cursor.getString(cursor.getColumnIndex("descripcion")));
                liga.setText(cursor.getString(cursor.getColumnIndex("liga")));
                equipo.setText(cursor.getString(cursor.getColumnIndex("equipo")));
                posi.setText(cursor.getString(cursor.getColumnIndex("posi")));
                edad.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex("edad"))));

                mostrarImagen(cursor.getString(cursor.getColumnIndex("equipo")));
            } else {
                eDesc.setText("");
                liga.setText("");
                equipo.setText("");
                posi.setText("");
                edad.setText("");
                Toast.makeText(this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
            }

            // Cerrar el cursor y la conexión a la base de datos
            cursor.close();
            basededatos.close();
            bBuscar.setText("Cambia");
            eClave.setEnabled(false);

            edad.setEnabled(true);

            posi.setEnabled(true);
            eDesc.setEnabled(true);
        } else if (cadenita.equals("Cambia")) {

            String nDesc = eDesc.getText().toString();
            String nGen = liga.getText().toString();
            String nArt = equipo.getText().toString();
            String nHor = posi.getText().toString();
            String nPreco = edad.getText().toString();

            // Abrir la base de datos en modo escritura
            Base adminActualizar = new Base(this, "administracion", null, 1);
            SQLiteDatabase basededatosActualizar = adminActualizar.getWritableDatabase();

            // Actualizar los datos en la base de datos
            ContentValues valores = new ContentValues();
            valores.put("descripcion", nDesc);
            valores.put("liga", nGen);
            valores.put("equipo", nArt);
            valores.put("posi", nHor);
            valores.put("edad", Double.parseDouble(nPreco));

            String clave = eClave.getText().toString();
            int filasActualizadas = basededatosActualizar.update("jugadores", valores, "codigo=?", new String[]{clave});


            if (filasActualizadas > 0) {
                imagen.setVisibility(View.INVISIBLE);


                eDesc.setText("");
                liga.setText("");
                equipo.setText("");
                posi.setText("");
                edad.setText("");
                eClave.setText("");
                bBuscar.setText("Buscar");
                eClave.setEnabled(true);
                equipo.setEnabled(false);
                edad.setEnabled(false);
                liga.setEnabled(false);
                posi.setEnabled(false);
                eDesc.setEnabled(false);

            } else {
                Toast.makeText(this, "Error al intentar actualizar los datos", Toast.LENGTH_SHORT).show();
            }


            basededatosActualizar.close();
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