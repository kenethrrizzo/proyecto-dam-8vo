package com.example.coffeestore.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeestore.ProductosSingleton;
import com.example.coffeestore.R;
import com.example.coffeestore.dto.Compra;
import com.example.coffeestore.dto.ProductoEnCarrito;
import com.example.coffeestore.dto.Tarjeta;
import com.example.coffeestore.utils.FileUtils;
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

    public void confirmarCompra(View v) throws Exception {
        TextInputLayout layoutNumero = findViewById(R.id.layoutcreditnumber);
        TextInputLayout layoutExpDate = findViewById(R.id.layoutexpirationdate);
        TextInputLayout layoutCvv = findViewById(R.id.layoutcvv);

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumero(Objects.requireNonNull(layoutNumero.getEditText()).getText().toString().trim());
        tarjeta.setFechaDeExpiracion(Objects.requireNonNull(layoutExpDate.getEditText()).getText().toString().trim());
        tarjeta.setCvv(Objects.requireNonNull(layoutCvv.getEditText()).getText().toString().trim());

        if (!validarFormatoFecha(Integer.parseInt(tarjeta.getFechaDeExpiracion()))) {
            return;
        }

        if (!validarCvv(tarjeta.getCvv())) {
            return;
        }

        ProductosSingleton datosCompartidos = ProductosSingleton.getInstancia();
        List<ProductoEnCarrito> listaProductos = datosCompartidos.getListaProductos();

        SharedPreferences sharedPreferences = getSharedPreferences("usuarioId", Context.MODE_PRIVATE);
        String usuarioId = sharedPreferences.getString("id", "0");

        for (ProductoEnCarrito productoEnCarrito : listaProductos) {
            Compra compra = new Compra();
            compra.setIdUsuario(usuarioId);
            compra.setIdProducto(String.valueOf(productoEnCarrito.getProducto().getId()));
            compra.setCantidad(String.valueOf(productoEnCarrito.getCantidad()));
            compra.setTarjeta(tarjeta);
            FileUtils.guardarCompraEnCsv(getExternalFilesDir(null), compra);
            Toast.makeText(this, "Compra realizada!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(v.getContext(), ActivityProductos.class);
            startActivity(intent);
        }
    }

    public boolean validarFormatoFecha(int fecha) {
        String fechaString = String.valueOf(fecha);
        int longitud = fechaString.length();
        int mitad = longitud / 2;

        String month = fechaString.substring(0, mitad);
        String year = fechaString.substring(mitad);

        if (Integer.parseInt(month) > 12 || Integer.parseInt(month) == 0) {
            Toast.makeText(this, "Mes inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (Integer.parseInt(year) < 23) {
            Toast.makeText(this, "Año inválido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public boolean validarCvv(String cvv) {
        return cvv.length() == 3 || cvv.length() == 4;
    }
}
