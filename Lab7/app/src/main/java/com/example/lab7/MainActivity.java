package com.example.lab7;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EdificacionAdapter adapter;
    private List<Edificacion> edificaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inicializar datos
        edificaciones = cargarEdificaciones();

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EdificacionAdapter(edificaciones);
        recyclerView.setAdapter(adapter);

        // Configurar el filtro
        EditText filterEditText = findViewById(R.id.filterEditText);
        filterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filtrar(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private List<Edificacion> cargarEdificaciones() {
        List<Edificacion> lista = new ArrayList<>();
        lista.add(new Edificacion("Catedral de Arequipa", "Catedral", "Construida en el siglo XVII", R.drawable.catedral));
        lista.add(new Edificacion("Monasterio de Santa Catalina", "Monasterio", "Fundado en 1580", R.drawable.monasterio));
        lista.add(new Edificacion("Casa del Moral", "Casa Colonial", "Casa colonial del siglo XVIII", R.drawable.casa_moral));
        return lista;
    }
}