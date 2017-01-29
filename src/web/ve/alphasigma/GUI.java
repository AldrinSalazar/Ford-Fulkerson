/*
 * GUI.java       01/04/2017
 * Copyright (C) 2017  Aldrin Salazar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see http://www.gnu.org/licenses
 *
 */
package web.ve.alphasigma;

import web.ve.alphasigma.controlador.AristaObservable;
import web.ve.alphasigma.controlador.Controlador;
import web.ve.alphasigma.controlador.VerticeObservable;
import web.ve.alphasigma.vista.PanelGrafos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GUI {//TODO: Documentacion, Acoplar al modelo
    private static final int ancho = 800;
    private static final int alto = 400;

    private static final String titulo = "Algoritmo de Ford-Fulkerson";
    private static final String titulo_btn_nuevo_vertice = "Nuevo vertice";
    private static final String titulo_btn_nuevo_arista = "Nueva arista";
    private static final String titulo_btn_borrar = "Borrar";
    private static final String titulo_btn_mover = "Mover";
    private static final String titulo_btn_editar = "Editar";
    private static final String titulo_btn_iniciar = "Resolver";
    private static final String titulo_btn_siguiente = "Paso siguiente";

    private JFrame marco_ventana;

    public PanelGrafos panel_canvas;

    private JButton btn_nuevo_vertice;
    private JButton btn_nuevo_arista;
    private JButton btn_borrar;
    private JButton btn_mover;
    private JButton btn_editar;
    private JButton btn_iniciar;
    private JButton btn_siguiente;

    private Controlador controlador;

    public GUI(Controlador controlador) {
        Utils.LogI("Iniciar Ventana");
        iniciarVentana();

        Utils.LogI("Iniciar Elementos");
        iniciarElementos();

        Utils.LogI("Iniciar acoplar Elementos");
        acoplarElementos();

        marco_ventana.setVisible(true);
        this.controlador = controlador;
    }

    private void acoplarElementos(){
        JPanel panel_botones = new JPanel(new GridLayout(1, 4));
        panel_canvas = new PanelGrafos();

        panel_botones.add(btn_nuevo_vertice);
        panel_botones.add(btn_nuevo_arista);
        panel_botones.add(btn_editar);
        panel_botones.add(btn_borrar);
        panel_botones.add(btn_mover);
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
        btn_mover = new JButton(titulo_btn_mover);
        btn_editar = new JButton(titulo_btn_editar);
        btn_iniciar = new JButton(titulo_btn_iniciar);
        btn_siguiente = new JButton(titulo_btn_siguiente);

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

        btn_mover.addActionListener((ActionEvent e) -> {
            Utils.LogD("Click boton mover");
            clickBotonMover();
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



    }

    // - // - // - // - // - // - // - // - // - //

    private void clickBotonNuevoVertice(){
        VerticeObservable v = controlador.nuevoVertice();
        panel_canvas.añadirDibujable(v);
    }

    private void clickBotonNuevoArista(){
        ArrayList<AristaObservable> a = controlador.nuevaArista();
        a.forEach(panel_canvas::añadirDibujable);
    }

    private void clickBotonBorrar(){
        controlador.borrar();
    }

    private void clickBotonMover(){
        panel_canvas.mover();
    }

    private void clickBotonEditar(){
        controlador.debug();
        controlador.editar();
    }

    private void clickBotonSiguiente(){}

    private void clickBotonIniciar(){
        controlador.resolver();
    }

}
