package com.example.crudcito;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialButton bAltas, bBajas, bCambios, bConsultas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bAltas = findViewById(R.id.altas);
        bAltas.setOnClickListener(this);
        bBajas = findViewById(R.id.bajas);
        bBajas.setOnClickListener(this);
        bCambios = findViewById(R.id.cambios);
        bCambios.setOnClickListener(this);
        bConsultas = findViewById(R.id.consultas);
        bConsultas.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String cadenita = ((MaterialButton)v).getText().toString();
        if(cadenita.equals("Altas")){
            Intent intentito = new Intent(this, MainActivity2.class);
            startActivity(intentito);
        } else if (cadenita.equals("Consultas")) {
            Intent intentito = new Intent(this, MainActivity3.class);
            startActivity(intentito);
        } else if (cadenita.equals("Bajas")) {
            Intent intentito = new Intent(this, MainActivity5.class);
            startActivity(intentito);
        } else if (cadenita.equals("Cambios")) {
            Intent intentito = new Intent(this, MainActivity4.class);
            startActivity(intentito);
        }
    }
}