/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author jm
 */
public class Usuario {
    protected int id;
    protected String fechaDeNacimiento;
    protected String apellidos; 
    protected String nombre;
    protected String pais; 
    protected String contrasena;
    protected boolean tipo;

    public Usuario(int id, String fechaDeNacimiento, String apellidos, String nombre, String pais, String contrasena, boolean tipo) {
        this.id = id;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.pais = pais;
        this.contrasena = contrasena;
        this.tipo = tipo;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
    
}
