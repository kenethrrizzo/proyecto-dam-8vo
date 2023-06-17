package com.example.coffeestore.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import  android.content.Intent;

import com.example.coffeestore.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout layoutUsuario = findViewById(R.id.layoutUsuario);
        TextInputEditText txt_usuario = findViewById(R.id.txt_Usuario);
    }

      public void registrarUsuario(View v) {

    }
    public void Cancelar (View v) {
        finishAffinity();
    }
    public void CrearCuenta (View v){
        Intent call_principal = new Intent(v.getContext(), ActivityProductos.class);
        startActivity(call_principal);
    }
}