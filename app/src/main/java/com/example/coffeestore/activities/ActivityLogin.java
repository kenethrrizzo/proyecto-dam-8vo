package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import  android.content.Intent;
import android.widget.Toast;

import com.example.coffeestore.R;
import com.example.coffeestore.database.MyOpenHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ActivityLogin extends AppCompatActivity {

    TextInputLayout layoutUsuario,layoutPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       layoutUsuario = findViewById(R.id.layoutUsuario);
       layoutPassword = findViewById(R.id.layoutcontrasena);

    }
    MyOpenHelper databaseHelper = new MyOpenHelper(this); // Pasa el contexto actual

      public void Ingresar(View v) {
          // Obtener los valores ingresados por el usuario

          String nombreUsuario = layoutUsuario.getEditText().getText().toString().trim();
          String password = layoutPassword.getEditText().getText().toString().trim();

          // Validar el nombre de usuario y la contraseña en la base de datos
          boolean loginExitoso = databaseHelper.validarCredenciales(nombreUsuario, password);

          if (loginExitoso) {
              // Redirigir al usuario a la pantalla de productos
              Intent call_productos = new Intent(v.getContext(), ActivityProductos.class);
              startActivity(call_productos);
              finish();
          } else {
              Toast.makeText(this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
          }
      }

    public void Cancelar (View v) {
        Intent intent= new Intent(v.getContext(), ActivityProductos.class);
        startActivity(intent);
        //finishAffinity();
    }
    public void CrearCuenta (View v){
        Intent call_principal = new Intent(v.getContext(), ActivityRegistro.class);
        startActivity(call_principal);
    }
}