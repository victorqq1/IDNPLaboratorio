package com.example.lab7;

public class Edificacion {
    private String titulo;
    private String categoria;
    private String descripcion;
    private int imagen;

    public Edificacion(String titulo, String categoria, String descripcion, int imagen) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public String getDescripcion() { return descripcion; }
    public int getImagen() { return imagen; }
}
