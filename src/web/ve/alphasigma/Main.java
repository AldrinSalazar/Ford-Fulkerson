package web.ve.alphasigma;

import web.ve.alphasigma.controlador.Controlador;

public class Main {

    public static void main(String[] args) {//TODO: Documentacion
        javax.swing.SwingUtilities.invokeLater( ()->{
            Utils.LogI("Iniciar GUI");
            new Controlador();
            Utils.LogI("GUI iniciado");
        } );
    }
}
