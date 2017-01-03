package web.ve.alphasigma;

public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater( ()->{
            Utils.LogI("Iniciar GUI");
            new GUI();
            Utils.LogI("GUI iniciado");
        } );
    }
}
