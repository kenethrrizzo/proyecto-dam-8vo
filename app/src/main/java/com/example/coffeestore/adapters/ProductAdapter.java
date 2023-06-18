package com.example.coffeestore.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coffeestore.R;
import com.example.coffeestore.dto.Producto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final List<Producto> productList;
    public final Context context;
    private OnItemClickListener clickListener;


    public ProductAdapter(List<Producto> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto product = productList.get(position);

        holder.textProductName.setText(product.getTitulo());
        holder.textProductDescription.setText(product.getDescripcion());
        holder.textProductPrice.setText("Price: $" + product.getPrecio());

        Picasso.get().load(product.getUrlImagen()).into(holder.imageProduct);

        ImageButton buttonIcon = holder.itemView.findViewById(R.id.button_icon);
        buttonIcon.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(position);
            }
        });
    }

    private void guardarEnSharedPreferences() {
        System.out.println("GUARDADOOOOOOO");
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }



    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct;
        TextView textProductName;
        TextView textProductDescription;
        TextView textProductPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageProduct = itemView.findViewById(R.id.image_product);
            textProductName = itemView.findViewById(R.id.text_product_name);
            textProductDescription = itemView.findViewById(R.id.text_product_description);
            textProductPrice = itemView.findViewById(R.id.text_product_price);
        }
    }
}

