package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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


        if (usuario != null) {
            // Mostrar los datos del usuario en las TextInputLayouts correspondientes
            layoutNombres.getEditText().setText(usuario.getNombres());
            layoutApellido.getEditText().setText(usuario.getApellidos());
            layoutCedula.getEditText().setText(usuario.getCedula());
            layoutPhone.getEditText().setText(usuario.getCedula());
            layoutDireccion.getEditText().setText(usuario.getCedula());
            // Mostrar otros datos del usuario en las TextInputLayouts correspondientes
        }

    }

    UsuarioHelper databaseHelper = new UsuarioHelper(this); // Pasa el contexto actual

    public void ActualizarUsuario(View v) {

        Usuario usuarioActualizado = new Usuario();

        // Actualizar los datos del usuario en la base de datos
        boolean exito = databaseHelper.actualizarUsuario(usuarioActualizado);

        if (exito) {
            Toast.makeText(this, "Usuario actualizado exitosamente", Toast.LENGTH_SHORT).show();
            // Realizar cualquier otra acción necesaria después de la actualización
        } else {
            Toast.makeText(this, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show();
        }
    }
    /*private boolean updateData(Usuario usuario) {
        UsuarioHelper databaseHelper = new UsuarioHelper(this); // Pasa el contexto actual
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsuarioHelper.COLUMN_NOMBRES, usuario.getNombres());
        contentValues.put(UsuarioHelper.COLUMN_APELLIDOS, usuario.getApellidos());
        contentValues.put(UsuarioHelper.COLUMN_CEDULA, usuario.getCedula());
        contentValues.put(UsuarioHelper.COLUMN_GENERO, usuario.getGenero());
        contentValues.put(UsuarioHelper.COLUMN_NUMERO_TELEFONICO, usuario.getNumeroTelefonico());
        contentValues.put(UsuarioHelper.COLUMN_DIRECCION, usuario.getDireccion());
        contentValues.put(UsuarioHelper.COLUMN_PROVINCIA, usuario.getProvincia());
        contentValues.put(UsuarioHelper.COLUMN_CIUDAD, usuario.getCiudad());

        //String selection = UsuarioHelper.COLTABLE_USUARIOUMN_ID + " = ?";
        //String[] selectionArgs = {String.valueOf(id)};

        int actualizados = db.update(TABLE_USUARIO, contentValues, null, null);

        return actualizados  > 0;
        // int rowsAffected = db.update(UsuarioHelper.TABLE_USUARIO, contentValues, selection, selectionArgs);

        //  return rowsAffected > 0;
    }*/




    /*public void ActualizarUsuario (View v){
        TextInputLayout idInputLayout = findViewById(R.id.layoutId);
        TextInputLayout nameInputLayout = findViewById(R.id.layoutName);
        TextInputLayout apellidoInputLayout = findViewById(R.id.layoutApellido);
        TextInputLayout cedulaInputLayout = findViewById(R.id.layoutCedula);
        TextInputLayout phoneInputLayout = findViewById(R.id.layouttelefono);
        TextInputLayout direccionInputLayout = findViewById(R.id.layoutDireccion);

        Spinner nSpinnerGenero = findViewById(R.id.sp_genero);
        Spinner nSpinnerProvincia = findViewById(R.id.sp_provincia);
        Spinner nSpinnerCiudad = findViewById(R.id.sp_ciudad);

        String idString = idInputLayout.getEditText().getText().toString().trim();
        String name = nameInputLayout.getEditText().getText().toString().trim();
        String apellidos = apellidoInputLayout.getEditText().getText().toString().trim();
        String cedulaString = cedulaInputLayout.getEditText().getText().toString().trim();
        String direccion = direccionInputLayout.getEditText().getText().toString().trim();
        String phones= phoneInputLayout.getEditText().getText().toString().trim();
        String genero = nSpinnerGenero.getSelectedItem().toString();
        String provincia = nSpinnerProvincia.getSelectedItem().toString();
        String ciudad = nSpinnerCiudad.getSelectedItem().toString();

        if (!TextUtils.isEmpty(idString) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(apellidos)&& !TextUtils.isEmpty(cedulaString)) {
            int id = Integer.parseInt(idString);
            int cedula = Integer.parseInt(cedulaString);
            int phone = Integer.parseInt(phones);

            boolean success = updateData(id, name, apellidos, cedula,genero,phone,direccion,provincia,ciudad);
            if (success) {
                Toast.makeText(ConsultarUsuario.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                // Limpiar los campos después de actualizar los datos
                idInputLayout.getEditText().setText("");
                nameInputLayout.getEditText().setText("");
                apellidoInputLayout.getEditText().setText("");
                cedulaInputLayout.getEditText().setText("");
                phoneInputLayout.getEditText().setText("");
                direccionInputLayout.getEditText().setText("");
            } else {
                Toast.makeText(ConsultarUsuario.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ConsultarUsuario.this, "Ingresa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean updateData(int id, String name, String apellidos,int cedula, String genero, int  phone, String direccion, String provincia, String ciudad) {
        UsuarioHelper databaseHelper = new UsuarioHelper(this); // Pasa el contexto actual
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsuarioHelper.COLUMN_NAME, name);
        contentValues.put(UsuarioHelper.COLUMN_APELLIDO, apellidos);
        contentValues.put(UsuarioHelper.COLUMN_CEDULA, cedula);
        contentValues.put(UsuarioHelper.COLUMN_GENERO, genero);
        contentValues.put(UsuarioHelper.COLUMN_PHONE, phone);
        contentValues.put(UsuarioHelper.COLUMN_DIRECCION, direccion);
        contentValues.put(UsuarioHelper.COLUMN_PROVINCIA, provincia);
        contentValues.put(UsuarioHelper.COLUMN_CIUDAD, ciudad);

        String selection = UsuarioHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int rowsAffected = db.update(UsuarioHelper.TABLE_NAME, contentValues, selection, selectionArgs);

        db.close();

        return rowsAffected > 0;
    }*/



}