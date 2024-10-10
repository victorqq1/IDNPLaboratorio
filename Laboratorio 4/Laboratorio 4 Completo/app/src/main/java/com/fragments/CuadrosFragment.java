package com.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lab1_login.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CuadrosFragment extends Fragment {

    private RecyclerView recyclerView;
    private CityAdapter adapter;

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

        // Configurar RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3); // 3 columnas
        recyclerView.setLayoutManager(gridLayoutManager);

        // Lista de ciudades de Arequipa
        List<String> cities = Arrays.asList("Arequipa", "Camana", "Caraveli", "Castilla", "Caylloma", "Condesuyos", "Islay", "La Unión");

        // Crear y asignar el adaptador con la lista de ciudades
        adapter = new CityAdapter(cities);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Adaptador para manejar el RecyclerView
    public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {
        private List<String> cities;

        public CityAdapter(List<String> cities) {
            this.cities = cities;
        }

        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Inflar un TextView para cada ciudad
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new CityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CityViewHolder holder, int position) {
            // Asignar el nombre de la ciudad al TextView
            String city = cities.get(position);
            holder.cityTextView.setText(city);
        }

        @Override
        public int getItemCount() {
            return cities.size();
        }

        // ViewHolder para manejar el TextView de cada ciudad
        public class CityViewHolder extends RecyclerView.ViewHolder {
            public TextView cityTextView;

            public CityViewHolder(View itemView) {
                super(itemView);
                cityTextView = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
