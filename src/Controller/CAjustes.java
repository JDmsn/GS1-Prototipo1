/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Conexion;
import Model.Usuario;
import View.CEditarPerfil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 *
 * @author jm
 */
public class CAjustes extends Conexion implements ActionListener {

    private Usuario u;
    private CEditarPerfil cel;
    private VComprador vc;
    public CAjustes(CEditarPerfil cel, Usuario u, VComprador vc) {
        this.u=u;
        this.cel=cel;
        this.vc=vc;
        cel.jButton1.addActionListener(this);
        cel.jButton2.addActionListener(this);
        cel.jButton3.addActionListener(this);
        cel.atras.addActionListener(this);
        mostrarCampos();
    }

    private void mostrarCampos() {
        //Toda la lógica para mostrar los campos (acceder a la base de datos, hacer consultas)       
        cel.jLabel2.setText(u.getNombre());
        cel.jLabel3.setText(u.getApellidos());
        cel.jLabel4.setText(u.getFechaDeNacimiento());
        cel.jLabel5.setText(u.getPais());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cel.jButton1){
            
        }
        
        if(e.getSource()==cel.jButton2){
            guardarDatos();
        }
        
        if(e.getSource()==cel.jButton3 || e.getSource()==cel.atras){
            vc.ventana3(true);
        }
        
            
    }

    private void guardarDatos() {
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection con= getConexion();      
                
        //Llamamos a un método para que compruebe los campos
        //comprobarCampos();
        
        //Obtenemos el texto del comentario
        char[] pass=cel.jPasswordField1.getPassword();
        String nombre="";
        //Vaciamos el array, para cuando haya una nueva consulta de productos
        /* 
        String sql="INSERT INTO Buywithme.valoraciones (id, nombreUsuario, comentario, calificacion, producto) VALUES (?,?,?,?,?)";
        
        try{
            ps=con.prepareStatement(sql);
            //Random numero
            ps.setString(1, "2");
            //Obtener usuario
            ps.setString(2, "Pepe");
            ps.setString(3, comentario);
            ps.setString(4, Integer.toString(num));
            ps.setString(5, producto);
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(this.cvt,
                "Se ha enviado tu valoración con éxito",
                "",
                JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){
            System.err.println(e);
            
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        */
    }
    
}
