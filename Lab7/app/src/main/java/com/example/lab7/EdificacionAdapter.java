package com.example.lab7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EdificacionAdapter extends RecyclerView.Adapter<EdificacionAdapter.ViewHolder> {
    private List<Edificacion> edificaciones;
    private List<Edificacion> edificacionesFiltradas;

    public EdificacionAdapter(List<Edificacion> edificaciones) {
        this.edificaciones = edificaciones;
        this.edificacionesFiltradas = new ArrayList<>(edificaciones);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_edificacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Edificacion edificacion = edificacionesFiltradas.get(position);
        holder.titulo.setText(edificacion.getTitulo());
        holder.categoria.setText(edificacion.getCategoria());
        holder.descripcion.setText(edificacion.getDescripcion());
        holder.imagen.setImageResource(edificacion.getImagen());
    }

    @Override
    public int getItemCount() {
        return edificacionesFiltradas.size();
    }

    public void filtrar(String texto) {
        edificacionesFiltradas.clear();
        if (texto.isEmpty()) {
            edificacionesFiltradas.addAll(edificaciones);
        } else {
            for (Edificacion edificacion : edificaciones) {
                if (edificacion.getTitulo().toLowerCase().contains(texto.toLowerCase())) {
                    edificacionesFiltradas.add(edificacion);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, categoria, descripcion;
        ImageView imagen;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            categoria = itemView.findViewById(R.id.categoria);
            descripcion = itemView.findViewById(R.id.descripcion);
            imagen = itemView.findViewById(R.id.imagen);
        }
    }
}
