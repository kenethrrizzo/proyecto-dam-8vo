package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

    private String nSpinnerGenero = "";
    private String nSpinnerProvincia = "";
    private String nSpinnerCiudad = "";
    private TextInputLayout layoutNombres, layoutApellido, layoutCedula, layoutPhone, layoutDireccion;
    TextInputEditText txt_nombres,txt_apellidos, txt_cedula,txt_numeroTelefonico, txt_direccion;

    private Usuario usuario;
    private Button btnActualizar;
    private SharedPreferences sharedPreferences;


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
        // Configuración del botón Actualizar
        btnActualizar = findViewById(R.id.btn_actualizar);
        btnActualizar.setOnClickListener(v -> actualizar());

        /*// Seleccionar los valores adecuados en los spinners
        seleccionarValorSpinner(nSpinnerGenero, usuario.getGenero());
        seleccionarValorSpinner(nSpinnerProvincia, usuario.getProvincia());
        seleccionarValorSpinner(nSpinnerCiudad, usuario.getCiudad());*/

        //Consutar el usuario
        try (UsuarioHelper helper = new UsuarioHelper(this)) {
            SharedPreferences sharedPreferences = getSharedPreferences("usuarioId", Context.MODE_PRIVATE);
            String usuarioId = sharedPreferences.getString("id", "0");

            usuario = helper.getUsuarioPorId(Integer.valueOf(usuarioId));

        }

       /* Spinner nSpinnerGenero = findViewById(R.id.sp_genero);
        Spinner nSpinnerProvincia = findViewById(R.id.sp_provincia);
        Spinner nSpinnerCiudad = findViewById(R.id.sp_ciudad);*/
        txt_nombres.setText(usuario.getNombres());
        txt_apellidos.setText(usuario.getApellidos());
        txt_cedula.setText(usuario.getCedula());
        txt_numeroTelefonico.setText(usuario.getNumeroTelefonico());
        txt_direccion.setText(usuario.getDireccion());


       /* try (UsuarioHelper helper = new UsuarioHelper(this)) {
            SharedPreferences sharedPreferences = getSharedPreferences("usuarioId", Context.MODE_PRIVATE);
            String usuarioID = sharedPreferences.getString("id", "0");

            usuario = helper.ActualizarUsuarioPorId(Integer.valueOf(usuarioID));

        }


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
        Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
        finish(); // Finalizar la actividad de actualización
        }/*


        private void seleccionarValorSpinner (Spinner spinner, String valor){
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
            int position = adapter.getPosition(valor);
            spinner.setSelection(position);*/
            // Obtener el usuario actual de la base de datos o de la actividad anterior
            //  usuario = obtenerUsuarioActual();

            // Llenar los campos con los datos actuales del usuario
            //llenarCamposUsuario();
        }




    UsuarioHelper databaseHelper = new UsuarioHelper(this); // Pasa el contexto actual

  /*  private Usuario obtenerUsuarioActual() {

        if (getIntent().hasExtra("usuario")) {
            return (Usuario) getIntent().getSerializableExtra("usuario");
        } else {
            return null;
        }
    }

    private void llenarCamposUsuario() {
        if (usuario != null) {

            layoutNombres.getEditText().setText(usuario.getNombres());
            layoutApellido.getEditText().setText(usuario.getApellidos());
            layoutCedula.getEditText().setText(usuario.getCedula());
            layoutPhone.getEditText().setText(usuario.getNumeroTelefonico());
            layoutDireccion.getEditText().setText(usuario.getDireccion());
            Spinner nSpinnerGenero = findViewById(R.id.sp_genero);
            Spinner nSpinnerProvincia = findViewById(R.id.sp_provincia);
            Spinner nSpinnerCiudad = findViewById(R.id.sp_ciudad);


            // Seleccionar los valores adecuados en los spinners
            seleccionarValorSpinner(nSpinnerGenero, usuario.getGenero());
            seleccionarValorSpinner(nSpinnerProvincia, usuario.getProvincia());
            seleccionarValorSpinner(nSpinnerCiudad, usuario.getCiudad());
        }
    }

    private void seleccionarValorSpinner(Spinner spinner, String valor) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        int position = adapter.getPosition(valor);
        spinner.setSelection(position);
    }*/



  /*  private void actualizarUsuario() {
        Spinner nSpinnerGenero = findViewById(R.id.sp_genero);
        Spinner nSpinnerProvincia = findViewById(R.id.sp_provincia);
        Spinner nSpinnerCiudad = findViewById(R.id.sp_ciudad);
       /* try (UsuarioHelper usuarioHelper = new UsuarioHelper(this)) {
            SharedPreferences sharedPreferences = getSharedPreferences("usuarioID", Context.MODE_PRIVATE);
            String usuarioID = sharedPreferences.getString("id", "0");

           // usuario = usuarioHelper.actualizarUsuario(Integer.valueOf(usuarioID));

        }

        // Obtener los nuevos valores de los campos de texto
        String nuevosNombres = layoutNombres.getEditText().getText().toString().trim();
        String nuevosApellidos = layoutApellido.getEditText().getText().toString().trim();
        String nuevaCedula = layoutCedula.getEditText().getText().toString().trim();
        String nuevoGenero = nSpinnerGenero.getSelectedItem().toString();
        String nuevoNumeroTelefonico = layoutPhone.getEditText().getText().toString().trim();
        String nuevaDireccion = layoutDireccion.getEditText().getText().toString().trim();
        String nuevaProvincia = nSpinnerProvincia.getSelectedItem().toString();
        String nuevaCiudad = nSpinnerCiudad.getSelectedItem().toString();

        // Validar que no haya campos vacíos
        if (nuevosNombres.isEmpty() || nuevosApellidos.isEmpty() || nuevaCedula.isEmpty() || nuevoNumeroTelefonico.isEmpty() || nuevaDireccion.isEmpty() ) {
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

        // Actualizar el usuario en la base de datos
        int result = databaseHelper.actualizarUsuarios();

        if (result>0){
            Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
            finish(); // Finalizar la actividad de actualización
        } else {
            Toast.makeText(this, "Error al actualizar usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void seleccionarValorSpinner(Spinner spinner, String valor) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        int position = adapter.getPosition(valor);
        spinner.setSelection(position);
    }*/

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



}