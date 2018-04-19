/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Catalogo;
import Model.Conexion;
import Model.Producto;
import View.CInicio;
import View.CValoracionDeProductos;
import View.CValorarProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author jm
 */
public class Valoracion extends Conexion implements ActionListener {
    private CValorarProductos cvt;
    private Catalogo mod;
    private PanelProductos ps;    
    private DefaultListModel dml;
    private ArrayList <Producto> pro; 
    private VComprador vc;
    
    Valoracion(Catalogo mod, CValorarProductos cvt, VComprador vc) {
        this.cvt=cvt;
        this.mod=mod;
        this.vc=vc;
        this.cvt.jButton4.addActionListener(this);
        this.cvt.jButton1.addActionListener(this);
        this.cvt.atras.addActionListener(this);
        this.cvt.Buscar.addActionListener(this);
        this.cvt.jList2.setVisible(true);
        this.cvt.jRadioButton1.addActionListener(this);
        this.cvt.jRadioButton2.addActionListener(this);
        this.cvt.jRadioButton3.addActionListener(this);
        this.cvt.jTextField3.addActionListener(this);
        this.cvt.myGroup.add(this.cvt.jRadioButton1);
        this.cvt.myGroup.add(this.cvt.jRadioButton2);
        this.cvt.myGroup.add(this.cvt.jRadioButton3); 
        ps = new PanelProductos(this.cvt.jTextField3, this.cvt.jRadioButton1, this.cvt.jRadioButton2, this.cvt.jRadioButton3, this.cvt.myGroup, this.cvt.Buscar, this.cvt.jList2, this.mod, this.dml, this.pro);
        dml = new DefaultListModel();        
        ps.mostrarProductos();
     
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("El producto es:"+ this.cvt.jList2.getSelectedValue());
        if(e.getSource() == cvt.jButton4 && validarCampos()){
            //Calificar & valorar
            valorarProducto();     
        }  
        if(e.getSource() == cvt.atras || e.getSource() == cvt.jButton1){
            vc.ventana2(true);
        }       
        
    }

    
    private boolean validarCampos() {       
        
        return true;
    }

    private void valorarProducto() {
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection con= getConexion();
        String[] v= this.cvt.jList2.getSelectedValue().split(",");        
        String producto=v[0];
        //Llamamos a un metodo para saber qué boton está seleccionado
        int num=getNum();
        //Esto es un ejemplo
        num=1;
        
        //Obtenemos el texto del comentario
        String comentario=this.cvt.jTextArea1.getText();
        //Vaciamos el array, para cuando haya una nueva consulta de productos
         
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
    }

    private int getNum() {
        return 0;
    }
    
    
    
}
