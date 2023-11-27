package com.example.crudcito;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, View.OnClickListener {

    TextInputEditText etClave, etNombre;
    RadioGroup grupillo;
    RadioButton rDisponible, rLesionado;
    Spinner sDiv, sEquipo;
    CheckBox cTrade, cExt;
    MaterialButton btnBuscar, btnRegresar;
    ArrayAdapter<String> divisiones;
    ArrayAdapter<Basurita> equipos;
    ArrayList<Basurita> Arecibe = new ArrayList<>();
    int seleccionado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        etClave = findViewById(R.id.etClave);
        etNombre = findViewById(R.id.etNombre);
        grupillo = findViewById(R.id.rGrupillo);
        rDisponible = findViewById(R.id.rDisponible);
        rLesionado = findViewById(R.id.rLesionado);
        sDiv = findViewById(R.id.sDiv);
        sEquipo = findViewById(R.id.sEquipo);
        cTrade = findViewById(R.id.cTrade);
        cExt = findViewById(R.id.cExt);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnBuscar.setOnClickListener(this);
        btnRegresar.setOnClickListener(this);
        sDiv.setOnItemSelectedListener(this);
        sEquipo.setOnItemSelectedListener(this);
        String division[] = {"Selecciona","Este","Oeste"};
        divisiones = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, division);
        sDiv.setAdapter(divisiones);
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

    @Override
    public void onClick(View v) {
        String cadenita = ((MaterialButton) v).getText().toString();
        if (cadenita.equals("Buscar")) {
            String clavecita = etClave.getText().toString();

            String mensajito;
            if (!clavecita.isEmpty()) {
                Base admin = new Base(this, "rostersillo", null, 1);
                SQLiteDatabase basededatos = admin.getReadableDatabase();

                Cursor fila = basededatos.rawQuery("SELECT nombre," +
                        " lesion, " +
                        "conf," +
                        " equipo," +
                        " trade," +
                        " ext FROM " +
                        "jugadores WHERE clave = " + clavecita, null);

                if (fila.moveToFirst()) {
                    etNombre.setText(fila.getString(0));

                    String les = fila.getString(1);
                    if (les.equals("Disponible")) {
                        grupillo.check(R.id.rDisponible);
                    } else if (les.equals("Lesionado")) {
                        grupillo.check(R.id.rLesionado);
                    }

                    String division = fila.getString(2);
                    int divI = getIndexFromSpinner(divisiones, division);
                    sDiv.setSelection(divI);

                    String team = fila.getString(3);
                    int teamI = obtenerIndexTeam(equipos, team);
                    sEquipo.setSelection(teamI);

                    boolean tradeable = fila.getInt(4) == 1;
                    boolean extendible = fila.getInt(5) == 1;

                    Log.d("Debug", "ida: " + fila.getInt(4));
                    Log.d("Debug", "vuelta: " + fila.getInt(5));

                    cTrade.setChecked(tradeable);
                    cExt.setChecked(extendible);

                    mensajito = "Estos son los datos";
                } else {
                    mensajito = "No existe el registro";
                }

                basededatos.close();
            } else {
                mensajito = "Ingrese un código válido";
            }

            AlertDialog.Builder mensa = new AlertDialog.Builder(this);
            mensa.setTitle("Resultado de la consulta");
            mensa.setMessage(mensajito);
            mensa.setPositiveButton("Aceptar", null);
            AlertDialog dialog = mensa.create();
            dialog.show();
        } else if (cadenita.equals("Regresar")) {
            Intent intentito = new Intent(this, MainActivity.class);
            startActivity(intentito);
        }
    }

    private int getIndexFromSpinner(ArrayAdapter<String> spinnerAdapter, String value) {
        for (int i = 0; i < spinnerAdapter.getCount(); i++) {
            if (spinnerAdapter.getItem(i).equals(value)) {
                return i;
            }
        }
        return 0;
    }

    private int obtenerIndexTeam(ArrayAdapter<Basurita> spinnerAdapter, String value) {
        if (spinnerAdapter != null) {
            for (int i = 0; i < spinnerAdapter.getCount(); i++) {
                if (spinnerAdapter.getItem(i).getNombre().equals(value)) {
                    return i;
                }
            }
        }
        return -1;
    }
}