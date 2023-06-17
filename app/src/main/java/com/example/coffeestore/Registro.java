package com.example.coffeestore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Spinner;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.text.DateFormat;
import java.util.Calendar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Registro extends AppCompatActivity{

    private String nSpinnerGenero ="";
    private String nSpinnerProvincia= "";
    private String nSpinnerCiudad = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Spinner spinner2 = (Spinner) findViewById(R.id.sp_genero);
        Spinner spinner3 = (Spinner) findViewById(R.id.sp_provincia);
        Spinner spinner4 = (Spinner) findViewById(R.id.sp_ciudad);

        //Create ArrayAdapter
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.genero,
                android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Provincia,
                android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.Ciudad,
                android.R.layout.simple_spinner_item);

        // especifico el diseño que se utilizara cuando aparezca la lista de opciones
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Aplicar el adaptador al spinner
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);

        //Controlar acciones del spinner
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nSpinnerGenero = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Ha escogido:" + nSpinnerGenero, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nSpinnerProvincia = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Ha escogido:" + nSpinnerProvincia, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nSpinnerCiudad= parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Ha escogido:" + nSpinnerCiudad, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    MyOpenHelper databaseHelper = new MyOpenHelper(this); // Pasa el contexto actual
    public void registrarUsuario(View v) {
        TextInputLayout layoutName = findViewById(R.id.layoutName);
        TextInputLayout layoutApellido = findViewById(R.id.layoutApellido);
        TextInputLayout layoutCedula = findViewById(R.id.layoutCedula);
        TextInputLayout layoutPhone = findViewById(R.id.layouttelefono);
        TextInputLayout layoutDireccion = findViewById(R.id.layoutDireccion);
        TextInputLayout layoutPassword= findViewById(R.id.layoutpassword);

        Spinner nSpinnerGenero = findViewById(R.id.sp_genero);
        Spinner nSpinnerProvincia = findViewById(R.id.sp_provincia);
        Spinner nSpinnerCiudad = findViewById(R.id.sp_ciudad);

        String name = layoutName.getEditText().getText().toString().trim();
        String apellidos = layoutApellido.getEditText().getText().toString().trim();
        String cedulaString = layoutCedula.getEditText().getText().toString().trim();
        String phones = layoutPhone.getEditText().getText().toString().trim();
        String direccion = layoutDireccion.getEditText().getText().toString().trim();
        String password = layoutPassword.getEditText().getText().toString().trim();
        String genero = nSpinnerGenero.getSelectedItem().toString();
        String provincia = nSpinnerProvincia.getSelectedItem().toString();
        String ciudad = nSpinnerCiudad.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(apellidos) && !TextUtils.isEmpty(cedulaString)) {
            int cedula = Integer.parseInt(cedulaString);
            int phone = Integer.parseInt(phones);

            boolean success = databaseHelper.insertData(name, apellidos, cedula, genero, phone, direccion, provincia, ciudad,password);
            if (success) {
                Toast.makeText(Registro.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                // Limpiar los campos después de guardar los datos
                layoutName.getEditText().setText("");
                layoutApellido.getEditText().setText("");
                layoutCedula.getEditText().setText("");
                layoutPhone.getEditText().setText("");
                layoutDireccion.getEditText().setText("");
                layoutPassword.getEditText().setText("");

            } else {
                Toast.makeText(Registro.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Registro.this, "Ingresa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Regresar(View v){
        Intent call_principal = new Intent(v.getContext(), Login.class);
        startActivity(call_principal);
    }

    public void Consultar(View v)
    {
        Intent call_consultar_usuario = new Intent(v.getContext(), ConsultarUsuario.class);
        startActivity(call_consultar_usuario);
    }
}