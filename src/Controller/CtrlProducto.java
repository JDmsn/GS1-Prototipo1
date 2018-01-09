/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Catalogo;
import Model.Comprador;
import Model.Conexion;
import Model.Producto;
import Model.Usuario;
import View.CInicio;
import View.Inicio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author jm
 */
public class CtrlProducto extends Conexion implements ActionListener {
    private Catalogo mod;
    private Inicio frm; 
    private CInicio ini;
    private ArrayList <Producto> pro;
    private ArrayList <Usuario> usr;
    private DefaultListModel dml;
    protected PanelProductos ps;
    
    public CtrlProducto (Catalogo mod, Inicio frm){       
        this.mod=mod;
        this.frm=frm;
        this.frm.jButton1.addActionListener(this);
        this.frm.Buscar.addActionListener(this);
        this.frm.jList1.setVisible(true);
        this.frm.jPasswordField1.addActionListener(this);
        this.frm.jRadioButton1.addActionListener(this);
        this.frm.jRadioButton2.addActionListener(this);
        this.frm.jRadioButton3.addActionListener(this);
        this.frm.jTextField1.addActionListener(this);
        this.frm.jTextField2.addActionListener(this);
        this.frm.myGroup.add(this.frm.jRadioButton1);
        this.frm.myGroup.add(this.frm.jRadioButton2);
        this.frm.myGroup.add(this.frm.jRadioButton3);
        ps=new PanelProductos(this.frm.jTextField2, this.frm.jRadioButton1, this.frm.jRadioButton2, this.frm.jRadioButton3, this.frm.myGroup, this.frm.Buscar, this.frm.jList1, this.mod, this.dml, this.pro);
        ps.mostrarProductos();
        dml=new DefaultListModel();
               
    }
       
    @Override
    public void actionPerformed(ActionEvent e) {
       
        if(e.getSource() == frm.jButton1){
            iniciarSesion();
        }
        
    }

    public void iniciar() {
        PreparedStatement ps=null;
        frm.setTitle("Inicio");
        frm.setLocationRelativeTo(null);
        frm.jList1.setVisible(true);
        
    }
    
    private void iniciarSesion() {
        PreparedStatement ps=null;
        ResultSet rs=null;
        char[] pass;
        String ctr="";
        Connection con= getConexion();
        
        //Vaciamos el array, para cuando haya una nueva consulta de usuarios
        //usr.clear();
        
        String sql="SELECT * FROM Buywithme.usuarios WHERE nombre=? AND contrasena=?";
        
        try{
            ps=con.prepareStatement(sql);
            ps.setString(1, this.frm.jTextField1.getText());
            pass=this.frm.jPasswordField1.getPassword();
            
            for(int i=0; i<pass.length; i++){
                ctr+=pass[i];
            }
            
            ps.setString(2, ctr);
            rs=ps.executeQuery();
            
            if(rs.next()){
                //System.out.println("USUARIO CORRECTO");
                //Identificacion del tipo de usuario,
                boolean tipo=false;
                Usuario usuario=null;
                
                if(Integer.parseInt(rs.getString("Tipo"))==0){
                    tipo=true;
                    usuario = new Comprador(Integer.parseInt(rs.getString("id")), 
                        rs.getString("FechaDeNacimiento"),
                        rs.getString("Apellidos"),
                        rs.getString("Nombre"),
                        rs.getString("Pais"),
                        rs.getString("Contrasena"),
                        tipo);                   
                }else{
                    //usuario = new Administrador();
                }
          
                if(tipo){                   
                    ini=new CInicio();
                    ventana(false);                   
                    VComprador vcom=new VComprador(mod, ini, usuario, this);
                    
                }else{
                    //Abrir nueva ventana para el administrador
                }
                
                //Cerrar ventana, abrir nueva ventana
                //ini=new CInicio();
                //ini.setVisible(true);                
                //this.frm.setVisible(false);
                
            }else{
                //Mosrar mensaje de Us or Con incorrectos
                JOptionPane.showMessageDialog(this.frm,
                "Usuario o contraseña incorrecta.",
                "Mensaje de error",
                JOptionPane.ERROR_MESSAGE);
            }       
            
            //Hacer algo 
            
        }catch(SQLException e){
            System.err.println(e);
            
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        
    }

    public void ventana(boolean onOff) {
        ini.setVisible(!onOff);
        frm.setVisible(onOff);
    }
    
}
