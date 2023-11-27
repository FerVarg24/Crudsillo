package com.example.crudcito;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
public class MainActivity2 extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {
    TextInputEditText etClave, etNombre;
    RadioGroup grupillo;
    RadioButton rDisponible, rLesionado;
    Spinner sDiv, sEquipo;
    CheckBox cTrade, cExt;
    MaterialButton btnAgregar, btnRegresar;
    ArrayAdapter<String> divisiones;
    ArrayAdapter<Basurita> equipos;
    ArrayList<Basurita> Arecibe = new ArrayList<>();
    int seleccionado = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etClave = findViewById(R.id.etClave);
        etNombre = findViewById(R.id.etNombre);
        grupillo = findViewById(R.id.rGrupillo);
        rDisponible = findViewById(R.id.rDisponible);
        rLesionado = findViewById(R.id.rLesionado);
        sDiv = findViewById(R.id.sDiv);
        sEquipo = findViewById(R.id.sEquipo);
        cTrade = findViewById(R.id.cTrade);
        cExt = findViewById(R.id.cExt);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnAgregar.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);
        sDiv.setOnItemSelectedListener(this);
        sEquipo.setOnItemSelectedListener(this);
        String division[] = {"Selecciona","Este","Oeste"};
        divisiones = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, division);
        sDiv.setAdapter(divisiones);
    }
    @Override
    public void onClick(View v) {
        String cadenita = ((MaterialButton) v).getText().toString();
        if (cadenita.equals("Agregar")) {
            String clave = etClave.getText().toString();
            String nombre = etNombre.getText().toString();
            String disp = rDisponible.isChecked() ? "Disponible" : "Lesionado";
            String divs = sDiv.getSelectedItem().toString();
            String team = sEquipo.getSelectedItem().toString();
            boolean trade = cTrade.isChecked();
            boolean extension = cExt.isChecked();
            insertarDatos(clave, nombre, disp, divs, team, trade, extension);
        } else if (cadenita.equals("Regresar")) {
            Intent intentito = new Intent(this, MainActivity.class);
            startActivity(intentito);
        }
    }
    private void insertarDatos(String clave, String nombre, String disp,
                               String divs, String team, boolean trade, boolean extension) {
        Base admin = new Base(this, "rostersillo", null, 1);
        SQLiteDatabase basededatos = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("clave", clave);
        registro.put("nombre", nombre);
        registro.put("lesion", disp);
        registro.put("conf", divs);
        registro.put("equipo", team);
        registro.put("trade", trade ? 1 : 0);
        registro.put("ext", extension ? 1 : 0);
        long resultado = basededatos.insert("jugadores", null, registro);
        if (resultado > 0) {
            Toast.makeText(this, "Registro agregado",
                    Toast.LENGTH_LONG).show();
            Reinicia();
        } else {
            Toast.makeText(this, "Error al agregar el registro",
                    Toast.LENGTH_LONG).show();
        }
        basededatos.close();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sDiv) {
            seleccionado = sDiv.getSelectedItemPosition();
            Arraycito aregreso = new Arraycito();
            if(seleccionado == 1) {
                aregreso.agregar(1);
                Arecibe = aregreso.regresar();
                equipos = new ArrayAdapter<Basurita>(this,
                        android.R.layout.simple_spinner_item,Arecibe);
                sEquipo.setAdapter(equipos);
            }
            else {
                if(seleccionado == 2) {
                    aregreso.agregar(2);
                    Arecibe = aregreso.regresar();
                    equipos = new ArrayAdapter<Basurita>(this,
                            android.R.layout.simple_spinner_item,Arecibe);
                    sEquipo.setAdapter(equipos);
                }
                else
                if(seleccionado == 0)
                    sEquipo.setAdapter(null);
            }
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void Reinicia() {
        etClave.setText("");
        etNombre.setText("");
        grupillo.clearCheck();
        sDiv.setSelection(0);
        cTrade.setChecked(false);
        cExt.setChecked(false);
    }
}