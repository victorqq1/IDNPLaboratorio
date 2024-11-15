package com.fragments;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.Edificacion;
import com.EdificacionAdapter;
import com.example.lab1_login.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CuadrosFragment extends Fragment {

    private RecyclerView recyclerView;
    private EdificacionAdapter adapter;
    private List<Edificacion> edificaciones;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public CuadrosFragment() {
        // Required empty public constructor
    }

    public static CuadrosFragment newInstance(String param1, String param2) {
        CuadrosFragment fragment = new CuadrosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cuadros, container, false);

        // Inicializar datos
        edificaciones = cargarEdificaciones();

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        adapter = new EdificacionAdapter(edificaciones);
        recyclerView.setAdapter(adapter);

        // Configurar el filtro
        EditText filterEditText = view.findViewById(R.id.filterEditText);
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

        return view;
    }

    private List<Edificacion> cargarEdificaciones() {
        try {
            // Acceder a los archivos assets
            AssetManager assetManager = requireContext().getAssets();
            InputStream inputStream = assetManager.open("edificaciones.json");

            // Convertir el InputStream en un String usando un BufferedReader
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // Usar Gson para parsear el JSON en una lista de objetos Edificacion
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Edificacion>>(){}.getType();
            List<Edificacion> edificaciones = gson.fromJson(inputStreamReader, listType);

            // Imprimir la lista de edificaciones en la consola
            if (edificaciones != null) {
                for (Edificacion edificacion : edificaciones) {
                    Log.d("Edificacion", "Edificación: " + edificacion.toString());
                }
            }
            return edificaciones;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
