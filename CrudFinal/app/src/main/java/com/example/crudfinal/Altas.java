package com.example.crudfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Altas extends AppCompatActivity implements View.OnClickListener {
    EditText eClave, eDesc, eEdad;
    Spinner sliga, sEquipo;
    RadioButton inic, banca;
    Button bAlta, bReg;

    ImageView imagen;
    RadioGroup posi;

    String posisele;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        sliga = findViewById(R.id.Liga);
        sEquipo = findViewById(R.id.Equipo);
        imagen = findViewById(R.id.imagen);
        bAlta = findViewById(R.id.alta);
        bAlta.setOnClickListener(this);
        bReg = findViewById(R.id.regresar);
        bReg.setOnClickListener(this);
        eClave = findViewById(R.id.clave);
        eDesc = findViewById(R.id.desc);
        eEdad = findViewById(R.id.edad);
        inic = findViewById(R.id.Tit);
        banca = findViewById(R.id.banc);
        posi = findViewById(R.id.start);
        String hor = "inicial";
        posi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton boton = findViewById(checkedId);

            if(boton != null)
            {
                posisele = boton.getText().toString();
            }
            }
        });

        ArrayList <String> ligas = new ArrayList<>();
        ligas.add("Selecciona una liga");
        ligas.add("NFL");
        ligas.add("NBA");
        ligas.add("Premier League");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ligas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sliga.setAdapter(adapter);

        sEquipo.setVisibility(View.INVISIBLE);

        configListener();
    }

    private void configListener(){
        sliga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 )
                {
                    sEquipo.setVisibility(View.INVISIBLE);
                    imagen.setVisibility(View.INVISIBLE);
                }
                else {
                    Act2doSpinner(position);
                    sEquipo.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sEquipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                {
                    mostrarImagen(sEquipo.getSelectedItem().toString());
                }else
                {
                    imagen.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void Act2doSpinner (int posicion)
    {
        ArrayList<String> equipos = new ArrayList<>();
        if (posicion == 1)
        {
            equipos.add("Selecciona un equipo");
            equipos.add("Cinncinati Bengals");
            equipos.add("Baltimore Ravens");
            equipos.add("Minnesota Vikings");
        } else if (posicion == 2) {
            equipos.add("Selecciona un equipo");
            equipos.add("Portland Trailblazers");
            equipos.add("Milwaukee Bucks");
            equipos.add("Miami Heat");
        } else if (posicion == 3) {
            equipos.add("Selecciona un Equipo");
            equipos.add("Tottenham");
            equipos.add("Arsenal");
            equipos.add("West Ham");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, equipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sEquipo.setAdapter(adapter);
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

    @Override
    public void onClick(View v) {
     String cadenita = ((Button)v).getText().toString();
     if (cadenita.equals("Regresar"))
     {
         Intent intentito = new Intent(this, MainActivity.class);
         startActivity(intentito);
     } else if (cadenita.equals("Alta")) {
         Base admin = new Base(this, "administracion", null, 1);
         SQLiteDatabase basededatos = admin.getWritableDatabase();
         String cod = eClave.getText().toString();
         String des = eDesc.getText().toString();
         String gen = sliga.getSelectedItem().toString();
         String nom = sEquipo.getSelectedItem().toString();
         String hor = posisele;
         String pre = eEdad.getText().toString();

         ContentValues registro = new ContentValues();
         registro.put("codigo", cod);
         registro.put("descripcion", des);
         registro.put("liga", gen);
         registro.put("equipo", nom);
         registro.put("posi", hor);
         registro.put("edad", pre);
         basededatos.insert("jugadores", null, registro);
         basededatos.close();


         Toast.makeText(this, "agregado", Toast.LENGTH_SHORT).show();

     }


    }
}


