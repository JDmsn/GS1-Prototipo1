/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Catalogo;
import Model.Producto;
import Model.Usuario;
import View.CCesta;
import View.CEditarPerfil;
import View.CInicio;
import View.CValorarProductos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author jm
 */
public class VComprador implements ActionListener{
    private CInicio ini;
    private PanelProductos ps;
    private DefaultListModel dml;
    private Catalogo mod;
    private ArrayList <Producto> pro;
    private boolean visible;
    private Usuario u;
    private CtrlProducto ctrl;
    private CCesta ccta;
    private CValorarProductos cvt;
    private CEditarPerfil cel;
    
    VComprador(Catalogo mod, CInicio ini, Usuario u, CtrlProducto ctrl) {
        this.ini=ini;
        this.mod=mod;
        this.u=u;
        visible=false;
        this.ccta=null;
        this.cvt=null;
        this.cel=null;
        this.ctrl=ctrl;
        this.ini.jButton1.addActionListener(this);
        this.ini.jButton2.addActionListener(this);
        this.ini.jButton3.addActionListener(this);
        this.ini.jButton4.addActionListener(this);
        this.ini.Buscar.addActionListener(this);
        this.ini.jList1.setVisible(true);
        this.ini.jRadioButton1.addActionListener(this);
        this.ini.jRadioButton2.addActionListener(this);
        this.ini.jRadioButton3.addActionListener(this);
        this.ini.jTextField2.addActionListener(this);
        this.ini.myGroup.add(this.ini.jRadioButton1);
        this.ini.myGroup.add(this.ini.jRadioButton2);
        this.ini.myGroup.add(this.ini.jRadioButton3);
        ps=new PanelProductos(this.ini.jTextField2, this.ini.jRadioButton1, this.ini.jRadioButton2, this.ini.jRadioButton3, this.ini.myGroup, this.ini.Buscar, this.ini.jList1, this.mod, this.dml, this.pro);
        dml = new DefaultListModel();        
        ps.mostrarProductos();
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ini.jButton1){
            ccta = new CCesta();
            ventana1(false);           
            CCompra cc = new CCompra(mod, ccta, this);
                 
        }

        if(e.getSource() == ini.jButton2){            
            cvt = new CValorarProductos();
            ventana2(false);
            Valoracion vn= new Valoracion(mod, cvt, this);
            
        }
        
        if(e.getSource() == ini.jButton3){
            cel=new CEditarPerfil();
            ventana3(false);
            CAjustes ca=new CAjustes(cel, u, this);
        }
        
        if(e.getSource() == ini.jButton4){
            ctrl.ventana(true);
            
        }
    }
    
    public void ventana1(boolean onOff){
        ini.setVisible(onOff);
        ccta.setVisible(!onOff);
        
    }
    
    public void ventana2(boolean onOff){
        ini.setVisible(onOff);
        cvt.setVisible(!onOff);
        
    }
    
    public void ventana3(boolean onOff){
        ini.setVisible(onOff);
        cel.setVisible(!onOff);        
    }
}
