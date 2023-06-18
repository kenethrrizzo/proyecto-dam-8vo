package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.coffeestore.R;


public class ActivityConsultarUsuario extends AppCompatActivity  {

    private String nSpinnerGenero = "";
    private String nSpinnerProvincia= "";
    private String nSpinnerCiudad = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuario);

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

   /* public void buscarUsuario(View v) {
        TextInputLayout idInputLayout = findViewById(R.id.layoutId);
        String idString = idInputLayout.getEditText().getText().toString().trim();

        if (!TextUtils.isEmpty(idString)) {
            int id = Integer.parseInt(idString);
            searchDataById(id);
        } else {
            Toast.makeText(ConsultarUsuario.this, "Ingresa un ID válido", Toast.LENGTH_SHORT).show();
        }
    }
    private void searchDataById(int id) {
        MyOpenHelper databaseHelper = new MyOpenHelper(this); // Pasa el contexto actual
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                MyOpenHelper.COLUMN_NAME,
                MyOpenHelper.COLUMN_APELLIDO,
                MyOpenHelper.COLUMN_CEDULA,
                MyOpenHelper.COLUMN_GENERO,
                MyOpenHelper.COLUMN_PHONE,
                MyOpenHelper.COLUMN_DIRECCION,
                MyOpenHelper.COLUMN_PROVINCIA,
                MyOpenHelper.COLUMN_CIUDAD,
        };

        String selection = MyOpenHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                MyOpenHelper.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_NAME));
            @SuppressLint("Range") String apellidos = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_APELLIDO));
            @SuppressLint("Range") String cedula = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_CEDULA));
            @SuppressLint("Range") String genero = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_GENERO));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_PHONE));
            @SuppressLint("Range") String direccion = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_DIRECCION));
            @SuppressLint("Range") String provincia = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_PROVINCIA));
            @SuppressLint("Range") String ciudad = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_CIUDAD));

            // Aquí puedes utilizar los datos obtenidos, por ejemplo, mostrarlos en los TextInputLayout
            TextInputLayout nameInputLayout = findViewById(R.id.layoutName);
            TextInputLayout apellidoInputLayout = findViewById(R.id.layoutApellido);
            TextInputLayout cedulaInputLayout = findViewById(R.id.layoutCedula);
            TextInputLayout phoneInputLayout = findViewById(R.id.layouttelefono);
            TextInputLayout direccionInputLayout = findViewById(R.id.layoutDireccion);

            nameInputLayout.getEditText().setText(name);
            apellidoInputLayout.getEditText().setText(apellidos);
            cedulaInputLayout.getEditText().setText(cedula);
            phoneInputLayout.getEditText().setText(phone);
            direccionInputLayout.getEditText().setText(direccion);
        } else {
            Toast.makeText(ConsultarUsuario.this, "No se encontraron resultados", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }

    public void ActualizarUsuario (View v){
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
        MyOpenHelper databaseHelper = new MyOpenHelper(this); // Pasa el contexto actual
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MyOpenHelper.COLUMN_NAME, name);
        contentValues.put(MyOpenHelper.COLUMN_APELLIDO, apellidos);
        contentValues.put(MyOpenHelper.COLUMN_CEDULA, cedula);
        contentValues.put(MyOpenHelper.COLUMN_GENERO, genero);
        contentValues.put(MyOpenHelper.COLUMN_PHONE, phone);
        contentValues.put(MyOpenHelper.COLUMN_DIRECCION, direccion);
        contentValues.put(MyOpenHelper.COLUMN_PROVINCIA, provincia);
        contentValues.put(MyOpenHelper.COLUMN_CIUDAD, ciudad);

        String selection = MyOpenHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int rowsAffected = db.update(MyOpenHelper.TABLE_NAME, contentValues, selection, selectionArgs);

        db.close();

        return rowsAffected > 0;
    }

    public  void EliminarUsuario(View v){
        TextInputLayout idInputLayout = findViewById(R.id.layoutId);
        TextInputLayout nameInputLayout = findViewById(R.id.layoutName);
        TextInputLayout apellidoInputLayout = findViewById(R.id.layoutApellido);
        TextInputLayout cedulaInputLayout = findViewById(R.id.layoutCedula);
        TextInputLayout phoneInputLayout = findViewById(R.id.layouttelefono);
        TextInputLayout direccionInputLayout = findViewById(R.id.layoutDireccion);

        String idString = idInputLayout.getEditText().getText().toString().trim();
        String name = nameInputLayout.getEditText().getText().toString().trim();
        String apellidos = apellidoInputLayout.getEditText().getText().toString().trim();
        String cedulaString = cedulaInputLayout.getEditText().getText().toString().trim();
        String direccion = direccionInputLayout.getEditText().getText().toString().trim();
        String phonee= phoneInputLayout.getEditText().getText().toString().trim();

        if (!TextUtils.isEmpty(idString) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(apellidos) && !TextUtils.isEmpty(cedulaString)) {
            int id = Integer.parseInt(idString);

            boolean success = deleteData(id);
            if (success) {
                Toast.makeText(ConsultarUsuario.this, "Datos eliminados correctamente", Toast.LENGTH_SHORT).show();
                // Limpiar el campo después de eliminar los datos
                idInputLayout.getEditText().setText("");
                nameInputLayout.getEditText().setText("");
                apellidoInputLayout.getEditText().setText("");
                cedulaInputLayout.getEditText().setText("");
                direccionInputLayout.getEditText().setText("");
                phoneInputLayout.getEditText().setText("");
            } else {
                Toast.makeText(ConsultarUsuario.this, "Error al eliminar los datos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ConsultarUsuario.this, "Ingresa un ID válido", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean deleteData(int id) {
        MyOpenHelper databaseHelper = new MyOpenHelper(this); // Pasa el contexto actual
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String selection = MyOpenHelper.COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int rowsAffected = db.delete(MyOpenHelper.TABLE_NAME, selection, selectionArgs);

        db.close();

        return rowsAffected > 0;
    }
    public void Salir(View v) {
        Intent call_Registro = new Intent(v.getContext(), Registro.class);
        startActivity(call_Registro);
    }*/
}