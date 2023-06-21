package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private TextInputLayout layoutNombres, layoutApellido, layoutCedula, layoutPhone, layoutDireccion;
    TextInputEditText txt_nombres,txt_apellidos, txt_cedula,txt_numeroTelefonico, txt_direccion;
    private Usuario usuario;
    private Button btnActualizar;
    private SharedPreferences sharedPreferences;
    private Spinner spinnerGenero, spinnerProvincia, spinnerCiudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuario);

        sharedPreferences = getSharedPreferences("MiSharedPreferences", MODE_PRIVATE);

        // Inicialización de vistas
        layoutNombres = findViewById(R.id.layoutName);
        txt_nombres = findViewById(R.id.txt_name);
        layoutApellido = findViewById(R.id.layoutApellido);
        txt_apellidos = findViewById(R.id.txt_apellidos);
        layoutCedula = findViewById(R.id.layoutCedula);
        txt_cedula = findViewById(R.id.txtcedula);
        layoutPhone = findViewById(R.id.layouttelefono);
        txt_numeroTelefonico = findViewById(R.id.txt_Phone);
        layoutDireccion = findViewById(R.id.layoutDireccion);
        txt_direccion = findViewById(R.id.txt_direccion);


        spinnerGenero = findViewById(R.id.sp_genero);
        spinnerProvincia = findViewById(R.id.sp_provincia);
        spinnerCiudad = findViewById(R.id.sp_ciudad);
        // Configuración de Spinner para Género
        ArrayAdapter<CharSequence> generoAdapter = ArrayAdapter.createFromResource(this, R.array.genero, android.R.layout.simple_spinner_item);
        generoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(generoAdapter);

        // Configuración de Spinner para Provincia
        ArrayAdapter<CharSequence> provinciaAdapter = ArrayAdapter.createFromResource(this, R.array.Provincia, android.R.layout.simple_spinner_item);
        provinciaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvincia.setAdapter(provinciaAdapter);

        // Configuración de Spinner para Ciudad
        ArrayAdapter<CharSequence> ciudadAdapter = ArrayAdapter.createFromResource(this, R.array.Ciudad, android.R.layout.simple_spinner_item);
        ciudadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudad.setAdapter(ciudadAdapter);

        // Configuración del botón Actualizar
        btnActualizar = findViewById(R.id.btn_actualizar);
        btnActualizar.setOnClickListener(v -> actualizar());


        //Consutar el usuario
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
        // Seleccionar los valores adecuados en los spinners
        seleccionarValorSpinner(spinnerGenero, usuario.getGenero());
        seleccionarValorSpinner(spinnerProvincia, usuario.getProvincia());
        seleccionarValorSpinner(spinnerCiudad, usuario.getCiudad());

    }

    private void seleccionarValorSpinner(Spinner spinner, String valor) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        int position = adapter.getPosition(valor);
        spinner.setSelection(position);
    }

    private void actualizar() {

        // Obtener los nuevos valores de los campos de texto
        Spinner nSpinnerGenero = findViewById(R.id.sp_genero);
        Spinner nSpinnerProvincia = findViewById(R.id.sp_provincia);
        Spinner nSpinnerCiudad = findViewById(R.id.sp_ciudad);
        String nuevosNombres = layoutNombres.getEditText().getText().toString().trim();
        String nuevosApellidos = layoutApellido.getEditText().getText().toString().trim();
        String nuevaCedula = layoutCedula.getEditText().getText().toString().trim();
        String nuevoGenero = nSpinnerGenero.getSelectedItem().toString();
        String nuevoNumeroTelefonico = layoutPhone.getEditText().getText().toString().trim();
        String nuevaDireccion = layoutDireccion.getEditText().getText().toString().trim();
        String nuevaProvincia = nSpinnerProvincia.getSelectedItem().toString();
        String nuevaCiudad = nSpinnerCiudad.getSelectedItem().toString();
        // Validar que no haya campos vacíos
        if (nuevosNombres.isEmpty() || nuevosApellidos.isEmpty() || nuevaCedula.isEmpty() ||
                nuevoNumeroTelefonico.isEmpty() || nuevaDireccion.isEmpty() ) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        // Actualizar los datos del usuario
        usuario.setNombres(nuevosNombres);
        usuario.setApellidos(nuevosApellidos);
        usuario.setCedula(nuevaCedula);
        usuario.setGenero(nuevoGenero);
        usuario.setNumeroTelefonico(nuevoNumeroTelefonico);
        usuario.setDireccion(nuevaDireccion);
        usuario.setProvincia(nuevaProvincia);
        usuario.setCiudad(nuevaCiudad);

        try (UsuarioHelper helper = new UsuarioHelper(this)) {
            SharedPreferences sharedPreferences = getSharedPreferences("usuarioId", Context.MODE_PRIVATE);
            String usuarioId = sharedPreferences.getString("id", "0");
            boolean isUpdated = helper.actualizarUsuarios(Integer.parseInt(usuarioId), usuario);
            if (!isUpdated) {
                Toast.makeText(this, "Error al actualizar usuario", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
        finish(); // Finalizar la actividad de actualización
    }
    public void CancelarRegistro (View v){
        Intent call_producto = new Intent(v.getContext(), ActivityProductos.class);
        startActivity(call_producto);
    }


}