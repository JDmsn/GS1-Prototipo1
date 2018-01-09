/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author jm
 */
public class Producto {
    private String nombre;
    private double precio;
    private int stock;
    private int calorias;
    private int oferta;
    private ArrayList <Integer> calificacion;
    private ArrayList <String> comentario;
    
    public Producto (String nombre, double precio, int stock, int calorias, int oferta){
        this.calorias=calorias;
        this.nombre=nombre;
        this.oferta=oferta;
        this.precio=precio;
        this.stock=stock;
        
    }
    
    //Metodos añadir calificacion modificar calificacion requeridos (IDEM con comentarios)

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public int getOferta() {
        return oferta;
    }

    public void setOferta(int oferta) {
        this.oferta = oferta;
    }
    
}
