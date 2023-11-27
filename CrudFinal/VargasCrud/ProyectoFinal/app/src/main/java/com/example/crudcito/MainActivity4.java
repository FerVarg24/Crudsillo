package com.example.crudcito;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
public class MainActivity4 extends AppCompatActivity implements
        View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextInputEditText etClave, etNombre;
    RadioGroup grupillo;
    RadioButton rDisponible, rLesionado;
    Spinner sDiv, sEquipo;
    CheckBox cTrade, cExt;
    MaterialButton btnRemplazar, btnRegresar;
    ArrayAdapter<String> divisiones;
    ArrayAdapter<Basurita> equipos;
    ArrayList<Basurita> Arecibe = new ArrayList<>();
    int seleccionado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        etClave = findViewById(R.id.etClave);
        etNombre = findViewById(R.id.etNombre);
        grupillo = findViewById(R.id.rGrupillo);
        rDisponible = findViewById(R.id.rDisponible);
        rLesionado = findViewById(R.id.rLesionado);
        sDiv = findViewById(R.id.sDiv);
        sEquipo = findViewById(R.id.sEquipo);
        cTrade = findViewById(R.id.cTrade);
        cExt = findViewById(R.id.cExt);
        btnRemplazar = findViewById(R.id.btnRemplazar);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnRemplazar.setOnClickListener(this);
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
        if (cadenita.equals("Buscar")) {
            Buscado();
            limpiezaRemplaza();
        } else if (cadenita.equals("Regresar")) {
            Intent intentito = new Intent(this, MainActivity.class);
            startActivity(intentito);
        } else if(cadenita.equals("Reemplazar")){
            Remplazado();
            Reinicia();
        }
    }

    private void Reinicia() {
        etClave.setEnabled(true);
        etClave.setText("");
        etNombre.setEnabled(false);
        etNombre.setText("");
        grupillo.setEnabled(false);
        grupillo.clearCheck();
        rDisponible.setEnabled(false);
        rLesionado.setEnabled(false);
        sDiv.setEnabled(false);
        sDiv.setSelection(0);
        sEquipo.setEnabled(false);
        cTrade.setEnabled(false);
        cTrade.setChecked(false);
        cExt.setEnabled(false);
        cExt.setChecked(false);
        btnRemplazar.setText("Buscar");
    }

    private void Remplazado() {
        Base admin = new Base(this,
                "rostersillo",
                null, 1);
        SQLiteDatabase basededatos = admin.getWritableDatabase();

        String clave = etClave.getText().toString();
        String nombre = etNombre.getText().toString();
        String disp = rDisponible.isChecked() ? "Disponible" : "Lesionado";
        String divs = sDiv.getSelectedItem().toString();
        String team = sEquipo.getSelectedItem().toString();
        boolean trade = cTrade.isChecked();
        boolean extension = cExt.isChecked();

        if(!clave.isEmpty() && !nombre.isEmpty() && !disp.isEmpty() && !divs.isEmpty() &&
                !team.isEmpty()){
            if (trade || extension){
                ContentValues registro = new ContentValues();
                registro.put("nombre", nombre);
                registro.put("lesion", disp);
                registro.put("conf", divs);
                registro.put("equipo", team);
                registro.put("trade", trade ? 1 : 0);
                registro.put("ext", extension ? 1 : 0);
                long resultado = basededatos.update("jugadores", registro,
                        "clave = " + clave, null);
                basededatos.close();
                if(resultado > 0)
                    Toast.makeText(this, "Modificaciones correctas",
                            Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "debes llenar los campos",
                            Toast.LENGTH_LONG).show();
            }
        }

    }

    private void limpiezaRemplaza() {
        etClave.setEnabled(false);
        etNombre.setEnabled(true);
        grupillo.setEnabled(true);
        rDisponible.setEnabled(true);
        rLesionado.setEnabled(true);
        sDiv.setEnabled(true);
        sEquipo.setEnabled(true);
        cTrade.setEnabled(true);
        cExt.setEnabled(true);
        btnRemplazar.setText("Reemplazar");
    }

    private void Buscado() {
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

                String disp = fila.getString(1);
                if (disp.equals("Disponible")) {
                    grupillo.check(R.id.rDisponible);
                } else if (disp.equals("Lesionado")) {
                    grupillo.check(R.id.rLesionado);
                }

                String division = fila.getString(2);
                int divIndex = getIndexFromSpinner(divisiones, division);
                sDiv.setSelection(divIndex);

                String equipo = fila.getString(3);
                int teamIndex = obtenerIndexCiudad(equipos, equipo);
                sEquipo.setSelection(teamIndex);

                boolean trade = fila.getInt(4) == 1;
                boolean extension = fila.getInt(5) == 1;

                Log.d("Debug", "ida: " + fila.getInt(4));
                Log.d("Debug", "vuelta: " + fila.getInt(5));

                cTrade.setChecked(trade);
                cExt.setChecked(extension);

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

    private int getIndexFromSpinner(ArrayAdapter<String> spinnerAdapter, String value) {
        for (int i = 0; i < spinnerAdapter.getCount(); i++) {
            if (spinnerAdapter.getItem(i).equals(value)) {
                return i;
            }
        }
        return 0;
    }

    private int obtenerIndexCiudad(ArrayAdapter<Basurita> spinnerAdapter, String value) {
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