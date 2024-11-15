package com.fragments;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.util.GeoPoint;

import com.Edificacion;
import com.example.lab1_login.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        // Cargar las edificaciones desde el archivo JSON
        List<Edificacion> edificaciones = cargarEdificaciones();

        // Verificar si se cargaron correctamente las edificaciones
        if (edificaciones != null) {
            // Agregar los marcadores para las edificaciones al mapa
            agregarMarcadores(edificaciones);
        } else {
            Toast.makeText(getContext(), "No se pudieron cargar las edificaciones", Toast.LENGTH_SHORT).show();
        }

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

    private List<Edificacion> cargarEdificaciones() {
        try {
            // Acceder a los archivos assets
            AssetManager assetManager = requireContext().getAssets();
            InputStream inputStream = assetManager.open("edificaciones.json");

            // Convertir el InputStream en un String usando InputStreamReader
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            // Usar Gson para parsear el JSON en una lista de objetos Edificacion
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Edificacion>>(){}.getType();
            List<Edificacion> edificaciones = gson.fromJson(inputStreamReader, listType);

            // Imprimir la lista de edificaciones (opcional, para depuración)
            if (edificaciones != null) {
                for (Edificacion edificacion : edificaciones) {
                    // Aquí puedes realizar un Log si deseas imprimir las edificaciones
                    // Log.d("Edificacion", "Edificación: " + edificacion.toString());
                }
            }

            return edificaciones;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void agregarMarcadores(List<Edificacion> edificaciones) {
        for (Edificacion edificacion : edificaciones) {
            // Utiliza las coordenadas de cada edificación (aquí se asume que las coordenadas son correctas)
            GeoPoint punto = new GeoPoint(edificacion.getLatitud(), edificacion.getLongitud());

            // Crear marcador
            Marker marcador = new Marker(mapView);
            marcador.setPosition(punto);
            marcador.setTitle(edificacion.getTitulo());
            marcador.setSubDescription(edificacion.getDescripcion());

            // Personaliza el ícono del marcador
            //marcador.setIcon(getResources().getDrawable(R.drawable.ic_marker));

            // Agregar el marcador al mapa
            mapView.getOverlays().add(marcador);

            // Configurar un infowindow que se muestre al hacer clic en el marcador
            marcador.setOnMarkerClickListener((m, mapView1) -> {
                m.showInfoWindow();  // Muestra la ventana de información
                return true;  // Evita que se haga zoom
            });
        }
    }

}
