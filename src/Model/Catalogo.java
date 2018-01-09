/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jm
 */
public class Catalogo extends Conexion{
    
    private ArrayList <Producto> pro; 
    
    public Catalogo(){
        // Inicializamos el array List
        pro= new ArrayList <Producto>();
    
    }
    
    
    public ArrayList<Producto> mostrarProductos(){
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection con= getConexion();
        
        //Vaciamos el array, para cuando haya una nueva consulta de productos
        pro.clear();
        
        String sql="SELECT nombre, precio, calorias, cantidad, oferta FROM Buywithme.producto";
        
        try{
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            
            while(rs.next()){
                pro.add(new Producto( rs.getString("nombre"), Double.parseDouble(rs.getString("precio")) , Integer.parseInt(rs.getString("cantidad")) , Integer.parseInt(rs.getString("calorias")), Integer.parseInt(rs.getString("oferta")) ));
                System.out.println("calorias:"+ Integer.parseInt(rs.getString("calorias")));               
            }       
            
            return pro; 
            
        }catch(SQLException e){
            System.err.println(e);
            
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        
        return pro;
    }
    
    
    public ArrayList<Producto> buscar (String seleccion, int op) throws SQLException{
    
        //Condiciones para las diferentes opciones que marco el usuario
        
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection con=getConexion();
        
        //Introducir nombre;
        String nombre="";
        String sql="";
        switch(op){
            case 1: 
                sql="SELECT * FROM producto WHERE nombre=?";
                System.out.println("La seleccion es:"+seleccion);
                break;                
            case 2: 
                sql= "SELECT * FROM producto WHERE precio=?";
                break;
            case 3:
                sql= "SELECT * FROM producto WHERE calorias=?";
                break;            
        }
        //String sql= "SELECT * FROM producto WHERE nombre=?";
        //String sql= "SELECT * FROM producto WHERE precio=?";
        //String sql= "SELECT * FROM producto WHERE calorias=?";
        
        try{
            ps=con.prepareStatement(sql);
            ps.setString(1, seleccion);
            rs=ps.executeQuery();
            
            while(rs.next()){
                pro.add(new Producto( rs.getString("nombre"), Double.parseDouble(rs.getString("precio")) , Integer.parseInt(rs.getString("cantidad")) , Integer.parseInt(rs.getString("calorias")), Integer.parseInt(rs.getString("oferta")) ));
                //System.out.println("calorias:"+ Integer.parseInt(rs.getString("calorias")));               
            }       
            
            return pro; 
            
        }catch(SQLException e){
            System.err.println(e);
            
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        
        return pro;
    }    
}
