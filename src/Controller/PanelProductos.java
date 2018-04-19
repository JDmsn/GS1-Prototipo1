/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Catalogo;
import Model.Producto;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author jm
 */
public class PanelProductos implements ActionListener{
    private JTextField jTextField2;
    private JRadioButton jRadioButton1;
    private JRadioButton jRadioButton2;
    private JRadioButton jRadioButton3;
    private ButtonGroup myGroup;
    private JButton Buscar;
    private JList jList1;
    private Catalogo mod;
    private DefaultListModel dml;
    private ArrayList <Producto> pro;
            
    public PanelProductos(JTextField jTextField2, JRadioButton jRadioButton1, JRadioButton jRadioButton2, JRadioButton jRadioButton3, ButtonGroup myGroup, JButton Buscar, JList jList1, Catalogo mod, DefaultListModel dml, ArrayList <Producto> pro) {
        this.jTextField2 = jTextField2;
        this.jRadioButton1 = jRadioButton1;
        this.jRadioButton2 = jRadioButton2;
        this.jRadioButton3 = jRadioButton3;
        this.myGroup = myGroup;
        this.Buscar = Buscar;
        this.jList1 = jList1;
        this.mod = mod;
        this.dml = dml;
        this.pro = pro;
        
        this.Buscar.addActionListener(this);
        this.jList1.setVisible(true);
        this.jRadioButton1.addActionListener(this);
        this.jRadioButton2.addActionListener(this);
        this.jRadioButton3.addActionListener(this);
        this.jTextField2.addActionListener(this);
        this.myGroup.add(this.jRadioButton1);
        this.myGroup.add(this.jRadioButton2);
        this.myGroup.add(this.jRadioButton3);
        this.dml=new DefaultListModel();
        this.jTextField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                mostrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                mostrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                mostrar();
            }
        
            public void  mostrar(){
                if(jTextField2.getText().equals("")){
                    mostrarProductos();
                }
            }
        
        });        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Buscar){
            try {
                if(jRadioButton1.isSelected()){
                    //System.out.println("nombre");
                    buscarProducto(jTextField2.getText(), 1);
                }else if(jRadioButton2.isSelected()){
                    //System.out.println("Precio"); 
                    buscarProducto(jTextField2.getText(), 2);
                }else{
                    //System.out.println("calorias");
                    buscarProducto(jTextField2.getText(), 3);
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
             
    }
    
    
    
    public void mostrarProductos() {
        Producto p;      
        pro=mod.mostrarProductos();
        System.out.println("Numero de productos:"+pro.size());

        //Limpiamos la lista
        dml.removeAllElements();
        dml.clear();

        for(int i=0; i<pro.size(); i++){
            p=pro.get(i);
            dml.addElement(p.getNombre() +", " + String.valueOf(p.getPrecio()) + "€, "+ String.valueOf(p.getCalorias()) +"Cal.");
        }

        jList1.setModel(dml);

    }
    
    public void copiar(PanelProductos ps){
       ps.jTextField2 = this.jTextField2;
       ps.jRadioButton1 = this.jRadioButton1;
       ps.jRadioButton2 = this.jRadioButton2;
       ps.jRadioButton3 = this.jRadioButton3;
       ps.myGroup = this.myGroup;
       ps.Buscar = this.Buscar;
       ps.jList1 = this.jList1;
       ps.mod = this.mod;
       ps.dml = this.dml;
       ps.pro = this.pro;
        
    }
    public void buscarProducto(String eleccion, int op) throws SQLException {
        
        //Limpiamos la lista
        dml.removeAllElements();
        dml.clear();
        jList1.removeAll();
        pro.removeAll(pro);
        
        pro=mod.buscar(eleccion, op);
        
        if(pro!=null){
            Producto p;
            
            for(int i=0; i<pro.size(); i++){
                p=pro.get(i);
                dml.addElement(p.getNombre() +", " + String.valueOf(p.getPrecio()) + "€, "+ String.valueOf(p.getCalorias()) +"Cal.");
            }
 
        }      
        
    }
    
    public JTextField getjTextField2() {
        return jTextField2;
    }

    public void setjTextField2(JTextField jTextField2) {
        this.jTextField2 = jTextField2;
    }

    public JRadioButton getjRadioButton1() {
        return jRadioButton1;
    }

    public void setjRadioButton1(JRadioButton jRadioButton1) {
        this.jRadioButton1 = jRadioButton1;
    }

    public JRadioButton getjRadioButton2() {
        return jRadioButton2;
    }

    public void setjRadioButton2(JRadioButton jRadioButton2) {
        this.jRadioButton2 = jRadioButton2;
    }

    public JRadioButton getjRadioButton3() {
        return jRadioButton3;
    }

    public void setjRadioButton3(JRadioButton jRadioButton3) {
        this.jRadioButton3 = jRadioButton3;
    }

    public ButtonGroup getMyGroup() {
        return myGroup;
    }

    public void setMyGroup(ButtonGroup myGroup) {
        this.myGroup = myGroup;
    }

    public JButton getBuscar() {
        return Buscar;
    }

    public void setBuscar(JButton Buscar) {
        this.Buscar = Buscar;
    }

    public JList getjList1() {
        return jList1;
    }

    public void setjList1(JList jList1) {
        this.jList1 = jList1;
    }
    
    
    
}
