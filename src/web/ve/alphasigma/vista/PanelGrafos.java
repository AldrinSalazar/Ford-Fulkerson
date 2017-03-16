/*
 * PanelGrafos.java       01/04/2017
 * Copyright (C) 2017  Aldrin Salazar
 * https://github.com/AldrinSalazar/Ford-Fulkerson
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
package web.ve.alphasigma.vista;

import web.ve.alphasigma.Utils;
import web.ve.alphasigma.controlador.VerticeObservable;
import web.ve.alphasigma.modelo.Vertice;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PanelGrafos extends JPanel
        implements MouseMotionListener, MouseListener, KeyListener{
    private enum Estado{
        NUEVO_VERTICE,
        NADA,
        SELECCION,
        MOVIENDO
    }

    private final List<Dibujable> elementos;
    private Estado estado;

    private Dibujable tmp;
    private Point movInicio;
    public final List<Dibujable> seleccion;


    public PanelGrafos() {
        elementos = new ArrayList<>();
        seleccion = new ArrayList<>();
        estado = Estado.NADA;
        setBackground(Color.GRAY);
        setFocusable(true);
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
    }

    public void añadirDibujableExistente(Dibujable d){
        elementos.add(d);
    }

    public void añadirDibujable(Dibujable d){
        if(d == null)
            return;

        elementos.add(d);

        if(d instanceof Vertice) {
            tmp = d;
            estado = Estado.NUEVO_VERTICE;
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }

        repaint();
    }

    public void quitarDibujable(Dibujable d){
        elementos.remove(d);
        repaint();
    }

    public void mover(){
        if(seleccion.size() > 0){
            estado = Estado.MOVIENDO;
            setCursor(new Cursor(Cursor.MOVE_CURSOR));
        }
    }

    public void eliminarTodo(){
        elementos.clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        elementos.forEach((Dibujable d) -> d.dibujar(g2));
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(estado == Estado.NUEVO_VERTICE){
            tmp.setPosicion(e.getPoint());
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(estado == Estado.NUEVO_VERTICE){
            estadoNuevoVertice(e);
            repaint();
        }else if(estado == Estado.SELECCION){
            estadoSeleccion(e);
            repaint();
            return;
        }

        estado = Estado.NADA;
        this.grabFocus();
        setCursor(Cursor.getDefaultCursor());
    }

    private void estadoNuevoVertice(MouseEvent e){
        tmp.setPosicion(e.getPoint());
        Vertice.Tipo tipo = (Vertice.Tipo) JOptionPane.showInputDialog(null, "Tipo de Vertice:",
                "Seleccionar tipo de Vertice",
                JOptionPane.QUESTION_MESSAGE,
                null,
                Vertice.Tipo.values(),
                Vertice.Tipo.values()[2]);
        ((Vertice)tmp).setTipo(tipo);
        tmp = null;
    }

    private void estadoSeleccion(MouseEvent e){
        int min = Integer.MAX_VALUE;
        int indice = -1;

        for(int i = 0; i < elementos.size(); i++){
            int dist = (int)elementos.get(i).getPosicion().distance(e.getPoint());
            if(dist < min && dist <= VerticeObservable.DIAMETRO_DIBUJO+5){
                min = dist;
                indice = i;
            }
        }

        if(indice != -1){
            Utils.LogD("Indice: "+indice);
            boolean prev = elementos.get(indice).estaSeleccionado();
            elementos.get(indice).seleccionar();

            if(prev){
                seleccion.remove(elementos.get(indice));
            }else{
                seleccion.add(elementos.get(indice));
            }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(estado == Estado.MOVIENDO){
            System.out.println("setPoint");
            movInicio = e.getPoint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(estado == Estado.MOVIENDO){
            estado = Estado.NADA;
            seleccion.forEach(Dibujable::seleccionar);
            seleccion.clear();
            this.grabFocus();
            setCursor(Cursor.getDefaultCursor());
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(estado == Estado.MOVIENDO) {
            seleccion.forEach((Dibujable d) -> {
                int diffX = e.getX() - movInicio.x;
                int diffY = e.getY() - movInicio.y;
                d.setPosicion(new Point(d.getPosicion().x + diffX, d.getPosicion().y + diffY));
            });
            movInicio = e.getPoint();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Keyevent press");
        if(estado == Estado.NADA && e.getKeyCode() == KeyEvent.VK_S){
            estado = Estado.SELECCION;
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else if(estado == Estado.SELECCION && e.getKeyCode() == KeyEvent.VK_S){
            estado = Estado.NADA;
            setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
