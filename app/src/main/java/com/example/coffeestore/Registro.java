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

public class Registro extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String nSpinnerNacionalidad = "";
    private String nSpinnerGenero = "";
    EditText txt_Fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        Spinner spinner = findViewById(R.id.sp_nacionalidad);
        Spinner spinner2 = findViewById(R.id.sp_genero);
        Spinner spinner3 = findViewById(R.id.sp_provincia);
        Spinner spinner4 = findViewById(R.id.sp_ciudad);

        txt_Fecha = findViewById(R.id.txt_fecha);
        ImageButton button = findViewById(R.id.btn_Fecha);
        button.setOnClickListener(v -> {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), "datePicker");
        });


        //Create ArrayAdapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.nacionalidad,
                android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.genero,
                android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Provincia,
                android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.Ciudad,
                android.R.layout.simple_spinner_item);

        // especifico el diseño que se utilizara cuando aparezca la lista de opciones
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //Aplicar el adaptador al spinner
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        spinner4.setAdapter(adapter4);

        //Controlar acciones del spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nSpinnerNacionalidad = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Ha escogido:" + nSpinnerNacionalidad, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                nSpinnerNacionalidad = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Ha escogido:" + nSpinnerNacionalidad, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nSpinnerNacionalidad = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Ha escogido:" + nSpinnerNacionalidad, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        Integer id = view.getId();
        if (id.equals(R.id.rbd_casado)) {
            if (checked) {
                Toast.makeText(getApplicationContext(), "Seleccionaste Casado", Toast.LENGTH_SHORT).show();
            }
        } else if (id.equals(R.id.rbd_soltero)) {
            if (checked) {
                Toast.makeText(getApplicationContext(), "Seleccionaste Soltero", Toast.LENGTH_SHORT).show();
            }
        } else if (id.equals(R.id.rbd_divorciado)) {
            if (checked) {
                Toast.makeText(getApplicationContext(), "Seleccionaste Divorciado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        txt_Fecha.setText(currentDateString);
    }


    // Pasa el contexto actual
    MyOpenHelper databaseHelper = new MyOpenHelper(this);

    public void registrarUsuario(View v) {
        TextInputLayout layoutName = findViewById(R.id.layoutName);
        TextInputEditText txt_name = findViewById(R.id.txt_name);
        TextInputLayout layoutApellido = findViewById(R.id.layoutApellido);
        TextInputEditText txt_apellido = findViewById(R.id.txt_apellidos);
        TextInputLayout layoutCedula = findViewById(R.id.layoutCedula);
        TextInputEditText txt_cedulas = findViewById(R.id.txtcedula);
        TextInputLayout layoutPhone = findViewById(R.id.layouttelefono);
        TextInputEditText txt_phone = findViewById(R.id.txt_Phone);

        String name = layoutName.getEditText().getText().toString().trim();
        String apellidos = layoutApellido.getEditText().getText().toString().trim();
        //String phone = layouttelefono.getEditText().getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(apellidos)) {
            boolean success = databaseHelper.insertData(name, apellidos);
            if (success) {
                Toast.makeText(Registro.this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                // Limpiar los campos después de guardar los datos
                layoutName.getEditText().setText("");
                layoutApellido.getEditText().setText("");
            } else {
                Toast.makeText(Registro.this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Registro.this, "Ingresa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }


    public void Regresar(View v) {
        Intent call_principal = new Intent(v.getContext(), Login.class);
        startActivity(call_principal);
    }

    public void Consultar(View v) {
        Intent call_consultar_usuario = new Intent(v.getContext(), ConsultarUsuario.class);
        startActivity(call_consultar_usuario);
    }
}