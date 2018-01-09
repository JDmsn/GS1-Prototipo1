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
public class Comprador extends Usuario {
    
       
    public Comprador(int id, String fechaDeNacimiento, String apellidos, String nombre, String pais, String contrasena, boolean tipo){
        //Es necesario llamar al la clase padre?
        super(id, fechaDeNacimiento, apellidos, nombre, pais, contrasena, tipo);
                
    }
    
    
    
}
