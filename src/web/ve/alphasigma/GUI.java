package web.ve.alphasigma;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GUI {
    private static final int ancho = 800;
    private static final int alto = 400;

    private static final String titulo = "Algoritmo de Ford-Fulkerson";
    private static final String titulo_btn_nuevo_vertice = "Nuevo vertice";
    private static final String titulo_btn_nuevo_arista = "Nueva arista";
    private static final String titulo_btn_borrar = "Borrar";
    private static final String titulo_btn_editar = "Editar";
    private static final String titulo_btn_iniciar = "Resolver";
    private static final String titulo_btn_siguiente = "Paso siguiente";
    private static final String titulo_btn_anterior = "Paso anterior";

    private JFrame marco_ventana;

    private JPanel panel_canvas;
    private JPanel panel_botones;

    private JButton btn_nuevo_vertice;
    private JButton btn_nuevo_arista;
    private JButton btn_borrar;
    private JButton btn_editar;
    private JButton btn_iniciar;
    private JButton btn_siguiente;
    private JButton btn_anterior;



    public GUI() {
        Utils.LogI("Iniciar Ventana");
        iniciarVentana();

        Utils.LogI("Iniciar Elementos");
        iniciarElementos();

        Utils.LogI("Iniciar acoplar Elementos");
        acoplarElementos();

        marco_ventana.setVisible(true);
    }

    private void acoplarElementos(){
        panel_botones = new JPanel(new GridLayout(1, 4));
        panel_canvas = new JPanel();

        panel_botones.add(btn_nuevo_vertice);
        panel_botones.add(btn_nuevo_arista);
        panel_botones.add(btn_editar);
        panel_botones.add(btn_borrar);
        panel_botones.add(btn_anterior);
        panel_botones.add(btn_siguiente);
        panel_botones.add(btn_iniciar);

        marco_ventana.add(panel_botones, BorderLayout.SOUTH);
        marco_ventana.add(panel_canvas, BorderLayout.CENTER);
    }

    private void iniciarVentana(){
        marco_ventana = new JFrame(titulo);
        marco_ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        marco_ventana.setResizable(false);
        marco_ventana.setSize(new Dimension(ancho, alto));
        marco_ventana.setLayout(new BorderLayout());
    }

    private void iniciarElementos(){
        //TODO: Asignar tamaño al incluir iconos
        //Dimension d = new Dimension(30, 30);

        btn_nuevo_vertice = new JButton(titulo_btn_nuevo_vertice);
        btn_nuevo_arista = new JButton(titulo_btn_nuevo_arista);
        btn_borrar = new JButton(titulo_btn_borrar);
        btn_editar = new JButton(titulo_btn_editar);
        btn_iniciar = new JButton(titulo_btn_iniciar);
        btn_siguiente = new JButton(titulo_btn_siguiente);
        btn_anterior = new JButton(titulo_btn_anterior);

        btn_nuevo_vertice.addActionListener((ActionEvent e) -> {
                Utils.LogD("Click nuevo vertice");
                clickBotonNuevoVertice();
        });

        btn_nuevo_arista.addActionListener((ActionEvent e) -> {
                Utils.LogD("Click nueva arista");
                clickBotonNuevoArista();
        });

        btn_borrar.addActionListener((ActionEvent e) -> {
                Utils.LogD("Click borrar");
                clickBotonBorrar();
        });

        btn_editar.addActionListener((ActionEvent e) -> {
                Utils.LogD("Click editar");
                clickBotonEditar();
        });

        btn_iniciar.addActionListener((ActionEvent e) -> {
                Utils.LogD("Click boton iniciar");
                clickBotonIniciar();
        });

        btn_siguiente.addActionListener((ActionEvent e) -> {
                Utils.LogD("Click boton siguiente");
                clickBotonSiguiente();
        });

        btn_anterior.addActionListener((ActionEvent e) -> {
                Utils.LogD("Click boton anterior");
                clickBotonAnterior();
        });

    }

    // - // - // - // - // - // - // - // - // - //

    private void clickBotonNuevoVertice(){
    }

    private void clickBotonNuevoArista(){

    }

    private void clickBotonEditar(){

    }

    private void clickBotonBorrar(){}

    private void clickBotonAnterior(){}

    private void clickBotonSiguiente(){}

    private void clickBotonIniciar(){}

}
