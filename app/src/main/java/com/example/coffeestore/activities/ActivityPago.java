package com.example.coffeestore.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeestore.ProductosSingleton;
import com.example.coffeestore.R;
import com.example.coffeestore.database.CompraDbHelper;
import com.example.coffeestore.database.ProductDbHelper;
import com.example.coffeestore.dto.ProductoEnCarrito;
import com.example.coffeestore.dto.Tarjeta;
import com.example.coffeestore.dto.Usuario;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

public class ActivityPago extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
    }

    public void volverACarrito(View v) {
        Intent intent = new Intent(v.getContext(), ActivityCarritoDeCompras.class);
        startActivity(intent);
    }

    public void confirmarCompra(View v) {
        TextInputLayout layoutNumero = findViewById(R.id.layoutcreditnumber);
        TextInputLayout layoutExpDate = findViewById(R.id.layoutexpirationdate);
        TextInputLayout layoutCvv = findViewById(R.id.layoutcvv);

        Tarjeta tarjeta = new Tarjeta();
        Usuario usuario = new Usuario();
        tarjeta.setNumero(Objects.requireNonNull(layoutNumero.getEditText()).getText().toString().trim());
        tarjeta.setFechaDeExpiracion(Objects.requireNonNull(layoutExpDate.getEditText()).getText().toString().trim());
        tarjeta.setCvv(Objects.requireNonNull(layoutCvv.getEditText()).getText().toString().trim());

        ProductosSingleton datosCompartidos = ProductosSingleton.getInstancia();
        List<ProductoEnCarrito> listaProductos = datosCompartidos.getListaProductos();

        try (CompraDbHelper helper = new CompraDbHelper(this)) {
            helper.insertarCompra(listaProductos, tarjeta);
            Toast.makeText(this, "Compra realizada!", Toast.LENGTH_SHORT).show();
        }
    }
}
