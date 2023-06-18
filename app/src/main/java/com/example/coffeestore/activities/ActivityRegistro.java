package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Spinner;
import android.content.Intent;
import com.example.coffeestore.R;

import com.example.coffeestore.database.MyOpenHelper;
import com.example.coffeestore.dto.Usuario;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityRegistro extends AppCompatActivity {

    private String nSpinnerGenero = "";
    private String nSpinnerProvincia = "";
    private String nSpinnerCiudad = "";
    private TextInputLayout layoutNombres, layoutApellido, layoutCedula, layoutPhone, layoutDireccion, layoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        layoutNombres = findViewById(R.id.layoutName);
        layoutApellido = findViewById(R.id.layoutApellido);
        layoutCedula = findViewById(R.id.layoutCedula);
        layoutPhone = findViewById(R.id.layouttelefono);
        layoutDireccion = findViewById(R.id.layoutDireccion);
        layoutPassword = findViewById(R.id.layoutpassword);


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
                nSpinnerCiudad = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Ha escogido:" + nSpinnerCiudad, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    MyOpenHelper databaseHelper = new MyOpenHelper(this); // Pasa el contexto actual

    public void registrarUsuario(View v) {
        // Inicializar las vistas
        Spinner nSpinnerGenero = findViewById(R.id.sp_genero);
        Spinner nSpinnerProvincia = findViewById(R.id.sp_provincia);
        Spinner nSpinnerCiudad = findViewById(R.id.sp_ciudad);

        String nombres = layoutNombres.getEditText().getText().toString().trim();
        String apellidos = layoutApellido.getEditText().getText().toString().trim();
        String cedula = layoutCedula.getEditText().getText().toString().trim();
        String numeroTelefonico = layoutPhone.getEditText().getText().toString().trim();
        String direccion = layoutDireccion.getEditText().getText().toString().trim();
        String password = layoutPassword.getEditText().getText().toString().trim();
        String genero = nSpinnerGenero.getSelectedItem().toString();
        String provincia = nSpinnerProvincia.getSelectedItem().toString();
        String ciudad = nSpinnerCiudad.getSelectedItem().toString();

        // Crear un nuevo objeto Usuario
        Usuario usuario = new Usuario();
        usuario.setNombres(nombres);
        usuario.setApellidos(apellidos);
        usuario.setCedula(cedula);
        usuario.setNumeroTelefonico(numeroTelefonico);
        usuario.setDireccion(direccion);
        usuario.setPassword(password);
        usuario.setGenero(genero);
        usuario.setProvincia(provincia);
        usuario.setCiudad(ciudad);

        // Guardar el usuario en la base de datos
        boolean resultado = databaseHelper.insertData(usuario);
        if (resultado) {
            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
            // Limpiar los campos después de guardar los datos
            layoutNombres.getEditText().setText("");
            layoutApellido.getEditText().setText("");
            layoutCedula.getEditText().setText("");
            layoutPhone.getEditText().setText("");
            layoutDireccion.getEditText().setText("");
        } else {
            Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
        }
    }


       /* boolean resultado = databaseHelper.insertData(usuario);

        if (resultado != -1) {
            Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
            /*layoutNombres.getEditText().setText("");
            layoutApellido.getEditText().setText("");
            layoutCedula.getEditText().setText("");
            layoutPhone.getEditText().setText("");
            layoutDireccion.getEditText().setText("");
            layoutPassword.getEditText().setText("");
            finish();
        } else {
            Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
        }
    }*/


    public void Regresar(View v){
        Intent call_principal = new Intent(v.getContext(), ActivityLogin.class);
        startActivity(call_principal);
    }

    public void Consultar(View v)
    {
        Intent call_consultar_usuario = new Intent(v.getContext(), ActivityConsultarUsuario.class);
        startActivity(call_consultar_usuario);
    }
}