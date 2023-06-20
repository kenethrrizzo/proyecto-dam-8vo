package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import  android.content.Intent;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.coffeestore.R;
import com.example.coffeestore.database.UsuarioHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ActivityLogin extends AppCompatActivity {

    CheckBox checkboxSession;
    SharedPreferences sharedPreferences;
    TextInputLayout layoutUsuario,layoutPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       layoutUsuario = findViewById(R.id.layoutUsuario);
       TextInputEditText  txt_usuario = findViewById(R.id.txt_Usuario);
       layoutPassword = findViewById(R.id.layoutcontrasena);
        TextInputEditText  txt_password = findViewById(R.id.txt_Usuario);



    }
    UsuarioHelper databaseHelper = new UsuarioHelper(this); // Pasa el contexto actual

      public void Ingresar(View v) {
          // Obtener los valores ingresados por el usuario

          String nombreUsuario = layoutUsuario.getEditText().getText().toString().trim();
          String password = layoutPassword.getEditText().getText().toString().trim();
          checkboxSession = findViewById(R.id.checkBox);
          sharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);


          // Validar el nombre de usuario y la contraseña en la base de datos
          int userId = databaseHelper.validarCredenciales(nombreUsuario, password);

          if (userId > 0) {
              // Verificar si la sesión está activada en SharedPreferences
              // Redirigir al usuario a la pantalla de productos
              SharedPreferences sharedPreferences = getSharedPreferences("usuarioId", Context.MODE_PRIVATE);
              @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
              // Verificar si la sesión está activada en SharedPreferences
              editor.putString("id", String.valueOf(userId));
              editor.apply();
              Intent call_productos = new Intent(v.getContext(), ActivityProductos.class);
              startActivity(call_productos);
              finish();
          } else {
              Toast.makeText(this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
          }
      }

    public void Cancelar (View v) {
       finishAffinity();
    }
    public void CrearCuenta (View v){
        Intent call_principal = new Intent(v.getContext(), ActivityRegistro.class);
        startActivity(call_principal);
    }
}