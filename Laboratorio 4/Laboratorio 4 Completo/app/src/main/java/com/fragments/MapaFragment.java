package com.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.util.GeoPoint;
import com.example.lab1_login.R;

public class MapaFragment extends Fragment {

    // Parámetros
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Variables para parámetros
    private String mParam1;
    private String mParam2;

    // Mapa
    private MapView mapView;

    public MapaFragment() {
        // Required empty public constructor
    }

    // Método para crear una nueva instancia del fragmento
    public static MapaFragment newInstance(String param1, String param2) {
        MapaFragment fragment = new MapaFragment();
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
        // Configuración de OSMDroid
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_mapas, container, false);

        // Inicializar el MapView
        mapView = view.findViewById(R.id.mapView);
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        // Configurar la ubicación del mapa (Arequipa, por ejemplo)
        GeoPoint startPoint = new GeoPoint(-16.409047, -71.537451); // Coordenadas de Arequipa
        mapView.getController().setZoom(10);
        mapView.getController().setCenter(startPoint);

        // Agregar un marcador
        /*
        Marker marker = new Marker(mapView);
        marker.setPosition(startPoint);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_marker)); // Cambia esto por tu ícono de marcador
        marker.setTitle("Arequipa"); // Título que se mostrará en el infowindow
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapView.getOverlays().add(marker);

        // Mostrar infowindow al hacer clic en el marcador
        marker.setOnMarkerClickListener((m, mapView1) -> {
            m.showInfoWindow();
            return true;
        });


        */
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Cuando el fragmento se reanuda, vuelve a cargar la configuración de OSMDroid
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Cuando el fragmento se pausa, pausa el MapView
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Limpiar el MapView al destruir el fragmento
        mapView.onDetach();
    }
}
