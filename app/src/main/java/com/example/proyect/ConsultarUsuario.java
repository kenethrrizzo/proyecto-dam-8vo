package com.example.proyect;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Spinner;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import java.text.DateFormat;
import java.util.Calendar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ConsultarUsuario extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String nSpinnerNacionalidad = "";
    private String nSpinnerGenero = "";
    EditText txt_Fechas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuario);

        Spinner spinner = (Spinner) findViewById(R.id.sp_nacionalidad);
        Spinner spinner2 = (Spinner) findViewById(R.id.sp_genero);
        Spinner spinner3 = (Spinner) findViewById(R.id.sp_provincia);
        Spinner spinner4 = (Spinner) findViewById(R.id.sp_ciudad);

        txt_Fechas= findViewById(R.id.txt_fechas);

        ImageButton button = (ImageButton) findViewById(R.id.btn_Fecha);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), "datePicker");
            }
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
        txt_Fechas.setText(currentDateString);
    }


    public void buscarUsuario(View v) {
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
                MyOpenHelper.COLUMN_PHONE
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
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex(MyOpenHelper.COLUMN_PHONE));

            // Aquí puedes utilizar los datos obtenidos, por ejemplo, mostrarlos en los TextInputLayout
            TextInputLayout nameInputLayout = findViewById(R.id.layoutName);
            TextInputLayout emailInputLayout = findViewById(R.id.layoutApellido);
            TextInputLayout phoneInputLayout = findViewById(R.id.layouttelefono);

            nameInputLayout.getEditText().setText(name);
            emailInputLayout.getEditText().setText(apellidos);
            phoneInputLayout.getEditText().setText(phone);
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

        String idString = idInputLayout.getEditText().getText().toString().trim();
        String name = nameInputLayout.getEditText().getText().toString().trim();
        String apellidos = apellidoInputLayout.getEditText().getText().toString().trim();

        if (!TextUtils.isEmpty(idString) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(apellidos)) {
            int id = Integer.parseInt(idString);
            boolean success = updateData(id, name, apellidos);
            if (success) {
                Toast.makeText(ConsultarUsuario.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                // Limpiar los campos después de actualizar los datos
                idInputLayout.getEditText().setText("");
                nameInputLayout.getEditText().setText("");
                apellidoInputLayout.getEditText().setText("");
               // phoneInputLayout.getEditText().setText("");
            } else {
                Toast.makeText(ConsultarUsuario.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ConsultarUsuario.this, "Ingresa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean updateData(int id, String name, String apellidos) {
        MyOpenHelper databaseHelper = new MyOpenHelper(this); // Pasa el contexto actual
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MyOpenHelper.COLUMN_NAME, name);
        contentValues.put(MyOpenHelper.COLUMN_APELLIDO, apellidos);
        //contentValues.put(MyOpenHelper.COLUMN_PHONE, phone);

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

        String idString = idInputLayout.getEditText().getText().toString().trim();
        String name = nameInputLayout.getEditText().getText().toString().trim();
        String apellidos = apellidoInputLayout.getEditText().getText().toString().trim();

        if (!TextUtils.isEmpty(idString) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(apellidos)) {
            int id = Integer.parseInt(idString);
            boolean success = deleteData(id);
            if (success) {
                Toast.makeText(ConsultarUsuario.this, "Datos eliminados correctamente", Toast.LENGTH_SHORT).show();
                // Limpiar el campo después de eliminar los datos
                idInputLayout.getEditText().setText("");
                nameInputLayout.getEditText().setText("");
                apellidoInputLayout.getEditText().setText("");
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
    }
}


   /* @SuppressLint("Range")
    public void buscarUsuario(View v) {

        MyOpenHelper databaseHelper = new MyOpenHelper(this); // Pasa el contexto actual
        final SQLiteDatabase db = databaseHelper.getReadableDatabase();
        if (db != null) {
            TextInputLayout layoutId = findViewById(R.id.layoutId);
            TextInputEditText txt_id = findViewById(R.id.txt_id);
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

            int identificador = Integer.parseInt(layoutId.getEditText().toString());
            Cursor c= db.rawQuery("SELECT _id, name, apellidos FROM usuario WHERE _id="+identificador);
            //Cursor c1 = db.rawQuery("SELECT _id, name, apellidos FROM usuario WHERE _id="+identificador);
            if (c != null){
                c.moveToFirst();
                txt_name.setText(c.getString(c.getColumnIndex("nombres")).toString());
                txt_name.setText(c.getString(c.getColumnIndex("apellidos")).toString());
            }
            c.close();
            db.close();
        }*/

