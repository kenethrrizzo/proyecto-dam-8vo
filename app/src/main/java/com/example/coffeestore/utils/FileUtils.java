package com.example.coffeestore.utils;

import android.util.Log;

import com.example.coffeestore.dto.Compra;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileUtils {

    public static void guardarCompraEnCsv(File parent, Compra compra) throws Exception {
        try {
            File f = new File(parent, "compra.csv");
            OutputStreamWriter fout = new OutputStreamWriter(new FileOutputStream(f, true));
            fout.write(compra.toString() + "\n");
            fout.close();
        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
            throw ex;
        }
    }

}
