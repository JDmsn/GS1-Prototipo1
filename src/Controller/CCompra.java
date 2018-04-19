/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Catalogo;
import Model.Producto;
import View.CCesta;
import View.CInicio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author jm
 */
class CCompra implements ActionListener{
    private CCesta ccta;
    private PanelProductos ps;
    private DefaultListModel dml;
    private DefaultListModel dml1;
    private Catalogo mod;
    private ArrayList <Producto> pro;
    private boolean visible;
    private VComprador vc;
    CCompra(Catalogo mod, CCesta ccta, VComprador vc) {
        this.ccta=ccta;
        this.mod=mod;
        this.vc=vc;
        this.ccta.jButton1.addActionListener(this);
        this.ccta.jButton2.addActionListener(this);
        this.ccta.jButton3.addActionListener(this);
        this.ccta.atras.addActionListener(this);
        this.ccta.Buscar.addActionListener(this);
        this.ccta.jList1.setVisible(true);
        this.ccta.jRadioButton1.addActionListener(this);
        this.ccta.jRadioButton2.addActionListener(this);
        this.ccta.jRadioButton3.addActionListener(this);
        this.ccta.jTextField2.addActionListener(this);
        this.ccta.myGroup.add(this.ccta.jRadioButton1);
        this.ccta.myGroup.add(this.ccta.jRadioButton2);
        this.ccta.myGroup.add(this.ccta.jRadioButton3);
        ps=new PanelProductos(this.ccta.jTextField2, this.ccta.jRadioButton1, this.ccta.jRadioButton2, this.ccta.jRadioButton3, this.ccta.myGroup, this.ccta.Buscar, this.ccta.jList1, this.mod, this.dml, this.pro);
        dml = new DefaultListModel(); 
        dml1 = new DefaultListModel(); 
        ps.mostrarProductos();
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ccta.jButton1){
            añadirProducto();
        }
        
        if(e.getSource() == ccta.jButton3){
            eliminarProducto();
        }
        
       if(e.getSource() == ccta.atras){
           vc.ventana1(true);
       }
    }

    private void añadirProducto() {        
        dml1.addElement(this.ccta.jList1.getSelectedValue());
        ccta.jList2.setModel(dml1);
        calcularPrecio();             
    }

    private void eliminarProducto() {
       dml1.removeElement(this.ccta.jList2.getSelectedValue());
       ccta.jList2.setModel(dml1); 
       calcularPrecio();
    }

    private void calcularPrecio() {
        String[] producto;
        double suma=0.0;
        for(int i=0; i<dml1.getSize();i++){
            producto=dml1.get(i).toString().split(",");           
            suma+= Double.parseDouble(producto[1].substring(1, producto[1].length()-1));
            //producto=producto[1].split("€");
        }
        
        ccta.jLabel2.setText(Double.toString(suma)+"€");       
    }
    
}
