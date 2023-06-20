package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.coffeestore.R;
import com.example.coffeestore.database.UsuarioHelper;
import com.example.coffeestore.dto.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ActivityConsultarUsuario extends AppCompatActivity {

    private String nSpinnerGenero = "";
    private String nSpinnerProvincia = "";
    private String nSpinnerCiudad = "";
    private Usuario usuario;
    private TextInputLayout layoutNombres, layoutApellido, layoutCedula, layoutPhone, layoutDireccion;

    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuario);

        layoutNombres = findViewById(R.id.layoutName);
        TextInputEditText txt_nombres = findViewById(R.id.txt_name);
        layoutApellido = findViewById(R.id.layoutApellido);
        TextInputEditText txt_apellidos = findViewById(R.id.txt_apellidos);
        layoutCedula = findViewById(R.id.layoutCedula);
        TextInputEditText txt_cedula = findViewById(R.id.txtcedula);
        layoutPhone = findViewById(R.id.layouttelefono);
        TextInputEditText txt_numeroTelefonico = findViewById(R.id.txt_Phone);
        layoutDireccion = findViewById(R.id.layoutDireccion);
        TextInputEditText txt_direccion = findViewById(R.id.txt_direccion);
        Spinner ciudad = findViewById(R.id.sp_ciudad);

        Usuario usuario;
        try (UsuarioHelper helper = new UsuarioHelper(this)) {
            SharedPreferences sharedPreferences = getSharedPreferences("usuarioId", Context.MODE_PRIVATE);
            String usuarioId = sharedPreferences.getString("id", "0");

            usuario = helper.getUsuarioPorId(Integer.valueOf(usuarioId));

        }

        txt_nombres.setText(usuario.getNombres());
        txt_apellidos.setText(usuario.getApellidos());
        txt_cedula.setText(usuario.getCedula());
        txt_numeroTelefonico.setText(usuario.getNumeroTelefonico());
        txt_direccion.setText(usuario.getDireccion());


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

    UsuarioHelper databaseHelper = new UsuarioHelper(this); // Pasa el contexto actual

   /* public void ActualizarUsuario(View v) {

        // Inicializar las vistas
        Spinner nSpinnerGenero = findViewById(R.id.sp_genero);
        Spinner nSpinnerProvincia = findViewById(R.id.sp_provincia);
        Spinner nSpinnerCiudad = findViewById(R.id.sp_ciudad);

        String nombres = layoutNombres.getEditText().getText().toString().trim();
        String apellidos = layoutApellido.getEditText().getText().toString().trim();
        String cedula = layoutCedula.getEditText().getText().toString().trim();
        String numeroTelefonico = layoutPhone.getEditText().getText().toString().trim();
        String direccion = layoutDireccion.getEditText().getText().toString().trim();
        String genero = nSpinnerGenero.getSelectedItem().toString();
        String provincia = nSpinnerProvincia.getSelectedItem().toString();
        String ciudad = nSpinnerCiudad.getSelectedItem().toString();

        // Crear un nuevo objeto Usuario
        Usuario usuarioActualizado = new Usuario();


        // Guardar el usuario en la base de datos
        boolean resultados = databaseHelper.actualizarUsuario(usuarioActualizado);
        if (resultados) {
            Toast.makeText(this, "Usuario actualizado exitosamente", Toast.LENGTH_SHORT).show();
            // Limpiar los campos después de guardar los datos

        } else {
            Toast.makeText(this, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show();
        }

    }*/


    public void ActualizarUsuario (View v){

        // Crear un nuevo objeto Usuario
        Usuario usuario = new Usuario();

        boolean success = databaseHelper.updateData(usuario.getId());
            if (success) {
                Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
    }


    public void CancelarRegistro(View v){
        Intent call_principal = new Intent(v.getContext(), ActivityProductos.class);
        startActivity(call_principal);
    }


}