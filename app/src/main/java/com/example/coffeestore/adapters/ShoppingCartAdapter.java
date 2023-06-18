package com.example.coffeestore.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeestore.R;
import com.example.coffeestore.dto.Producto;
import com.example.coffeestore.dto.ProductoEnCarrito;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private final List<ProductoEnCarrito> productList;
    public final Context context;
    private OnItemClickListener clickListener;

    public ShoppingCartAdapter(List<ProductoEnCarrito> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_in_shopping_cart, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (productList.size() > 0) {
            ProductoEnCarrito productoEnCarrito = productList.get(position);
            Producto producto = productoEnCarrito.getProducto();

            holder.textProductName.setText(producto.getTitulo());
            holder.textProductPrice.setText("$" + producto.getPrecio() * productoEnCarrito.getCantidad());
            holder.textProductQuantity.setText("" + productoEnCarrito.getCantidad());

            ImageButton buttonIncrement = holder.itemView.findViewById(R.id.button_increment);
            buttonIncrement.setOnClickListener(v -> {
                if (clickListener != null) {
                    ProductoEnCarrito productoRetornado = clickListener.plusClick(productoEnCarrito);
                    productList.set(position, productoRetornado);
                    onBindViewHolder(holder, position);
                }
            });

            ImageButton buttonDecrement = holder.itemView.findViewById(R.id.button_decrement);
            buttonDecrement.setOnClickListener(v -> {
                if (clickListener != null) {
                    ProductoEnCarrito productoRetornado = clickListener.minusClick(productoEnCarrito);
                    productList.set(position, productoRetornado);
                    onBindViewHolder(holder, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public List<ProductoEnCarrito> getProductList() {
        return this.productList;
    }

    // OnClickEvent
    public interface OnItemClickListener {
        ProductoEnCarrito plusClick(ProductoEnCarrito productoEnCarrito);
        ProductoEnCarrito minusClick(ProductoEnCarrito productoEnCarrito);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textProductName;
        TextView textProductPrice;
        TextView textProductQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textProductName = itemView.findViewById(R.id.text_product_name);
            textProductPrice = itemView.findViewById(R.id.text_product_price);
            textProductQuantity = itemView.findViewById(R.id.text_product_quantity);
        }
    }
}