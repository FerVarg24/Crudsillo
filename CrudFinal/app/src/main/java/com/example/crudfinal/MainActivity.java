package com.example.crudfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button baltas, bbajas, bcambios, bconsultas;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baltas = findViewById(R.id.baltas);
        bcambios = findViewById(R.id.bcambios);
        bbajas = findViewById(R.id.bbajas);
        bconsultas = findViewById(R.id.bconsultas);

        baltas.setOnClickListener(this);
        bbajas.setOnClickListener(this);
        bcambios.setOnClickListener(this);
        bconsultas.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        String cadenita = ((Button)view).getText().toString();

        if (cadenita.equals("Altas"))
        {
            Intent intentito = new Intent(this, Altas.class);
            startActivity(intentito);
        } else if (cadenita.equals("Consultas")) {
            Intent intentito = new Intent(this, Consultas.class);
            startActivity(intentito);
        } else if (cadenita.equals("Bajas")) {
            Intent intentito = new Intent(this, Bajas.class);
            startActivity(intentito);
        } else if (cadenita.equals("Cambios")) {
            Intent intentito = new Intent(this, Cambios.class);
            startActivity(intentito);
        }
    }
}