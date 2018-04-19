/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buy.with.me;

import Controller.CtrlProducto;
import Model.Catalogo;
import Model.Producto;
import View.CInicio;
import View.Inicio;

/**
 *
 * @author jm
 */
public class BuyWithMe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Producto mod=new Producto("Papas del pais", 11, 22, 42, 3);
        Catalogo ca=new Catalogo();
        
        Inicio frm=new Inicio();
        
        CtrlProducto con=new CtrlProducto(ca, frm);
        con.iniciar();
        frm.setVisible(true);
        
    }
    
}
